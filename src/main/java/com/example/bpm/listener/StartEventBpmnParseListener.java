package com.example.bpm.listener;

import com.example.bpm.StartProcessRequestAdapterExecutor;
import com.example.bpm.behavior.StartProcessBehavior;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;
import org.springframework.stereotype.Service;


import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_ERROR_EVENT_DEFINITION;

/**
 * Парсер описаний StartEvent, добавляющий специфичную логику в обработку activity.
 */
@Service
public class StartEventBpmnParseListener extends BpmnParseListenerBase {
    private final StartProcessRequestAdapterExecutor requestAdapterExecutor;

    /**
     * Конструктор
     *
     * @param requestAdapterExecutor исполнитель обработчиков событий старта процесса/завершения задачи
     */
    public StartEventBpmnParseListener(
            StartProcessRequestAdapterExecutor requestAdapterExecutor
    ) {
        this.requestAdapterExecutor = requestAdapterExecutor;
    }

    @Override
    public void parseStartEvent(Element startEventElement, ScopeImpl scope, ActivityImpl startEventActivity) {
        Element element = startEventElement.element(BPMN_ELEMENT_ERROR_EVENT_DEFINITION);
        if (element != null) {
            return;
        }
        startEventActivity.setActivityBehavior(new StartProcessBehavior(
                requestAdapterExecutor,
                getRequestAdapterClass(startEventElement)
        ));
    }



}
