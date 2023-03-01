package fivesenses.server.fivesenses.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("refreshToken")
public class RefreshToken {

    @Id
    private Long userIdKey;

    private String tokenValue;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Integer expiration;

    public RefreshToken updateValue(String tokenValue) {
        this.tokenValue = tokenValue;
        return this;
    }
}