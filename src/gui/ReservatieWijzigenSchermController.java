/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ReservatieWijzigenController;
import domein.Product;
import domein.Reservatie;
import domein.ReservatieLijn;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author dewul
 */
public class ReservatieWijzigenSchermController extends GridPane
{

    @FXML
    private TextField txfAfhaalUur;
    @FXML
    private Button btnWijzigen;
    @FXML
    private Button btnAnnuleren;
    @FXML
    private Button btnVerwijderen;
    @FXML
    private Label lblFoutbericht;
    @FXML
    private DatePicker datePickerOphaaldatum;
    @FXML
    private TextField txfOphaaluur;
    @FXML
    private DatePicker datePickerAfhaaldatum;
    @FXML
    private TableView<ReservatieLijn> tableViewProducten;
    @FXML
    private TableColumn<ReservatieLijn, String> tableColumnNaam;
    @FXML
    private TableColumn<ReservatieLijn, String> tableColumnAantalGeleend;
    @FXML
    private TableColumn<ReservatieLijn, String> tableColumnAantalterug;

    private Reservatie reservatie;
    private ReservatieWijzigenController controller;
    private ReservatieDetailController detail;
    private ReservatieOverzichtController overzicht;

    public ReservatieWijzigenSchermController(Reservatie reservatie, ReservatieDetailController detail, ReservatieOverzichtController overzichtController)
    {
        this.overzicht = overzichtController;
        this.reservatie = reservatie;
        this.detail = detail;
        this.controller = new ReservatieWijzigenController(reservatie);
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("ReservatieWijzigenScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }

        tableViewProducten.setItems(controller.getLijnen());
        tableColumnNaam.setCellValueFactory(cellData -> cellData.getValue().getProduct().naamProperty());
        tableColumnAantalGeleend.setCellValueFactory(cellData -> cellData.getValue().aantalProperty());
        tableColumnAantalGeleend.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnAantalterug.setCellFactory(TextFieldTableCell.forTableColumn());

        tableColumnAantalterug.setCellValueFactory(cellData -> cellData.getValue().aantalTeruggebrachtProperty());
        tableColumnAantalGeleend.setOnEditCommit(this::editAantalUitgeleend);
        tableColumnAantalterug.setOnEditCommit(this::editAantalTerugGebracht);
//        LocalDate afhaalDatum = this.reservatie.getStartDatum().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate terugBrengDatum = this.reservatie.getEindDatum().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        datePickerOphaaldatum.setValue(reservatie.getStartDatum().toLocalDate());
        datePickerAfhaaldatum.setValue(reservatie.getEindDatum().toLocalDate());
    }

    @FXML
    private void btnWijzigenOnAction(ActionEvent event)
    {
        StringBuilder errorMessage = new StringBuilder();
        boolean error = false;

        Date startDatum = Date.valueOf(datePickerOphaaldatum.getValue());
        Date eindDatum = Date.valueOf(datePickerAfhaaldatum.getValue());
        if (eindDatum.before(startDatum))
        {
            error = true;
            errorMessage.append("Einddatum kan niet voor begindatum liggen\n");

        }
        java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        if (startDatum.before(today))
        {
            error = true;
            errorMessage.append("Startdatum kan niet in het verleden liggen\n");
        }
        if (eindDatum.before(today))
        {
            error = true;
            errorMessage.append("Einddatum kan niet in het verleden liggen\n");
        }
        if (error)
        {
            lblFoutbericht.setText(errorMessage.toString());
        } else
        {
            reservatie.setStartDatum(startDatum);
            reservatie.setEindDatum(eindDatum);
            controller.wijzigReservatie(reservatie);
            detail.update(reservatie);
            overzicht.update(null);
            this.getScene().getWindow().hide();
        }
    }

    @FXML
    private void btnAnnulerenOnAction(ActionEvent event)
    {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    private void btnVerwijderenOnAction(ActionEvent event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Opgelet");
        alert.setHeaderText("Bent u zeker dat u deze reservatie wil verwijderen?");
        alert.setContentText("Wanneer u op OK drukt is de reservatie definitief verwijdert.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            controller.verwijderReservatie(reservatie);
            overzicht.update(null);
        } else
        {
            alert.close();
        }
        ((Node) event.getSource()).getScene().getWindow().hide();
        
    }

    private void editAantalUitgeleend(TableColumn.CellEditEvent<ReservatieLijn, String> event)
    {
        int aantalUitgeleend = 0;
        try
        {
            aantalUitgeleend = Integer.parseInt(event.getNewValue());
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setAantal(aantalUitgeleend);
            if(aantalUitgeleend < 0)
            {
                lblFoutbericht.setText("Gelieve een positief getal in te voeren");
            }
            detail.update(reservatie);
        } catch (NumberFormatException ex)
        {
            lblFoutbericht.setText("Gelieve een nummer in te geven bij \"Aantal uitgeleend\"");
        } finally //Fixed een bug in JavaFX waarbij de data in de tableview altijd wordt aangepast
        {
            tableViewProducten.getColumns().get(0).setVisible(false);
            tableViewProducten.getColumns().get(0).setVisible(true);
        }

    }

    private void editAantalTerugGebracht(TableColumn.CellEditEvent<ReservatieLijn, String> event)
    {
        int aantalTeruggebracht = 0;
        try
        {

            aantalTeruggebracht = Integer.parseInt(event.getNewValue());
            if(aantalTeruggebracht < 0)
            {
                lblFoutbericht.setText("Gelieve een positief getal in te voeren");
            }
            if (aantalTeruggebracht > event.getTableView().getItems().get(event.getTablePosition().getRow()).getAantal())
            {
                throw new IllegalArgumentException("Aantal teruggebracht kan niet groter zijn dan aantal uitgeleend");
            }
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setAantalTeruggebracht(aantalTeruggebracht);

            detail.update(reservatie);
        } catch (NumberFormatException ex)
        {
            lblFoutbericht.setText("Gelieve een nummer in te geven bij \"Aantal teruggebracht\"");
        } catch (IllegalArgumentException ex)
        {
            lblFoutbericht.setText(ex.getMessage());
        } finally //Fixed een bug in JavaFX waarbij de data in de tableview altijd wordt aangepast
        {
            tableViewProducten.getColumns().get(0).setVisible(false);
            tableViewProducten.getColumns().get(0).setVisible(true);
        }

    }

}
