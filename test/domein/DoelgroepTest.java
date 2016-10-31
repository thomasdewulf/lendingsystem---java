package domein;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Maarten
 */
public class DoelgroepTest
{
    
    private Doelgroep doelgroep;
    
    @Before
    public void before()
    {
        doelgroep = new Doelgroep("doelgroep1");
    }
    
    @Test
    public void doelgroepWordtCorrectAangemaakt()
    {
        Doelgroep test = new Doelgroep("Test");
        Assert.assertNotNull(test);
        Assert.assertEquals("Test", test.getNaam());
    }
    
    @Test
    public void getNaamGeeftDeNaamVanDeDoelgroepTerug()
    {
        String naam = doelgroep.getNaam();
        Assert.assertEquals("doelgroep1", naam);
    }
    
    @Test
    public void eqaulsGeeftTrueTerugIndienDeNamenVanDeDoelgroepenGelijkZijn()
    {
        Doelgroep test = new Doelgroep("doelgroep1");
        Assert.assertTrue(doelgroep.equals(test));
    }
    
    @Test
    public void eqaulsGeeftFalseTerugIndienDeNamenVanDeDoelgroepenNietGelijkZijn()
    {
        Doelgroep test = new Doelgroep("doelgroep2");
        Assert.assertFalse(doelgroep.equals(test));
    }
    
}
