package com.todo.finance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passports")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Length(max = 20)
    private String name;
    @Length(max = 20)
    private String middleName;
    @Length(max = 20)
    private String surname;
    @Length(min = 10, max = 10)
    private String passportNumber;
    @Min(14)
    private Date dateOfBirth;
}
