package com.example.processor.rq;

import com.example.bpm.model.ProcessRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@NoArgsConstructor
public class StartOpenAccountRequest implements ProcessRequest {
    @JsonProperty("fio")
    private String fio;
    @JsonProperty("vip")
    private boolean vip;



}
