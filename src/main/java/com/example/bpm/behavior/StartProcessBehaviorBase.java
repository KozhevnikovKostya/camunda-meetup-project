package com.example.bpm.behavior;

import org.camunda.bpm.engine.impl.bpmn.behavior.FlowNodeActivityBehavior;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;

import java.time.OffsetDateTime;


/**
 * Базовый обработчик старта процесса
 */
public abstract class StartProcessBehaviorBase extends FlowNodeActivityBehavior {

    public static final String PROCESS_DEFINITION_KEY = "$process-definition-key";
    public static final String START_PROCESS_DATE = "$start-process-date";


    @Override
    public void execute(ActivityExecution execution) throws Exception {
        ExecutionEntity executionEntity = (ExecutionEntity) execution;

        String processDefinitionKey = executionEntity.getProcessDefinition().getKey();
        execution.setVariable(PROCESS_DEFINITION_KEY, processDefinitionKey);
        execution.setVariable(START_PROCESS_DATE, OffsetDateTime.now().toString());
        preExecute(executionEntity);

        super.execute(execution);
    }

    protected abstract void preExecute(ExecutionEntity executionEntity) throws Exception;
}
