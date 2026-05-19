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
public class ErrorMessage {

    @JsonProperty("english")
    private String english;

    @JsonProperty("indonesian")
    private String indonesian;
}
