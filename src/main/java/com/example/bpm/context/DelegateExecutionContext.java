package com.example.bpm.context;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import java.util.HashMap;
import java.util.Map;


/**
 * Контекст исполнения на основе DelegateExecution
 */
public class DelegateExecutionContext implements ExecutionContext {
    private final DelegateExecution execution;

    public static final String SYSTEM_VARIABLE_KEY_PREFIX = "$";


    /**
     * Конструктор
     *
     * @param execution DelegateExecution
     */
    public DelegateExecutionContext(DelegateExecution execution) {
        this.execution = execution;
    }

    @Override
    public String getProcessInstanceId() {
        return execution.getProcessInstanceId();
    }

    @Override
    public Object getVariable(String name) {
        assertNotSystemVariable(name);
        return execution.getVariable(name);
    }

    @Override
    public void setVariable(String name, Object value) {
        assertNotSystemVariable(name);
        execution.setVariable(name, value);
    }

    @Override
    public Object removeVariable(String name) {
        Object variable = getVariable(name);
        execution.removeVariable(name);
        return variable;
    }

    @Override
    public void setVariables(Map<String, Object> data) {
        if (data == null) {
            data = new HashMap<>();
        }
        for (String name : data.keySet()) {
            assertNotSystemVariable(name);
        }
        execution.removeVariables(getVariables().keySet());
        execution.setVariables(data);
    }


    public Map<String, Object> getVariables() {
        return filterPublicVariables(execution.getVariables());
    }

    /**
     * Метод для проверки, является ли переменная системной
     *
     * @param key ключ переменной
     */
    public static void assertNotSystemVariable(String key) {
        if (isSystemVariable(key)) {
            throw new IllegalArgumentException("Переменные, начинающиеся с " + SYSTEM_VARIABLE_KEY_PREFIX + ", зарезервированы.");

        }
    }

    public static boolean isSystemVariable(String key) {
        return key.startsWith(SYSTEM_VARIABLE_KEY_PREFIX);
    }

    public static Map<String, Object> filterPublicVariables(Map<String, Object> input) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> variable : input.entrySet()) {
            if (!isSystemVariable(variable.getKey())) {
                result.put(variable.getKey(), variable.getValue());
            }
        }
        return result;
    }

}
