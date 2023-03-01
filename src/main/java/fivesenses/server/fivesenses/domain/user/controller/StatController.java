package fivesenses.server.fivesenses.domain.user.controller;

import fivesenses.server.fivesenses.common.dto.Meta;
import fivesenses.server.fivesenses.common.dto.Result;
import fivesenses.server.fivesenses.domain.user.dto.StatResponseDto;
import fivesenses.server.fivesenses.domain.user.service.StatViewService;
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

    private final StatViewService statViewService;


    //============= Badge =============//
    @GetMapping("/stats")
    public ResponseEntity<Result<StatResponseDto>> getStatByUserId(@RequestParam Long userId) {
        StatResponseDto statResponseDto = statViewService.getStatViewByUser(userId);

        Result<StatResponseDto> result = new Result<>(new Meta(HttpStatus.OK.value()), statResponseDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
