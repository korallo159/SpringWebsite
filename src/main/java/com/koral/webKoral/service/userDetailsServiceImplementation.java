package com.koral.webKoral.service;

import com.koral.webKoral.model.ApplicationUser;
import com.koral.webKoral.repo.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class userDetailsServiceImplementation implements UserDetailsService {

    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    public userDetailsServiceImplementation(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepository.findByUsername(username);
        if(user != null)
        return applicationUserRepository.findByUsername(username);
        else
        throw new UsernameNotFoundException("User not exist with name :" +username);

    }

}
