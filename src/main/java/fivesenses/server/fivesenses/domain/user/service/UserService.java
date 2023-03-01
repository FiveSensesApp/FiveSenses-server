package fivesenses.server.fivesenses.domain.user.service;

import fivesenses.server.fivesenses.common.jwt.SecurityUtil;
import fivesenses.server.fivesenses.common.service.MailService;
import fivesenses.server.fivesenses.domain.user.dto.ChangePwDto;
import fivesenses.server.fivesenses.domain.user.dto.CreateUserDto;
import fivesenses.server.fivesenses.domain.user.dto.UpdateUserDto;
import fivesenses.server.fivesenses.domain.user.entity.Authority;
import fivesenses.server.fivesenses.domain.user.entity.User;
import fivesenses.server.fivesenses.domain.user.entity.UserAuthority;
import fivesenses.server.fivesenses.domain.user.entity.UserTemp;
import fivesenses.server.fivesenses.domain.user.repository.AuthorityRepository;
import fivesenses.server.fivesenses.domain.user.repository.UserAuthorityRepository;
import fivesenses.server.fivesenses.domain.user.repository.UserRepository;
import fivesenses.server.fivesenses.domain.user.repository.UserTempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final MailService mailService;

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final UserTempRepository userTempRepository;

    private final PasswordEncoder passwordEncoder;

    static final int RANDOM_PW_MAX = 9999999;
    static final int RANDOM_PW_MIN = 1000000;
    static final int RANDOM_VALID_MAX = 999;
    static final int RANDOM_VALID_MIN = 100;

    public User findUserFromToken() {
        Long currentUserId = SecurityUtil.getCurrentUserId();
        return findById(currentUserId);
    }

    @Transactional
    public Long createUser(CreateUserDto createUserDto) {
        User user = createUserDto.toEntityExceptId();
        user.changePw(passwordEncoder.encode(user.getPassword()));

        validateDuplicateUser(user.getEmail());
        userRepository.save(user);

//        Authority role = authorityRepository.findById("ROLE_NEED_EMAIL").get();
        Authority role = authorityRepository.findById("ROLE_USER").get();

        UserAuthority userAuthority = UserAuthority.builder()
                .authority(role)
                .user(user)
                .build();
        userAuthorityRepository.save(userAuthority);

        return user.getId();
    }

    public void validateDuplicateUser(String email) {
        if (email == null)
            throw new IllegalArgumentException("이메일은 반드시 포함되어야합니다.");

        if (userRepository.existsByEmail(email))
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
    }

    @Transactional
    public void updateUser(UpdateUserDto updateUserDto) {
        User user = findById(updateUserDto.getUserId());
        user.update(updateUserDto);
    }

    @Transactional
    public void changePw(ChangePwDto changePwDto) {
        if (changePwDto.getNewPw() == null || changePwDto.getOgPw() == null)
            throw new IllegalArgumentException("비밀번호를 입력하였는지 확인해주세요.");

        User user = this.findUserFromToken();

        if (!passwordEncoder.matches(changePwDto.getOgPw(), user.getPassword()))
            throw new IllegalStateException("기존 비밀번호가 일치하지 않습니다.");

        user.changePw(passwordEncoder.encode(changePwDto.getNewPw()));
    }

    @Transactional
    public void lostPassword(String userEmail) {
        //generate random password
        String randomPw = generateRandomPw();

        //send mail
        mailService.lostPw(userEmail, randomPw);

        //change user's pw to new random password
        User user = findUserByEmail(userEmail);
        user.changePw(passwordEncoder.encode(randomPw));
    }

    @Transactional
    public void validateEmail(String email) {
        UserTemp userTemp = userTempRepository.findByEmail(email);

        if (userTemp == null)
            userTemp = userTempRepository.save(new UserTemp(email));

        String randomValidCode = generateRandomValidCode();
        userTemp.changeEmailValidCode(randomValidCode);

        //send mail
        mailService.validateEmail(email, randomValidCode);
    }

    @Transactional
    public void validateEmailSendCode(String email, String emailValidCode) {
        UserTemp userTemp = userTempRepository.findByEmail(email);
        if (userTemp == null)
            throw new EntityNotFoundException("존재하지 않는 임시 인증 정보입니다.");

        if (!userTemp.getEmailValidCode().equals(emailValidCode))
            throw new IllegalStateException("잘못된 인증 코드를 입력했습니다.");

        userTempRepository.delete(userTemp);
    }

    private String generateRandomPw() {
        int randomNum = (int) ((Math.random() * (RANDOM_PW_MAX - RANDOM_PW_MIN)) + RANDOM_PW_MIN);
        return String.valueOf(randomNum);
    }

    private String generateRandomValidCode() {
        int randomNum = (int) ((Math.random() * (RANDOM_VALID_MAX - RANDOM_VALID_MIN)) + RANDOM_VALID_MIN);
        return String.valueOf(randomNum);
    }

}
