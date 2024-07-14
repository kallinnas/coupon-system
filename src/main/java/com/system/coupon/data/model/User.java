package com.system.coupon.data.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.system.coupon.data.ex.UnknownRoleForUserException;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne()
    @JoinColumn(name = "client_id")
    private Client client;

    public User(String email, String password, int role) throws UnknownRoleForUserException {
        this.email = email;
        this.password = password;
        this.id = 0;

        if (role == 1) {
            this.client = new Customer();
            this.client.setId(0);
        } else if (role == 2) {
            this.client = new Company();
            this.client.setId(0);
        } else
            throw new UnknownRoleForUserException(String
                    .format("To create user-customer set a role as - 1, for user-company -2. Unknown role %d.", role));
    }

}
