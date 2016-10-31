/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ConfiguratieController;
import domein.Doelgroep;
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
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Remko
 */
public class DoelgroepWijzigenSchermController extends GridPane{

    @FXML
    private Button btnDoelgroepToevoegen;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnAnnuleren;
    @FXML
    private ListView<Doelgroep> listDoelgroepen;
    @FXML
    private Label lblError;
    @FXML
    private Button btnVerwijderen;
    
    private ConfiguratieController controller;
    private List<Doelgroep> doelgroepen;
    private List<Doelgroep> doelgroepenVerwijderd;

     public DoelgroepWijzigenSchermController(ConfiguratieController controller) {
        this.controller = controller;
        doelgroepen = new ArrayList<>();
        doelgroepenVerwijderd = new ArrayList<>();
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("DoelgroepWijzigenScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        listDoelgroepen.setItems(controller.getSortedDoelgroepen());
        listDoelgroepen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
     

    @FXML
    private void doelgroepToevoegen(ActionEvent event) {
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
                doelgroepen.add(d);
                controller.addDoelgroep(d);
                lblError.setText("");
            }else{
               lblError.setText("De doelgroep "+naam+" bestaat al!");
                
            }
        });
    }

    @FXML
    private void okOnAction(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void annulerenOnAction(ActionEvent event) {
        if(!doelgroepen.isEmpty()){
            doelgroepen.forEach(d -> this.controller.verwijderDoelgroep(d));
        }
        if(!doelgroepenVerwijderd.isEmpty()){
            doelgroepenVerwijderd.forEach(d->this.controller.addDoelgroep(d));
        }
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void doelgroepVerwijderen(ActionEvent event) {
        Doelgroep d = this.listDoelgroepen.getSelectionModel().getSelectedItem();
        doelgroepenVerwijderd.add(d);
        this.controller.verwijderDoelgroep(d);
    }
    
}
