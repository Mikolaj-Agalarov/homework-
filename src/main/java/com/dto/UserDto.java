package com.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Value;

@Value
public class UserDto {
    @JsonIgnore
    private Long id;
    private String username;
    private String password;
    private String email;

}
