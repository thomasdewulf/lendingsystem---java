/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ProductToevoegenController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dewul
 */
public class ProductenToevoegenController extends VBox
{

    @FXML
    private Button btnBestandKiezen;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnAnnuleren;

    private ProductToevoegenController controller;
    @FXML
    private Label lblPath;

    private File file;

    public ProductenToevoegenController(ProductToevoegenController controller)
    {
        this.controller = controller;
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("ProductenToevoegen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }

        btnBestandKiezen.setOnAction(this::bestandKiezen);
    }

    private void bestandKiezen(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".csv", "*.csv"), new FileChooser.ExtensionFilter("All files", "*.*"));

        this.file = fileChooser.showOpenDialog(new Stage()).getAbsoluteFile();

        lblPath.setText(this.file.getAbsolutePath());

    }

    @FXML
    private void btnOkOnAction(ActionEvent event)
    {
        if (file != null)
        {
            controller.leesCSV(file);
            this.getScene().getWindow().hide();
        }
    }

    @FXML
    private void btnAnnulerenOnAction(ActionEvent event)
    {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

}
