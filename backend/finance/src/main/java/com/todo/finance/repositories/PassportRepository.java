package com.todo.finance.repositories;

import com.todo.finance.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PassportRepository extends JpaRepository<Passport, Integer> {

}
