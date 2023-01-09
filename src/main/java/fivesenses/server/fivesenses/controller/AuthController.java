package fivesenses.server.fivesenses.controller;


import fivesenses.server.fivesenses.dto.*;
import fivesenses.server.fivesenses.entity.User;
import fivesenses.server.fivesenses.jwt.JwtFilter;
import fivesenses.server.fivesenses.jwt.TokenProvider;
import fivesenses.server.fivesenses.service.UserService;
import fivesenses.server.fivesenses.service.auth.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Result<UserResponseDto>> signUp(@RequestBody CreateUserDto createUserDto) {
        Long userId = userService.createUser(createUserDto);
        User user = userService.findById(userId);

        Result<UserResponseDto> result = new Result<>(new Meta(HttpStatus.CREATED.value()), new UserResponseDto(user));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    //구버전 대응용. 나중에 클라 업뎃 확인 후 지우기
    @Data
    static class LegacyTokenDto{
        String token;
    }

    @PostMapping("/signin")
    public ResponseEntity<Result<LegacyTokenDto>> signIn(@Valid @RequestBody SigninDto signinDto) {
        String token = authService.signInLegacy(signinDto);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);

        LegacyTokenDto legacyTokenDto = new LegacyTokenDto();
        legacyTokenDto.setToken(token);

        Result<LegacyTokenDto> result = new Result<>(new Meta(HttpStatus.OK.value()), legacyTokenDto);
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }

    //신버전. signin으로 고치기
    @PostMapping("/login")
    public ResponseEntity<Result<TokenDto>> login(@Valid @RequestBody SigninDto signinDto) {
        TokenDto tokenDto = authService.signIn(signinDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDto.getAccessToken());

        Result<TokenDto> result = new Result<>(new Meta(HttpStatus.OK.value()), tokenDto);
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<Result<TokenDto>> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto tokenDto = authService.reissue(tokenRequestDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDto.getAccessToken());

        Result<TokenDto> result = new Result<>(new Meta(HttpStatus.OK.value()), tokenDto);
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }
}
