/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.ProductToevoegenController;
import controllers.ReservatieToevoegenController;
import domein.Beheerder;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManager;
import util.EntityManagerUtil;

/**
 * FXML Controller class
 *
 * @author dewul
 */
public class ContainerController extends BorderPane
{

    @FXML
    private MenuItem itemProductToevoegen;
    @FXML
    private MenuItem itemProductenToevoegen;
    @FXML
    private MenuItem itemReservatiesBekijken;
    @FXML
    private MenuItem itemBeheerdersBeheren;
    @FXML
    private MenuItem itemReservatieToevoegen;
    
    private Stage stage;
    private ProductToevoegenController controller;
    private Beheerder beheerder;
    private ReservatieToevoegenController resController;
    @FXML
    private MenuItem itemSysteemconfiguratieWijzigen;
    
    private ProductOverzichtController overzicht;
    private ReservatieOverzichtController overzichtReservaties;
    

    public ContainerController(Stage stage, Beheerder beheerder)
    {
        overzicht = new ProductOverzichtController();
        overzichtReservaties = new ReservatieOverzichtController(beheerder);
        EntityManager em = EntityManagerUtil.getEm();
        this.stage = stage;
        controller = new ProductToevoegenController();
        resController = new ReservatieToevoegenController();
        this.beheerder = beheerder;
        this.setCenter(overzicht);
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("Container.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
        if (!beheerder.isHoofdBeheerder())
        {
            itemBeheerdersBeheren.setDisable(true);
            itemSysteemconfiguratieWijzigen.setDisable(true);
        }
        stage.setOnCloseRequest((WindowEvent we) ->
        {
            System.out.println("EntityManager wordt gesloten");
            em.close();
        });
    }

    @FXML
    private void productToevoegenOnAction(ActionEvent event)
    {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.getScene().getWindow());
        Scene scene = new Scene(new ProductToevoegenSchermController(controller, beheerder, overzicht));
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        //this.setCenter(new ProductToevoegenSchermController(controller, beheerder));
    }

    @FXML
    private void productenToevoegenOnAction(ActionEvent event)
    {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.getScene().getWindow());
        Scene scene = new Scene(new ProductenToevoegenController(controller));
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

    @FXML
    private void reservatiesBekijkenOnAction(ActionEvent event)
    {
        this.setCenter(overzichtReservaties);
    }

    @FXML
    private void beheerdersBeherenOnAction(ActionEvent event)
    {
        this.setCenter(new BeheerderOverzichtController(beheerder));
    }

    @FXML
    private void reservatieToevoegenOnAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.getScene().getWindow());
        Scene scene = new Scene(new ReservatieToevoegenSchermController(resController, overzichtReservaties));
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

    @FXML
    private void configuratieWijzigen(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.getScene().getWindow());
        Scene scene = new Scene(new SysteemConfiguratieWijzigenSchermController());
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
    }

}
