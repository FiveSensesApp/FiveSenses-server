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

    @PostMapping("/badges/special")
    public ResponseEntity<?> createSpecialBadge(@RequestBody Map<String, List<String>> userEmailList) {
        Badge badge = badgeService.findById("16_덕분에 출시.svg");

        for(String userEmail : userEmailList.get("emails")){
            if(userBadgeRepository.existsByBadge(badge)) continue;

            User user = userService.findUserByEmail(userEmail);
            userBadgeService.createUserBadge(user, badge);
        }

        Result<?> result = new Result<>(new Meta(HttpStatus.OK.value()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/badges/tumbl")
    public TumblBadgeDto giveTumblbugBadge(){

        String str = "ad_design_0810@naver.com,1618wlsdk@naver.com,k034kr010@naver.com,hi0602199@gmail.com,ekgml6231@naver.com,agny_@naver.com,xdylim@gmail.com,dudgns0145@naver.com,ys961002@gmail.com,jungsanghee6410@gmail.com,bjyi0603@naver.com,23ohye@naver.com,dmstj9808@naver.com,bin6736@naver.com,kmjj0410@naver.com,934740114@naver.com,ddo0401@naver.com,shtnals9911@naver.com,lilac0214@daum.net,inthesky0827@naver.com,wnsgur6524@kakao.com,eeeasycode@gmail.com,kimhs997@naver.com,aan224@naver.com,omh0427@naver.com,cranit@naver.com,spyho16@naver.com,wuc2952@gmail.com,charm020@kakao.com,hollowlife0613@nate.com,dlddu9702@gmail.com,skfkantu@naver.com,agmee0516@naver.com,pouyznoq22@naver.com,suju20460@gmail.com,dg04140@gmail.com,123lhy@naver.com,yyon121@naver.com,lr_breakup7@naver.com,ghcho999@naver.com,eni001024@naver.com,jisu1233@naver.com,uiuiui118437@gmail.com,zmxkf123@naver.com,rlatkddnjs04@naver.com,ysyjkm3@naver.com,eu13gene@naver.com,yero1412@naver.com,jiheon7140@gmail.com,ddh00ddh@gmail.com,allgyshine@naver.com,jiso__o@naver.com,cutejudy7@naver.com,solitdo@gmail.com";
        String[] str2 = str.split(",");

        TumblBadgeDto dto = new TumblBadgeDto();
        for(String email : str2){
            User user;
            try{
                user = userService.findUserByEmail(email);

                Badge badge = badgeService.findById("16_덕분에 출시.svg");

                boolean isPresent = userBadgeRepository.existsByBadgeAndUser(badge, user);
                if(isPresent) continue;

                createUserBadge(user, badge);
                dto.getInserted().add(email);

            }catch(EntityNotFoundException e){
                dto.getNotInserted().add(email);
            }
        }
        return dto;
    }

    @Transactional
    public Long createUserBadge(User user, Badge badge) {
        UserBadge userBadge = UserBadge.builder()
                .user(user)
                .badge(badge)
                .build();

        return userBadgeRepository.save(userBadge).getId();
    }


    @Data
    class TumblBadgeDto {
        List<String> inserted = new ArrayList<>();
        List<String> notInserted = new ArrayList<>();;
    }

}
