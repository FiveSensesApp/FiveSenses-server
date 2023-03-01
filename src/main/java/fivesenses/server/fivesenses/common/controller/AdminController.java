package fivesenses.server.fivesenses.common.controller;

import fivesenses.server.fivesenses.common.service.AdminUserService;
import fivesenses.server.fivesenses.common.service.MailService;
import fivesenses.server.fivesenses.domain.badge.service.BadgeService;
import fivesenses.server.fivesenses.domain.badge.service.UserBadgeService;
import fivesenses.server.fivesenses.domain.user.service.UserService;
import fivesenses.server.fivesenses.common.dto.Meta;
import fivesenses.server.fivesenses.common.dto.Result;
import fivesenses.server.fivesenses.domain.badge.repository.UserBadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminUserService adminUserService;

    private final MailService mailService;
    private final UserBadgeService userBadgeService;
    private final BadgeService badgeService;
    private final UserService userService;

    private final UserBadgeRepository userBadgeRepository;

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        adminUserService.deleteUser(id);

        Result<?> result = new Result<>(new Meta(HttpStatus.OK.value()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
