package com.hyuri.kanji_study.controller;

import com.hyuri.kanji_study.dto.AuthenticationDto.AuthRequest;
import com.hyuri.kanji_study.dto.AuthenticationDto.AuthResponse;
import com.hyuri.kanji_study.repository.UserRepository;
import com.hyuri.kanji_study.security.JwtUtil;
import com.hyuri.kanji_study.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthApi {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

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
            String accessToken = jwtUtil.generateToken(authRequest.loginId());
            log.debug("생성된 JWT 토큰: {}", accessToken);
            String refreshToken = jwtUtil.generateRefreshToken(authRequest.loginId());
            log.debug("생성된 Refresh Token 토큰: {}", refreshToken);
            // JWT 생성

            // Refresh Token
            refreshTokenService.save(authRequest.loginId(), refreshToken, jwtUtil.getRefreshExpirationMs());

            // HttpOnly 쿠키로 refresh 토큰 전달
            ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .sameSite("None")
                    .maxAge(Duration.ofMillis(jwtUtil.getRefreshExpirationMs()))
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            // JWT 반환
            return ResponseEntity.ok(new AuthResponse(accessToken));
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
    /** Access 토큰 재발급 */
    @PostMapping("/token/refresh")
    public ResponseEntity<?> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refresh token이 없습니다.");
        }

        try {
            String userId = jwtUtil.extractUsername(refreshToken);
            String saved = refreshTokenService.get(userId);
            if (saved == null || !saved.equals(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refresh token이 유효하지 않습니다.");
            }

            String newAccess = jwtUtil.generateToken(userId);
            // 고민 해볼 내용 - 리프레시 토큰 회전 전략(새 refresh 발급 + Redis 갱신 + 쿠키 재세팅)

            return ResponseEntity.ok(Map.of("accessToken", newAccess));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refresh token이 유효하지 않습니다.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "refreshToken", required = false) String refreshToken,
                                    HttpServletResponse response) {
        if (refreshToken != null && !refreshToken.isBlank()) {
            try {
                String userId = jwtUtil.extractUsername(refreshToken);
                refreshTokenService.delete(userId);
            } catch (Exception ignore) {}
        }
        // 쿠키 제거
        ResponseCookie expired = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, expired.toString());

        return ResponseEntity.ok("Logged out");
    }

}
