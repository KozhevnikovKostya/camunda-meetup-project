package com.example.bpm;

import com.example.bpm.context.DelegateExecutionContext;
import com.example.bpm.model.ProcessRequest;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Исполнитель обработчиков событий старта процесса
 */
public abstract class RequestAdapterExecutor {
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    public RequestAdapterExecutor(
            RequestMappingHandlerAdapter requestMappingHandlerAdapter
    ) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }

    /**
     * Обработать запрос
     *
     * @param execution            активи, в рамках которой происходит обработка запроса
     * @param actionRequestAdapter кастомный обработчик событий старта процесса или завершения задачи.
     * @throws Exception при ошибках
     */
    public abstract void execute(ActivityExecution execution, ActionRequestAdapter<? super ProcessRequest> actionRequestAdapter) throws Exception;

    protected ProcessRequest getRequestObject(ActionRequestAdapter<? super ProcessRequest> actionRequestAdapter) throws HttpMediaTypeNotSupportedException, IOException {
        if (actionRequestAdapter == null) {
            throw new IllegalArgumentException("Невозможно определить тип объекта запроса без переданного ActionRequestAdapter");
        }
        Class<?> requestClass = ResolvableType.forInstance(actionRequestAdapter).as(ActionRequestAdapter.class).resolveGeneric();

        HttpInputMessage inputMessage = getCurrentHttpInputMessage();
        MediaType contentType = inputMessage.getHeaders().getContentType();
        if (contentType == null) {
            throw new HttpMediaTypeNotSupportedException("No Content-Type found");
        }

        List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter.getMessageConverters();
        List<MediaType> allSupportedMediaTypes = new ArrayList<>();
        if (messageConverters != null) {
            for (HttpMessageConverter<?> messageConverter : messageConverters) {
                allSupportedMediaTypes.addAll(messageConverter.getSupportedMediaTypes());
                if (messageConverter.canRead(requestClass, contentType)) {
                    return (ProcessRequest) messageConverter.read((Class) requestClass, inputMessage);
                }
            }
        }
        throw new HttpMediaTypeNotSupportedException(contentType, allSupportedMediaTypes);
    }

    private static HttpInputMessage getCurrentHttpInputMessage() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        return new ServletServerHttpRequest(request);
    }

    protected void executeOperation(ActionRequestAdapter<? super ProcessRequest> actionRequestAdapter, ActivityExecution execution, ProcessRequest requestObject) throws Exception {
        try {
            actionRequestAdapter.process(new DelegateExecutionContext(execution), requestObject);
        } catch (Exception e) {
            throw e;
        }
    }

}
