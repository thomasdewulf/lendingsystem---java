/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.sun.javafx.scene.control.skin.TableViewSkinBase;
import controllers.ProductController;
import domein.Observer;
import domein.Product;
import domein.Subject;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Remko
 */
public class ProductOverzichtController extends GridPane implements Subject, Observer
{

    @FXML
    private TableView<Product> tblProducten;
    @FXML
    private TableColumn<Product, String> tbcNaam;
    @FXML
    private TableColumn<Product, String> tbcAantal;
    @FXML
    private TableColumn<Product, String> tbcArtikelnummer;
    @FXML
    private GridPane gridOverzicht;

    private ProductController controller;
    private Product p;
    private Set<Observer> observers = new HashSet<>();
    @FXML
    private TextField txtFilter;

    public ProductOverzichtController()
    {
        controller = new ProductController();
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("ProductOverzicht.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }

        tbcNaam.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
        tbcAantal.setCellValueFactory(cellData -> cellData.getValue().aantalProperty());
        tbcArtikelnummer.setCellValueFactory(cellData -> cellData.getValue().artikelnummerProperty());

        tblProducten.setItems(controller.getProductenSortedList());
        tblProducten.setPlaceholder(new Label("Geen producten gevonden"));
        DetailProductController detailController = new DetailProductController(this);
        gridOverzicht.add(detailController, 2, 0);
        tblProducten.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) -> 
                        {
                            if (newValue != null)
                            {
                                p = newValue;
                                notifyObservers();
                            }
                });

    }

    @Override
    public void addObserver(Observer observer)
    {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer)
    {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers()
    {
        observers.forEach(observer -> observer.update(p));
    }

    @FXML
    private void filterLijst(KeyEvent event) {
        controller.setFilter(this.txtFilter.getText());
        controller.veranderFilter(this.txtFilter.getText());
    }

    @Override
    public void update(Object object)
    {
        this.tblProducten.setItems(controller.getProductenSortedList());
        this.tblProducten.refresh();
    }

}
