package com.truelayer.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private int status;

    private String error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> messages;
}
