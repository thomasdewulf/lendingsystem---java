/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.sun.javafx.scene.control.skin.TableViewSkinBase;
import controllers.BeheerderController;
import domein.Beheerder;
import domein.BeheerderType;
import domein.Observer;
import domein.Subject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Maarten
 */
public class BeheerderOverzichtController extends GridPane implements Observer, Subject {

    @FXML
    private TableView<Beheerder> tblBeheerders;
    @FXML
    private TableColumn<Beheerder, String> tbcNaam;
    @FXML
    private TableColumn<Beheerder, String> tbcVoornaam;
    @FXML
    private TableColumn<Beheerder, String> tbcEmail;
    @FXML
    private Button btnVoegBeheerderToe;
    @FXML
    private Button btnVerwijderBeheerder;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnAnnuleer;

    private Beheerder beheerder;
    private BeheerderController controller;
    private Beheerder hulp;
    private List<Beheerder> lijstBeheerders = new ArrayList<>();
    @FXML
    private Button btnWijzigBeheerder;
    @FXML
    private Label lblError;

    private Set<Observer> observers = new HashSet<>();

    public BeheerderOverzichtController(Beheerder beheerder) {
        this.beheerder = beheerder;
        this.controller = new BeheerderController();
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("BeheerderOverzicht.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        tbcNaam.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
        tbcVoornaam.setCellValueFactory(cellData -> cellData.getValue().voornaamProperty());
        tbcEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        tblBeheerders.setItems(controller.getBeheerders());
        tblBeheerders.setPlaceholder(new Label("Geen beheerders gevonden"));
        observers.add(this);
    }

    @FXML
    private void voegBeheerderToeOnAction(ActionEvent event) {
        Dialog<Beheerder> dialog = new Dialog<>();
        dialog.setTitle("Maak beheerder");
        dialog.setHeaderText(null);
        dialog.setResizable(false);
        Label label1 = new Label("Naam: ");
        Label label2 = new Label("Voornaam: ");
        Label label3 = new Label("E-mail:* ");
        TextField naam = new TextField();
        TextField voornaam = new TextField();
        TextField email = new TextField();
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(label1, 1, 1);
        grid.add(naam, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(voornaam, 2, 2);
        grid.add(label3, 1, 3);
        grid.add(email, 2, 3);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter((ButtonType b)
                -> {
            if (b == buttonTypeOk) {
                return new Beheerder(naam.getText(), voornaam.getText(), email.getText(), BeheerderType.BEHEERDER);
            }
            return null;
        });
        Optional<Beheerder> result = dialog.showAndWait();
        if (result.isPresent()) {
            hulp = result.get();
            boolean error = true;
            StringBuilder builder = new StringBuilder();
            if (hulp.getEmail().trim().isEmpty() || hulp.getEmail() == null) {
                builder.append("\nEmail is verplicht");
                error = false;
            } else if (controller.geefBeheerder(hulp.getEmail()) != null) {
                builder.append("\nBeheerder met email : " + email.getText() + " bestaat al");
                error = false;
            } else {

                if (!Pattern.compile("[A-Za-z0-9]*@[A-Za-z0-9]*\\.[a-z]{2,}").matcher(hulp.getEmail()).find()) {
                    builder.append("\nEmail is niet geldig");
                    error = false;
                }
                if (!(hulp.getNaam().trim().isEmpty() || hulp.getNaam() == null) && hulp.getNaam().matches(".*\\d+.*")) {
                    builder.append("\nNaam is niet geldig");
                    error = false;
                }
                if (!(hulp.getVoornaam().trim().isEmpty() || hulp.getVoornaam() == null) && hulp.getVoornaam().matches(".*\\d+.*")) {
                    builder.append("\nVoornaam is niet geldig");
                    error = false;
                }
            }
            lblError.setText(builder.toString());
            if (error) {
                lblError.setText("");
                lijstBeheerders.add(hulp);
                controller.voegBeheerderToe(hulp);
            }
        }
    }

    @FXML
    private void verwijderBeheerderOnAction(ActionEvent event) {
        Beheerder hulp = tblBeheerders.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Opgelet");
        alert.setHeaderText("Bent u zeker dat u de beheerder " + hulp.getNaam() + " wilt verwijderen ?");
        alert.setContentText("Wanneer u op OK drukt is de beheerder definitief verwijdert.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            controller.verwijderBeheerder(hulp);
            notifyObservers();
            lijstBeheerders.remove(hulp);
            lblError.setText("");
        } else {
            alert.close();
        }
        tblBeheerders.getSelectionModel();
    }

    @FXML
    private void okOnAction(ActionEvent event) {
        if (!this.lijstBeheerders.isEmpty()) {
            controller.persisteerBeheerders(lijstBeheerders);
            System.out.println("Persisteren");
        }
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        Scene scene = new Scene(new ContainerController(stage, beheerder));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void btnAnnuleerOnAction(ActionEvent event) {
        controller.verwijderBeheerders(lijstBeheerders);
        System.out.println("annuleren");
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        Scene scene = new Scene(new ContainerController(stage, beheerder));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void wijzigBeheerderOnAction(ActionEvent event) {
        Beheerder selectie = tblBeheerders.getSelectionModel().getSelectedItem();
        if (selectie != null) {
            //controller.verwijderBeheerder(selectie);
            Dialog<Beheerder> dialog = new Dialog<>();
            dialog.setTitle("Wijzig selectie");
            dialog.setHeaderText(null);
            dialog.setResizable(false);
            Label label1 = new Label("Naam: ");
            Label label2 = new Label("Voornaam: ");
            Label label3 = new Label("E-mail:* ");
            TextField naam = new TextField(selectie.getNaam());
            TextField voornaam = new TextField(selectie.getVoornaam());
            TextField email = new TextField(selectie.getEmail());
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.add(label1, 1, 1);
            grid.add(naam, 2, 1);
            grid.add(label2, 1, 2);
            grid.add(voornaam, 2, 2);
            grid.add(label3, 1, 3);
            grid.add(email, 2, 3);
            dialog.getDialogPane().setContent(grid);

            ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

            dialog.setResultConverter(new Callback<ButtonType, Beheerder>() {
                @Override
                public Beheerder call(ButtonType b) {
                    if (b == buttonTypeOk) {
                        return new Beheerder(naam.getText(), voornaam.getText(), email.getText(), BeheerderType.BEHEERDER);
                    }
                    return null;
                }
            });
            Optional<Beheerder> result = dialog.showAndWait();
            if (result.isPresent()) {

                Beheerder b = result.get();
                StringBuilder builder = new StringBuilder();
                boolean error = true;
                if (b.getEmail().trim().isEmpty() || b.getEmail() == null) {
                    builder.append("\nEmail is verplicht");
                    error = false;
                } else if (!b.getEmail().equals(selectie.getEmail()) && controller.bevatBeheerder(b)) {
                    builder.append("\nBeheerder met email : " + b.getEmail().trim() + " bestaat al!");
                    error = false;
                } else {

                    if (!Pattern.compile("[A-Za-z0-9]*@[A-Za-z0-9]*\\.[a-z]{2,}").matcher(b.getEmail()).find()) {
                        builder.append("\nEmail is niet geldig");
                        error = false;
                    }
                    if (!(b.getNaam().trim().isEmpty() || b.getNaam() == null) && b.getNaam().matches(".*\\d+.*")) {
                        builder.append("\nNaam is niet geldig");
                        error = false;
                    }
                    if (!(b.getVoornaam().trim().isEmpty() || b.getVoornaam() == null) && b.getVoornaam().matches(".*\\d+.*")) {
                        builder.append("\nVoornaam is niet geldig");
                        error = false;
                    }
                }
                lblError.setText(builder.toString());
                if (error) {
                    controller.wijzigBeheerder(selectie, b);
                    notifyObservers();
                    lijstBeheerders.add(b);
                    lblError.setText("");
                }
            }
        }

    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        this.observers.forEach(observer -> observer.update(null));
    }

    @Override
    public void update(Object object) {   //refresh gaat niet bij Remko
        tblBeheerders.refresh();
    }

}
