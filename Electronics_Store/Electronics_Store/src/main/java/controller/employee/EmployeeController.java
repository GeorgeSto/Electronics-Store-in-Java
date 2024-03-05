package controller.employee;

import com.itextpdf.text.DocumentException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import launcher.ComponentFactory;
import model.Electro;
import model.User;
import pdf.GeneratePdfSoldElectros;
import service.electro.ElectroService;
import view.employee.CreateElectroView;
import view.employee.EmployeeView;
import view.employee.SellElectroView;
import view.employee.UpdateElectroView;
import view.login.LoginView;

import java.io.IOException;
import java.util.List;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final SellElectroView sellElectroView;
    private final CreateElectroView createElectroView;
    private final UpdateElectroView updateElectroView;
    private final Stage stage;
    private final LoginView loginView;
    private final ComponentFactory componentFactory;
    private final User user;
    private final ElectroService electroService;

    public EmployeeController(EmployeeView employeeView, ComponentFactory componentFactory, Stage stage, LoginView loginView, CreateElectroView createElectroView, UpdateElectroView updateElectroView, SellElectroView sellElectroView, User user) {
        this.employeeView = employeeView;
        this.stage = stage;
        this.loginView = loginView;
        this.electroService = componentFactory.getElectroService();
        this.createElectroView = createElectroView;
        this.updateElectroView = updateElectroView;
        this.componentFactory = componentFactory;
        this.sellElectroView = sellElectroView;
        this.user = user;
        this.employeeView.addCreateButtonListener(new CreateButtonListener());
        this.employeeView.addReadButtonListener(new ReadButtonListener());
        this.employeeView.addUpdateButtonListener(new UpdateButtonListener());
        this.employeeView.addDeleteButtonListener(new DeleteButtonListener());
        this.employeeView.addSellButtonListener(new SellButtonListener());
        this.employeeView.addLogoutButtonListener(new LogoutButtonListener());
    }

    private class CreateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            createElectroView.show();
            new CreateElectroController(createElectroView, componentFactory);
        }
    }

    private class ReadButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            employeeView.setTable();
        }
    }

    private class UpdateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            updateElectroView.show();
            new UpdateElectroController(updateElectroView, componentFactory, employeeView);
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String title = employeeView.getTitleInput();
            if (title.isEmpty()) {
                showErrorAlert("Title is required", "Please enter the title of the book to delete.");
            } else {
                if (electroService.deleteElectroByTitle(title)) {
                    employeeView.setTable();
                    showInformationAlert("Electro Deleted", "The book has been deleted successfully!");
                    employeeView.getTitleTextField().clear();
                } else {
                    showErrorAlert("Delete Error", "An error occurred while deleting the book.");
                }
            }
        }
    }

    private class SellButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            sellElectroView.show();
            new SellElectroController(sellElectroView, componentFactory, employeeView, user);

        }
    }

    private class PdfButtonListener implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event)
        {
            GeneratePdfSoldElectros generatePdfSoldElectros = new GeneratePdfSoldElectros();
            List<Electro> electros = electroService.findSoldElectros();
            try {
                generatePdfSoldElectros.generatePdf("reportSoldElectros.pdf", electros);
                System.out.println("PDF GENERATED SUCCESSFULLY!");
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
