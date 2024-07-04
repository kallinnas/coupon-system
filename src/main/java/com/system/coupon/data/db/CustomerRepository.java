package com.system.coupon.data.db;

import com.system.coupon.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByFirstName(String firstName);
    Optional<Customer> findByLastName(String lastName);
//    List<Customer> findAllCustomersByCoupons(long coupon_id);

}
