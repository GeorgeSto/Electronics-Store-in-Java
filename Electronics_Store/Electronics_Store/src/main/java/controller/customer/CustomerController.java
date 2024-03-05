package controller.customer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import launcher.ComponentFactory;
import model.Electro;
import model.Orders;
import model.User;
import model.builder.OrderBuilder;
import repository.customer.CustomerRepository;
import repository.user.UserRepository;
import service.customer.CustomerService;
import service.electro.ElectroService;
import service.orders.OrdersService;
import view.customer.CustomerView;
import view.login.LoginView;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalDate;
import java.util.List;

public class CustomerController {

    private final CustomerView customerView;
    private final String username;
    private final UserRepository userRepository;
    private final CustomerService customerService;

    private final OrdersService orderService;

    private final ElectroService electroService;
    private final Stage stage;
    private final LoginView loginView;
    private final ListView products;
    private final Long id;


    public CustomerController(CustomerView customerView, ComponentFactory componentFactory, Stage stage, LoginView loginView, ListView products, ElectroService electroService, OrdersService orderService, Long id) {
        this.customerView = customerView;
        this.username = loginView.getUsername();
        this.userRepository = componentFactory.getUserRepository();
        this.customerService = componentFactory.getCustomerService();
        this.stage = stage;
        this.loginView = loginView;
        this.products = products;
        this.customerView.addBuyButtonListener(new BuyButtonListener());
        this.customerView.addAddButtonListener(new AddButtonListener());
        this.customerView.addLogoutButtonListener(new LogoutButtonListener());
        this.customerView.addRemoveButtonListener(new RemoveButtonListener());
        this.electroService = electroService;
        this.orderService = orderService;
        this.id = id;
    }

    private class BuyButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {

//            User user = userRepository.findUserByUsername(username);
//            if(customerService.buyElectroByTitle(title, user))
//            {
//                customerView.setTable();
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Electro Purchased Successfully");
//                alert.setHeaderText(null);
//                alert.setContentText("The book has been purchased successfully!");
//                alert.showAndWait();
//            }
//            else{
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Electro Cannot Be Purchased");
//                alert.setHeaderText(null);
//                alert.setContentText("The book is not in stock!");
//                alert.showAndWait();
//            }


            List<Orders> exist = orderService.findAll();
            for (Orders i : exist) {
                Electro produs = electroService.findById(i.getElectro_id());

                if (i.getQuantity() <= produs.getStock()) {
                    electroService.updateStockById(i.getElectro_id(), produs.getStock() - i.getQuantity());

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Electro out of stock");
                    alert.setHeaderText(null);
                    alert.setContentText(produs.getTitle() + " doesn't have enough stock");
                    alert.showAndWait();
                    return;
                }

            }

            orderService.removeAll();
            customerView.setTable();
            customerView.getTableOrders().setStyle("-fx-background-color: transparent;");
            customerView.getTableOrders().refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucessfully bought from our store!");
            alert.setHeaderText(null);
            alert.setContentText("Thank you for shopping from our store!");
            alert.showAndWait();
            return;
        }
    }

    private class AddButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            int index = products.getSelectionModel().getSelectedIndex();
            int productId = index + 1;
            Electro product = electroService.findById((long) productId);
            int stockProduct = product.getStock();

            OrderBuilder orders = new OrderBuilder();
            orders.setUser_id(id);
            orders.setElectro_id((long) productId);
            orders.setQuantity(1);
            orders.setPrice(product.getPrice());
            orders.setDate(LocalDate.now());
            Orders orderProduct = orders.build();

            List<Orders> exist = orderService.findAll();
            for (Orders i : exist) {
                if (i.getElectro_id() == orderProduct.getElectro_id()) {
                    orderService.updateQuantityById(i.getElectro_id(), i.getQuantity() + 1);
                    customerView.setTable();
                    return;
                }
            }

            orderService.save(orderProduct);
            customerView.setTable();

        }
    }

    private class LogoutButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            loginView.setPasswordField("");
            loginView.setUserTextField("");
            loginView.setActionTargetText("");
            stage.setScene(loginView.getScene());
        }
    }

    private class RemoveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            //System.out.println("FGJFKNGDFKJGHFDGKJFDHGJFDHJKGFD");

            if(customerView.getCurrentSelectedOrder() != null){
                orderService.removeById(customerView.getCurrentSelectedOrder().getId());
                customerView.setTable();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                Electro removedFromOrder = electroService.findById(customerView.getCurrentSelectedOrder().getElectro_id());
                alert.setTitle("Removed item from shopping cart");
                alert.setHeaderText(null);
                alert.setContentText("Succesfully removed " + removedFromOrder.getTitle() + " from your cart!");
                alert.showAndWait();
                return;
            }

        }
    }

}
