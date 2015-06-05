package at.skert.swe.ue6.view.page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import at.skert.swe.ue6.contracts.data.Order;
import at.skert.swe.ue6.viewmodel.OrderViewModel;

public class OrderPage extends AnchorPane {

  private TableView<Order> table;
  private OrderViewModel viewModel;

  public OrderPage(OrderViewModel viewModel) {
    this.viewModel = viewModel;
    this.getChildren().add(createOrderView());
  }

  private Pane createOrderView() {
    VBox pane = new VBox();
    pane.getChildren()
        .addAll(
            createTitleHeadBox("Aktuelle Bestellungen am "
                + LocalDate.now().format(
                    DateTimeFormatter.ofPattern("dd.MM.yyyy"))),
            createTableView());
    AnchorPane.setTopAnchor(pane, 10.0);
    AnchorPane.setLeftAnchor(pane, 10.0);
    AnchorPane.setRightAnchor(pane, 10.0);
    AnchorPane.setBottomAnchor(pane, 10.0);
    return pane;
  }

  @SuppressWarnings({ "unchecked" })
  private TableView<Order> createTableView() {
    table = new TableView<>();
    table.setItems(viewModel.getOrderList());
    table.getColumns().addAll(createTimeColumn(), createMenuColumn(),
        createMenuCategoryColummn(), createCustomerColumn(),
        createPriceColumn());
    table.getSortOrder().add(table.getColumns().get(0));
    AnchorPane.setTopAnchor(table, 50.0);
    AnchorPane.setLeftAnchor(table, 10.0);
    AnchorPane.setRightAnchor(table, 10.0);
    AnchorPane.setBottomAnchor(table, 10.0);
    return table;
  }

  private TableColumn<Order, String> createTimeColumn() {
    TableColumn<Order, String> timeCol = new TableColumn<>("Uhrzeit");
    timeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue()
        .getDateTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
    timeCol.setMinWidth(70);
    return timeCol;
  }

  private TableColumn<Order, String> createMenuColumn() {
    TableColumn<Order, String> menuCol = new TableColumn<>("Hauptspeise");
    menuCol
        .setCellValueFactory(new PropertyValueFactory<Order, String>("menu"));
    menuCol.setMinWidth(150);
    return menuCol;
  }

  private TableColumn<Order, String> createMenuCategoryColummn() {
    TableColumn<Order, String> categoryCol = new TableColumn<>("Kategorie");
    categoryCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue()
        .getMenu().getCategory().toString()));
    categoryCol.setMinWidth(120);
    return categoryCol;
  }

  private TableColumn<Order, String> createCustomerColumn() {
    TableColumn<Order, String> customerCol = new TableColumn<>("Kunde");
    customerCol.setCellValueFactory(new PropertyValueFactory<Order, String>(
        "user"));
    customerCol.setMinWidth(120);
    return customerCol;
  }

  private TableColumn<Order, String> createPriceColumn() {
    TableColumn<Order, String> priceCol = new TableColumn<>("Preis");
    priceCol.setCellValueFactory(c -> new SimpleStringProperty(String.format(
        "%.2f €", c.getValue().getMenu().getPrice())));
    priceCol.setMinWidth(70);
    priceCol.setStyle("-fx-alignment: CENTER-RIGHT;");
    return priceCol;
  }

  private HBox createTitleHeadBox(String title) {
    Label titleLabel = new Label(title);
    titleLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    titleLabel.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(titleLabel, Priority.ALWAYS);

    HBox titlePane = new HBox(titleLabel, createAmountLabel());
    HBox.setHgrow(titlePane, Priority.ALWAYS);
    AnchorPane.setTopAnchor(titlePane, 10.0);
    AnchorPane.setLeftAnchor(titlePane, 10.0);
    titlePane.setPadding(new Insets(10));
    return titlePane;
  }

  private Label createAmountLabel() {
    Label amountLabel = new Label("Anzahl: " + viewModel.getOrderList().size());
    amountLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
    AnchorPane.setTopAnchor(amountLabel, 10.0);
    AnchorPane.setRightAnchor(amountLabel, 10.0);
    return amountLabel;
  }
}