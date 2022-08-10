package fivesenses.server.fivesenses.controller;

import fivesenses.server.fivesenses.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.mail.MessagingException;
import java.io.IOException;

@ApiIgnore
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {


    private final MailService mailService;
//
//    @PostMapping("/lost-pw2")
//    public String lostPassword2(@RequestParam String userEmail) throws MessagingException, IOException {
//        String s = mailService.lostPw2(userEmail,"1234");
//        return s;
//    }
}
