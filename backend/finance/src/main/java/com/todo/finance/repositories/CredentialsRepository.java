package com.todo.finance.repositories;

import com.todo.finance.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {
}
