package fivesenses.server.fivesenses.controller;

import fivesenses.server.fivesenses.dto.Meta;
import fivesenses.server.fivesenses.dto.Result;
import fivesenses.server.fivesenses.entity.Badge;
import fivesenses.server.fivesenses.entity.User;
import fivesenses.server.fivesenses.entity.UserBadge;
import fivesenses.server.fivesenses.repository.UserBadgeRepository;
import fivesenses.server.fivesenses.service.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
