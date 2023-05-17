package ru.kradin.blog.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
    Class for converting BindingResult to Map
 */
public class FieldErrorsUtil {

    //return Map<field name, List<error messages>>
    public static Map<String, List<String>> getErrors(BindingResult bindingResult) {
        Map<String, List<String>> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            List<String> fieldErrors = errors.get(fieldName);
            if (fieldErrors == null) {
                fieldErrors = new ArrayList<>();
                errors.put(fieldName, fieldErrors);
            }
            fieldErrors.add(errorMessage);
        }
        return errors;
    }
}
