package fivesenses.server.fivesenses;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "7nd ver";
    }

    @GetMapping("/health")
    public String health(){
        return "health check";
    }

    @GetMapping("/test2")
    public String root(){
        return "test2";
    }
}
