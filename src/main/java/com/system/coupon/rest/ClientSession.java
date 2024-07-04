package com.system.coupon.rest;

import com.system.coupon.service.AdminService;
import com.system.coupon.service.CompanyService;
import com.system.coupon.service.CustomerService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ClientSession {

    private long lastAccessedMillis;
    private AdminService adminService;
    private CustomerService customerService;
    private CompanyService companyService;
    private int role;

    public CustomerService getCustomerService() {
        return customerService;
    }
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public AdminService getAdminService() {
        return adminService;
    }
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public long getLastAccessedMillis() {
        return lastAccessedMillis;
    }
    public void accessed() {
        this.lastAccessedMillis = System.currentTimeMillis();
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
