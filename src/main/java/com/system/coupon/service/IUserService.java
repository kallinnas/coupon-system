package com.system.coupon.service;

import com.system.coupon.data.ex.UnknownRoleForUserException;
import com.system.coupon.data.model.User;
import com.system.coupon.ex.UserAlreadyExistException;
import com.system.coupon.service.ex.UserIsNotExistException;

public interface IUserService {
    boolean isUserExist(String email, String password);
    User getUserByEmailAndPassword(String email, String password) throws UserIsNotExistException;
    void updateUsersEmail(String email, String password, String newEmail) throws UserIsNotExistException;
    void updateUsersPassword(String email, String password, String newPassword) throws UserIsNotExistException;
    User createUser(String email, String password, int role) throws UserAlreadyExistException, UnknownRoleForUserException;
}
