package gui;

import controllers.ProductBeherenController;
import domein.Doelgroep;
import domein.ToevoegenType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class DoelgroepenSelecterenController extends GridPane {

    @FXML
    private Button btnSendRight;

    @FXML
    private Button btnSendLeft;

    @FXML
    private ListView<Doelgroep> doelgroepKandidaten;
    @FXML
    private ListView<Doelgroep> doelgroepenGekozen;
    private ProductBeherenController controller;
    @FXML
    private Button btnMaakDoelgroep;
    @FXML
    private Button btnAnnuleer;
    @FXML
    private Button btnOk;

    private List<Doelgroep> hulpLijstKandidaat = new ArrayList<>();
    private List<Doelgroep> hulpLijstGekozen = new ArrayList<>();
    @FXML
    private Label lblError;

    //private SoortLijst lijst;
    public DoelgroepenSelecterenController(ProductBeherenController controller) {
        //this.domeinController = domeinController;
        this.controller = controller;
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("DoelgroepenSelecteren.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //---Zet items op de linker en rechterkant---
        doelgroepKandidaten.setItems(controller.getKandidaatDoelgroepen().sorted());
        doelgroepenGekozen.setItems(controller.getGekozenDoelgroepen().sorted());
        controller.addObserver(e -> btnSendRight.setDisable(controller.geenDoelgroepKandidaten()), ToevoegenType.KANDIDAATDOELGROEP);
        controller.addObserver(e -> btnSendLeft.setDisable(controller.geenGekozenDoelgroep()), ToevoegenType.GEKOZENDOELGROEP);
        doelgroepenGekozen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }
//--Stuurt items naar rechts--

    @FXML
    private void sendRight(ActionEvent event) {
        Doelgroep selectie = doelgroepKandidaten.getSelectionModel().getSelectedItem();
        if (selectie != null) {
            doelgroepKandidaten.getSelectionModel().clearSelection();
            controller.addGekozenDoelgroep(selectie);
            controller.removeKandidaatDoelgroep(selectie);
            if (!hulpLijstGekozen.contains(selectie)) {
                if (!hulpLijstKandidaat.contains(selectie)) {
                    hulpLijstGekozen.add(selectie);
                }
            }
        }
    }

    @FXML
    private void sendLeft(ActionEvent event) {
        Doelgroep selectie = doelgroepenGekozen.getSelectionModel().getSelectedItem();
        if (selectie != null) {
            doelgroepenGekozen.getSelectionModel().clearSelection();
            controller.addKandidaatDoelgroep(selectie);
            controller.removeGekozenDoelgroep(selectie);
            if (!hulpLijstKandidaat.contains(selectie)) {
                if (!hulpLijstGekozen.contains(selectie)) {
                    hulpLijstKandidaat.add(selectie);
                }
            }
        }
    }

    @FXML
    private void btnOkOnAction(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void btnAnnuleerOnAction(ActionEvent event) {
        hulpLijstGekozen.stream().forEach(gd -> {
            if (!controller.getKandidaatDoelgroepen().contains(gd)) {
                controller.removeGekozenDoelgroep(gd);
                controller.addKandidaatDoelgroep(gd);
            }
        });
        hulpLijstKandidaat.stream().forEach(kd -> {
            if (!controller.getGekozenDoelgroepen().contains(kd)) {
                controller.addGekozenDoelgroep(kd);
                controller.removeKandidaatDoelgroep(kd);
            }
        });
        hulpLijstGekozen.clear();
        hulpLijstKandidaat.clear();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void maakDoelgroep(ActionEvent event) {
        TextInputDialog venster = new TextInputDialog();
        venster.setGraphic(null);
        venster.setHeaderText(null);
        venster.setTitle("Maak nieuw doelgroep");
        venster.setContentText("Naam:*");
        Optional<String> result = venster.showAndWait();
        result.ifPresent(naam
                -> {
              Doelgroep d = new Doelgroep(naam);

            if(d.getNaam().trim().isEmpty() || d.getNaam() == null){
                lblError.setText("Naam is verplicht");
            }else if(!controller.bevatDoelgroep(d)) {
                controller.addKandidaatDoelgroep(d);
                controller.addDoelgroep(d);
                lblError.setText("");
            }else{
               lblError.setText("De doelgroep "+naam+" bestaat al!");
                
            }
        });

    }

    @FXML
    private void dubbelKlikkenKandidaat(MouseEvent event) {
        if (event.getClickCount() >= 2) {
            Doelgroep selectie = doelgroepKandidaten.getSelectionModel().getSelectedItem();
            if (selectie != null) {
                doelgroepKandidaten.getSelectionModel().clearSelection();
                controller.addGekozenDoelgroep(selectie);
                controller.removeKandidaatDoelgroep(selectie);
                if (!hulpLijstGekozen.contains(selectie)) {
                    if (!hulpLijstKandidaat.contains(selectie)) {
                        hulpLijstGekozen.add(selectie);
                    }
                }
            }
        }
    }

    @FXML
    private void dubbelKlikkenGekozen(MouseEvent event) {
        if (event.getClickCount() >= 2) {
            Doelgroep selectie = doelgroepenGekozen.getSelectionModel().getSelectedItem();
            if (selectie != null) {
                doelgroepenGekozen.getSelectionModel().clearSelection();
                controller.addKandidaatDoelgroep(selectie);
                controller.removeGekozenDoelgroep(selectie);
                if (!hulpLijstKandidaat.contains(selectie)) {
                    if (!hulpLijstGekozen.contains(selectie)) {
                        hulpLijstKandidaat.add(selectie);
                    }
                }
            }
        }
    }

}
