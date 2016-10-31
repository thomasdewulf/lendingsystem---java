/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ProductBeherenController;
import domein.Leergebied;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Remko
 */
public class LeergebiedenSelecterenController extends GridPane {

    @FXML
    private Button btnSendLeft;
    @FXML
    private Button btnSendRight;
    @FXML
    private ListView<Leergebied> leergebiedenKandidaten;
    @FXML
    private ListView<Leergebied> leergebiedenGekozen;
    @FXML
    private Button btnMaakLeergebied;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnAnnuleer;

    private ProductBeherenController controller;
    private List<Leergebied> hulpLijstKandidaat = new ArrayList<>();
    private List<Leergebied> hulpLijstGekozen = new ArrayList<>();
    @FXML
    private Label lblError;

    public LeergebiedenSelecterenController(ProductBeherenController controller) {
        //this.domeinController = domeinController;
        this.controller = controller;
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("LeergebiedenSelecteren.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //---Zet items op de linker en rechterkant---
        leergebiedenKandidaten.setItems(controller.getKandidaatLeergebieden().sorted());
        leergebiedenGekozen.setItems(controller.getGekozenLeergebieden().sorted());
        controller.addObserver(e -> btnSendRight.setDisable(controller.geenLeergebiedKandidaten()), ToevoegenType.KANDIDAATLEERGEBIED);
        controller.addObserver(e -> btnSendLeft.setDisable(controller.geenGekozenLeergebied()), ToevoegenType.GEKOZENLEERGEBIED);
        leergebiedenGekozen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    @FXML
    private void sendLeft(ActionEvent event) {
        Leergebied selectie = leergebiedenGekozen.getSelectionModel().getSelectedItem();
        if (selectie != null) {
            leergebiedenGekozen.getSelectionModel().clearSelection();
            controller.addKandidaatLeergebied(selectie);
            controller.removeGekozenLeergebied(selectie);
            if (!hulpLijstKandidaat.contains(selectie))
            {
                if (!hulpLijstGekozen.contains(selectie))
                {
                    hulpLijstKandidaat.add(selectie);
                }
            }
        }
    }

    @FXML
    private void sendRight(ActionEvent event) {
        Leergebied selectie = leergebiedenKandidaten.getSelectionModel().getSelectedItem();
        if (selectie != null) {
            leergebiedenKandidaten.getSelectionModel().clearSelection();
            controller.addGekozenLeergebied(selectie);
            controller.removeKandidaatLeergebied(selectie);
            if(!hulpLijstGekozen.contains(selectie))
            {
                if(!hulpLijstKandidaat.contains(selectie))
                {
                    hulpLijstGekozen.add(selectie);
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
        hulpLijstGekozen.stream().forEach(kl->
                {
                    if(!controller.getKandidaatLeergebieden().contains(kl))
                    {
                        controller.removeGekozenLeergebied(kl);
                        controller.addKandidaatLeergebied(kl);
                    }
                });
        hulpLijstKandidaat.stream().forEach(gl->
                {
                    if(!controller.getGekozenLeergebieden().contains(gl))
                    {
                        controller.addGekozenLeergebied(gl);
                        controller.removeKandidaatLeergebied(gl);
                    }
                });
        hulpLijstGekozen.clear();
        hulpLijstKandidaat.clear();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void maakLeergebied(ActionEvent event) {
        TextInputDialog venster = new TextInputDialog();
        venster.setGraphic(null);
        venster.setHeaderText(null);
        venster.setTitle("Maak nieuw leergebied");
        venster.setContentText("Naam:");
        Optional<String> result = venster.showAndWait();
        final Button okButton = (Button) venster.getDialogPane().lookupButton(ButtonType.OK);

        result.ifPresent(naam -> {
            Leergebied l = new Leergebied(naam);
            if(l.getNaam().trim().isEmpty() || l.getNaam() == null){
                lblError.setText("Naam is verplicht");
            }else if(!controller.bevatLeergebied(l)) {
                controller.addKandidaatLeergebied(l);
                controller.addLeergebied(l);
                lblError.setText("");
            }else{
               lblError.setText("Het leergebied "+naam+" bestaat al!");
                
            }
        });

    }

    @FXML
    private void dubbelKlikkenKandidaat(MouseEvent event)
    {
        if (event.getClickCount() >= 2)
        {
            Leergebied selectie = leergebiedenKandidaten.getSelectionModel().getSelectedItem();
            if (selectie != null)
            {
                leergebiedenKandidaten.getSelectionModel().clearSelection();
                controller.addGekozenLeergebied(selectie);
                controller.removeKandidaatLeergebied(selectie);
                if (!hulpLijstGekozen.contains(selectie))
                {
                    if (!hulpLijstKandidaat.contains(selectie))
                    {
                        hulpLijstGekozen.add(selectie);
                    }
                }
            }
        }
    }

    @FXML
    private void dubbelKlikkenGekozen(MouseEvent event)
    {
        if (event.getClickCount() >= 2)
        {
            Leergebied selectie = leergebiedenGekozen.getSelectionModel().getSelectedItem();
            if (selectie != null)
            {
                leergebiedenGekozen.getSelectionModel().clearSelection();
                controller.addKandidaatLeergebied(selectie);
                controller.removeGekozenLeergebied(selectie);
                if (!hulpLijstKandidaat.contains(selectie))
                {
                    if (!hulpLijstGekozen.contains(selectie))
                    {
                        hulpLijstKandidaat.add(selectie);
                    }
                }
            }
        }
    }
}
