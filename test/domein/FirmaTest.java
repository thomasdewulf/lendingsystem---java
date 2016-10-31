package domein;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Maarten
 */
public class FirmaTest
{
    private Firma firma;
    
    @Before
    public void before()
    {
        firma = new Firma("Naam", "Website", "ContactPersoon", "EmailContactPersoon");
    }
    
    @Test
    public void firmaWordtCorrectAangemaakt()
    {
        Firma test = new Firma("Test", "TestWebsite", "TestContactPersoon", "TestEmailContactPersoon");
        Assert.assertEquals("Test",test.getNaam());
        Assert.assertEquals("TestWebsite", test.getWebsite());
        Assert.assertNotNull(test);
    }
    
    @Test
    public void getNaamGeeftDeNaamVanDeFirmaTerug()
    {
        String naam = firma.getNaam();
        Assert.assertEquals("Naam",naam);
    }
    
    @Test
    public void getWebsiteGeeftDeWebsiteVanDeFirmaTerug()
    {
        String website = firma.getWebsite();
        Assert.assertEquals("Website", website);
    }
    
    @Test
    public void getContactPersoonGeeftDeContactPersoonVanDeFirmaTerug()
    {
        String contactPersoon = firma.getContactPersoon();
        Assert.assertEquals("ContactPersoon", contactPersoon);
    }
    
    @Test
    public void getEmailContactPersoonGeeftDeEmailVanDeContactPersoonTerug()
    {
        String email = firma.getEmailContactPersoon();
        Assert.assertEquals("EmailContactPersoon", email);
    }
    
    @Test
    public void eqaulsGeeftTrueTerugIndienDeNamenVanDeFirmasGelijkZijn()
    {
        Firma test = new Firma("Naam", "TestWebsite", "TestContactPersoon", "TestEmailContactPersoon");
        Assert.assertTrue(firma.equals(test));
    }
    
    @Test
    public void eqaulsGeeftFalseTerugIndienDeNamenVanDeFirmasNietGelijkZijn()
    {
        Firma test = new Firma("Test", "TestWebsite", "TestContactPersoon", "TestEmailContactPersoon");
        Assert.assertFalse(firma.equals(test));
    }
    
}
