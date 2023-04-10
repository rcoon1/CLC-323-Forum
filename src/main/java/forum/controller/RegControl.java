package forum.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import forum.model.User;
import forum.repos.AuthorityRepository;
import forum.repos.UserRepository;

@Controller
public class RegControl {
    private final UserRepository userRep;
    private final AuthorityRepository authRep;
    private final PasswordEncoder encoder;

    public RegControl(UserRepository userRep, AuthorityRepository authRep, PasswordEncoder encoder) {
        this.userRep = userRep;
        this.authRep = authRep;
        this.encoder = encoder;
    }

    @GetMapping("/reg")
    public String reg() {
        return "reg";
    }

    @PostMapping("/reg")
    public String save(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authRep.findByAuthority("ROLE_USER"));
        userRep.save(user);
        return "redirect:/login";
    }
}
