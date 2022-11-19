package com.todo.finance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todo.finance.enumerations.Role;
import com.todo.finance.enumerations.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private UserStatus status;
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "passports_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Passport passport;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "credentials_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Credentials credentials;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            joinColumns = { @JoinColumn(name = "users_id") },
            inverseJoinColumns = { @JoinColumn(name = "transactions_id") }
    )
    Set<Transaction> transactions = new HashSet<>();


}
