/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ConfiguratieController;
import domein.Leergebied;
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
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Remko
 */
public class LeergebiedWijzigenSchermController extends GridPane {

    @FXML
    private Button btnLeergebiedToevoegen;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnAnnuleren;
    @FXML
    private ListView<Leergebied> listLeergebieden;
    @FXML
    private Label lblError;
    @FXML
    private Button btnVerwijderen;
    private ConfiguratieController controller;
    
    private List<Leergebied> leergebieden;
    private List<Leergebied> leergebiedenVerwijderd;

    public LeergebiedWijzigenSchermController(ConfiguratieController controller) {
        this.controller = controller;
        leergebieden = new ArrayList<>();
        leergebiedenVerwijderd = new ArrayList<>();
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("LeergebiedWijzigenScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        listLeergebieden.setItems(controller.getSortedLeergebieden());
        listLeergebieden.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void leergebiedToevoegen(ActionEvent event) {
        TextInputDialog venster = new TextInputDialog();
        venster.setGraphic(null);
        venster.setHeaderText(null);
        venster.setTitle("Maak nieuw leergebied");
        venster.setContentText("Naam:");
        Optional<String> result = venster.showAndWait();
        final Button okButton = (Button) venster.getDialogPane().lookupButton(ButtonType.OK);

        result.ifPresent(naam -> {
            Leergebied l = new Leergebied(naam);
            if (l.getNaam().trim().isEmpty() || l.getNaam() == null) {
                lblError.setText("Naam is verplicht");
            } else if (!controller.bevatLeergebied(l)) {
                controller.addLeergebied(l);
                leergebieden.add(l);
                lblError.setText("");
            } else {
                lblError.setText("Het leergebied " + naam + " bestaat al!");

            }
        });
    }

    @FXML
    private void okOnAction(ActionEvent event) {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void annulerenOnAction(ActionEvent event) {
        if(!leergebieden.isEmpty()){
            leergebieden.forEach(l -> this.controller.verwijderLeergebied(l));
        }
        if(!leergebiedenVerwijderd.isEmpty()){
            leergebiedenVerwijderd.forEach(l->this.controller.addLeergebied(l));
        }
        ((Node)event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void leergebiedVerwijderen(ActionEvent event) {
        Leergebied l = this.listLeergebieden.getSelectionModel().getSelectedItem();
        leergebiedenVerwijderd.add(l);
        this.controller.verwijderLeergebied(l);
    }

}
