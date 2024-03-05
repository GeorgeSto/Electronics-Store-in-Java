package controller.employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import launcher.ComponentFactory;
import model.User;
import repository.user.UserRepository;
import service.electro.ElectroService;
import view.employee.EmployeeView;
import view.employee.SellElectroView;

public class SellElectroController {

    private final SellElectroView sellElectroView;
    private final ElectroService bookService;
    private final EmployeeView employeeView;
    private final UserRepository userRepository;
    private final User user;

    public SellElectroController(SellElectroView sellElectroView, ComponentFactory componentFactory, EmployeeView employeeView,User user) {
        this.sellElectroView = sellElectroView;
        this.bookService = componentFactory.getElectroService();
        this.employeeView = employeeView;
        this.userRepository = componentFactory.getUserRepository();
        this.user = user;

        this.sellElectroView.addSellButtonListener(new SellButtonListener());
    }

    private class SellButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String title = sellElectroView.getTitleInput();
            String customerUsername = sellElectroView.getCustomerUsernameInput();

            User customer = userRepository.findUserByUsername(customerUsername);

            if (customer != null) {
                boolean success = bookService.sellElectro(title, customer, user);

                if (success) {
                    showInformationAlert("Sale Successful", "Electro sold successfully.");
                    employeeView.setTable();
                } else {
                    showErrorAlert("Sale Error", "Electro sale failed. Check the book availability.");
                }
            } else {
                showErrorAlert("Customer Not Found", "Customer with username " + customerUsername + " not found.");
            }


        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
