/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Maarten
 */
public class BeheerderTest
{
    private Beheerder beheerder1;
    private Beheerder beheerder2;
    private Beheerder hoofdbeheerder;
    
    @Before
    public void before()
    {
        beheerder1 = new Beheerder("Beheerder", "Voornaam1", "beheerder1@beheerder.be", BeheerderType.BEHEERDER);
        hoofdbeheerder = new Beheerder("Hoofdbeheerder", "HoofdVoornaam", "hoofdbeheerder@beheerder.be", BeheerderType.HOOFDBEHEERDER);
    }
    
    @Test
    public void beheerderWordtCorrectAangemaakt()
    {
        beheerder2 = new Beheerder("Beheerder", "Voornaam2", "beheerder2@beheerder.be", BeheerderType.BEHEERDER);
        Assert.assertEquals("beheerder2@beheerder.be",beheerder2.getEmail());
        Assert.assertEquals("Beheerder", beheerder2.getNaam());
        Assert.assertEquals(BeheerderType.BEHEERDER, beheerder2.getType());
    }
    
    @Test
    public void getIdGeeftDeIdTerugVanDeBeheerder()
    {
        int id = beheerder1.getId();
        Assert.assertEquals(0, id);
    }
    
    @Test
    public void getNaamGeeftDeNaamTerugVanDeBeheerder()
    {
        String naam = beheerder1.getNaam();
        Assert.assertEquals("Beheerder", naam);
    }
    
    @Test
    public void getTypeGeeftHetTypeTerugVanDeBeheerder()
    {
        BeheerderType type = beheerder1.getType();
        BeheerderType type2 = hoofdbeheerder.getType();
        Assert.assertEquals(BeheerderType.BEHEERDER, type);
        Assert.assertEquals(BeheerderType.HOOFDBEHEERDER, type2);
    }
    
    @Test
    public void getVoornaamGeeftDeVoornaamTerugVanDeBeheerder()
    {
        String voornaam = beheerder1.getVoornaam();
        Assert.assertEquals("Voornaam1", voornaam);
    }
    
    @Test
    public void getEmailGeeftDeEmailTerugVanDeBeheerder()
    {
        String email = beheerder1.getEmail();
        Assert.assertEquals("beheerder1@beheerder.be", email);
    }
    
    @Test
    public void naamPropertyGeeftEenSimpleStrinPropertyVanDeNaamTerug()
    {
        StringProperty str = new SimpleStringProperty(beheerder1.getNaam());
        StringProperty prop = beheerder1.naamProperty();
        Assert.assertEquals(str.toString(), prop.toString());
    }
    
    @Test
    public void voornaamPropertyGeeftEenSimpleStrinPropertyVanDevoornaamTerug()
    {
        StringProperty str = new SimpleStringProperty(beheerder1.getVoornaam());
        StringProperty prop = beheerder1.voornaamProperty();
        Assert.assertEquals(str.toString(), prop.toString());
    }
    
    @Test
    public void emailPropertyGeeftEenSimpleStrinPropertyVanDeEmailTerug()
    {
        StringProperty str = new SimpleStringProperty(beheerder1.getEmail());
        StringProperty prop = beheerder1.emailProperty();
        Assert.assertEquals(str.toString(), prop.toString());
    }
    
    @Test
    public void isHoofdBeheerderGeeftTrueTerugIndienDeBeheerderEenHoofdBeheerderIs()
    {
        Assert.assertTrue(hoofdbeheerder.isHoofdBeheerder());
    }
    
    @Test
    public void isHoofdBeheerderGeeftFalseTerugIndienDeBeheerderGeenHoofdBeheerderIs()
    {
        Assert.assertFalse(beheerder1.isHoofdBeheerder());
    }
}
