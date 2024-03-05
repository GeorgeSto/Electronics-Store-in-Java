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

public class UpdateElectroView {

    private final Stage stage;
    private final TextField titleTextField;
    private final TextField stockTextField;
    private final Button updateButton;

    public UpdateElectroView() {
        stage = new Stage();
        stage.setTitle("Update Electronics");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        titleTextField = new TextField();
        titleTextField.setPromptText("Title");
        gridPane.add(titleTextField, 0, 0);

        stockTextField = new TextField();
        stockTextField.setPromptText("Stock");
        gridPane.add(stockTextField, 0, 1);

        updateButton = new Button("Update");
        gridPane.add(updateButton, 0, 2);
        updateButton.setStyle("-fx-background-color: #271BBF; -fx-text-fill: white; -fx-border-color: black;");
        GridPane.setHalignment(updateButton, Pos.CENTER.getHpos());

        Scene scene = new Scene(gridPane, 300, 150);
        scene.getRoot().setStyle("-fx-background-image: url('file:/C:/Users/George/Desktop/fundal.jpg'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: no-repeat;");
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    public String getTitle() {
        return titleTextField.getText();
    }

    public String getStock() {
        return stockTextField.getText();
    }


    public TextField getTitleTextField() {
        return titleTextField;
    }

    public TextField getStockTextField() {
        return stockTextField;
    }

    public void addUpdateButtonListener(EventHandler<ActionEvent> updateButtonListener) {
        updateButton.setOnAction(updateButtonListener);
    }
}
