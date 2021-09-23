package com.example.bpm.context;

import java.util.Map;


/**
 * Контекст исполнения activity
 */
public interface ExecutionContext {
    /**
     * Получить идентификатор инстанса процесса
     *
     * @return идентификатор инстанса процесса
     */
    String getProcessInstanceId();



    /**
     * Получить переменную процесса
     *
     * @param name имя переменой
     * @return значение переменной
     */
    Object getVariable(String name);

    /**
     * Установить переменную процесса
     *
     * @param name  имя
     * @param value знчение
     */
    void setVariable(String name, Object value);

    /**
     * Удалить переменную из процесса
     *
     * @param name имя переменой
     * @return значение переменной
     */
    Object removeVariable(String name);

    /**
     * Установить переменные процесса.
     * В процессе останутся только переменные, переданные в параметре
     *
     * @param data переменные
     */
    void setVariables(Map<String, Object> data);

}
