package com.koral.webKoral.service;

import com.koral.webKoral.WebSecurityConfig;
import com.koral.webKoral.model.ApplicationUser;
import com.koral.webKoral.model.Token;
import com.koral.webKoral.repo.ApplicationUserRepository;
import com.koral.webKoral.repo.TokenRepo;
import org.springframework.stereotype.Controller;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.UUID;

@Controller
public class UserService {

    private ApplicationUserRepository applicationUserRepository;
    private WebSecurityConfig webSecurityConfig;
    private TokenRepo tokenRepo;
    private MailService mailService;


    public UserService(ApplicationUserRepository applicationUserRepository, WebSecurityConfig webSecurityConfig, TokenRepo tokenRepo, MailService mailService) {
        this.applicationUserRepository = applicationUserRepository;
        this.webSecurityConfig = webSecurityConfig;
        this.tokenRepo = tokenRepo;
        this.mailService = mailService;
    }

    public void addUser(String username, String password, String email, String role) {
        ApplicationUser applicationUser = new ApplicationUser(username, webSecurityConfig.passwordEncoder().encode(password), email, role);
        this.applicationUserRepository.save(applicationUser);
        sendToken(applicationUser);
    }


    private void sendToken(ApplicationUser applicationUser){
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(tokenValue);
        token.setApplicationUser(applicationUser);
        tokenRepo.save(token);
        String url = "http://localhost:8080/token?value=" + tokenValue;
        try {
            mailService.sendMail(applicationUser.getEmail(), "Potwierdz adres email!", url, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        }
}
