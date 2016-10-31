/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import domein.Catalogus;
import domein.Doelgroep;
import domein.Email;
import domein.Leergebied;
import domein.SysteemConfiguratie;
import domein.Tijdstip;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Maarten
 */
public class ConfiguratieControllerTest
{
    @Mock
    private Catalogus catalogus;
    
    private ConfiguratieController controller;
    private SysteemConfiguratie sysconfig;
    
    private Doelgroep doelgroep1;
    private Doelgroep doelgroep2;
    private Doelgroep doelgroep3;
    
    private Leergebied leergebied1;
    private Leergebied leergebied2;
    private Leergebied leergebied3;
    
    private Email email1;
    private Email email2;
    
    private List<Doelgroep> doelgroepenTest;
    private List<Leergebied> leergebiedenTest;
    private List<Email> emailTest;
    
    @Before
    public void before()
    {
        MockitoAnnotations.initMocks(this);
        this.controller = new ConfiguratieController(catalogus);
        
        doelgroepenTest = new ArrayList<>();
        leergebiedenTest = new ArrayList<>();
        emailTest = new ArrayList<>();
        
        sysconfig = new SysteemConfiguratie();
        sysconfig.setReservatiePeriode(5);
        sysconfig.setMaximaleVerlenging(14);
        sysconfig.setMomentOphalen(Tijdstip.MAANDAGOCHTEND);
        sysconfig.setMomentBinnenbrengen(Tijdstip.VRIJDAGNAMIDDAG);
        sysconfig.setPlaats("0000");
        
        doelgroep1 = new Doelgroep("Kleuter onderwijs");
        doelgroep2 = new Doelgroep("Lager onderwijs");
        doelgroep3 = new Doelgroep("Hoger ondwerwijs");
        leergebied1 = new Leergebied("mens");
        leergebied2 = new Leergebied("biologie");
        leergebied3 = new Leergebied("welzijn");
        email1 = new Email(1, "Header1", "Body1", "Footer1", "Subject1");
        email2 = new Email(2, "Header2", "Body2", "Footer2", "Subject2");
        
        doelgroepenTest.add(doelgroep1);
        doelgroepenTest.add(doelgroep2);
        leergebiedenTest.add(leergebied1);
        leergebiedenTest.add(leergebied2);
        emailTest.add(email1);
        emailTest.add(email2);
    }
    
    @Test
    public void getDoelgroepenGeeftDeDoelgroepenTerug()
    {
        Mockito.when(catalogus.getDoelgroepenLijst()).thenReturn(doelgroepenTest);
        controller = new ConfiguratieController(catalogus);
        List<Doelgroep> lijst = controller.getDoelgroepen();
        Assert.assertEquals(2, lijst.size());
        Assert.assertTrue(lijst.contains(doelgroep1));
    }
    
    @Test
    public void getLeergebiedenGeeftDeLeergebiedenTerug()
    {
        Mockito.when(catalogus.getLeergebiedenLijst()).thenReturn(leergebiedenTest);
        controller = new ConfiguratieController(catalogus);
        List<Leergebied> lijst = controller.getLeergebieden();
        Assert.assertEquals(2, lijst.size());
        Assert.assertTrue(lijst.contains(leergebied1));
    }
    
    @Test
    public void getEmailsGeeftDeEmailsTerug()
    {
        Mockito.when(catalogus.getEmails()).thenReturn(emailTest);
        controller = new ConfiguratieController(catalogus);
        List<Email> lijst = controller.getEmails();
        Assert.assertEquals(2, lijst.size());
        Assert.assertTrue(lijst.contains(email1));
    }
    
    @Test
    public void getSysteemConfiguratieGeeftDeSysteemConfiguratieTerug()
    {
        Mockito.when(catalogus.getSysteemConfiguratie()).thenReturn(sysconfig);
        controller = new ConfiguratieController(catalogus);
        SysteemConfiguratie config = controller.getSysteemConfiguratie();
        Assert.assertEquals(sysconfig, config);
    }
    
