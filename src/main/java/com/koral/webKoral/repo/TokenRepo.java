package com.koral.webKoral.repo;

import com.koral.webKoral.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findByValue(String value);
    @Transactional
    void deleteByApplicationUserId(Long id);
}
