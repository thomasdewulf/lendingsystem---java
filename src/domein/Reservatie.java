/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author Remko
 */
@Entity
@NamedQuery(name = "Reservatie.geefAlleReservaties", query = "select r from Reservatie r")
public class Reservatie implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservatieId;
    private Date startDatum;
    private Date eindDatum;
    private Date aanmaakDatum;
    private ReservatieStatus reservatieStatus;
    private String discriminator;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ApplicationUserId")
    private AspNetUsers user;
    @OneToMany(mappedBy = "reservatie", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Collection<ReservatieLijn> reservatieLijnen;

    @Transient
    private List<ReservatieLijn> lijnenLijst;

    protected Reservatie()
    {

    }

    public Reservatie(Date startDatum, Date eindDatum, Date aanmaakDatum, ReservatieStatus reservatieStatus, String discriminator,
            Collection<ReservatieLijn> reservatieLijnen, AspNetUsers user)
    {
        this.startDatum = startDatum;
        this.eindDatum = eindDatum;
        this.aanmaakDatum = aanmaakDatum;
        this.discriminator = discriminator;
        this.user = user;
        this.reservatieLijnen = reservatieLijnen;
        this.reservatieStatus = reservatieStatus;
        this.lijnenLijst = new ArrayList<>(reservatieLijnen);
    }

    public int getReservatieId()
    {
        return reservatieId;
    }

    public void setReservatieId(int reservatieId)
    {
        this.reservatieId = reservatieId;
    }

    public Date getStartDatum()
    {
        return startDatum;
    }

    public void setStartDatum(Date startDatum)
    {
        this.startDatum = startDatum;
    }

    public Date getEindDatum()
    {
        return eindDatum;
    }

    public void setEindDatum(Date eindDatum)
    {
        this.eindDatum = eindDatum;
    }

    public Date getAanmaakDatum()
    {
        return aanmaakDatum;
    }

    public void setAanmaakDatum(Date aanmaakDatum)
    {
        this.aanmaakDatum = aanmaakDatum;
    }

    public AspNetUsers getUser()
    {
        return user;
    }

    public void setUser(AspNetUsers user)
    {
        this.user = user;
    }

    public Collection<ReservatieLijn> getReservatieLijnen()
    {
        return reservatieLijnen;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }
    
    

    public void setReservatieLijnen(Collection<ReservatieLijn> reservatieLijnen)
    {
        this.reservatieLijnen = reservatieLijnen;
    }

    public ReservatieStatus getReservatieStatus()
    {
        return reservatieStatus;
    }

    public StringProperty startDatumProperty()
    {
        return new SimpleStringProperty(String.format("%s", startDatum));
    }

    public StringProperty eindDatumProperty()
    {
        return new SimpleStringProperty(String.format("%s", eindDatum));
    }

    @Override
    public String toString()
    {
        return "Reservatie{" + "startDatum=" + startDatum + ", eindDatum=" + eindDatum + ", aanmaakDatum=" + aanmaakDatum + '}';
    }

    public List<Product> geefAlleProductenPerReservatie()
    {
        List<Product> producten = new ArrayList<>();
        reservatieLijnen.stream().forEach((lijn)
                ->
                {
                    producten.add(lijn.getProduct());
        });

        return producten;
    }

    public ReservatieLijn getLijn(int index)
    {
        return lijnenLijst.get(index);
    }

}