    @Test
    public void addDoelgroepVoegtEenDoelgroepToeAanDeLijstMetDoelgroepen()
    {
        Mockito.when(catalogus.getDoelgroepenLijst()).thenReturn(doelgroepenTest);
        controller = new ConfiguratieController(catalogus);
        controller.addDoelgroep(doelgroep3);
        List<Doelgroep> lijst = controller.getDoelgroepen();
        Assert.assertEquals(3, lijst.size());
        Assert.assertTrue(lijst.contains(doelgroep3));
    }
    
    @Test
    public void addLeergebiedVoegtEenLeergebiedToeAanDeLijstMetLeergebieden()
    {
        Mockito.when(catalogus.getLeergebiedenLijst()).thenReturn(leergebiedenTest);
        controller = new ConfiguratieController(catalogus);
        controller.addLeergebied(leergebied3);
        List<Leergebied> lijst = controller.getLeergebieden();
        Assert.assertEquals(3, lijst.size());
        Assert.assertTrue(lijst.contains(leergebied3));
    }
    
    @Test
    public void bevatDoelgroepGeeftTrueTerugIndienDeDoelgroepAanwezigIs()
    {
        Mockito.when(catalogus.getDoelgroepenLijst()).thenReturn(doelgroepenTest);
        controller = new ConfiguratieController(catalogus);
        Assert.assertTrue(controller.bevatDoelgroep(doelgroep1));
    }
    
    @Test
    public void bevatDoelgroepGeeftFalseTerugIndienDeDoelgroepNietAanwezigIs()
    {
        Mockito.when(catalogus.getDoelgroepenLijst()).thenReturn(doelgroepenTest);
        controller = new ConfiguratieController(catalogus);
        Assert.assertFalse(controller.bevatDoelgroep(doelgroep3));
    }
    
    @Test
    public void bevatLeergebiedGeeftTrueTerugIndienHetLeergebiedAanwezigIs()
    {
        Mockito.when(catalogus.getLeergebiedenLijst()).thenReturn(leergebiedenTest);
        controller = new ConfiguratieController(catalogus);
        Assert.assertTrue(controller.bevatLeergebied(leergebied1));
    }
    
    @Test
    public void bevatLeergebiedGeeftFalseTerugIndienHetLeergebiedNietAanwezigIs()
    {
        Mockito.when(catalogus.getLeergebiedenLijst()).thenReturn(leergebiedenTest);
        controller = new ConfiguratieController(catalogus);
        Assert.assertFalse(controller.bevatLeergebied(leergebied3));
    }
    
    @Test
    public void vewijderDoelgroepVerwijdertDeDoelgroepUitDeLijstMetDoelgroepen()
    {
        Mockito.when(catalogus.getDoelgroepenLijst()).thenReturn(doelgroepenTest);
        controller = new ConfiguratieController(catalogus);
        controller.verwijderDoelgroep(doelgroep1);
        List<Doelgroep> lijst = controller.getDoelgroepen();
        Assert.assertFalse(lijst.contains(doelgroep1));
        Assert.assertTrue(lijst.contains(doelgroep2));
        Assert.assertEquals(1, lijst.size());
    }
    
    @Test
    public void vewijderLeergebiedVerwijdertHetLeergebiedUitDeLijstMetLeergebieden()
    {
        Mockito.when(catalogus.getLeergebiedenLijst()).thenReturn(leergebiedenTest);
        controller = new ConfiguratieController(catalogus);
        controller.verwijderLeergebied(leergebied1);
        List<Leergebied> lijst = controller.getLeergebieden();
        Assert.assertFalse(lijst.contains(leergebied1));
        Assert.assertTrue(lijst.contains(leergebied2));
        Assert.assertEquals(1, lijst.size());
    }
}
