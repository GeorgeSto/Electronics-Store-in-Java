package view.employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CreateElectroView {

    private final Stage stage;
    private final TextField titleTextField;
    private final TextField companyTextField;

    private final TextField descriptionTextField;

    private final TextField imagePathTextField;

    private final TextField priceTextField;
    private final TextField publishedDateTextField;
    private final TextField stockTextField;
    private final Button createButton;

    public CreateElectroView() {
        stage = new Stage();
        stage.setTitle("Create Electro");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        titleTextField = new TextField();
        titleTextField.setPromptText("Title");
        gridPane.add(titleTextField, 0, 0);

        companyTextField = new TextField();
        companyTextField.setPromptText("Company");
        gridPane.add(companyTextField, 0, 1);

        publishedDateTextField = new TextField();
        publishedDateTextField.setPromptText("Published Date");
        gridPane.add(publishedDateTextField, 0, 2);

        stockTextField = new TextField();
        stockTextField.setPromptText("Stock");
        gridPane.add(stockTextField, 0, 3);

        descriptionTextField = new TextField();
        descriptionTextField.setPromptText("Company");
        gridPane.add(descriptionTextField, 0, 4);

        imagePathTextField = new TextField();
        imagePathTextField.setPromptText("image Path");
        gridPane.add(imagePathTextField, 0, 5);

        priceTextField = new TextField();
        priceTextField.setPromptText("price");
        gridPane.add(priceTextField, 0, 6);

        createButton = new Button("Create");
        gridPane.add(createButton, 0, 7);
        createButton.setStyle("-fx-background-color: #271BBF; -fx-text-fill: white; -fx-border-color: black;");
        GridPane.setHalignment(createButton, Pos.CENTER.getHpos());

        Scene scene = new Scene(gridPane, 300, 200);
        scene.getRoot().setStyle("-fx-background-image: url('file:/C:/Users/George/Desktop/fundal.jpg'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: no-repeat;");
        stage.setScene(scene);

    }

    public void show() {
        stage.show();
    }

    public TextField getTitleTextField() {
        return titleTextField;
    }

    public TextField getCompanyTextField() {
        return companyTextField;
    }

    public TextField getPublishedDateTextField() {
        return publishedDateTextField;
    }

    public TextField getStockTextField() {
        return stockTextField;
    }

    public Button getCreateButton() {
        return createButton;
    }

    public void addCreateButtonListener(EventHandler<ActionEvent> createButtonListener) {
        createButton.setOnAction(createButtonListener);
    }

    public TextField getDescriptionTextField() {
        return descriptionTextField;
    }

    public TextField getImagePathTextField() {
        return imagePathTextField;
    }

    public TextField getPriceTextField() {
        return priceTextField;
    }
}