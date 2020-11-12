package com.koral.webKoral.repo;

import com.koral.webKoral.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
     Optional<ApplicationUser> findByUsername(String username);
    List<ApplicationUser> findAll();
    void deleteById(Long id);
}
