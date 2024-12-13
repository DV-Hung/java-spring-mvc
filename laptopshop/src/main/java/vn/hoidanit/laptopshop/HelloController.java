package vn.hoidanit.laptopshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String index() {
        return "Hello world hungdz";
    }

    @GetMapping("/user")
    public String userPage() {
        return "Hello world user";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "Hello world admin";
    }
}
