package com.koral.webKoral;

import com.koral.webKoral.Repo.ApplicationUserRepository;
import com.koral.webKoral.User.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private userDetailsServiceImplementation userDetailsService;
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    public WebSecurityConfig(userDetailsServiceImplementation userDetailsService, ApplicationUserRepository applicationUserRepository){
        this.userDetailsService = userDetailsService;
        this.applicationUserRepository = applicationUserRepository;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.inMemoryAuthentication().withUser(new User("Koral", passwordEncoder().encode("koral123"), Collections.singleton(new SimpleGrantedAuthority("user"))));
        auth.userDetailsService(userDetailsService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/databasemanager").hasRole("ADMIN")
                .and()
                .formLogin().permitAll()
        .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

   /* @EventListener(ApplicationReadyEvent.class)
    public void get() {
        ApplicationUser appUserUser = new ApplicationUser("koraluser", passwordEncoder().encode("koral123"), "ROLE_USER");
        ApplicationUser appUserAdmin = new ApplicationUser("koral", passwordEncoder().encode("koral123"), "ROLE_ADMIN");
        applicationUserRepository.save(appUserUser);
        applicationUserRepository.save(appUserAdmin);
    }*/


}
