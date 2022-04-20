package fun.tianlefirstweb.www.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/github/callback")
    public String helloGithubOauth2(@RequestParam String code){
        return code;
    }

    @GetMapping("/github/redirect")
    public String helloGithubOauth2ridirect(@RequestParam String code){
        return "redirect";
    }
}
