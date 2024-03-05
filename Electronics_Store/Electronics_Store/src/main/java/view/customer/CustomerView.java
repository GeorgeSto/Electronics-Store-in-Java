package view.customer;

import controller.customer.CustomerController;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import launcher.ComponentFactory;
import model.Electro;
import model.Orders;
import model.User;
import repository.user.UserRepository;
import service.electro.ElectroService;
import service.customer.CustomerService;
import service.orders.OrdersService;
import view.login.LoginView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


import java.io.FileInputStream;
import java.util.List;

public class CustomerView {

    private final Scene scene;
    private TableView<Electro> tableElectros;

    private final TableView<Orders> tableOrders = new TableView<>();
    private final GridPane gridPane;
    private final Button buyButton;
    private final Button addButton;
    private final Button logoutButton;
    private final Button removeButton;
    private final ElectroService electroService;
    private final OrdersService ordersService;
    private Orders currentSelectedOrder;
    private UserRepository userRepository;
    private CustomerService customerService;

    private final ComponentFactory componentFactory;
    private ListView items;

    public CustomerView(Stage primaryStage, LoginView loginView, ComponentFactory componentFactory, Long id) {  //, LoginView loginView, ComponentFactory componentFactory
        primaryStage.setTitle("Customer Interface");
        this.electroService = componentFactory.getElectroService();
        this.userRepository = componentFactory.getUserRepository();
        this.customerService = componentFactory.getCustomerService();
        this.componentFactory = componentFactory;
        this.ordersService = componentFactory.getOrdersService();

        gridPane = new GridPane();
        initializeGridPane(gridPane);
        scene = new Scene(gridPane, 1024, 768);
        //scene.getRoot().setStyle("C:\\Users\\George\\Desktop\\fundal.jpg");
        //gridPane.setStyle("C:\\Users\\George\\Desktop\\fundal.jpg");
        scene.getRoot().setStyle("-fx-background-image: url('file:/C:/Users/George/Desktop/fundal.jpg'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: no-repeat;");

        primaryStage.setScene(scene);
        primaryStage.show();

        VBox leftSide = new VBox();
        leftSide.setPadding(new Insets(10));
        leftSide.setSpacing(100);
        Label numeProiect = new Label("RVS Electronics Store");
        numeProiect.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");

        leftSide.getChildren().add(numeProiect);
        User name = userRepository.findUserById(id);

        Label numeClient = new Label("Welcome, " + name.getUsername());
        numeClient.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        leftSide.getChildren().add(numeClient);
        gridPane.add(leftSide,0,0);


        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setAlignment(Pos.CENTER);

        addButton = new Button("Add");
        addButton.setMinWidth(80);
        addButton.setStyle("-fx-background-color: #271BBF; -fx-text-fill: white; -fx-border-color: black;");
        buttonsHBox.getChildren().add(addButton);

        buyButton = new Button("Buy");
        buyButton.setMinWidth(80);
        buyButton.setStyle("-fx-background-color: #271BBF; -fx-text-fill: white; -fx-border-color: black;");
        buttonsHBox.getChildren().add(buyButton);

        removeButton = new Button("Remove");
        removeButton.setMinWidth(80);
        removeButton.setStyle("-fx-background-color: #271BBF; -fx-text-fill: white; -fx-border-color: black;");
        buttonsHBox.getChildren().add(removeButton);

        gridPane.add(buttonsHBox, 2, 2, 2, 1);

        logoutButton = new Button("Logout");
        logoutButton.setMinWidth(80);
        logoutButton.setStyle("-fx-background-color: #271BBF; -fx-text-fill: white; -fx-border-color: black;");
        HBox.setMargin(logoutButton, new Insets(0, 0, 0, 0));
        gridPane.add(logoutButton, 0, 2);

        items = new ListView<>();
        items.setPrefSize(500,600);
        items.setStyle("-fx-background-color: transparent;");
        List<Electro> products = electroService.findAll();
        showProducts(products);

        items.setCellFactory(lv -> new ListCell<HBox>() {
            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    setGraphic(item);
                    setStyle("-fx-background-color: transparent;");
                }
            }
        });

        Group imagineT = new Group();
          imagineT.getChildren().addAll(items);
          gridPane.add(imagineT,1,0);
          setTable();
        new CustomerController(this, componentFactory, primaryStage, loginView,items,electroService,ordersService,id);
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

    }

    private void showProducts(List<Electro> products) {

        for(Electro product : products){
            String pathImage = product.getImagePath();
            String description = "Company : ";
            description += product.getCompany();
            description += ", Title : ";
            description += product.getTitle();
            description += ", Stock : ";
            description += product.getStock();
            description += "\nDescription : ";
            description += product.getDescription();
            description += ", Price : ";
            description += product.getPrice();
            ImageView electroImage;
            Image image = null;
            try{
                image = new Image(new FileInputStream(pathImage));
            }catch (Exception e){

            }
            electroImage = new ImageView(image);
            electroImage.setX(1);
            electroImage.setY(0);
            electroImage.setFitHeight(100);
            electroImage.setFitWidth(100);
            electroImage.setPreserveRatio(true);

            Label textElectro = new Label(description);
            textElectro.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");

            HBox electroProduct = new HBox(10);
            electroProduct.setStyle("-fx-background-color: transparent;");
            electroProduct.getChildren().addAll(electroImage,textElectro);
            items.getItems().add(electroProduct);

        }
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

    public ObservableList<Orders> getOrders() {
        ObservableList<Orders> orders = FXCollections.observableArrayList();
        List<Orders> listOfOrders = ordersService.findAll();

        for (Orders b : listOfOrders) {
            orders.add(b);
        }

        return orders;
    }

    public void setTable()
    {
        TableColumn<Orders, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(100);
        titleColumn.setCellValueFactory(cellData -> {
            Orders order = cellData.getValue();
            String title = electroService.getTitleById(order.getElectro_id());
            return new SimpleStringProperty(title);
        });

        TableColumn<Orders, String> companyColumn = new TableColumn<>("Company");
        companyColumn.setMinWidth(100);

        companyColumn.setCellValueFactory(new PropertyValueFactory<>("company"));
        companyColumn.setMinWidth(100);
        companyColumn.setCellValueFactory(cellData ->{
            Orders order = cellData.getValue();
            String company = electroService.getCompanyById(order.getElectro_id());
            return new SimpleStringProperty(company);
        });


        TableColumn<Orders, String> publishedDateColumn = new TableColumn<>("Price");
        publishedDateColumn.setMinWidth(150);
        publishedDateColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Orders, String> stockColumn = new TableColumn<>("Quantity");
        stockColumn.setMinWidth(100);
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        //tableOrders = new TableView<>();
        tableOrders.setStyle("-fx-background-color: transparent;");
        tableOrders.setItems(getOrders());
        tableOrders.getColumns().clear();
        tableOrders.getColumns().addAll(titleColumn, companyColumn, publishedDateColumn,stockColumn);

        tableOrders.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // newSelection is the selected item
                currentSelectedOrder = newSelection;
            }
        });

        gridPane.getChildren().remove(tableOrders);
        gridPane.add(tableOrders, 2, 0, 2, 1);
    }


    public void addBuyButtonListener(EventHandler<ActionEvent> buyButtonListener) {
        buyButton.setOnAction(buyButtonListener);
    }

    public void addRemoveButtonListener(EventHandler<ActionEvent> removeButtonListener) {
        removeButton.setOnAction(removeButtonListener);
    }

    public void addAddButtonListener(EventHandler<ActionEvent> sellButtonListener) {
        addButton.setOnAction(sellButtonListener);
    }

    public void addLogoutButtonListener(EventHandler<ActionEvent> logoutButtonListener) {
        logoutButton.setOnAction(logoutButtonListener);
    }

    public TableView<Orders> getTableOrders() {
        return tableOrders;
    }

    public Orders getCurrentSelectedOrder() {
        return currentSelectedOrder;
    }
}
