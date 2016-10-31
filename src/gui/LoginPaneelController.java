/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.BeheerderController;
import domein.Beheerder;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import util.EntityManagerUtil;

/**
 * FXML Controller class
 *
 * @author dewul
 */
public class LoginPaneelController extends GridPane
{

    @FXML
    private TextField txfEmail;
    @FXML
    private PasswordField txfWachtwoord;
    @FXML
    private Button btnInloggen;
    @FXML
    private Label lblValidatie;

    private Stage stage;
    @FXML
    private Label lblError;

    private EntityManager em = EntityManagerUtil.getEm();
    private BeheerderController beheerderController;

    public LoginPaneelController(Stage stage)
    {
        beheerderController = new BeheerderController();
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource("LoginPaneel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }

        btnInloggen.setOnAction(this::inloggen);
    }

    private void inloggen(ActionEvent event)
    {
        JsonObject obj = null;
        if (this.txfEmail.getText().trim().isEmpty() || this.txfWachtwoord.getText().trim().isEmpty())
        {
            lblError.setText("Gelieve een geldige combinatie in te geven.");
        } else
        {
            String passwordEncrypted = sha256(this.txfWachtwoord.getText());
            try
            {
                URL url = new URL("https://studservice.hogent.be/auth/" + this.txfEmail.getText() + "/" + passwordEncrypted);

                try (InputStream is = url.openStream(); JsonReader reader = Json.createReader(is);)
                {
                    if (reader == null)
                    {
                        lblError.setText("Gelieve een geldige combinatie in te geven.");
                        return;
                    }
                    obj = reader.readObject();
                } catch (IOException ex)
                {
                } catch (JsonParsingException jex)
                {
                    lblError.setText("Gelieve een geldige combinatie in te geven.");
                    return;
                }
            } catch (MalformedURLException ex)
            {
                Logger.getLogger(LoginPaneelController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(obj.get("VOORNAAM"));
            System.out.println(obj.get("NAAM"));
            System.out.println(obj.get("FACULTEIT"));
            System.out.println(obj.get("TYPE"));
            System.out.println(obj.get("EMAIL"));
            Beheerder user = null;
            try
            {
                user = em.createNamedQuery("Beheerder.geefBeheerderMetEmail", Beheerder.class).setParameter("email", obj.getString("EMAIL")).getSingleResult();
            } catch (NoResultException nrex)
            {
                System.out.println("NIET GEVONDEN IN DATABANK");
                lblError.setText("U hebt niet de nodige rechten gekregen. Contacteer de hoofdbeheerder.");
                return;
            }
            Scene scene = new Scene(new ContainerController(stage, user));
            stage.setScene(scene);
            
        }
    }

    private String sha256(String base)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++)
            {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex)
        {

        }
        return "";
    }

}
