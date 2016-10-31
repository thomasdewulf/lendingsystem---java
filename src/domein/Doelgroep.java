package domein;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(
        {
            @NamedQuery(name = "Doelgroep.geefAlleNamen", query = "select d.naam from Doelgroep d"),
            @NamedQuery(name = "Doelgroep.geefAlleDoelgroepen", query = "select d from Doelgroep d"),
            @NamedQuery(name = "Doelgroep.zoekDoelgroepOpNaam", query = "select d FROM Doelgroep d where d.naam = :naam")

        })
public class Doelgroep implements Serializable {

    private String naam;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doelgroepId;
    
    protected Doelgroep() {

    }

    public Doelgroep(String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

    @Override
    public String toString() {
        return String.format("%s", this.naam);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return true;
        }
        Doelgroep f = (Doelgroep) obj;
    
        return f.getNaam().equals(this.naam);
    }

}
