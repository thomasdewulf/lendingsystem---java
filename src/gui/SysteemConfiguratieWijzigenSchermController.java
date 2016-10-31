/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ConfiguratieController;
import domein.Tijdstip;
import java.io.IOException;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Remko
 */
public class SysteemConfiguratieWijzigenSchermController extends GridPane {

    @FXML
    private TextField txfReservatieperiode;
    @FXML
    private TextField txfMaximaleVerlenging;
    @FXML
    private Button btnDoelgroepen;
    @FXML
    private Button btnLeergebieden;
    @FXML
    private Button btnWijzigen;
    @FXML
    private Button btnAnnuleren;
    @FXML
    private ChoiceBox<Tijdstip> choOphalen;
    @FXML
    private ChoiceBox<Tijdstip> choAfhalen;
   
    private ConfiguratieController controller;
    @FXML
    private Label lblError;
    public SysteemConfiguratieWijzigenSchermController()
    {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SysteemConfiguratieWijzigenScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
        this.controller = new ConfiguratieController();
        choOphalen.setItems(FXCollections.observableArrayList(Arrays.asList(Tijdstip.values())));
        choAfhalen.setItems(FXCollections.observableArrayList(Arrays.asList(Tijdstip.values())));
        this.txfMaximaleVerlenging.setText(""+controller.getSysteemConfiguratie().getMaximaleVerlenging());
        this.txfReservatieperiode.setText(""+controller.getSysteemConfiguratie().getReservatiePeriode());
        choAfhalen.getSelectionModel().select(this.controller.getSysteemConfiguratie().getMomentBinnenbrengen());
        choOphalen.getSelectionModel().select(this.controller.getSysteemConfiguratie().getMomentOphalen());
    }

    @FXML
    private void btnDoelgroepenOnAction(ActionEvent event) {
        Stage stage = new Stage();
        this.disableOwnerScherm(stage);
        DoelgroepWijzigenSchermController c = new DoelgroepWijzigenSchermController(controller);
        Scene scene = new Scene(c);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnLeergebiedenOnAction(ActionEvent event) {
        Stage stage = new Stage();
        this.disableOwnerScherm(stage);
        LeergebiedWijzigenSchermController c = new LeergebiedWijzigenSchermController(controller);
        Scene scene = new Scene(c);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnWijzigenOnAction(ActionEvent event) {
        boolean error = true;
        StringBuilder builder = new StringBuilder();
        try{
        if(this.txfMaximaleVerlenging.getText().isEmpty() || txfMaximaleVerlenging.getText() == null){
            builder.append("\nMaximale verlening is verplicht");
            error = false;
        }else if(Integer.parseInt(this.txfMaximaleVerlenging.getText()) <= 0){
            builder.append("\nMaximale verlening moet positief zijn");
            error = false;
        }}catch(NumberFormatException ex){
            error = false;
            builder.append("\nMaximale verleningen moet een getal zijn");
        }
        try{
        if(this.txfReservatieperiode.getText().isEmpty() || txfReservatieperiode.getText() == null){
            builder.append("\nReservatieperiode is verplicht");
            error = false;
        }else if(Integer.parseInt(this.txfReservatieperiode.getText()) <= 0){
            builder.append("\nReservatieperiode moet positief zijn");
            error = false;
        }}catch(NumberFormatException ex){
            error=false;
            builder.append("\nReservatieperiode moet een getal zijn");
        }
        if(choAfhalen.getSelectionModel().getSelectedItem() == null){
            builder.append("\nMoment van afhalen is verplicht");
            error = false;
        }
        if(choOphalen.getSelectionModel().getSelectedItem() == null){
            builder.append("\nMoment van ophalen is verplicht");
            error = false;
        }
        lblError.setText(builder.toString());
        if(error){
            controller.wijzigConfiguratie(choAfhalen.getSelectionModel().getSelectedItem(),choOphalen.getSelectionModel().getSelectedItem(),Integer.parseInt(txfMaximaleVerlenging.getText()),Integer.parseInt(txfReservatieperiode.getText()));
                ((Node) event.getSource()).getScene().getWindow().hide();
        }
    }

    @FXML
    private void btnAnnulerenOnAction(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    private void disableOwnerScherm(Stage stage)
    {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.getScene().getWindow());
    }
}
