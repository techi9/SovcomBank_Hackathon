package com.todo.finance.controllers;

import com.todo.finance.enumerations.AccountStatus;
import com.todo.finance.enumerations.Role;
import com.todo.finance.enumerations.UserStatus;
import com.todo.finance.model.Account;
import com.todo.finance.model.Credentials;
import com.todo.finance.model.Passport;
import com.todo.finance.model.User;
import com.todo.finance.repositories.AccountRepository;
import com.todo.finance.repositories.CredentialsRepository;
import com.todo.finance.repositories.PassportRepository;
import com.todo.finance.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Autowired
    CredentialsRepository credentialsRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PassportRepository passportRepository;

    @GetMapping("/check")
    public List<User> check() throws ParseException {
        log.info(String.valueOf(credentialsRepository.findAll().size()));

        Credentials credentials = credentialsRepository.save(Credentials.builder()
                .email("tqdhthq@mail.ru")
                .phoneNumber("89312068978")
                .build()
        );
        Passport passport = passportRepository.save(Passport.builder()
                .name("newname")
                        .surname("surnamhdfmme")
                        .dateOfBirth(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse("2011-01-18 00:00:00.0"))
                        .middleName("midllle")
                        .passportNumber("7894561230")
                        .build());

        log.warn(String.valueOf(passportRepository.findAll().size()));
//        userRepository.save(User.builder()
//                .status(UserStatus.ACTIVE)
//                .role(Role.ADMIN)
//                .passport(passport)
//                .credentials(credentials)
//                .transactions(new HashSet<>())
//                .build());
        //accountRepository.save(new Account(1, "USD", "1234567890", AccountStatus.ACTIVE, 1000, 1));

        return userRepository.findAll();
    }

}
