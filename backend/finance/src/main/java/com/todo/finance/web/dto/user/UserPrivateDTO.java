package com.todo.finance.web.dto.user;


import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPrivateDTO {
    private String name;
    private String surname;
    private String middleName;
}
