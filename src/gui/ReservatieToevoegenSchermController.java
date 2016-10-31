/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ReservatieToevoegenController;
import domein.Observer;
import domein.ReservatieLijn;
import domein.ReservatieStatus;
import domein.Subject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Calendar;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author De Puysseleyr
 */
public class ReservatieToevoegenSchermController extends GridPane implements Subject{

    @FXML
    private DatePicker datePickerStartdatum;
    @FXML
    private DatePicker datePickerEinddatum;
    @FXML
    private Button btnToevoegen;
    @FXML
    private Button btnAnnuleren;
    @FXML
    private ChoiceBox<String> cboxKeuzeReservering;
    @FXML
    private Button btnMaterialenToevoegen;
    @FXML
    private Button btnUitlenerKiezen;

    @FXML
    private Label lblNaamUitlener;

    private ReservatieToevoegenController controller;

    private ObservableList<String> keuzeReservering = FXCollections.observableArrayList("Reservatie", "Blokkering");
    @FXML
    private Label lblFoutbericht;
    
    private Set<Observer> observers = new HashSet<>();

    public ReservatieToevoegenSchermController(ReservatieToevoegenController controller, ReservatieOverzichtController overzicht) {
        this.controller = controller;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReservatieToevoegenScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {

            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        cboxKeuzeReservering.setItems(keuzeReservering);
        lblNaamUitlener.textProperty().bind(controller.getPersoneelProperty());
        addObserver(overzicht);
    }

    @FXML
    private void btnToevoegenOnAction(ActionEvent event) {
        boolean error = true;
        StringBuilder builder = new StringBuilder();
        lblFoutbericht.setText("");
        
        LocalDate vandaag = LocalDate.now();

        if(!(datePickerStartdatum.getValue() instanceof ChronoLocalDate) || !(datePickerEinddatum.getValue() instanceof ChronoLocalDate))
        {
            error = false;
            builder.append("\nGelieve een datum van geldig formaat in te geven");
        }
        if (datePickerStartdatum.getValue() == null) {
            error = false;
            builder.append("\nStartdatum mag niet leeg zijn");
        }
        if (datePickerEinddatum.getValue() == null) {
            error = false;
            builder.append("\nEinddatum mag niet leeg zijn");
        }
        if (datePickerStartdatum.getValue() != null && datePickerEinddatum.getValue() != null) {
            if (!datePickerStartdatum.getValue().isBefore(datePickerEinddatum.getValue())) {
                error = true;
                builder.append("\nEinddatum kan niet voor begindatum liggen");
                
            }
            if (datePickerStartdatum.getValue().isBefore(vandaag))
            {
                error = true;
                builder.append("\nStartdatum kan niet in het verleden liggen");
            }
            if (datePickerEinddatum.getValue().isBefore(vandaag)) {
                error = true;
                builder.append("\nEinddatum kan niet in het verleden liggen");
            }
        }

        if (cboxKeuzeReservering.getValue() == null) {
            error = false;
            builder.append("\nJe moet een keuze maken tussen reservering en blokkering");
        }
        if (controller.getGekozenPersoneel() == null) {
            builder.append("\nUitlener mag niet leeg zijn");
            error = false;
        }
        if (controller.getGekozenReservatieLijnen().isEmpty()) {
            builder.append("\nGelieve materiaal te kiezen");
            error = false;
        }

        
        lblFoutbericht.setText(builder.toString());
        if(error){
        Date startDatum = Date.valueOf(datePickerStartdatum.getValue());
        Date eindDatum = Date.valueOf(datePickerEinddatum.getValue());
        Date v = Date.valueOf(vandaag);
        controller.getGekozenReservatieLijnen().stream().forEach(lijn -> pasAantalBeschikbaarAan(lijn));
        controller.voegReservatieToe(startDatum,eindDatum,v,ReservatieStatus.GERESERVEERD,
                cboxKeuzeReservering.getValue(),controller.getGekozenReservatieLijnen(),controller.getGekozenPersoneel());
        controller.setGekozenPersoneel(null); 
        ((Node) event.getSource()).getScene().getWindow().hide();
        }
        notifyObservers();
        
    }

    @FXML
    private void btnAnnulerenOnAction(ActionEvent event) {
        System.out.println("annuleren");
        controller.getGekozenReservatieLijnen().clear();
        controller.setGekozenPersoneel(null);
        ((Node) event.getSource()).getScene().getWindow().hide();
//        Stage stage = new Stage();
//        Scene scene = new Scene(new ContainerController(stage, beheerder));
//        stage.setScene(scene);
//        stage.show();
    }

    @FXML
    private void btnMaterialenToevoegenOnAction(ActionEvent event) {
        Stage stage = new Stage();
        MaterialenKiezenController materialenController = new MaterialenKiezenController(controller);
        Scene scene = new Scene(materialenController);
        stage.setScene(scene);
        stage.show();
        System.out.println("materialen kiezen");

    }

    @FXML
    private void btnUitlenerKiezenOnAction(ActionEvent event) {
        Stage stage = new Stage();
        UitlenerSelecterenController uitlenerController = new UitlenerSelecterenController(this, controller);
        Scene scene = new Scene(uitlenerController);
        stage.setScene(scene);
        stage.show();
        System.out.println("uitlener");

    }

    public void updateLabel() {
        lblNaamUitlener.textProperty().bind(controller.getPersoneelProperty());
        notifyObservers();
    }
    
    private void pasAantalBeschikbaarAan(ReservatieLijn lijn)
    {
        int aantal = lijn.getAantal();
        controller.pasAantalBeschikbaarAan(aantal, lijn);
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

}
