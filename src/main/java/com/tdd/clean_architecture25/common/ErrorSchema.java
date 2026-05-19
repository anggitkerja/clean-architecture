package com.tdd.clean_architecture25.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorSchema {

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_message")
    private ErrorMessage errorMessage;
}
