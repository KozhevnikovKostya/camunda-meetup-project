package com.example.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class ProcessTaskService {

    private final TaskService taskService;

    public void completeUserTask(String id){
        taskService.complete(id);
    }
}
