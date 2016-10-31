/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import domein.Product;
import domein.Reservatie;
import domein.ReservatieLijn;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.EntityManagerUtil;

/**
 *
 * @author dewul
 */
public class ReservatieWijzigenController
{

    private Reservatie reservatie;
    private ObservableList<Product> producten;
    private ObservableList<ReservatieLijn> lijnen;

    public ReservatieWijzigenController(Reservatie reservatie)
    {
        this.reservatie = reservatie;
        producten = FXCollections.observableArrayList(this.reservatie.geefAlleProductenPerReservatie());
        lijnen = FXCollections.observableArrayList(this.reservatie.getReservatieLijnen());
    }

    public ObservableList<Product> getProducten()
    {
        return FXCollections.unmodifiableObservableList(producten);
    }

    public ObservableList<ReservatieLijn> getLijnen()
    {
        return FXCollections.unmodifiableObservableList(lijnen);
    }

    public void wijzigReservatie(Reservatie reservatie)
    {
        EntityManagerUtil.getEm().getTransaction().begin();
        Reservatie teWijzigenreservatie = reservatie;
        EntityManagerUtil.getEm().merge(teWijzigenreservatie);
        for (ReservatieLijn lijnen : teWijzigenreservatie.getReservatieLijnen())
        {
            EntityManagerUtil.getEm().merge(lijnen); 
        }

        EntityManagerUtil.getEm().getTransaction().commit();
        System.out.println("gewijzigd");

    }

    public void verwijderReservatie(Reservatie reservatie)
    {
        List <ReservatieLijn > lijnen = (List <ReservatieLijn >) reservatie.getReservatieLijnen();
        lijnen.stream().forEach((lijn) -> {
            int aantal = lijn.getAantal();
            int aantalBeschikbaar = lijn.getProduct().getAantalInCatalogus();
            lijn.getProduct().setAantalInCatalogus(aantalBeschikbaar + aantal);
        });
        EntityManagerUtil.getEm().getTransaction().begin();
        EntityManagerUtil.getEm().remove(EntityManagerUtil.getEm().merge(reservatie));
        EntityManagerUtil.getEm().getTransaction().commit();

        System.out.println("verwijdert");
    }

}
