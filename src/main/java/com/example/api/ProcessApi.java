package com.example.api;

import com.example.service.ProcessDefinitionService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/processes")
@RequiredArgsConstructor
public class ProcessApi {

    private final ProcessDefinitionService processDefinitionService;

    @GetMapping("/definition/all")
    public ResponseEntity<List<String>> getAllProcess(){
        return ResponseEntity.ok(processDefinitionService.getProcessDefinitions());
    }

    @DeleteMapping("/definition/delete/{id}")
    public ResponseEntity<String>deleteDefinition(@PathVariable String id){
        return ResponseEntity.ok(processDefinitionService.deleteProcessDefinition(id));
    }

}
