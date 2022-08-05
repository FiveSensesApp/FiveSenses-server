package fivesenses.server.fivesenses.controller;


import fivesenses.server.fivesenses.dto.*;
import fivesenses.server.fivesenses.entity.User;
import fivesenses.server.fivesenses.jwt.JwtFilter;
import fivesenses.server.fivesenses.jwt.TokenProvider;
import fivesenses.server.fivesenses.service.UserService;
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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Result<UserResponseDto>> createUser(@RequestBody UserRequestDto userRequestDto,
                                                              UriComponentsBuilder b) {
        User user = userRequestDto.toEntityExceptId();
        Long userId = userService.createUser(user);

        UriComponents uriComponents =
                b.path("/users/{userId}").buildAndExpand(userId);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());

        Result<UserResponseDto> result = new Result<>(new Meta(HttpStatus.CREATED.value()), new UserResponseDto(user));
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<Result<TokenDto>> authorize(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        Result<TokenDto> result = new Result<>(new Meta(HttpStatus.OK.value()), new TokenDto(jwt));
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }
}
