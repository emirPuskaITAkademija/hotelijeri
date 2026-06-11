package com.academy.hotelijeri.user;

import com.academy.hotelijeri.user.privilege.Privilege;
import com.academy.hotelijeri.user.privilege.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//1. DispatcherServlet -> HTTP GET /register -> showRegisterPage -> register.html
@Controller
public class UserController {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";//view login.html
    }


    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        List<Privilege> privileges = privilegeRepository.findAll();
        model.addAttribute("privileges", privileges);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);//create i update u zavisnosti od vrijednosti polja ID
        return "redirect:/login";
    }
}
