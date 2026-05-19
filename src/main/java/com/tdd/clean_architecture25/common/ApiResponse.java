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
public class ApiResponse<T> {

    @JsonProperty("error_schema")
    private ErrorSchema errorSchema;

    @JsonProperty("output_schema")
    private T outputSchema;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .errorSchema(ErrorSchema.builder()
                        .errorCode("CC-00-000")
                        .errorMessage(ErrorMessage.builder()
                                .english("Success")
                                .indonesian("Success")
                                .build())
                        .build())
                .outputSchema(data)
                .build();
    }

    public static <T> ApiResponse<T> error(String errorCode, String englishMessage, String indonesianMessage) {
        return ApiResponse.<T>builder()
                .errorSchema(ErrorSchema.builder()
                        .errorCode(errorCode)
                        .errorMessage(ErrorMessage.builder()
                                .english(englishMessage)
                                .indonesian(indonesianMessage)
                                .build())
                        .build())
                .outputSchema(null)
                .build();
    }
}
