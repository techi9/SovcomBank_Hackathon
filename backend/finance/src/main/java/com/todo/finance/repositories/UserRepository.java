package com.todo.finance.repositories;

import com.todo.finance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByCredentials_PhoneNumber(String phoneNumber);
}
