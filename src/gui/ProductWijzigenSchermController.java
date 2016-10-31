/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ProductBeherenController;
import controllers.ProductWijzigenController;
import domein.Observer;
import domein.Product;
import domein.Subject;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Maarten
 */
public class ProductWijzigenSchermController extends GridPane implements Subject
{

    @FXML
    private TextField txfNaam;
    @FXML
    private TextField txfArtikelNummer;
    @FXML
    private TextField txfPrijs;
    @FXML
    private TextField txfAantal;
    @FXML
    private TextField txfAantalOnbeschikbaar;
    @FXML
    private TextField txfPlaats;
    @FXML
    private CheckBox chbUitleenbaar;
    @FXML
    private Button btnDoelgroepen;
    @FXML
    private Button btnLeergebieden;
    @FXML
    private Button btnWijzigen;
    @FXML
    private Button btnAnnuleren;
    @FXML
    private TextArea txfOmschrijving;
    @FXML
    private Label lblFoutbericht;
    @FXML
    private Button btnFirma;
    @FXML
    private Button btnVerwijderen;

    private ProductBeherenController controller;
    private Product origineelProduct;
    private Set<Observer> observers = new HashSet<>();

    public ProductWijzigenSchermController(Product product, ProductOverzichtController overzicht)
    {
        origineelProduct = product;
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("ProductWijzigenScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
        this.controller = new ProductWijzigenController(product);
        this.txfNaam.setText(product.getArtikelNaam());
        this.txfOmschrijving.setText(product.getOmschrijving());
        this.txfArtikelNummer.setText(product.getArtikelNummer());
        this.txfPrijs.setText(String.format("%.2f", product.getPrijs()));
        this.txfAantal.setText(String.format("%d", product.getAantalInCatalogus()));
        this.txfAantalOnbeschikbaar.setText(String.format("%d", product.getAantalProductStukken()));
        this.chbUitleenbaar.setSelected(product.isUitleenbaar());
        this.txfPlaats.setText(product.getPlaats());
        this.addObserver(overzicht);
    }

    @FXML
    private void btnWijzigenOnAction(ActionEvent event)
    {
        boolean error = true;
        StringBuilder builder = new StringBuilder();
        lblFoutbericht.setText("");
        if (txfNaam.getText().trim().isEmpty())
        {
            builder.append("Naam is verplicht");
            error = false;
        }
        if (txfAantal.getText().trim().isEmpty())
        {
            error = false;
            if (builder.length() == 0)
            {
                builder.append("Aantal is verplicht");
            } else
            {
                builder.append("\nAantal is verplicht");
            }
        }
        try
        {
            if (Integer.parseInt(txfAantal.getText()) < 0)
            {
                error = false;
                builder.append("\nAantal moet groter zijn dan 0");
            }
            if (Integer.parseInt(txfAantalOnbeschikbaar.getText()) < 0)
            {
                error = false;
                builder.append("\nAantal onbeschikbaar moet groter zijn dan 0");
            }
            if (Double.parseDouble(txfPrijs.getText().replace(",", ".")) < 0)
            {
                error = false;
                builder.append("\nPrijs moet groter zijn dan 0");
            }
        } catch (NumberFormatException n)
        {
            error = false;
            builder.append("\nGelieve een getal in te voeren");
        }
        if (controller.geenGekozenDoelgroep())
        {
            error = false;
            builder.append("\nDoelgroep(en) is(zijn) verplicht");
        }
        if (controller.geenGekozenLeergebied())
        {
            error = false;
            builder.append("\nLeergebied(en) is(zijn) verplicht");
        }
        if (controller.getGekozenFirma() == null)
        {
            error = false;
            builder.append("\nFirma is verplicht");
        }
        lblFoutbericht.setText(builder.toString());
        if (error)
        {
            double prijs;
            int aantalInCatalogus;
            int aantalProductStukken;
            String naam = txfNaam.getText();
            String artikelNummer = txfArtikelNummer.getText();
            String foto = origineelProduct.getFoto();
            String omschrijving = txfOmschrijving.getText();
            try
            {
                prijs = Double.parseDouble(txfPrijs.getText().replace(",", "."));
            } catch (NumberFormatException n)
            {
                prijs = 0.0;
            }
            try
            {
                aantalInCatalogus = Integer.parseInt(txfAantal.getText());
            } catch (NumberFormatException n)
            {
                aantalInCatalogus = 1;
            }
            try
            {
                aantalProductStukken = Integer.parseInt(txfAantalOnbeschikbaar.getText());
            } catch (NumberFormatException n)
            {
                aantalProductStukken = 0;
            }
            boolean uitleenbaar = chbUitleenbaar.isSelected();
            String plaats = txfPlaats.getText();
            System.out.println(artikelNummer);
            controller.beheerProduct(foto, naam, omschrijving, artikelNummer, prijs, aantalInCatalogus, aantalProductStukken, uitleenbaar, plaats, origineelProduct.getArtikelNaam());
            this.getScene().getWindow().hide();
        }
        notifyObservers();
    }

    @FXML
    private void btnAnnulerenOnAction(ActionEvent event)
    {
        notifyObservers();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void btnDoelgroepenOnAction(ActionEvent event)
    {
        Stage stage = new Stage();
        this.disableOwnerScherm(stage);
        DoelgroepenSelecterenController doelgroepController = new DoelgroepenSelecterenController(controller);
        Scene scene = new Scene(doelgroepController);
        stage.setScene(scene);
        stage.show();
        System.out.println("doelgroepen");
    }

    @FXML
    private void btnLeergebiedenOnAction(ActionEvent event)
    {
        Stage stage = new Stage();
        this.disableOwnerScherm(stage);
        LeergebiedenSelecterenController leergebiedController = new LeergebiedenSelecterenController(controller);
        Scene scene = new Scene(leergebiedController);
        stage.setScene(scene);
        stage.show();
        System.out.println("leergebieden");
    }

    @FXML
    private void btnFirmaOnAction(ActionEvent event)
    {
        Stage stage = new Stage();
        this.disableOwnerScherm(stage);
        FirmasSelecterenController firmaController = new FirmasSelecterenController(controller);
        Scene scene = new Scene(firmaController);
        stage.setScene(scene);
        stage.show();
        System.out.println("firmas");
    }

    @FXML
    private void btnVerwijderenOnAction(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Opgelet");
        alert.setHeaderText("Bent u zeker dat u het product " + origineelProduct.getArtikelNaam() + " wilt verwijderen ?");
        alert.setContentText("Wanneer u op OK drukt is het product definitief verwijdert.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            controller.verwijderProduct(origineelProduct);
            ((Node) event.getSource()).getScene().getWindow().hide();
        } else
        {
            alert.close();
        }
        notifyObservers();
    }

    private void disableOwnerScherm(Stage stage)
    {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.getScene().getWindow());
    }

    @Override
    public void addObserver(Observer observer)
    {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer)
    {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers()
    {
        observers.forEach(observer -> observer.update(origineelProduct));
    }

}
