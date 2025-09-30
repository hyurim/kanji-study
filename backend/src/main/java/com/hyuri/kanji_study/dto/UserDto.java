package com.hyuri.kanji_study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String loginId;
    private String nickname;
    private Integer streakDays;
    private String role;
    private LocalDateTime createdAt;
}
