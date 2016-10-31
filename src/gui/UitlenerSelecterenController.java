/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ReservatieToevoegenController;
import domein.AspNetUsers;
import domein.Firma;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author De Puysseleyr
 */
public class UitlenerSelecterenController extends GridPane {

    @FXML
    private Button btnOk;
    @FXML
    private Button btnAnnuleren;
    @FXML
    private ListView<AspNetUsers> personeel;
    @FXML
    private Label lblError;
    
    private ReservatieToevoegenController controller;

    private AspNetUsers selectie;
    private ReservatieToevoegenSchermController c;
    
     public UitlenerSelecterenController(ReservatieToevoegenSchermController c,ReservatieToevoegenController controller) {
        this.controller = controller;
        this.c = c;
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("UitlenerSelecteren.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        personeel.setItems(controller.getAllePersoneel());
        personeel.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        personeel.getSelectionModel().select(this.controller.getGekozenPersoneel());
    }
        
    @FXML
    private void okOnAction(ActionEvent event) {
        selectie = personeel.getSelectionModel().getSelectedItem();
        if (selectie != null) {
            //firmas.getSelectionModel().clearSelection();
            controller.setGekozenPersoneel(selectie);
            controller.setPersoneelProperty(new SimpleStringProperty(String.format("%s %s",selectie.getNaam(),selectie.getVoornaam())));
            c.updateLabel();
        }
        ((Node) event.getSource()).getScene().getWindow().hide();
        personeel.getSelectionModel().clearSelection();
    }
    
    
    

    @FXML
    private void btnAnnulerenOnAction(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        
    }


    @FXML
    private void mouseClickedPersoneel(MouseEvent event) {
    }
    
}
