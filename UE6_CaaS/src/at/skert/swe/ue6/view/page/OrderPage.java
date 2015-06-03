package at.skert.swe.ue6.view.page;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import at.skert.swe.ue6.contracts.IRepository;
import at.skert.swe.ue6.contracts.data.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class OrderPage extends AnchorPane {

  private TableView<Order> table;
  private ObservableList<Order> orders;

  public OrderPage(List<Order> orders) {
    this.orders = FXCollections.observableList(orders);
    this.getChildren().addAll(createTitleHeadBox("Aktuelle Bestellungen"), createTableView());
  }

  private TableView<Order> createTableView() {
    table = new TableView<>();
    table.setItems(orders);
    // Column: Order time
    TableColumn<Order, String> timeCol = new TableColumn<>("Uhrzeit");
    timeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
    timeCol.setMinWidth(70);
    table.getColumns().add(timeCol);

    // Column: Ordered menu
    TableColumn<Order, String> menuCol = new TableColumn<>("Hauptspeise");
    menuCol.setCellValueFactory(new PropertyValueFactory<Order, String>("menu"));
    menuCol.setMinWidth(150);
    table.getColumns().add(menuCol);

    // Column: Menu Category
    TableColumn<Order, String> categoryCol = new TableColumn<>("Kategorie");
    categoryCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMenu().getCategory().toString()));
    categoryCol.setMinWidth(120);
    table.getColumns().add(categoryCol);

    // Column: Customer who ordered this menu
    TableColumn<Order, String> customerCol = new TableColumn<>("Kunde");
    customerCol.setCellValueFactory(new PropertyValueFactory<Order, String>("user"));
    customerCol.setMinWidth(120);
    table.getColumns().add(customerCol);

    // Column: Price of the menu
    TableColumn<Order, String> priceCol = new TableColumn<>("Preis");
    priceCol.setCellValueFactory(c -> new SimpleStringProperty(String.format("%.2f €", c.getValue().getMenu().getPrice())));
    priceCol.setMinWidth(70);
    priceCol.setStyle("-fx-alignment: CENTER-RIGHT;");
    table.getColumns().add(priceCol);

    // Sort by time
    table.getSortOrder().add(timeCol);
    AnchorPane.setTopAnchor(table, 50.0);
    AnchorPane.setLeftAnchor(table, 10.0);
    AnchorPane.setRightAnchor(table, 10.0);
    return table;
  }

  private HBox createTitleHeadBox(String title) {
    Label titleLabel = new Label(title);
    titleLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    HBox.setHgrow(titleLabel, Priority.ALWAYS);
    HBox titlePane = new HBox(titleLabel);
    titlePane.setPadding(new Insets(10));
    return titlePane;
  }
}
