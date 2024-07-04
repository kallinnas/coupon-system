package com.system.coupon.data.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import com.system.coupon.data.ex.UnknownRoleForUserException;
import org.hibernate.annotations.*;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    @Column(name = "password")
    private String password;

    //        @Any
//        @AnyDiscriminator(DiscriminatorType.STRING)
//        @AnyDiscriminatorValue(discriminator = "1", entity = Customer.class)
//        @AnyDiscriminatorValue(discriminator = "2", entity = Company.class)
//        @AnyKeyJavaClass(Long.class)
//        @JoinColumn(name = "client_id")
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public User() { /*Empty*/ }

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
