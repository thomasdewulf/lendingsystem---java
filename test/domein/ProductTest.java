/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Maarten
 */
public class ProductTest
{
    private Product product;
    
    private Doelgroep doelgroep;
    private Leergebied leergebied;
    
    private List<Doelgroep> doelgroepen = new ArrayList<>();
    private List<Leergebied> leergebieden = new ArrayList<>();
    private Firma firma;
    

    @Before
    public void before()
    {
        doelgroep = new Doelgroep("doelgroep");
        leergebied = new Leergebied("leergebied");
        firma = new Firma("naam", "website", "contactPersoon", "emailContactPersoon");
        doelgroepen.add(doelgroep);
        leergebieden.add(leergebied);
        product = new Product(doelgroepen, leergebieden, firma, "foto", "artikelNaam", "omschrijving", "artikelNummer", 0.50, 5, 2, true, "B12");
    }
    
    @Test
    public void productWordtCorrectAangemaakt()
    {
        Product p = new Product(doelgroepen, leergebieden, firma, "foto1", "artikelNaam1", "omschrijving1", "artikelNummer1", 0.75, 10, 4, false, "B32");
        Assert.assertNotNull(p);
        Assert.assertEquals("artikelNaam1", p.getArtikelNaam());
    }
    
    @Test
    public void bevatDoelgroepGeeftTrueTerugIndienDeNaamOvereenkomtMetDeDoelgroepenVanHetProduct()
    {
        Assert.assertTrue(product.bevatDoelgroep("doelgroep"));
    }
    
    @Test
    public void bevatDoelgroepGeeftFalseTerugIndienDeNaamNietOvereenkomtMetDeDoelgroepenVanHetProduct()
    {
        Assert.assertFalse(product.bevatDoelgroep("doelgroep2"));
    }
    
    @Test
    public void bevatLeergebiedGeeftTrueTerugIndienDeNaamOvereenkomtMetDeLeergebiedenVanHetProduct()
    {
        Assert.assertTrue(product.bevatLeergebied("leergebied"));
    }
    
    @Test
    public void bevatLeergebiedGeeftFalseTerugIndienDeNaamOvereenkomtMetDeLeergebiedenVanHetProduct()
    {
        Assert.assertFalse(product.bevatLeergebied("leergebied3"));
    }
}
