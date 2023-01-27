package fivesenses.server.fivesenses.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fivesenses.server.fivesenses.dto.SigninDto;
import fivesenses.server.fivesenses.dto.TokenDto;
import fivesenses.server.fivesenses.dto.TokenRequestDto;
import fivesenses.server.fivesenses.entity.RefreshToken;
import fivesenses.server.fivesenses.jwt.TokenProvider;
import fivesenses.server.fivesenses.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.HashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private static final Integer REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenDto signIn(SigninDto signinDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signinDto.getEmail(), signinDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .userIdKey(Long.parseLong(authentication.getName()))
                .tokenValue(tokenDto.getRefreshToken())
                .expiration(REFRESH_TOKEN_EXPIRE_TIME)
                .build();

        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new BadCredentialsException("Refresh Token 이 유효하지 않습니다.");
       }

        HashMap<String, String> payloadMap = getPayloadByToken(tokenRequestDto.getAccessToken());
        String userId = payloadMap.get("sub");
        String authorities = payloadMap.get("auth");

        RefreshToken refreshToken = refreshTokenRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new BadCredentialsException("Refresh Token 이 유효하지 않습니다."));

        if (!refreshToken.getTokenValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new BadCredentialsException("Refresh Token 이 유효하지 않습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(userId, authorities);

        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDto;
    }

    private HashMap<String, String> getPayloadByToken(String token) {
        try {
            String[] splitJwt = token.split("\\.");

            Base64.Decoder decoder = Base64.getDecoder();
            String payload = new String(decoder.decode(splitJwt[1] .getBytes()));

            return new ObjectMapper().readValue(payload, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("JWT 페이로드 파싱 실패");
        }
    }

    //구버전 대응용. 지워야함
    @Transactional
    public String signInLegacy(SigninDto signinDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signinDto.getEmail(), signinDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateTokenDtoLegacy(authentication);
    }

    //테스트용. 1분짜리 토큰 반환
    @Transactional
    public String signInTest(SigninDto signinDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signinDto.getEmail(), signinDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateTokenDtoTest(authentication);
    }
}
