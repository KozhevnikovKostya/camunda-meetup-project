package com.example.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.example.processor.ProcessConstants.VIP_STATUS;

@Service
@RequiredArgsConstructor
public class ProcessInstanceService {

    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;

    public String startProcess(String key, boolean vip){
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(key)
                .latestVersion()
                .singleResult();
        return runtimeService.createProcessInstanceById(definition.getId())
                .setVariable(VIP_STATUS, vip)
                .execute().getProcessInstanceId();
    }
}
