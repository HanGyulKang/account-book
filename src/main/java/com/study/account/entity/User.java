package com.study.account.entity;

import com.study.account.common.enums.UserRole;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user", indexes = @Index(name = "index_user_username", columnList = "username"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String username;
    private String password;
    private String token;
    @Enumerated(EnumType.STRING)
    private UserRole roles;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<AccountBook> accountBooks = new ArrayList<>();

    public List<String> getRoleList() {
        if(this.roles.name().length() > 0) {
            return Arrays.asList(this.roles.name().split(","));
        }

        return new ArrayList<>();
    }

    public void saveUserToken(String token) {
        this.token = token;
    }
}
