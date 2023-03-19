package com.controller;

import com.converter.Converter;
import com.dto.CreateUserDto;
import com.dto.UserDto;
import com.entity.User;
import com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        final List<User> users = userService.findAll();
        List<UserDto> usersDto = users.stream().map(Converter::toUserDto).collect(Collectors.toList());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam("file") final MultipartFile file) {
        return ResponseEntity.ok(file.getName());
    }
}
