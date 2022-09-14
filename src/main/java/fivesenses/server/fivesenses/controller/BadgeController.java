package fivesenses.server.fivesenses.controller;

import fivesenses.server.fivesenses.dto.*;
import fivesenses.server.fivesenses.entity.Badge;
import fivesenses.server.fivesenses.entity.UserBadge;
import fivesenses.server.fivesenses.service.BadgeService;
import fivesenses.server.fivesenses.service.UserBadgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BadgeController {

    private final BadgeService badgeService;
    private final UserBadgeService userBadgeService;


    //============= Badge =============//
    @GetMapping("/badges/{id}")
    public ResponseEntity<Result<BadgeResponseDto>> getBadge(@PathVariable String id){
        Badge badge = badgeService.findById(id);

        Result<BadgeResponseDto> result = new Result<>(new Meta(HttpStatus.OK.value()), new BadgeResponseDto(badge));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/badges")
    public ResponseEntity<?> createBadges(@RequestParam("data") MultipartFile[] files, @RequestParam String dirName){
        badgeService.createBadge(files, dirName);

        Result<?> result = new Result<>(new Meta(HttpStatus.OK.value()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/badges/{id}")
    public ResponseEntity<?> updateBadge(@RequestBody BadgeRequestDto badgeRequestDto){
        badgeService.updateBadge(badgeRequestDto);

        Result<?> result = new Result<>(new Meta(HttpStatus.OK.value()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/badges/{id}")
    public ResponseEntity<?> deleteBadge(@PathVariable String id) {
        badgeService.deleteBadge(id);

        Result<?> result = new Result<>(new Meta(HttpStatus.OK.value()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //============= UserBadge =============//
    @GetMapping("users/{userId}/badges")
    public ResponseEntity<Result<List<BadgeResponseDto>>> getUserBadgesByUser(@PathVariable Long userId){
        List<BadgeResponseDto> dtos = userBadgeService.findBadgeListByUserId(userId).stream()
                .map(BadgeResponseDto::new)
                .collect(Collectors.toList());

        Result<List<BadgeResponseDto>> result = new Result<>(new Meta(HttpStatus.OK.value()), dtos);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("users/{userId}/badges/{id}")
    public ResponseEntity<Result<UserBadgeResponseDto>> getUserBadge(@PathVariable Long userId, @PathVariable Long id){
        UserBadge userBadge = userBadgeService.findById(id);

        Result<UserBadgeResponseDto> result = new Result<>(new Meta(HttpStatus.OK.value()), new UserBadgeResponseDto(userBadge));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("users/{userId}/badges")
    public ResponseEntity<?> createUserBadge(@RequestBody UserBadgeRequestDto dto){
        userBadgeService.createUserBadge(dto);

        Result<?> result = new Result<>(new Meta(HttpStatus.OK.value()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("users/{userId}/badges/{id}")
    public ResponseEntity<?> deleteUserBadge(@PathVariable Long userId, @PathVariable Long id) {
        userBadgeService.deleteUserBadge(id);

        Result<?> result = new Result<>(new Meta(HttpStatus.OK.value()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
