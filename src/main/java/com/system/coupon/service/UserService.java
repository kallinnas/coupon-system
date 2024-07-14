package com.system.coupon.service;

import com.system.coupon.data.db.CompanyRepository;
import com.system.coupon.data.db.CustomerRepository;
import com.system.coupon.data.db.UserRepository;
import com.system.coupon.data.ex.UnknownRoleForUserException;
import com.system.coupon.data.model.Company;
import com.system.coupon.data.model.Customer;
import com.system.coupon.data.model.User;
import com.system.coupon.ex.UserAlreadyExistException;
import com.system.coupon.service.ex.UserIsNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements IUserService {
    private final ApplicationContext context;
    private final UserRepository userRepository;

    @Override
    public boolean isUserExist(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).isPresent();
    }
    @Override
    public User getUserByEmailAndPassword(String email, String password) throws UserIsNotExistException {
        Optional<User> optUser = userRepository.findByEmailAndPassword(email, password);
        if (!optUser.isPresent()) {
            throw new UserIsNotExistException(String.format("User with such email %s is not exist!", email));
        }
        return optUser.get();
    }
    @Override
    public User createUser(String email, String password, int role) throws UserAlreadyExistException, UnknownRoleForUserException {
        Optional<User> optUser = userRepository.findByEmailAndPassword(email, password);
        if (optUser.isPresent()) {
            throw new UserAlreadyExistException(String.format("User with such email %s is already exist.", email));
        }
        User user = new User(email, password, role);
        if(user.getClient() instanceof Customer){
            //Call customerRepo to save in it User's client witch is instance of Customer
            CustomerRepository customerRepository = this.context.getBean(CustomerRepository.class);
            customerRepository.save((Customer)user.getClient());
        } else{
            CompanyRepository companyRepository = this.context.getBean(CompanyRepository.class);
            companyRepository.save((Company)user.getClient());
        }
        userRepository.save(user);
        return user;
    }
    @Override
    public void updateUsersEmail(String email, String password, String newEmail) throws UserIsNotExistException {
        User user = getUserByEmailAndPassword(email, password);
        user.setEmail(newEmail);
        userRepository.save(user);
    }
    @Override
    public void updateUsersPassword(String email, String password, String newPassword) throws UserIsNotExistException {
        User user = getUserByEmailAndPassword(email, password);
        user.setPassword(newPassword);
        userRepository.save(user);
    }

}
