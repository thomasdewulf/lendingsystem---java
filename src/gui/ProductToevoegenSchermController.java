/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ProductBeherenController;
import controllers.ProductToevoegenController;
import domein.Beheerder;
import domein.Observer;
import domein.Subject;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class ProductToevoegenSchermController extends GridPane implements Subject
{

    @FXML
    private TextField txfNaam;
    @FXML
    private TextArea txfOmschrijving;
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
    private Button btnToevoegen;
    @FXML
    private Button btnAnnuleren;
    @FXML
    private Label lblFoutbericht;
    @FXML
    private Button btnFirma;

    private ProductBeherenController controller;
    private Beheerder beheerder;
    
    private Set<Observer> observers = new HashSet<>();

    public ProductToevoegenSchermController(ProductToevoegenController controller, Beheerder beheerder, ProductOverzichtController overzicht)
    {
        this.controller = controller;
        this.beheerder = beheerder;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductToevoegenScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {

            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }

        this.btnAnnuleren.setOnAction(this::annuleren);
        this.btnDoelgroepen.setOnAction(this::toonDoelgroepen);
        this.btnLeergebieden.setOnAction(this::toonLeergebieden);
        this.btnToevoegen.setOnAction(this::toevoegen);
        this.btnFirma.setOnAction(this::toonFirmas);
        addObserver(overzicht);
    }

    public void annuleren(ActionEvent event)
    {
        System.out.println("annuleren");
        ((Node) event.getSource()).getScene().getWindow().hide();
//        Stage stage = new Stage();
//        Scene scene = new Scene(new ContainerController(stage, beheerder));
//        stage.setScene(scene);
//        stage.show();
    }

    public void toonDoelgroepen(ActionEvent event)
    {
        Stage stage = new Stage();
        DoelgroepenSelecterenController doelgroepController = new DoelgroepenSelecterenController(controller);
        Scene scene = new Scene(doelgroepController);
        stage.setScene(scene);
        stage.show();
        System.out.println("doelgroepen");
        disableOwnerScherm(stage);
    }

    public void toonLeergebieden(ActionEvent event)
    {
        Stage stage = new Stage();
        LeergebiedenSelecterenController leergebiedController = new LeergebiedenSelecterenController(controller);
        Scene scene = new Scene(leergebiedController);
        stage.setScene(scene);
        stage.show();
        System.out.println("leergebieden");
        disableOwnerScherm(stage);
    }

    public void toevoegen(ActionEvent event)
    {
        boolean error = true;
        StringBuilder builder = new StringBuilder();
        if (txfNaam.getText().trim().isEmpty() || txfNaam.getText() == null)
        {
            builder.append("Naam is verplicht");
            error = false;
        }
        if (txfAantal.getText().trim().isEmpty())
        {
            error = false;
            if(builder.length() == 0){
                builder.append("Aantal is verplicht");
            } else
            {
                builder.append("\nAantal is verplicht");
            }
        } 

        if (controller.getGekozenDoelgroepen().isEmpty() || controller.getGekozenDoelgroepen() == null)
        {
            error = false;
            builder.append("\nDoelgroep(en) is(zijn) verplicht");
        }
        if (controller.getGekozenLeergebieden().isEmpty() || controller.getGekozenLeergebieden() == null)
        {
            error = false;
            builder.append("\nLeergebied(en) is(zijn) verplicht");
        }
        if (controller.getGekozenFirma() == null)
        {
            error = false;
            builder.append("\nFirma is verplicht");
        }
        if(error && this.controller.bevatProduct(txfNaam.getText().trim())){
            error = false;
            builder.append("\nProduct is al aanwezig in de databank");
        }
        lblFoutbericht.setText(builder.toString());
        if (error)
        {
            double prijs = 0.0;
            int aantalInCatalogus = 1;
            int aantalProductStukken = 1;
            String naam = txfNaam.getText();
            String artikelNummer = txfArtikelNummer.getText();
            String foto = "";
            String omschrijving = txfOmschrijving.getText();
            try
            {
                prijs = Double.parseDouble(txfPrijs.getText());
            } catch (NumberFormatException n)
            {
            }
            try
            {
                aantalInCatalogus = Integer.parseInt(txfAantal.getText());
            } catch (NumberFormatException n)
            {
            }
            try
            {
                aantalProductStukken = Integer.parseInt(txfAantalOnbeschikbaar.getText());
            } catch (NumberFormatException n)
            {
            }
            boolean uitleenbaar = chbUitleenbaar.isSelected();
            String plaats = txfPlaats.getText();
            System.out.println(artikelNummer);
            controller.beheerProduct(foto, naam, omschrijving, artikelNummer, prijs, aantalInCatalogus, aantalProductStukken, uitleenbaar, plaats, "");
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
        notifyObservers();
    }

    public void toonFirmas(ActionEvent event)
    {
        Stage stage = new Stage();
        FirmasSelecterenController firmaController = new FirmasSelecterenController(controller);
        Scene scene = new Scene(firmaController);
        stage.setScene(scene);
        stage.show();
        System.out.println("firmas");
        disableOwnerScherm(stage);
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
        observers.forEach(observer -> observer.update(null));
    }
    
    private void disableOwnerScherm(Stage stage)
    {
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.getScene().getWindow());
    }

}
