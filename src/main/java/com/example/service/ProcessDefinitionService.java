package com.example.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ResourceDefinition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.util.Pair.toMap;

@Service
@RequiredArgsConstructor
public class ProcessDefinitionService {

    private final RepositoryService repositoryService;

    public List<String> getProcessDefinitions(){
        return repositoryService.createProcessDefinitionQuery().latestVersion().list().stream()
                .map(ResourceDefinition::getId)
                .collect(Collectors.toList());
    }

    public String deleteProcessDefinition(String id){
        repositoryService.deleteProcessDefinition(id);
        return id;
    }
}
