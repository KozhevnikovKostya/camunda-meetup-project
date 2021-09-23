package com.example.bpm;

import com.example.bpm.context.ExecutionContext;
import com.example.bpm.model.ProcessRequest;
import org.springframework.context.ApplicationContext;

/**
 * Интрефейс кастомного обработчика событий старта процесса и завершения задачи.
 * Обработчики событий должны быть зарегистрированы в {@link ApplicationContext}
 * <p>
 * Если требуется в случае ошибок вернуть некий объект, то можно использовать следующий подход:
 * <pre>
 *    &#064;Component
 *    <b>&#064;ControllerAdvice</b>
 *    public class CustomErrorResponseActionRequestAdapter implements ActionRequestAdapter&lt;Void&gt; {
 *       static final SomeObject ERROR_OBJECT = new SomeObject("some-error", true);
 *
 *       &#064;Override
 *       public void process(ExecutionContext executionContext, Void requestObject) throws SameException {
 *           throw new SameException(ERROR_OBJECT);
 *       }
 *
 *       <b>&#064;ExceptionHandler
 *       ResponseEntity&lt;SomeObject&gt; handleException(final SameException exception) {
 *           return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getObject());
 *       }</b>
 *    }
 *
 * </pre>
 *
 * @param <Request> Тип запроса пользователя.
 *                      Если пользовательский запрос не должен содержать данных, используйте ActionRequestAdapter&lt;Void&gt;
 */
public interface ActionRequestAdapter<Request extends ProcessRequest> {
    /**
     * Выполнить обработку запроса пользователя.
     * Если метод выбрасывает исключение, то активити, к которой привязан класс, не выполняется.
     *
     * @param executionContext - контекст исполнения
     * @param requestObject    - десериализованный запрос пользователя.
     * @throws Exception -
     */
    void process(ExecutionContext executionContext, Request requestObject) throws Exception;
}
