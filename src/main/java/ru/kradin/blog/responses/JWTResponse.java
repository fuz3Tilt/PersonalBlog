package ru.kradin.blog.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "JWT response")
public class JWTResponse {

    @ApiModelProperty(value = "JWT token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYzMzU0ODk4MiwiZXhwIjoxNjMzNTUyNTgyfQ.7wbK2OuQD4Nf4qGZd1Qz5XJWblsFvXqJNYt9O6VhNcU")
    private String token;

    public JWTResponse(String token) {
        this.token = token;
    }

    public String getToken(){
        return token;
    }
}
