/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import domein.Beheerder;
import domein.BeheerderType;
import domein.Catalogus;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Maarten
 */
public class BeheerderControllerTest
{
    @Mock
    private Catalogus catalogus;
        
    private BeheerderController controller;
    private List<Beheerder> beheerders;
    private Beheerder beheerder1;
    private Beheerder beheerder2;
    private Beheerder beheerder3;
    private Beheerder hoofdBeheerder;
    
    @Before
    public void before()
    {
        MockitoAnnotations.initMocks(this);
        controller = new BeheerderController(catalogus);
        beheerder1 = new Beheerder("Beheerder", "Voornaam", "beheerder1@beheerder.be", BeheerderType.BEHEERDER);
        beheerder2 = new Beheerder("Beheerder", "Voornaam2", "beheerder2@beheerder.be", BeheerderType.BEHEERDER);
        beheerder3 = new Beheerder("Beheerder", "Voornaam3", "beheerder3@beheerder.be", BeheerderType.BEHEERDER);
        hoofdBeheerder = new Beheerder("HoofdBeheerder", "HoofdVoornaam", "hoofdbeheerder@beheerder.be", BeheerderType.HOOFDBEHEERDER);
        beheerders = new ArrayList<>();
        beheerders.add(beheerder1);
        beheerders.add(beheerder2);
        beheerders.add(hoofdBeheerder);
    }
    
    @Test
    public void getBeheerdersGeeftAlleBeheerdersTerugBehalveDeHoofdBeheerder()
    {
        Mockito.when(catalogus.getBeheerders()).thenReturn(beheerders);
        controller = new BeheerderController(catalogus);
        List<Beheerder> hulp = controller.getBeheerders();
        Assert.assertNotNull(hulp);
        Assert.assertFalse(hulp.contains(hoofdBeheerder));
        Assert.assertEquals(2, hulp.size());
    }
    
    @Test
    public void isBeheerderGeeftTrueTerugIndienDeBeheerderTotBeheerdersBehoort()
    {
        Mockito.when(catalogus.getBeheerders()).thenReturn(beheerders);
        controller = new BeheerderController(catalogus);
        Assert.assertTrue(controller.isBeheerder(beheerder2));
    }    
    
    @Test
    public void isBeheerderGeeftFalseTerugIndienDeBeheerderNietTotBeheerdersBehoort()
    {
        Mockito.when(catalogus.getBeheerders()).thenReturn(beheerders);
        controller = new BeheerderController(catalogus);
        Assert.assertFalse(controller.isBeheerder(beheerder3));
    }
    
    @Test
    public void geefBeheerderGeeftDeBeheerderTerugMetOvereenkomstigEmail()
    {
        Mockito.when(catalogus.geefBeheerder("beheerder2@beheerder.be")).thenReturn(beheerder2);
        controller = new BeheerderController(catalogus);
        String email = "beheerder2@beheerder.be";
        Beheerder b = controller.geefBeheerder(email);
        Assert.assertEquals(email, b.getEmail());
        Assert.assertEquals(beheerder2, b);
    }
    
    @Test
    public void geefBeheerderGeeftNullTerugIndienErGeenBeheerderMetOvereenkomstigEmailGevondenIs()
    {
        Mockito.when(catalogus.geefBeheerder("beheerder4@beheerder.be")).thenReturn(null);
        controller = new BeheerderController(catalogus);
        String email = "beheerder3@beheerder.be";
        Beheerder b = controller.geefBeheerder(email);
        Assert.assertNull(b);
    }
    
    @Test
    public void voegBeheerderToeVoegtDeBeheerderToeAanDeLijstMetBeheerders()
    {
        Mockito.when(catalogus.getBeheerders()).thenReturn(beheerders);
        controller = new BeheerderController(catalogus);
        controller.voegBeheerderToe(beheerder3);
        List<Beheerder> hulp = controller.getBeheerders();
        Assert.assertTrue(hulp.contains(beheerder3));
        Assert.assertFalse(hulp.contains(hoofdBeheerder));
        Assert.assertEquals(3, hulp.size());
    }
    
    @Test
    public void verwijderBeheerdersVerwijdertDeMeegegevenLijstMetBeheerdersUitBeheerders()
    {
        Mockito.when(catalogus.getBeheerders()).thenReturn(beheerders);
        controller = new BeheerderController(catalogus);
        List<Beheerder> hulp = new ArrayList<>();
        Beheerder b = new Beheerder("Beheerder", "Voornaam4", "beheerder5@beheerder.be", BeheerderType.BEHEERDER);
        controller.voegBeheerderToe(b);
        hulp.add(beheerder2);
        hulp.add(beheerder1);
        controller.verwijderBeheerders(hulp);
        List<Beheerder> lijst = controller.getBeheerders();
        Assert.assertEquals(1, lijst.size());
        Assert.assertFalse(lijst.contains(beheerder2));
        Assert.assertFalse(lijst.contains(beheerder1));
        Assert.assertTrue(lijst.contains(b));
    }
}
