package service.customer;

import model.User;
import repository.customer.CustomerRepository;

import java.util.List;

public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }
    @Override
    public boolean buyElectroByTitle(String title, User user) {
        return customerRepository.buyElectroByTitle(title, user);
    }

    @Override
    public boolean sellElectroByTitle(String title, User user) {
        return customerRepository.sellElectroByTitle(title, user);
    }
    @Override
    public List<User> findAllCustomers()
    {
        return customerRepository.findAllCustomers();
    }
}
