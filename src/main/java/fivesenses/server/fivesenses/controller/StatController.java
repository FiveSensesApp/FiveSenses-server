package fivesenses.server.fivesenses.controller;

import fivesenses.server.fivesenses.dto.BadgeResponseDto;
import fivesenses.server.fivesenses.dto.Meta;
import fivesenses.server.fivesenses.dto.Result;
import fivesenses.server.fivesenses.dto.StatResponseDto;
import fivesenses.server.fivesenses.entity.Badge;
import fivesenses.server.fivesenses.service.BadgeService;
import fivesenses.server.fivesenses.service.StatViewService;
import fivesenses.server.fivesenses.service.UserBadgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StatController {

    private final BadgeService badgeService;
    private final UserBadgeService userBadgeService;
    private final StatViewService statViewService;


    //============= Badge =============//
    @GetMapping("/stats")
    public ResponseEntity<Result<StatResponseDto>> getStatByUserId(@RequestParam Long userId) {
        StatResponseDto statResponseDto = statViewService.getStatViewByUser(userId);

        Result<StatResponseDto> result = new Result<>(new Meta(HttpStatus.OK.value()), statResponseDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
