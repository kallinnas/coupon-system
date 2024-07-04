package com.system.coupon.rest;

import com.system.coupon.data.db.CompanyRepository;
import com.system.coupon.data.db.CouponRepository;
import com.system.coupon.data.db.UserRepository;
import com.system.coupon.data.ex.NoSuchCompanyException;
import com.system.coupon.data.ex.NoSuchCouponException;
import com.system.coupon.data.model.Coupon;
import com.system.coupon.data.model.Customer;
import com.system.coupon.service.CompanyService;
import com.system.coupon.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class DailyTask implements Runnable {

    private static final long day = 86400000;
    private static boolean isWorking = false;
    private CouponRepository couponRepo;
    private ApplicationContext context;

    @Autowired
    public DailyTask(CouponRepository couponRepo, ApplicationContext context) {
        this.couponRepo = couponRepo;
        this.context = context;
    }

    @Override
    public void run() {
        isWorking = true;
        while (isWorking) {
            long coupon_id;
            for (Coupon coupon : couponRepo.findAll()) {
                coupon_id = coupon.getId();
                LocalDate endDate = coupon.getEndDate();
                if (LocalDate.now().isAfter(endDate)) {
                    CompanyService companyService = context.getBean(CompanyService.class);
                    try {
                        companyService.deleteCouponById(coupon_id);
                    } catch (NoSuchCompanyException e) {
                        e.printStackTrace();
                    } catch (NoSuchCouponException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(day);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void stopDailyTask() {
        isWorking = false;
    }
}
