package com.koral.webKoral.repo;

import com.koral.webKoral.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
     ApplicationUser findByUsername(String username);
    List<ApplicationUser> findAll();
    @Transactional
    void deleteById(Long id);
}
