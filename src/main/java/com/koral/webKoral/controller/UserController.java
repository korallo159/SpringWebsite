package com.koral.webKoral.controller;

import com.koral.webKoral.model.ApplicationUser;
import com.koral.webKoral.model.Token;
import com.koral.webKoral.repo.ApplicationUserRepository;
import com.koral.webKoral.repo.TokenRepo;
import com.koral.webKoral.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class UserController {
    private TokenRepo tokenRepo;
    private ApplicationUserRepository applicationUserRepository;
    private UserService userService;

    public UserController(TokenRepo tokenRepo, UserService userService, ApplicationUserRepository applicationUserRepository) {
        this.tokenRepo = tokenRepo;
        this.userService = userService;
        this.applicationUserRepository = applicationUserRepository;
    }

    @GetMapping("/token")
    public String confirm(@RequestParam String value) {

        try {
            Token byTokenValue = tokenRepo.findByValue(value).get();

            ApplicationUser applicationUser = byTokenValue.getApplicationUser();
            applicationUser.setEnabled(true);
            applicationUserRepository.save(applicationUser);
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }
    }

}
