package view.employee;

import controller.employee.EmployeeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import launcher.ComponentFactory;
import model.Electro;
import model.User;
import repository.user.UserRepository;
import service.electro.ElectroService;
import service.customer.CustomerService;
import view.login.LoginView;

import java.util.List;

public class EmployeeView {

    private final Scene scene;
    private TableView<Electro> tableElectros;
    private final GridPane gridPane;
    private final TextField titleTextField;
    private final Button sellButton;
    private final Button logoutButton;
    private final Button createButton;
    private final Button readButton;
    private final Button updateButton;
    private final Button deleteButton;
    private final ElectroService electroService;
    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final ComponentFactory componentFactory;
    private final User user;

    public EmployeeView(Stage primaryStage, LoginView loginView, ComponentFactory componentFactory, User user) {
        primaryStage.setTitle("Interfata Angajat");
        this.electroService = componentFactory.getElectroService();
        this.userRepository = componentFactory.getUserRepository();
        this.customerService = componentFactory.getCustomerService();
        this.componentFactory = componentFactory;
        this.user = user;

        gridPane = new GridPane();
        initializeGridPane(gridPane);
        scene = new Scene(gridPane, 720, 480);
        scene.getRoot().setStyle("-fx-background-image: url('file:/C:/Users/George/Desktop/fundal.jpg'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: no-repeat;");
        primaryStage.setScene(scene);
        primaryStage.show();

        titleTextField = new TextField();
        titleTextField.setPromptText("Enter name for delete");

        gridPane.add(titleTextField, 1, 1);

        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setAlignment(Pos.CENTER);

        // Adaugare butoane pentru operatiile CRUD
        createButton = new Button("Create");
        createButton.setMinWidth(80);
        createButton.setStyle("-fx-background-color: #271BBF; -fx-text-fill: white; -fx-border-color: black;");
        buttonsHBox.getChildren().add(createButton);

        readButton = new Button("Read");
        readButton.setMinWidth(80);
        readButton.setStyle("-fx-background-color: #271BBF; -fx-text-fill: white; -fx-border-color: black;");
        buttonsHBox.getChildren().add(readButton);

        updateButton = new Button("Update");
        updateButton.setMinWidth(80);
        updateButton.setStyle("-fx-background-color: #271BBF; -fx-text-fill: white; -fx-border-color: black;");
        buttonsHBox.getChildren().add(updateButton);

        deleteButton = new Button("Delete");
        deleteButton.setMinWidth(80);
        deleteButton.setStyle("-fx-background-color: #271BBF; -fx-text-fill: white; -fx-border-color: black;");
        buttonsHBox.getChildren().add(deleteButton);

        sellButton = new Button("Sell");
        sellButton.setMinWidth(80);
        sellButton.setStyle("-fx-background-color: #271BBF; -fx-text-fill: white; -fx-border-color: black;");
        buttonsHBox.getChildren().add(sellButton);

        gridPane.add(buttonsHBox, 0, 2, 2, 1); // Adauga HBox in gridPane

        logoutButton = new Button("Logout");
        logoutButton.setMinWidth(80);
        logoutButton.setStyle("-fx-background-color: #271BBF; -fx-text-fill: white; -fx-border-color: black;");
        HBox.setMargin(logoutButton, new Insets(0, 10, 10, 0));
        gridPane.add(logoutButton, 3, 3, 1, 1);
        setTable();

        CreateElectroView createView = new CreateElectroView();
        UpdateElectroView updateView = new UpdateElectroView();
        SellElectroView sellElectroView = new SellElectroView(componentFactory);

        new EmployeeController(this, componentFactory, primaryStage, loginView, createView, updateView, sellElectroView, user);
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    public Scene getScene() {
        return scene;
    }

    public ObservableList<Electro> getElectro() {
        ObservableList<Electro> electros = FXCollections.observableArrayList();
        List<Electro> listOfElectros = electroService.findAll();

        for (Electro b : listOfElectros) {
            electros.add(b);
        }

        return electros;
    }

    public void setTable() {
        TableColumn<Electro, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(140);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Electro, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setMinWidth(100);
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Electro, String> publishedDateColumn = new TableColumn<>("PublishedDate");
        publishedDateColumn.setMinWidth(150);
        publishedDateColumn.setCellValueFactory(new PropertyValueFactory<>("publishedDate"));

        TableColumn<Electro, String> stockColumn = new TableColumn<>("Stock");
        stockColumn.setMinWidth(100);
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tableElectros = new TableView<>();
        tableElectros.setItems(getElectro());
        tableElectros.getColumns().addAll(titleColumn, authorColumn, publishedDateColumn, stockColumn);

        gridPane.add(tableElectros, 0, 0, 2, 1);



    }

    public String getTitleInput() {
        return titleTextField.getText();
    }

    public TextField getTitleTextField() {
        return titleTextField;
    }

    public void addSellButtonListener(EventHandler<ActionEvent> sellButtonListener) {
        sellButton.setOnAction(sellButtonListener);
    }

    public void addLogoutButtonListener(EventHandler<ActionEvent> logoutButtonListener) {
        logoutButton.setOnAction(logoutButtonListener);
    }

    public void addCreateButtonListener(EventHandler<ActionEvent> createButtonListener) {
        createButton.setOnAction(createButtonListener);
    }

    public void addReadButtonListener(EventHandler<ActionEvent> readButtonListener) {
        readButton.setOnAction(readButtonListener);
    }

    public void addUpdateButtonListener(EventHandler<ActionEvent> updateButtonListener) {
        updateButton.setOnAction(updateButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }

}
