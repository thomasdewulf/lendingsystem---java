/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Doelgroep;
import domein.Leergebied;
import domein.Product;
import java.io.IOException;
import java.util.Observable;
import domein.Observer;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Maarten
 */
public class DetailProductController extends GridPane implements Observer
{

    @FXML
    private Button btnWijzigen;
    @FXML
    private Label lblNaam;
    @FXML
    private Label lblOmschrijving;
    @FXML
    private Label lblPrijs;
    @FXML
    private Label lblAantal;
    @FXML
    private Label lblAantalOnbeschikbaar;
    @FXML
    private CheckBox chbUitleenbaar;
    @FXML
    private Label lblPlaats;
    @FXML
    private Label lblDoelgroepen;
    @FXML
    private Label lblLeergebieden;
    @FXML
    private Label lblFirma;

    private Product product;
    private ProductOverzichtController overzichtController;
    @FXML
    private Label lblArtikelnummer;

    public DetailProductController(ProductOverzichtController controller)
    {
        overzichtController = controller;
        controller.addObserver(this);
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("DetailProduct.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
        btnWijzigen.setDisable(true);
    }

    @FXML
    private void btnWijzigenOnAction(ActionEvent event)
    {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.getScene().getWindow());

        ProductWijzigenSchermController wijzigenController = new ProductWijzigenSchermController(product, overzichtController);
        Scene scene = new Scene(wijzigenController);
        stage.setScene(scene);
        stage.show();
        System.out.println("wijzigen");
    }

//    public void update(Product p)
//    {
//        if (p != null)
//        {
//            btnWijzigen.setDisable(false);
//
//            product = p;
//            lblLeergebieden.setText(p.getLeergebieden().stream().map(Leergebied::toString).collect(Collectors.joining("\n")));
//            lblDoelgroepen.setText(p.getDoelgroepen().stream().map(Doelgroep::toString).collect(Collectors.joining("\n")));
//            lblNaam.setText(p.getArtikelNaam());
//            lblAantal.setText(String.format("%d", p.getAantalInCatalogus()));
//            lblAantalOnbeschikbaar.setText(String.format("%d", p.getAantalProductStukken()));
//            lblFirma.setText(p.getFirma().toString());
//            lblOmschrijving.setText(p.getOmschrijving());
//            lblPlaats.setText(p.getPlaats());
//            lblPrijs.setText(String.format("€ %.2f", p.getPrijs()));
//            chbUitleenbaar.setSelected(p.isUitleenbaar());
//        }
//    }

    @Override
    public void update(Object object) {
         if (object != null)
        {
            btnWijzigen.setDisable(false);

            product = (Product) object;
            lblLeergebieden.setText(product.getLeergebieden().stream().map(Leergebied::toString).collect(Collectors.joining("\n")));
            lblDoelgroepen.setText(product.getDoelgroepen().stream().map(Doelgroep::toString).collect(Collectors.joining("\n")));
            lblNaam.setText(product.getArtikelNaam());
            lblAantal.setText(String.format("%d", product.getAantalInCatalogus()));
            lblAantalOnbeschikbaar.setText(String.format("%d", product.getAantalProductStukken()));
            lblFirma.setText(product.getFirma().toString());
            lblOmschrijving.setText(product.getOmschrijving());
            lblPlaats.setText(product.getPlaats());
            lblPrijs.setText(String.format("€ %.2f", product.getPrijs()));
            chbUitleenbaar.setSelected(product.isUitleenbaar());
            lblArtikelnummer.setText(product.getArtikelNummer());
        }
    }


}
