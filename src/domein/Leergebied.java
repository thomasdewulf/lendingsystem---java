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

            @NamedQuery(name = "Leergebied.geefAlleLeergebieden", query = "select l from Leergebied l"),
            @NamedQuery(name = "Leergebied.zoekLeergebiedOpNaam", query = "select l from Leergebied l where l.naam = :naam")

        })
public class Leergebied implements Serializable
{

    private String naam;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leergebiedId;
    
    protected Leergebied()
    {

    }

    public Leergebied(String naam)
    {
        this.naam = naam;
    }

    public String getNaam()
    {
        return naam;
    }

    @Override
    public String toString()
    {
        return String.format("%s", naam);
    }
     @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return true;
        }
       Leergebied f = (Leergebied) obj;
      
       return f.naam.equals(this.naam);
    }

}
