package launcher;

import controller.login.LoginController;
import database.DataBaseConnectionFactory;
import javafx.stage.Stage;
import model.Electro;
import model.User;
import model.builder.UserBuilder;
import repository.admin.AdminRepository;
import repository.admin.AdminRepositoryMySQL;
import repository.electro.ElectroRepository;
import repository.electro.ElectroRepositoryMySql;
import repository.customer.CustomerRepository;
import repository.customer.CustomerRepositoryMySQL;
import repository.orders.OrdersRepository;
import repository.orders.OrdersRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.superAdmin.SuperAdminRepository;
import repository.superAdmin.SuperAdminRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.admin.AdminService;
import service.admin.AdminServiceImpl;
import service.electro.ElectroService;
import service.electro.ElectroServiceImpl;
import service.customer.CustomerService;
import service.customer.CustomerServiceImpl;
import service.orders.OrdersService;
import service.orders.OrdersServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.login.LoginView;

import java.sql.Connection;

public class ComponentFactory {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ElectroRepository electroRepository;
    private final ElectroService electroService;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    private final OrdersRepository ordersRepository;

    private final OrdersService ordersService;

    private final AdminRepository adminRepository;
    private final AdminService adminService;
    private final SuperAdminRepository superAdminRepository;
    private static volatile ComponentFactory instance;

    public static ComponentFactory getInstance(Boolean componentsForTests, Stage stage)
    {
        if(instance == null) {
            synchronized (ComponentFactory.class) {
                if (instance == null) {
                    instance = new ComponentFactory(componentsForTests, stage);
                }
            }
        }
        return instance;
    }

    public ComponentFactory(Boolean componentsForTests, Stage stage)
    {
        Connection connection = DataBaseConnectionFactory.getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);

        this.userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);

        this.electroRepository = new ElectroRepositoryMySql(connection);

        this.electroService = new ElectroServiceImpl(electroRepository);

        this.ordersRepository = new OrdersRepositoryMySQL(connection);

        this.ordersService = new OrdersServiceImpl(ordersRepository);

        this.customerRepository = new CustomerRepositoryMySQL(connection);

        this.customerService = new CustomerServiceImpl(customerRepository);

        this.adminRepository = new AdminRepositoryMySQL(connection, rightsRolesRepository, electroRepository, userRepository);

        this.adminService = new AdminServiceImpl(adminRepository);

        this.superAdminRepository = new SuperAdminRepositoryMySQL(connection, rightsRolesRepository);

        this.authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository, adminRepository, superAdminRepository);

        this.loginView = new LoginView(stage);
        //insertAdmin();


    }

    public LoginView getLoginView() {
        return loginView;
    }


    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public ElectroRepository getElectroRepository() {
        return electroRepository;
    }

    public ElectroService getElectroService() {
        return electroService;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public OrdersService getOrdersService() {return ordersService;}

    public AdminService getAdminService()
    {
        return adminService;
    }
    public void insertAdmin()
    {
        String username = "admin@gmail.com";
        String password = "admin123!";
        authenticationService.registerAdmin(username, password);
    }
}
