package service.customer;

import model.User;

import java.util.List;

public interface CustomerService {
    boolean buyElectroByTitle(String title, User user);
    List<User> findAllCustomers();

    boolean sellElectroByTitle(String title, User user);
}
