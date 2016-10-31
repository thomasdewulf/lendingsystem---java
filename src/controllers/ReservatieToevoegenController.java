/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import domein.AspNetUsers;
import domein.Catalogus;
import domein.Product;
import domein.Reservatie;
import domein.ReservatieLijn;
import domein.ReservatieStatus;
import java.sql.Date;
import java.util.Collection;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author De Puysseleyr
 */
public class ReservatieToevoegenController extends ReservatieBeherenController
{

    public ReservatieToevoegenController()
    {
        productKandidaten = FXCollections.observableArrayList(catalogus.getProductenLijst());
        gekozenProducten = FXCollections.observableArrayList();
        gekozenReservatieLijnen = FXCollections.observableArrayList();
        personeel = FXCollections.observableArrayList(catalogus.getUserLijst());
    }

    ReservatieToevoegenController(Catalogus catalogus)
    {
        this.catalogus = catalogus;
        productKandidaten = FXCollections.observableArrayList(catalogus.getProductenLijst());
        gekozenProducten = FXCollections.observableArrayList();
        gekozenReservatieLijnen = FXCollections.observableArrayList();
        personeel = FXCollections.observableArrayList(catalogus.getUserLijst());
    }

    @Override
    public void voegReservatieToe(Date startDatum, Date eindDatum, Date aanmaakDatum, ReservatieStatus reservatieStatus, String discriminator, Collection<ReservatieLijn> reservatieLijnen, AspNetUsers user)
    {
        em.getTransaction().begin();
        Reservatie reservatie = new Reservatie(startDatum, eindDatum, aanmaakDatum, reservatieStatus, discriminator, reservatieLijnen, user);
        for (ReservatieLijn r : reservatieLijnen)
        {
            r.setReservatie(reservatie);
        }

        em.persist(reservatie);
        em.getTransaction().commit();
    }

}
