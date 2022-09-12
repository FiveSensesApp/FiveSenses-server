package fivesenses.server.fivesenses.controller;

import fivesenses.server.fivesenses.dto.*;
import fivesenses.server.fivesenses.entity.Post;
import fivesenses.server.fivesenses.entity.User;
import fivesenses.server.fivesenses.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<Result<UserResponseDto>> getUserInfo(@PathVariable Long userId) {
        User user = userService.findById(userId);

        Result<UserResponseDto> result = new Result<>(new Meta(HttpStatus.OK.value()), new UserResponseDto(user));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUser(updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate-duplicate")
    public ResponseEntity<?> validateDuplicate(@RequestBody UserValidationDto userValidationDto) {
        userService.validateDuplicateUser(userValidationDto.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-pw")
    public ResponseEntity<?> changePassword(@RequestBody ChangePwDto changePwDto) {
        userService.changePassword(changePwDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/lost-pw")
    public ResponseEntity<?> lostPassword(@RequestParam String userEmail) {
        userService.lostPassword(userEmail);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate-email")
    public ResponseEntity<?> validateEmail(@RequestParam String email) {
        userService.validateEmail(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate-email-send-code")
    public ResponseEntity<?> validateEmailSendCode(@RequestParam String email, @RequestParam String emailCode) {
        userService.validateEmailSendCode(email, emailCode);
        return ResponseEntity.noContent().build();
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
//        User user = userService.findUser(id);
//    }

    @Data
    static class UserValidationDto {
        String email;
    }
}
