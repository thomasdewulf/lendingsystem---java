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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author dewul
 */
public class ReservatieBeherenTest
{

    private ReservatieWijzigenController controller;
    @Mock
    private Catalogus catalogus;

    Product product1;
    Product product2;
    Product product3;
    Reservatie reservatie1;
    Reservatie reservatie2;
    ReservatieLijn lijn1;
    ReservatieLijn lijn2;
    ReservatieLijn lijn3;
    Date startDatum1;
    Date startDatum2;
    Date eindDatum1;
    Date eindDatum2;
    Date aanmaakDatum1;
    Date aanmaakDatum2;
    AspNetUsers user;
    List<ReservatieLijn> reservatieLijnen1;
    List<ReservatieLijn> reservatieLijnen2;
    @Mock
    private ReservatieBeherenController beheerController;
    private List<Product> productTest;

    @Before
    public void before()
    {
        MockitoAnnotations.initMocks(this);
        product1 = new Product();
        product2 = new Product();
        product3 = new Product();
        lijn1 = new ReservatieLijn(5, product1, 0);
        lijn2 = new ReservatieLijn(10, product2, 0);
        lijn3 = new ReservatieLijn(12, product3, 0);
        startDatum1 = new Date(2016, 6, 3);
        startDatum2 = new Date(2016, 12, 31);
        eindDatum1 = new Date(2016, 6, 10);
        eindDatum2 = new Date(2017, 1, 7);
        aanmaakDatum1 = new Date(2016, 5, 8);
        aanmaakDatum2 = new Date(2016, 6, 1);
        user = new AspNetUsers("de Wulf", "Thomas", "thomas.dewulf.v4732@student.hogent.be");

        reservatieLijnen1 = new ArrayList<>();
        reservatieLijnen2 = new ArrayList<>();
        reservatieLijnen1.add(lijn1);
        reservatieLijnen2.add(lijn1);
        reservatieLijnen2.add(lijn2);
        reservatieLijnen2.add(lijn3);
        reservatie1 = new Reservatie(startDatum1, eindDatum1, aanmaakDatum1, ReservatieStatus.GERESERVEERD, "Reservatie", reservatieLijnen1, user);
        reservatie2 = new Reservatie(startDatum2, eindDatum2, aanmaakDatum2, ReservatieStatus.GERESERVEERD, "Reservatie", reservatieLijnen2, user);

        productTest = new ArrayList<>();
        productTest.add(product1);
        productTest.add(product2);

    }

    @Test
    public void getProductenGeeftJuisteAantalProductenTerug()
    {
        controller = new ReservatieWijzigenController(reservatie1);
        ObservableList<Product> lijst = controller.getProducten();
        Assert.assertEquals(1, lijst.size());
        Assert.assertEquals(product1, lijst.get(0));
        Assert.assertNotNull(lijst);

    }

    @Test
    public void getLijnenGeeftJuistAantalLijnenTerug()
    {
        controller = new ReservatieWijzigenController(reservatie2);
        ObservableList<ReservatieLijn> lijst = controller.getLijnen();
        Assert.assertEquals(3, lijst.size());
        Assert.assertNotNull(lijst);

    }

    @Test
    public void addKandidaatProductVoegtKandidaatToe()
    {
        this.mockitoInOrdeZettenProducten();
        beheerController = new ReservatieToevoegenController(catalogus);
        beheerController.addKandidaatProduct(product3);
        Assert.assertEquals(3, beheerController.getProductKandidaten().size());
    }

//    @Test
//    public void removeGekozenProductVerwijdertGekozenProduct()
//    {
//        this.mockitoInOrdeZettenGekozenProducten();
//        beheerController = new ReservatieToevoegenController(catalogus);
//        List<Product> gekozenProducten = new ArrayList<>();
//        gekozenProducten.add(product1);
//        beheerController.removeGekozenProducten(FXCollections.observableArrayList(gekozenProducten));
//        Assert.assertEquals(1, beheerController.getGekozenProducten().size());
//    }

//    @Test
//    public void removeReservatieLijnVerwijdertReservatieLijn()
//    {
//        reservatieLijnenInOrdeZetten();
//        beheerController.removeReservatieLijn(lijn1);
//        Assert.assertEquals(0, beheerController.getGekozenReservatieLijnen().size());
//    }

    //Werkt niet
//    @Test
//    public void addReservatieLijnVoegtReservatieLijnToe()
//    {
//        reservatieLijnenInOrdeZetten();
//        beheerController.addReservatieLijn(lijn2);
//        Assert.assertEquals(2, beheerController.getGekozenReservatieLijnen().size());
//    }

    public void mockitoInOrdeZettenProducten()
    {
        Mockito.when(catalogus.getProductenLijst()).thenReturn(productTest);
    }

    public void mockitoInOrdeZettenGekozenProducten()
    {
        Mockito.when(beheerController.getGekozenProducten()).thenReturn(FXCollections.observableArrayList(productTest));
    }

    public void reservatieLijnenInOrdeZetten()

    {
        Mockito.when(beheerController.getGekozenReservatieLijnen()).thenReturn(FXCollections.observableArrayList(reservatieLijnen1));
    }
}
