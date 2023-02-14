package rosa.felipe.pdi.studies.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringSecurityControllerTest {


    @GetMapping("/test-no-jwt")
    public String test() {
        return "test-no-jwt";
    }

    @GetMapping("/test-jwt")
    public String testJwt() {
        return "test-jwt";
    }
}
