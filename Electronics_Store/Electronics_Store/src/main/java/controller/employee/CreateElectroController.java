package controller.employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import launcher.ComponentFactory;
import model.Electro;
import model.builder.ElectroBuilder;
import service.electro.ElectroService;
import view.employee.CreateElectroView;

import java.time.LocalDate;

public class CreateElectroController {

    private final CreateElectroView createView;
    private final ElectroService bookService;

    public CreateElectroController(CreateElectroView createView, ComponentFactory componentFactory) {
        this.createView = createView;
        this.bookService = componentFactory.getElectroService();

        this.createView.addCreateButtonListener(new CreateElectroController.CreateButtonListener());
    }

    private class CreateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String title = createView.getTitleTextField().getText();
            String author = createView.getCompanyTextField().getText();
            String publishedDate = createView.getPublishedDateTextField().getText();
            String stockStr = createView.getStockTextField().getText();
            String description = createView.getDescriptionTextField().getText();
            String imagePath = createView.getImagePathTextField().getText();
            int price = Integer.parseInt(createView.getPriceTextField().getText());

            Electro checkElectro = bookService.findByTitle(title);
            if(checkElectro == null){
                try {
                    int stock = Integer.parseInt(stockStr);
                    Electro book = new ElectroBuilder()
                            .setTitle(title)
                            .setCompany(author)
                            .setPublishedDate(LocalDate.parse(publishedDate))
                            .setStock(stock)
                            .setDescription(description)
                            .setImagePath(imagePath)
                            .setPrice(price)
                            .build();


                    if (bookService.save(book)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Electro Created Successfully");
                        alert.setHeaderText(null);
                        alert.setContentText("The book has been created successfully!");
                        alert.showAndWait();

                        createView.getTitleTextField().clear();
                        createView.getCompanyTextField().clear();
                        createView.getPublishedDateTextField().clear();
                        createView.getStockTextField().clear();
                    } else {
                        showErrorAlert("Error Creating Electro", "An error occurred while creating the book.");
                    }
                } catch (NumberFormatException e) {
                    showErrorAlert("Invalid Stock", "Please enter a valid integer for the stock.");
                }
            }else {
                showErrorAlert("Electro Exists", "Electro already exists");
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
}