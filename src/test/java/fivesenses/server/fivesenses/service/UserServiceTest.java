package fivesenses.server.fivesenses.service;

import fivesenses.server.fivesenses.dto.ChangePwDto;
import fivesenses.server.fivesenses.dto.CreateUserDto;
import fivesenses.server.fivesenses.entity.Authority;
import fivesenses.server.fivesenses.entity.User;
import fivesenses.server.fivesenses.jwt.SecurityUtil;
import fivesenses.server.fivesenses.repository.AuthorityRepository;
import fivesenses.server.fivesenses.repository.UserAuthorityRepository;
import fivesenses.server.fivesenses.repository.UserRepository;
import fivesenses.server.fivesenses.repository.UserTempRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    @Spy
    private UserService userService;

    @Mock
    private MailService mailService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthorityRepository authorityRepository;
    @Mock
    private UserAuthorityRepository userAuthorityRepository;
    @Mock
    private UserTempRepository userTempRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @MockBean
    private SecurityUtil securityUtil;

    @Test
    void createUser_정상() {
        //given
        User user = User.builder()
                .email("email@e.com")
                .build();
        CreateUserDto createUserDto = mock(CreateUserDto.class);
        given(createUserDto.toEntityExceptId()).willReturn(user);
        given(passwordEncoder.encode(any())).willReturn("newPw");
        given(userRepository.save(any())).willReturn(user);
        given(userRepository.existsByEmail(any())).willReturn(false);
        given(authorityRepository.findById(any())).willReturn(Optional.of(new Authority("ROLE_USER")));

        //when
        userService.createUser(createUserDto);

        //then
        then(userRepository).should(times(1)).save(any());
    }

    @Test
    void createUser_이메일_중복() {
        //given
        User user = User.builder()
                .email("email@e.com")
                .build();
        CreateUserDto createUserDto = mock(CreateUserDto.class);
        given(createUserDto.toEntityExceptId()).willReturn(user);
        given(userRepository.existsByEmail(any())).willReturn(true);

        //when
        //then
        assertThatThrownBy(() -> userService.createUser(createUserDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("이미 사용중인 이메일입니다.");
        then(userRepository).should(times(0)).save(any());
    }

    @Test
    void createUser_이메일_없음() {
        //given
        User user = User.builder()
                .build();
        CreateUserDto createUserDto = mock(CreateUserDto.class);
        given(createUserDto.toEntityExceptId()).willReturn(user);
        given(passwordEncoder.encode(any())).willReturn("newPw");

        //when
        //then
        assertThatThrownBy(() -> userService.createUser(createUserDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이메일은 반드시 포함되어야합니다.");
        then(userRepository).should(times(0)).save(any());
    }

    @Test
    void changePw_정상() {
        //given
        User user = User.builder()
                .email("email@e.com")
                .build();
        ChangePwDto changePwDto = new ChangePwDto("ogPw", "newPw");
        doReturn(user).when(userService).findUserFromToken();
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        given(passwordEncoder.encode(any())).willReturn("newPw");

        //when
        userService.changePw(changePwDto);

        //then
        then(userService).should(times(1)).changePw(any());
        assertThat(user.getPassword()).isEqualTo(changePwDto.getNewPw());
    }

    @Test
    void changePw_기존비밀번호_틀림() {
        //given
        User user = User.builder()
                .email("email@e.com")
                .build();
        ChangePwDto changePwDto = new ChangePwDto("ogPw", "newPw");
        doReturn(user).when(userService).findUserFromToken();
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        //when
        //then
        assertThatThrownBy(() -> userService.changePw(changePwDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("기존 비밀번호가 일치하지 않습니다.");
    }
}