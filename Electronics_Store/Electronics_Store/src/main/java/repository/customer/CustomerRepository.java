package repository.customer;

import model.User;

import java.util.List;

public interface CustomerRepository {

    boolean buyElectroByTitle(String title, User user);
    boolean sellElectroByTitle(String title, User user);
    List<User> findAllCustomers();
}
