package com.example.bpm.listener;

import com.example.bpm.ActionRequestAdapter;
import com.example.bpm.exceprion.InvalidProcessDefinitionException;
import com.example.bpm.model.ProcessRequest;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.util.ClassDelegateUtil;
import org.camunda.bpm.engine.impl.util.xml.Element;

import java.util.List;

import static java.lang.String.format;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_ID;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_NAME;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_VALUE;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_EXTENSION_ELEMENTS;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_PROPERTY;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.CAMUNDA_ELEMENT_PROPERTIES;

class BpmnParseListenerBase extends AbstractBpmnParseListener {

    //Имя нашей проперти из json
    public static final String BPM_ACTION_REQUEST_ADAPTER_CLASS_PROPERTY_NAME = "bpm-action-request-adapter-class";

    private static final String MULTIPLE_PROPERTIES_ERROR = "Элемент %s с id %s содержит несколько свойств %s.";

    protected static final String INVALID_CLASS_IMPLEMENTATION = "Класс %s должен реализовывать интерфейс %s(%s[id=%s]).";

    // получаем значение нужной нам проперти элемента в процессе из XML
    protected static String getExtensionProperty(Element element, String name) {
        Element extensionElements = element.element(BPMN_ELEMENT_EXTENSION_ELEMENTS);
        if (extensionElements == null) {
            return null;

        }
        Element properties = extensionElements.element(CAMUNDA_ELEMENT_PROPERTIES);
        if (properties == null) {
            return null;
        }
        List<Element> propertyList = properties.elements(BPMN_ELEMENT_PROPERTY);
        if (propertyList == null) {
            return null;
        }

        String value = null;
        for (Element property : propertyList) {
            if (name.equals(property.attribute(BPMN_ATTRIBUTE_NAME))) {
                if (value != null) {

                    throw new InvalidProcessDefinitionException(format(MULTIPLE_PROPERTIES_ERROR, element.getTagName(), element.attribute(BPMN_ATTRIBUTE_ID), name));
                }
                value = property.attribute(BPMN_ATTRIBUTE_VALUE);
            }
        }
        if (value == null || value.length() == 0) {
            return null;
        }
        return value;
    }

    // Получить из элемента xml класс обработчика
    static ActionRequestAdapter<ProcessRequest> getRequestAdapterClass(Element element) {
        String actionRequestAdapterClassName = getExtensionProperty(element, BPM_ACTION_REQUEST_ADAPTER_CLASS_PROPERTY_NAME);
        if (actionRequestAdapterClassName == null) {
            return null;
        }
        Object actionRequestAdapterClass = ClassDelegateUtil.instantiateDelegate(actionRequestAdapterClassName, null);
        if (actionRequestAdapterClass instanceof ActionRequestAdapter) {
            return (ActionRequestAdapter<ProcessRequest>) actionRequestAdapterClass;
        }
        throw new InvalidProcessDefinitionException(format(INVALID_CLASS_IMPLEMENTATION, actionRequestAdapterClassName, ActionRequestAdapter.class.getName(), element.getTagName(), element.attribute("id")));
    }


}
