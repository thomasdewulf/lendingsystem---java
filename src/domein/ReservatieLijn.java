/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Remko
 */
@Entity
public class ReservatieLijn implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservatieLijnId;
    private int aantal;
    @ManyToOne
    @JoinColumn(name = "ReservatieId")
    private Reservatie reservatie;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product product;
    private int aantalTeruggebracht;

    protected ReservatieLijn()
    {

    }

    public ReservatieLijn(int aantal, Product product, int aantalTeruggebracht)
    {
        this.aantal = aantal;
        this.product = product;
        this.aantalTeruggebracht = aantalTeruggebracht;
    }

    public void setReservatie(Reservatie reservatie)
    {
        this.reservatie = reservatie;
    }

    public int getAantal()
    {
        return aantal;
    }

    public SimpleStringProperty aantalProperty()
    {

        return new SimpleStringProperty(String.valueOf(aantal));
    }

    public int getReservatieLijnId()
    {
        return reservatieLijnId;
    }

    public void setAantal(int aantal)
    {
        this.aantal = aantal;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public int getAantalTeruggebracht()
    {
        return aantalTeruggebracht;
    }
    
    public SimpleStringProperty aantalTeruggebrachtProperty()
    {

        return new SimpleStringProperty(String.valueOf(aantalTeruggebracht));
    }

    public void setAantalTeruggebracht(int aantalTeruggebracht)
    {
        this.aantalTeruggebracht = aantalTeruggebracht;
    }

    public  Reservatie getReservatie() {
        return reservatie;
    }

}
