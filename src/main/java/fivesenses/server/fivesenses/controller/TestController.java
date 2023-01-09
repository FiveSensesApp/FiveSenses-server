package fivesenses.server.fivesenses.controller;

import fivesenses.server.fivesenses.entity.RefreshToken;
import fivesenses.server.fivesenses.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final RefreshTokenRepository refreshTokenRepository;

    @GetMapping("/test-1")
    @Transactional
    public String test() {

        RefreshToken refreshToken = RefreshToken.builder()
                .userIdKey(1L)
                .tokenValue("refreshString")
                .expiration(100)
                .build();

        refreshTokenRepository.save(refreshToken);


        return "test";
    }

    @GetMapping("/test-2")
    @Transactional
    public Object test2() {

        return refreshTokenRepository.findById(1L);
    }
}