/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.sun.javafx.scene.control.skin.TableViewSkinBase;
import controllers.ReservatieController;
import domein.Beheerder;
import domein.Catalogus;
import domein.Observer;
import domein.Reservatie;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author De Puysseleyr
 */
public class ReservatieOverzichtController extends GridPane implements Subject, Observer
{

    @FXML
    private GridPane gridOverzicht;
    @FXML
    private TableView<Reservatie> tblReservaties;
    @FXML
    private TableColumn<Reservatie, String> tbcNaam;
    @FXML
    private TableColumn<Reservatie, String> tbcVoornaam;
    @FXML
    private TableColumn<Reservatie, String> tbcOphaalDatum;
    @FXML
    private TableColumn<Reservatie, String> tbcIndienDatum;

    private ReservatieController controller;
    @FXML
    private Button btnAnnuleer;

    private Set<Observer> observers = new HashSet<>();
    private Reservatie reservatie;

    private Beheerder beheerder;

    public ReservatieOverzichtController(Beheerder beheerder)
    {
        this.beheerder = beheerder;
        controller = new ReservatieController(new Catalogus());

        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("ReservatieOverzicht.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }

        tbcNaam.setCellValueFactory(cellData -> cellData.getValue().getUser().naamProperty());
        tbcVoornaam.setCellValueFactory(cellData -> cellData.getValue().getUser().voornaamProperty());
        tbcOphaalDatum.setCellValueFactory(cellData -> cellData.getValue().startDatumProperty());
        tbcIndienDatum.setCellValueFactory(cellData -> cellData.getValue().eindDatumProperty());

        tblReservaties.setItems(controller.getReservatieSortedList());
        ReservatieDetailController detailController = new ReservatieDetailController(this);
        gridOverzicht.add(detailController, 2, 0);
        tblReservaties.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, oldValue, newValue)
                        ->
                        {
                            if (newValue != null)
                            {
                                reservatie = newValue;
                                notifyObservers();
                            }
                });

    }

    @FXML
    private void btnAnnulerenOnAction(ActionEvent event)
    {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        Scene scene = new Scene(new ContainerController(stage, beheerder));
        stage.setScene(scene);
        stage.show();
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
        observers.forEach(observer -> observer.update(reservatie));
    }

    @Override
    public void update(Object object)
    {
        this.tblReservaties.setItems(controller.getReservatieSortedList());
        this.tblReservaties.refresh();
    }

}
