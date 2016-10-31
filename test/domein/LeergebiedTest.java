/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Maarten
 */
public class LeergebiedTest
{
    private Leergebied leergebied;

    @Before
    public void before()
    {
        leergebied = new Leergebied("leergebied");
    }
    
    @Test
    public void leergebiedWordtCorrectAangemaakt()
    {
        Leergebied l = new Leergebied("hulp");
        Assert.assertEquals("hulp", l.getNaam());
        Assert.assertNotNull(l);
    }
    
    @Test
    public void getNaamGeeftDeNaamVanHetLeergebiedTerug()
    {
        String naam = leergebied.getNaam();
        Assert.assertEquals("leergebied", naam);
    }
    
    @Test
    public void equalsGeeftTrueTerugIndienDeNamenOvereenkomen()
    {
        Leergebied l = new Leergebied("leergebied");
        Assert.assertTrue(leergebied.equals(l));
    }
    
    @Test
    public void equalsGeeftFalseTerugIndienDeNamenOvereenkomen()
    {
        Leergebied l = new Leergebied("test");
        Assert.assertFalse(leergebied.equals(l));
    }
}
