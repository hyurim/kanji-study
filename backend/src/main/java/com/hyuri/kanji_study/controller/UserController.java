package com.hyuri.kanji_study.controller;

import com.hyuri.kanji_study.dto.UserDto;
import com.hyuri.kanji_study.service.KanjiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final KanjiService kanjiService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody UserDto userDto) {

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if (userDto.getRole() == null || userDto.getRole().isBlank()) {
            userDto.setRole("USER");
        }

        UserDto created = kanjiService.join(userDto);

        return ResponseEntity.ok(created);
    }
}