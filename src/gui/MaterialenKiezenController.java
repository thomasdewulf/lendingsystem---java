/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ProductController;
import controllers.ReservatieToevoegenController;
import domein.Catalogus;
import domein.Product;
import domein.ReservatieLijn;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author De Puysseleyr
 */
public class MaterialenKiezenController extends GridPane {

    @FXML
    private TableView<Product> tblProducten;
    @FXML
    private Button btnToevoegen;
    @FXML
    private Button btnAnnuleren;

    private ReservatieToevoegenController controller;
    private ProductController pcontroller = new ProductController();
    @FXML
    private TableColumn<Product, String> tbcNaam;
    @FXML
    private TableColumn<Product, String> tbcAantalBeschikbaar;
    @FXML
    private TableColumn<ReservatieLijn, String> tbcAantalBestellen;
    @FXML
    private Label lblFoutbericht;
    @FXML
    private TableView<ReservatieLijn> tblReservatieLijnen;
    @FXML
    private TableColumn<ReservatieLijn, String> tbcGekozenNaam;
    @FXML
    private Button btnSendRight;
    @FXML
    private Button btnSendLeft;

    private List<ReservatieLijn> hulpLijstGekozen = new ArrayList<>();

    private ObservableList<Product> producten = pcontroller.getProductenSortedList();

    private List<Product> producthulpLijst = new ArrayList<>();
    private Map<Product, Integer> map = new HashMap<>();

    private Catalogus catalogus;
    @FXML
    private Label lblDefaultWaarde;

    public MaterialenKiezenController(ReservatieToevoegenController controller) {
        this.controller = controller;
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("MaterialenKiezen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        lblDefaultWaarde.setText("Foutieve waarden worden bij toevoegen\n" + "herzet naar de defaultwaarde 1");
        tbcNaam.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
        tbcAantalBeschikbaar.setCellValueFactory(cellData -> cellData.getValue().aantalProperty());
        tbcAantalBestellen.setCellFactory(TextFieldTableCell.forTableColumn());

        tbcGekozenNaam.setCellValueFactory(cellData -> cellData.getValue().getProduct().naamProperty());
        tbcAantalBestellen.setCellValueFactory(cellData -> cellData.getValue().aantalProperty());

        tblReservatieLijnen.setItems(controller.getGekozenReservatieLijnen());

        tblProducten.setItems(producten);
        tblProducten.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tblReservatieLijnen.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void btnToevoegenOnAction(ActionEvent event) {

        ((Node) event.getSource()).getScene().getWindow().hide();

    }

    @FXML
    private void btnAnnulerenOnAction(ActionEvent event) {
        hulpLijstGekozen.stream().forEach(gd -> {
            producthulpLijst.stream().forEach(p -> {

                if (gd.getProduct().equals(p)) {
                    controller.removeReservatieLijn(gd);
                }
            });
        });

        hulpLijstGekozen.clear();

        ((Node) event.getSource()).getScene().getWindow().hide();

    }

    @FXML
    private void btnSendRightOnAction(ActionEvent event) {
        List<Product> hulpproduct = tblProducten.getSelectionModel().getSelectedItems();
        if (hulpproduct != null) {
            hulpproduct.stream().forEach((p) -> {
                if (p.getAantalInCatalogus() == 0) {
                    lblFoutbericht.setText("Kan geen product reserveren waarvan er geen beschikbaar zijn");
                } else {
                    ReservatieLijn lijn = new ReservatieLijn(1, p, 0);
                    if (!producthulpLijst.contains(p)) {
                        controller.addReservatieLijn(lijn);
                        producthulpLijst.add(p);
                    }
                    if (!hulpLijstGekozen.contains(lijn)) {
                        {
                            hulpLijstGekozen.add(lijn);
                        }
                    }
                }

            });
        }

        tblProducten.getSelectionModel().clearSelection();

    }

    @FXML
    private void btnSendLeftOnAction(ActionEvent event) {
        List<ReservatieLijn> hulpreservatielijnen = tblReservatieLijnen.getSelectionModel().getSelectedItems();
        if (hulpreservatielijnen != null) {
            hulpreservatielijnen.stream().forEach(lijn -> producthulpLijst.remove(lijn.getProduct()));
            controller.removeReservatieLijnen(hulpreservatielijnen);
            hulpLijstGekozen.removeAll(hulpreservatielijnen);
        }
        tblReservatieLijnen.getSelectionModel().clearSelection();
    }

    @FXML
    private void sendRight(MouseEvent event) {
        if (event.getClickCount() >= 2) {
            List<Product> hulpproduct = tblProducten.getSelectionModel().getSelectedItems();
            if (hulpproduct != null) {
                hulpproduct.stream().forEach((p) -> {
                    if (p.getAantalInCatalogus() == 0) {
                        lblFoutbericht.setText("Kan geen product reserveren waarvan er geen beschikbaar zijn");
                    } else {
                        ReservatieLijn lijn = new ReservatieLijn(1, p, 0);
                        if (!producthulpLijst.contains(p)) {
                            controller.addReservatieLijn(lijn);
                            producthulpLijst.add(p);
                        }
                        if (!hulpLijstGekozen.contains(lijn)) {
                            {
                                hulpLijstGekozen.add(lijn);
                            }
                        }
                    }
                });
            }

            tblProducten.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void AantalBestellenEdit(CellEditEvent<ReservatieLijn, String> event) {

        lblFoutbericht.setText("");
        try {
            int aantalBestellen = Integer.parseInt(event.getNewValue());
            int index = event.getTablePosition().getRow();
            ReservatieLijn lijn = controller.getGekozenReservatieLijnen().get(index);

            if (aantalBestellen < 0 || aantalBestellen > lijn.getProduct().getAantalInCatalogus()) {
                lblFoutbericht.setText("Gelieve een getal groter dan 0 en kleiner dan aantal beschikbaar in te geven\n");
                return;
            }
            controller.pasAantalAan(aantalBestellen, lijn);

        } catch (NumberFormatException ex) {
            lblFoutbericht.setText("Gelieve een geldig geheel getal in te geven");
        }

        tblReservatieLijnen.getSelectionModel().clearSelection();

    }

}
