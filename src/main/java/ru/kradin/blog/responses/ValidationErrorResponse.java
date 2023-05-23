package ru.kradin.blog.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel(description = "Validation error response")
public class ValidationErrorResponse {
    @ApiModelProperty(value = "Map of field names and error messages")
    private Map<String, List<String>> errors;

    public ValidationErrorResponse(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
