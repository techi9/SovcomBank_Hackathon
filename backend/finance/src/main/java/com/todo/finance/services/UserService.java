package com.todo.finance.services;

import com.todo.finance.model.User;
import com.todo.finance.registration.token.ConfirmationTokenService;
import com.todo.finance.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {//implements UserDetailsService {
//    private final static String USER_NOT_FOUND_MSG =
//            "user with email %s not found";
//
//    private final UserRepository appUserRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final ConfirmationTokenService confirmationTokenService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }
//
//    public String signUpUser(User appUser) {
//        User userExists = appUserRepository
//                .findByCredentials_PhoneNumber(appUser.getCredentials().getPhoneNumber());
//
//
//        String encodedPassword = bCryptPasswordEncoder
//                .encode(appUser.getPassport().getPassportNumber());
//
//        appUser.setPassword(encodedPassword);
//
//        appUserRepository.save(appUser);
//
//        String token = UUID.randomUUID().toString();
//
//        ConfirmationToken confirmationToken = ConfirmationToken(
//                token,
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(15),
//                appUser
//        );
//
//        confirmationTokenService.saveConfirmationToken(
//                confirmationToken);
//
////        TODO: SEND EMAIL
//
//        return token;
//    }
//
//    public int enableAppUser(String email) {
//        return appUserRepository.enableAppUser(email);
//    }
}
