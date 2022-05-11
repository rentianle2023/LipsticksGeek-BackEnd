package fun.tianlefirstweb.www.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public ResponseEntity<String> login(){
        return ResponseEntity.ok("登录成功");
    }
}
