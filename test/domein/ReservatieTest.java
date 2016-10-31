/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dewul
 */
public class ReservatieTest
{

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

    @Before
    public void before()
    {
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
        Collection<ReservatieLijn> reservatieLijnen1 = new ArrayList<>();
        Collection<ReservatieLijn> reservatieLijnen2 = new ArrayList<>();

        reservatieLijnen1.add(lijn1);
        reservatieLijnen2.add(lijn1);
        reservatieLijnen2.add(lijn2);
        reservatieLijnen2.add(lijn3);
        reservatie1 = new Reservatie(startDatum1, eindDatum1, aanmaakDatum1, ReservatieStatus.GERESERVEERD, "Reservatie", reservatieLijnen1, user);
        reservatie2 = new Reservatie(startDatum2, eindDatum2, aanmaakDatum2, ReservatieStatus.GERESERVEERD, "Reservatie", reservatieLijnen2, user);
    }

    @Test
    public void geefAlleProductenPerReservatieGeeftJuistAantalProductenTerug()
    {
        List<Product> producten = reservatie1.geefAlleProductenPerReservatie();
        Assert.assertEquals(1, producten.size());
        Assert.assertNotNull(producten);
    }
}
