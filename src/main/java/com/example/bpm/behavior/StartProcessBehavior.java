package com.example.bpm.behavior;

import com.example.bpm.ActionRequestAdapter;
import com.example.bpm.RequestAdapterExecutor;
import com.example.bpm.StartProcessRequestAdapterExecutor;
import com.example.bpm.model.ProcessRequest;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;



/**
 * Обработчик старта процесса
 */

public class StartProcessBehavior extends StartProcessBehaviorBase {
    private static final String SUPER_PROCESS_INFO = "super-process-info";

    private final RequestAdapterExecutor requestAdapterExecutor;
    private final ActionRequestAdapter<? super ProcessRequest> actionRequestAdapter;

    /**
     * конструктор
     *
     * @param requestAdapterExecutor адаптер для исполнения пользовательского класса
     * @param actionRequestAdapter   кастомный обработчик событий старта процесса
     */
    public StartProcessBehavior(
            StartProcessRequestAdapterExecutor requestAdapterExecutor,
            ActionRequestAdapter<? super ProcessRequest> actionRequestAdapter
    ) {

        this.requestAdapterExecutor = requestAdapterExecutor;
        this.actionRequestAdapter = actionRequestAdapter;
    }

    @Override
    protected void preExecute(ExecutionEntity execution) throws Exception {
        String processDefinitionKey = execution.getProcessDefinition().getKey();
        execution.setVariable(SUPER_PROCESS_INFO, processDefinitionKey);

        requestAdapterExecutor.execute(execution, actionRequestAdapter);
    }
}
