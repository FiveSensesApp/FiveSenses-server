package fivesenses.server.fivesenses.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<?> health(){
        return ResponseEntity.ok().build();
    }
}
