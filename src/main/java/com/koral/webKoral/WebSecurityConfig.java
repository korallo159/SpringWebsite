package com.koral.webKoral;

import com.koral.webKoral.repo.ApplicationUserRepository;
import com.koral.webKoral.service.userDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
    public void configure(WebSecurity web){
        web.ignoring().antMatchers(
                // Vaadin Flow static resources //
                "/VAADIN/**",
                // the standard favicon URI
                "/favicon.ico",
                // the robots exclusion standard
                "/robots.txt",
                // web application manifest //
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",
                // (development mode) static resources //
                "/frontend/**",
                // (development mode) webjars //
                "/webjars/**",
                // (production mode) static resources //
                "/frontend-es5/**", "/frontend-es6/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/databasemanager").hasRole("ADMIN")
                .and()
                .formLogin().loginPage("/login").permitAll()
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
