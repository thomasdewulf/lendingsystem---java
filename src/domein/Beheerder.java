/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
//import javax.persistence.OneToMany;

/**
 *
 * @author Remko
 */
@Entity
@Table(name = "Beheerder")
@NamedQueries(
        {
            @NamedQuery(name = "Beheerder.geefBeheerderMetEmail", query = "select b from Beheerder b where b.email = :email"),
            @NamedQuery(name = "Beheerder.geefBeheerders", query = "select b from Beheerder b where b.beheerderType = :beheerder or b.beheerderType = :hoofdbeheerder")
        }
)
public class Beheerder implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String naam;
    private String voornaam;
    private String email;
    @Enumerated(EnumType.STRING)
    private BeheerderType beheerderType;

    protected Beheerder() {
    }
    
    public Beheerder(String naam, String voornaam, String email, BeheerderType type)
    {
        this.naam = naam;
        this.voornaam = voornaam;
        this.email = email;
        this.beheerderType = type;
    }

    public int getId() {
        return id;
    }
    
    public String getNaam() {
        return naam;
    }

    public BeheerderType getType() {
        return beheerderType;
    }

    public String getVoornaam() {
        return voornaam;
    }
    
    public String getEmail() {
        return email;
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
        return "Beheerder{" + "id=" + id + ", naam=" + naam + ", voornaam=" + voornaam + ", email=" + email + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return true;
        Beheerder b = (Beheerder) obj;
        return b.getEmail().equals(this.email);
    }
    
    
    public boolean isHoofdBeheerder()
    {
        return beheerderType == BeheerderType.HOOFDBEHEERDER;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
