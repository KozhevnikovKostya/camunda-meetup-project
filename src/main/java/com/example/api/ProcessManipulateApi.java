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

    @PostMapping("/{key}/start")
    public ResponseEntity<String> startProcess(@PathVariable String key, @RequestParam(defaultValue = "false") boolean vip){
        return ResponseEntity.ok(processInstanceService.startProcess(key, vip));
    }

}
