package com.hyuri.kanji_study.controller;

import com.hyuri.kanji_study.dto.AuthenticationDto.AuthRequest;
import com.hyuri.kanji_study.dto.AuthenticationDto.AuthResponse;
import com.hyuri.kanji_study.repository.UserRepository;
import com.hyuri.kanji_study.security.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthApi {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest,
                                   HttpServletResponse response
    ) {
        log.info("토큰 : {}", authRequest);

        try {
            // 암호화된 비밀번호와 입력된 비밀번호 비교
            log.info("exists : {}", userRepository.existsByLoginId(authRequest.loginId()));

            // 아이디, 비밀번호 맞는지 확인 (틀리면 예외, 맞으면 반환)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.loginId(), authRequest.password())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT 생성
            String token = jwtUtil.generateToken(authRequest.loginId());
            log.debug("생성된 JWT 토큰: {}", token);

            // JWT 반환
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            log.error("로그인 처리 중 오류", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("아이디 또는 비밀번호가 일치하지 않습니다.");
        } catch (Exception e) {
            log.error("로그인 처리 중 오류", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("로그인 처리 중 오류가 발생했습니다.");
        }
    }
}
