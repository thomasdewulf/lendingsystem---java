/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Remko
 */
@Entity
@NamedQueries(
        {
            @NamedQuery(name = "AspNetUsers.geefAlleUsers", query = "select a from AspNetUsers a"),
            @NamedQuery(name = "AspNetUsers.geefUserMetEmail", query = "select a from AspNetUsers a where a.email = :email")
        }
)
public class AspNetUsers implements Serializable
{

    @Id
    private String id;
    private String gebruikersNummer;
    private String naam;
    private String voornaam;
    private String foto;
    private String email;
    private String userName;
    @OneToMany(mappedBy = "user")
    private List<Reservatie> reservaties;
    protected AspNetUsers() {
    }

    public String getId() {
        return id;
    }
    
    public String getGebruikersNummer() {
        return gebruikersNummer;
    }

    public AspNetUsers(String naam, String voornaam, String email)
    {
        this.naam = naam;
        this.voornaam = voornaam;
        this.email = email;
    }

    public AspNetUsers(String gebruikersNummer, String naam, String voornaam, String foto, String email, String userName)
    {
        this.gebruikersNummer = gebruikersNummer;
        this.naam = naam;
        this.voornaam = voornaam;
        this.foto = foto;
        this.email = email;
        this.userName = userName;
    }
    
    public String getNaam() {
        return naam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFoto() {
        return foto;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public List<Reservatie> getReservaties() {
        return reservaties;
    }
    
    public void setReservaties(List<Reservatie> reservaties) {
        this.reservaties = reservaties;
    }
    
     public StringProperty naamProperty()
    {
        return new SimpleStringProperty(naam);
    }
    
    public StringProperty voornaamProperty()
    {
        return new SimpleStringProperty(voornaam);
    }
    
    public StringProperty emailProperty()
    {
        return new SimpleStringProperty(email);
    }
    
    
    
    
    @Override
    public String toString()
    {
        return naam + " " + voornaam;
    }

}
