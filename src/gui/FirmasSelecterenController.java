/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ProductBeherenController;
import domein.Firma;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
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
 * @author Maarten
 */
public class FirmasSelecterenController extends GridPane {
    
    @FXML
    private Button firmaToevoegen;
    @FXML
    private Button btnOk;
    @FXML
    private ListView<Firma> firmas;
    
    private ProductBeherenController controller;
    @FXML
    private Button btnAnnuleren;
    @FXML
    private Label lblError;
    
    public FirmasSelecterenController(ProductBeherenController controller) {
        this.controller = controller;
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("FirmasSelecteren.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        firmas.setItems(controller.getAlleFirmas());
        firmas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        firmas.getSelectionModel().select(this.controller.getGekozenFirma());
    }
    
    @FXML
    private void firmaToevoegen(ActionEvent event) {
        Dialog<Firma> dialog = new Dialog<>();
        dialog.setTitle("Maak firma");
        dialog.setHeaderText(null);
        dialog.setResizable(false);
        Label label1 = new Label("Naam: ");
        Label label2 = new Label("Website: ");
        Label label3 = new Label("Contactpersoon: ");
        Label label4 = new Label("E-mail: ");
        TextField naam = new TextField();
        TextField website = new TextField();
        TextField contactPersoon = new TextField();
        TextField emailContactPersoon = new TextField();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(label1, 1, 1);
        grid.add(naam, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(website, 2, 2);
        grid.add(label3, 1, 3);
        grid.add(contactPersoon, 2, 3);
        grid.add(label4, 1, 4);
        grid.add(emailContactPersoon, 2, 4);
        dialog.getDialogPane().setContent(grid);
        
        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        
        dialog.setResultConverter(new Callback<ButtonType, Firma>() {
            @Override
            public Firma call(ButtonType b) {
                if (b == buttonTypeOk) {
                    return new Firma(naam.getText(), website.getText(), contactPersoon.getText(), emailContactPersoon.getText());
                }
                return null;
            }
        });
        Optional<Firma> result = dialog.showAndWait();
        if (result.isPresent()) {
            
            Firma f = result.get();
            if (controller.bevatFirma(f)) {
                lblError.setText("Firma " + f.getNaam().trim() + " bestaat al!");
            } else {
                if (f.getNaam().trim().isEmpty() || f.getNaam() == null) {
                    lblError.setText("Naam is verplicht");
                } else if (!f.getWebsite().isEmpty() && f.getWebsite() != null && !Pattern.compile("(https?:\\/\\/(?:www\\.|(?!www))[^\\s\\.]+\\.[^\\s]{2,}|www\\.[^\\s]+\\.[^\\s]{2,})").matcher(f.getWebsite()).find()) {
                    lblError.setText("Foutieve website");
                } else if (!f.getEmailContactPersoon().isEmpty() && f.getEmailContactPersoon() != null && !Pattern.compile("[A-Za-z0-9]*@[A-Za-z0-9]*\\.[a-z]{2,}").matcher(f.getEmailContactPersoon()).find()) {
                    lblError.setText("Foutieve email");
                } else {
                    controller.addFirma(f);
                    lblError.setText("");
                }
            }
        }
    }
    
    @FXML
    private void okOnAction(ActionEvent event) {
        Firma selectie = firmas.getSelectionModel().getSelectedItem();
        if (selectie != null) {
            //firmas.getSelectionModel().clearSelection();
            controller.setGekozenFirma(selectie);
        }
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
    @FXML
    private void mouseClickedFirma(MouseEvent event) {
    }
    
    @FXML
    private void annulerenOnAction(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
    
}
