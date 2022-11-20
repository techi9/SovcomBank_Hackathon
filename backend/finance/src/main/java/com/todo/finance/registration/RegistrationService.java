package com.todo.finance.registration;

import com.todo.finance.enumerations.Role;
import com.todo.finance.enumerations.UserStatus;
import com.todo.finance.model.Passport;
import com.todo.finance.model.User;
import com.todo.finance.registration.token.ConfirmationToken;
import com.todo.finance.registration.token.ConfirmationTokenService;
import com.todo.finance.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationDTO request) {
        Passport passport = Passport.builder()
                .passportNumber(request.getPassportNumber())
                .name(request.getFirstName())
                .surname(request.getLastName())
                .middleName(request.getMiddleName())
                .dateOfBirth(request.getDateOfBirth())
                .build();

//        return userService.signUpUser(
//                User.builder()
//                        .passport(passport)
//                        .role(Role.USER)
//                        .status(UserStatus.PENDING);
        return null;


    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        return "confirmed";
    }

}
