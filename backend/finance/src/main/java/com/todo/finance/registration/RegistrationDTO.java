package com.todo.finance.registration;

import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private Date dateOfBirth;
    private String passportNumber;
    private String phoneNumber;
    private final String email;
    private final String password;
}
