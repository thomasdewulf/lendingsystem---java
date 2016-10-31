package domein;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(
        {
            @NamedQuery(name = "Firma.geefAlleNamen", query = "select f.naam from Firma f"),
            @NamedQuery(name = "Firma.geefAlleFirmas", query = "select f from Firma f"),
            @NamedQuery(name = "Firma.zoekFirmaOpnaam", query = "select f from Firma f where f.naam = :naam")
        })
public class Firma implements Serializable
{

    private String naam;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int firmaId;
    private String website;
    private String contactPersoon;
    private String emailContactPersoon;

    public Firma()
    {

    }

    public Firma(String naam, String website, String contactPersoon, String emailContactPersoon)
    {
        this.naam = naam;
        this.website = website;
        this.contactPersoon = contactPersoon;
        this.emailContactPersoon = emailContactPersoon;
    }

    public String getNaam() {
        return naam;
    }

    public String getWebsite() {
        return website;
    }

    public String getContactPersoon() {
        return contactPersoon;
    }

    public String getEmailContactPersoon() {
        return emailContactPersoon;
    }
    
    @Override
    public String toString()
    {
        return String.format("%s", this.naam);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return true;
        }
       Firma f = (Firma) obj;
       return f.naam.equals(this.naam);
    }
    

}
