package com.koral.webKoral.Repo;

import com.koral.webKoral.User.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
    List<ApplicationUser> findAll();
    void deleteById(Long id);
}
