package com.system.coupon.data.db;

import com.system.coupon.data.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);

//    Company findCompanyByCouponsContains(long id);

}
