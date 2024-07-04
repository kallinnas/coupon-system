package com.system.coupon.service;

import com.system.coupon.data.ex.NoSuchCompanyException;
import com.system.coupon.data.ex.NoSuchCouponException;
import com.system.coupon.data.ex.NoSuchCustomerException;
import com.system.coupon.data.ex.UnknownRoleForUserException;
import com.system.coupon.data.model.Company;
import com.system.coupon.data.model.Coupon;
import com.system.coupon.data.model.Customer;
import com.system.coupon.data.model.User;
import com.system.coupon.service.ex.UserIsAlreadyExistException;
import com.system.coupon.service.ex.UserIsNotExistException;

import java.time.LocalDate;
import java.util.List;

public interface AdminService {

    //Company
    Company getCompanyById(long id);
    Company getCompanyByName(String name);
    List<Company> getAllCompanies();

    // UPDATE FOR COMPANY
    void updateCompany(Company company);
    void deleteCompanyById(long id) throws UserIsNotExistException;

    //Customer
    Customer getCustomerById(long id);
    Customer getCustomerByFirstName(String firstName);
    Customer getCustomerByLastName(String lastName);
    List<Customer> getAllCustomers();
//    List<Customer> getCustomersByCouponId(long coupon_id);

    //Coupon
    Coupon getCouponById(long id);
    List<Coupon> getAllCoupons();
    List<Coupon> getCouponsByTitle(String title);
    List<Coupon> getCouponsByCategory(int category);
    List<Coupon> getCouponsByDescription(String description);

    List<Coupon> getCouponsBeforeEndDate(LocalDate endDate);
    List<Coupon> getCouponsBeforeStartDate(LocalDate startDate);

    List<Coupon> getCouponsByCompanyId(long company_id);
    List<Coupon> getCustomerCouponsById(long customer_id);
    List<Coupon> getCouponsBelowPrice(double price);
    List<Coupon> getCouponsAbovePrice(double price);


    //Coupon
    Coupon createCoupon(Coupon coupon, long company_id);
    Coupon updateCoupon(Coupon coupon) throws NoSuchCouponException;
    void deleteCoupon(long id) throws NoSuchCouponException;


    Customer updateCustomer(Customer customer) throws NoSuchCustomerException;
    void  deleteCustomersCoupon(long customerId, long couponId);
    void deleteCustomerById(long id);
}
