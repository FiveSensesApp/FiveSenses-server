package fivesenses.server.fivesenses.service;

import fivesenses.server.fivesenses.dto.Meta;
import fivesenses.server.fivesenses.dto.Result;
import fivesenses.server.fivesenses.dto.UserResponseDto;
import fivesenses.server.fivesenses.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StatService {

    private final PostService postService;
    private final UserService userService;

//    public StatRes
}
