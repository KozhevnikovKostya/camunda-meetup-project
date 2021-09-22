package com.example.api;

import com.example.service.ProcessInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instance")
@RequiredArgsConstructor
public class ProcessManipulateApi {

    private final ProcessInstanceService processInstanceService;

    @PostMapping("/{id}/start")
    public ResponseEntity<String> startProcess(@PathVariable String id, @RequestParam(defaultValue = "false") boolean vip){
        return ResponseEntity.ok(processInstanceService.startProcess(id, vip));
    }

}
