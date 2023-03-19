package com.converter;

import com.dto.UserDto;
import com.entity.User;
import org.springframework.stereotype.Component;

@Component

public class Converter {
    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
    }
}
