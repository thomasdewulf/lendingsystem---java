/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Observer;
import domein.Reservatie;
import domein.ReservatieLijn;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author De Puysseleyr
 */
public class ReservatieDetailController extends GridPane implements Observer
{

    @FXML
    private Button btnWijzigen;
    @FXML
    private Label lblOphaaldatum;
    @FXML
    private Label lblIndiendatum;

    private Reservatie reservatie;
    @FXML
    private Label lblAantalUitgeleend;
    @FXML
    private Label lblAantalTeruggebracht;
    @FXML
    private Label lblReservatieStatus;
    private ReservatieOverzichtController controller;

    public ReservatieDetailController(ReservatieOverzichtController controller)
    {
        this.controller = controller;
        controller.addObserver(this);
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("ReservatieDetail.fxml"));
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

        ReservatieWijzigenSchermController wijzigenController = new ReservatieWijzigenSchermController(reservatie, this, controller);
        Scene scene = new Scene(wijzigenController);
        stage.setScene(scene);
        stage.show();
        System.out.println("wijzigen");

    }

    @Override
    public void update(Object object)
    {
        if (object != null)
        {
            btnWijzigen.setDisable(false);

            reservatie = (Reservatie) object;
            Collection<ReservatieLijn> lijnen = reservatie.getReservatieLijnen();
            lblOphaaldatum.setText(String.format("%s", reservatie.getStartDatum()));
            lblIndiendatum.setText(String.format("%s", reservatie.getEindDatum()));

            lblAantalUitgeleend.setText(lijnen.stream().map((lijn) -> String.format("%s: %d", lijn.getProduct().getArtikelNaam(),
                    lijn.getAantal())).collect(Collectors.joining("\n")));

            lblAantalTeruggebracht.setText(lijnen.stream().map((lijn) -> String.format("%s: %d", lijn.getProduct().getArtikelNaam(),
                    lijn.getAantalTeruggebracht())).collect(Collectors.joining("\n")));
            lblReservatieStatus.setText(String.format("%s",reservatie.getReservatieStatus()));

        }
    }

}
