package com.study.account.entity;

import com.study.account.common.enums.UserRole;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole roles;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<AccountBook> accountBooks = new ArrayList<>();

    public List<String> getRoleList() {
        if(this.roles.name().length() > 0) {
            return Arrays.asList(this.roles.name().split(","));
        }

        return new ArrayList<>();
    }
}
