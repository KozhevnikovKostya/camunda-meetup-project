package com.example.bpm;

import com.example.bpm.model.ProcessRequest;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Исполнитель обработчиков событий старта процесса
 */
@Service
public class StartProcessRequestAdapterExecutor extends RequestAdapterExecutor {
    /**
     * Конструктор
     *
     * @param requestMappingHandlerAdapter   RequestMappingHandlerAdapter
     */
    public StartProcessRequestAdapterExecutor(
            RequestMappingHandlerAdapter requestMappingHandlerAdapter
    ) {
        super(requestMappingHandlerAdapter);
    }

    @Override
    public void execute(ActivityExecution execution, ActionRequestAdapter<? super ProcessRequest> actionRequestAdapter) throws Exception {
        if (actionRequestAdapter == null) {
            return;
        }
        ProcessRequest requestObject = getRequestObject(actionRequestAdapter);
            executeOperation(actionRequestAdapter, execution, requestObject);
            return;
        }

}
