/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import domein.Catalogus;
import domein.Product;
import domein.Reservatie;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javax.persistence.EntityManager;
import util.EntityManagerUtil;

/**
 *
 * @author De Puysseleyr
 */
public class ReservatieController
{

    private ObservableList<Reservatie> reservaties;
    private FilteredList<Reservatie> filteredReservatieLijst;
    private ObservableList<Product> productenPerReservatie;

    private Comparator<Reservatie> byAchternaam = (p1, p2) -> p1.getUser().getNaam().compareToIgnoreCase(p2.getUser().getNaam());
    private Comparator<Reservatie> byVoornaam = (p1, p2) -> p1.getUser().getVoornaam().compareToIgnoreCase(p2.getUser().getVoornaam());
    private Comparator<Reservatie> byOphaaldatum = (p1, p2) -> p1.getStartDatum().compareTo(p2.getStartDatum());
    private Comparator<Reservatie> byIndiendatum = (p1, p2) -> p1.getEindDatum().compareTo(p2.getEindDatum());

    private Comparator<Reservatie> sortOrder = byAchternaam.thenComparing(byVoornaam).thenComparing(byOphaaldatum).thenComparing(byIndiendatum);

    private EntityManager em = EntityManagerUtil.getEm();
    private Catalogus catalogus;

    public ReservatieController(Catalogus catalogus)
    {
        this.catalogus = catalogus;
        reservaties = FXCollections.observableArrayList(catalogus.getReservatieLijst());

        filteredReservatieLijst = new FilteredList<>(reservaties, p -> true);
    }

    public ObservableList<Reservatie> getReservatieSortedList()
    {
        reservaties = FXCollections.observableArrayList(catalogus.getReservatieLijst());
        filteredReservatieLijst = new FilteredList<>(reservaties, p -> true);
        return this.filteredReservatieLijst.sorted(sortOrder);
    }

}
