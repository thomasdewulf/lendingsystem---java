/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import domein.Catalogus;
import domein.Doelgroep;
import domein.Firma;
import domein.Leergebied;
import domein.Product;
import java.util.ArrayList;
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
 * @author Maarten
 */
public class ProductBeherenControllerTest
{

    @Mock
    private Catalogus catalogus;

    private Doelgroep doelgroep1;
    private Doelgroep doelgroep2;
    private Doelgroep doelgroep3;
    private Doelgroep doelgroep4;
    private Leergebied leergebied1;
    private Leergebied leergebied2;
    private Firma firma1;
    private Product product;

    private List<Doelgroep> doelgroepenTest;
    private List<Leergebied> leergebiedenTest;
    private List<Firma> firmasTest;

    private ProductBeherenController controller;

    @Before
    public void before()
    {
        MockitoAnnotations.initMocks(this);
        
        doelgroep1 = new Doelgroep("Kleuter onderwijs");
        doelgroep2 = new Doelgroep("Lager onderwijs");
        doelgroep3 = new Doelgroep("Secundair onderwijs");
        doelgroep4 = new Doelgroep("Afstands Onderwijs");
        leergebied1 = new Leergebied("mens");
        leergebied2 = new Leergebied("biologie");
        firma1 = new Firma("firma1", "website", "contactPersoon", "emailContactPersoon");
        controller = new ProductToevoegenController(catalogus);

        doelgroepenTest = new ArrayList<>();
        leergebiedenTest = new ArrayList<>();
        firmasTest = new ArrayList<>();
       
        doelgroepenTest.add(doelgroep1);
        doelgroepenTest.add(doelgroep2);
        doelgroepenTest.add(doelgroep3);
        leergebiedenTest.add(leergebied1);
        firmasTest.add(firma1);
    }
    
    @Test
    public void getKandidaatDoelgroepenGeeftDeKandidaatDoelgroepenTerug()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        ObservableList<Doelgroep> lijst = controller.getKandidaatDoelgroepen();
        Assert.assertEquals(3, lijst.size());
        Assert.assertNotNull(lijst);
    }
    @Test
    public void getGekozenDoelgroepenGeeftDeGekozenDoelgroepenTerug()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        controller.addGekozenDoelgroep(doelgroep1);
        ObservableList<Doelgroep> lijstGekozenDoelgroepen = controller.getGekozenDoelgroepen();
        Assert.assertEquals(1, lijstGekozenDoelgroepen.size());
        Assert.assertEquals(doelgroep1, lijstGekozenDoelgroepen.get(0));
        Assert.assertNotNull(lijstGekozenDoelgroepen);
    }
    @Test
    public void getAlleFirmasGeeftAlleFirmasTerug()
    {
        mockitoInOrdeZettenFirmas();
        controller = new ProductToevoegenController(catalogus);
        ObservableList<Firma> lijstFirmas = controller.getAlleFirmas();
        Assert.assertEquals(1, lijstFirmas.size());
        Assert.assertEquals(firma1, lijstFirmas.get(0));
        Assert.assertNotNull(lijstFirmas);
    }
    @Test
    public void addKandidaatLeergebiedVoegtLeergebiedToeAanKandidaatLeergebieden()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        controller.addKandidaatLeergebied(leergebied2);
        ObservableList<Leergebied> lijstLeergebieden = controller.getKandidaatLeergebieden();   
        Assert.assertEquals(2, lijstLeergebieden.size());
        Assert.assertNotNull(lijstLeergebieden);
    }
    @Test
    public void removeGekozenLeergebiedenVerwijdertDeMeegegevenLeergebiedenUitGekozenLeergebieden()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        ObservableList<Leergebied> hulp = FXCollections.observableArrayList();
        controller.addGekozenLeergebied(leergebied1);
        controller.addGekozenLeergebied(leergebied2);
        hulp.add(leergebied1);
        controller.removeGekozenLeergebieden(hulp);
        ObservableList<Leergebied> lijstGekozenLeergebieden = controller.getGekozenLeergebieden();
        Assert.assertEquals(1, lijstGekozenLeergebieden.size());
        Assert.assertNotNull(lijstGekozenLeergebieden);
        Assert.assertEquals(leergebied2, lijstGekozenLeergebieden.get(0));
    }
    @Test
    public void addGekozenDoelgroepVoegtDoelgroepToeAanGekozenDoelgroepen()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        controller.addGekozenDoelgroep(doelgroep1);
        ObservableList<Doelgroep> lijstDoelgroepen = controller.getGekozenDoelgroepen();
        Assert.assertEquals(1, lijstDoelgroepen.size());
    }
    @Test
    public void removeKandidaatDoelgroepVerwijdertDoelgroepUitKandidaatDoelgroepen()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        controller.removeKandidaatDoelgroep(doelgroep1);
        ObservableList<Doelgroep> lijstDoelgroepen = controller.getKandidaatDoelgroepen();
        System.out.println(lijstDoelgroepen);
        Assert.assertEquals(2, lijstDoelgroepen.size());
    }
    @Test
    public void addKandidaatDoelgroepVoegtDoelgroepToeAanKandidaatDoelgroepen()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        controller.addKandidaatDoelgroep(doelgroep4);
        ObservableList<Doelgroep> lijstDoelgroepen = controller.getKandidaatDoelgroepen();
        Assert.assertEquals(4, lijstDoelgroepen.size());
    }
     @Test
    public void setGekozenFirmaVultDeGekozenFirmaIn()
    {
        mockitoInOrdeZettenFirmas();
        controller = new ProductToevoegenController(catalogus);
        controller.setGekozenFirma(firma1);
        Assert.assertEquals(firma1, controller.getGekozenFirma());
    }
    @Test
    public void getGekozenFirmaGeeftDeGekozenFirmaTerug()
    {
        mockitoInOrdeZettenFirmas();
        controller = new ProductToevoegenController(catalogus);
        controller.setGekozenFirma(firma1);
        Firma f = controller.getGekozenFirma();
        Assert.assertEquals(firma1, f);
    }
    @Test
    public void removeGekozenDoelgroepenMaaktDeGekozenDoelgroepenLeeg()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        ObservableList<Doelgroep> hulp = FXCollections.observableArrayList();   
        controller.addGekozenDoelgroep(doelgroep1);
        controller.addGekozenDoelgroep(doelgroep4);
        controller.removeGekozenDoelgroepen();
        ObservableList<Doelgroep> lijstGekozenDoelgroepen = controller.getGekozenDoelgroepen();   
        Assert.assertEquals(0, lijstGekozenDoelgroepen.size());
        Assert.assertNotNull(lijstGekozenDoelgroepen);
    }
    @Test
    public void geenDoelgroepKandidatenGeeftTrueTerugIndienDoelgroepKandidatenLeegIs()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        controller.removeAlleKandidaatDoelgroepen();
        Assert.assertTrue(controller.geenDoelgroepKandidaten());
    }
    @Test
    public void geenDoelgroepKandidatenGeeftFalseTerugIndienDoelgroepKandidatenGevuldIs()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        Assert.assertFalse(controller.geenDoelgroepKandidaten());
    }
    @Test
    public void geenGekozenDoelgroepGeeftFalseTerugIndienGekozenDoelgroepenGevuldIs()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        controller.addGekozenDoelgroep(doelgroep1);
        Assert.assertFalse(controller.geenGekozenDoelgroep());
    }
    @Test
    public void geenGekozenDoelgroepGeeftTrueTerugIndienGekozenDoelgroepenLeegIs()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        controller.removeAlleGekozenDoelgroepen();
        Assert.assertTrue(controller.geenGekozenDoelgroep());
    }
    @Test
    public void getKandidaatLeergebiedenGeeftDeKandidaatLeergebiedenTerug()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        ObservableList<Leergebied> lijst = controller.getKandidaatLeergebieden();
        System.out.println(lijst);
        Assert.assertEquals(1, lijst.size());
        Assert.assertNotNull(lijst);
    }
    @Test
    public void getGekozenLeergebiedenGeeftDeGekozenLeergebiedenTerug()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        controller.addGekozenLeergebied(leergebied2);
        ObservableList<Leergebied> lijstGekozenLeergebieden = controller.getGekozenLeergebieden();
        Assert.assertEquals(1, lijstGekozenLeergebieden.size());
        Assert.assertEquals(leergebied2, lijstGekozenLeergebieden.get(0));
        Assert.assertNotNull(lijstGekozenLeergebieden);
    }
    @Test
    public void addGekozenLeergebiedVoegtLeergebiedToeAanGekozenLeergebieden()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        controller.addGekozenLeergebied(leergebied2);
        ObservableList<Leergebied> lijstLeergebieden = controller.getGekozenLeergebieden();
        Assert.assertEquals(1, lijstLeergebieden.size());
    }
    @Test
    public void removeKandidaatLeergebiedVerwijdertLeergebiedUitKandidaatLeergebieden()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        controller.removeKandidaatLeergebied(leergebied1);
        ObservableList<Leergebied> lijstLeergebieden = controller.getKandidaatLeergebieden();
        Assert.assertEquals(0, lijstLeergebieden.size());
    }
    @Test
    public void geenLeergebiedKandidatenGeeftTrueTerugIndienLeergebiedKandidatenLeegIs()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        controller.removeKandidaatLeergebied(leergebied1);
        Assert.assertTrue(controller.geenLeergebiedKandidaten());
    }
    @Test
    public void geenLeergebiedKandidatenGeeftFalseTerugIndienLeergebiedKandidatenGevuldIs()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        Assert.assertFalse(controller.geenLeergebiedKandidaten());
    }
    @Test
    public void geenGekozenLeergebiedGeeftFalseTerugIndienGekozenLeergebiedenGevuldIs()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        controller.addGekozenLeergebied(leergebied2);
        Assert.assertFalse(controller.geenGekozenLeergebied());
    }
    @Test
    public void geenGekozenLeergebiedGeeftTrueTerugIndienGekozenLeergebiedenLeegIs()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        controller.removeAlleGekozenLeergebieden();
        Assert.assertTrue(controller.geenGekozenLeergebied());
    }
    @Test
    public void removeGekozenLeergebiedVerwijdertLeergebiedUitGekozenLeergebieden()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        controller.addGekozenLeergebied(leergebied1);
        controller.addGekozenLeergebied(leergebied2);
        controller.removeGekozenLeergebied(leergebied2);
        ObservableList<Leergebied> lijstLeergebieden = controller.getGekozenLeergebieden();
        Assert.assertEquals(1, lijstLeergebieden.size());
        Assert.assertEquals(leergebied1, lijstLeergebieden.get(0));
    }
    @Test
    public void removeAlleGekozenLeergebiedenMaaktDeGekozenLeergebiedenLeeg()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        ObservableList<Leergebied> hulp = FXCollections.observableArrayList();   
        controller.addGekozenLeergebied(leergebied1);
        controller.addGekozenLeergebied(leergebied2);
        controller.removeAlleGekozenLeergebieden();
        ObservableList<Leergebied> lijstGekozenLeergebieden = controller.getGekozenLeergebieden();   
        Assert.assertEquals(0, lijstGekozenLeergebieden.size());
        Assert.assertNotNull(lijstGekozenLeergebieden);
    }
    @Test
    public void removeAlleGekozenDoelgroepenMaaktDeGekozenDoelgroepenLeeg()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        ObservableList<Doelgroep> hulp = FXCollections.observableArrayList();   
        controller.addGekozenDoelgroep(doelgroep1);
        controller.addGekozenDoelgroep(doelgroep2);
        controller.removeAlleGekozenDoelgroepen();
        ObservableList<Doelgroep> lijstGekozenDoelgroepen = controller.getGekozenDoelgroepen();   
        Assert.assertEquals(0, lijstGekozenDoelgroepen.size());
        Assert.assertNotNull(lijstGekozenDoelgroepen);
    }
    @Test
    public void removeAlleKandidaatDoelgroepenMaaktDeKandidaatDoelgroepenLeeg()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        controller.removeAlleKandidaatDoelgroepen();
        ObservableList<Doelgroep> lijstDoelgroepen = controller.getKandidaatDoelgroepen();   
        Assert.assertEquals(0, lijstDoelgroepen.size());
        Assert.assertNotNull(lijstDoelgroepen);
    }
    @Test
    public void removeGekozenDoelgroepVerwijdertDoelgroepUitGekozenDoelgroepen()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        controller.addGekozenDoelgroep(doelgroep1);
        controller.addGekozenDoelgroep(doelgroep4);
        controller.removeGekozenDoelgroep(doelgroep4);
        ObservableList<Doelgroep> lijstDoelgroepen = controller.getGekozenDoelgroepen();
        Assert.assertEquals(1, lijstDoelgroepen.size());
        Assert.assertEquals(doelgroep1, lijstDoelgroepen.get(0));
    }
    @Test
    public void addFirmaVoegtFirmaToeAanLijstMetFirmas()
    {
        mockitoInOrdeZettenFirmas();
        controller = new ProductToevoegenController(catalogus);
        Firma firma2 = new Firma("firma2", "website", "contactPersoon", "emailContactPersoon");
        controller.addFirma(firma2);
        ObservableList<Firma> lijstFirmas = controller.getAlleFirmas();
        Assert.assertEquals(2, lijstFirmas.size());
        Assert.assertEquals(firma2, lijstFirmas.get(1));
    }
    @Test
    public void bevatFirmaGeeftTrueTerugIndienDeFirmaAanwezigIsInDeLijstMetFirmas()
    {
        mockitoInOrdeZettenFirmas();
        controller = new ProductToevoegenController(catalogus);
        System.out.println(controller.getAlleFirmas());
        Firma f = new Firma("firma1", null, null, null);
        Assert.assertTrue(controller.bevatFirma(f));
    }
    @Test
    public void bevatFirmaGeeftFalseTerugIndienDeFirmaNietAanwezigIsInDeLijstMetFirmas()
    {
        mockitoInOrdeZettenFirmas();
        controller = new ProductToevoegenController(catalogus);
        Firma f = new Firma("firma2", null, null, null);
        Assert.assertFalse(controller.bevatFirma(f));
    }
    @Test
    public void bevatLeergebiedGeeftTrueTerugIndienLeergebiedAanwezigIsInDeLijstMetLeergebieden()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        Leergebied l = new Leergebied("mens");
        Assert.assertTrue(controller.bevatLeergebied(l));
    }
    @Test
    public void bevatLeergebiedGeeftFalseTerugIndienLeergebiedNietAanwezigIsInDeLijstMetLeergebieden()
    {
        mockitoInOrdeZettenLeergebieden();
        controller = new ProductToevoegenController(catalogus);
        Leergebied l = new Leergebied("leergebiedNaam");
        Assert.assertFalse(controller.bevatLeergebied(l));
    }
    @Test
    public void bevatDoelgroepGeeftTrueTerugIndienDoelgroepAanwezigIsInDeLijstMetDoelgroepen()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        Doelgroep d = new Doelgroep("Lager onderwijs");
        Assert.assertTrue(controller.bevatDoelgroep(d));
    }
    @Test
    public void bevatDoelgroepGeeftFalseTerugIndienDoelgroepNietAanwezigIsInDeLijstMetDoelgroepen()
    {
        mockitoInOrdeZettenDoelGroepen();
        controller = new ProductToevoegenController(catalogus);
        Doelgroep d = new Doelgroep("Volwassenen onderwijs");
        Assert.assertFalse(controller.bevatDoelgroep(d));
    }

    //MOCKITO
    public void mockitoInOrdeZettenDoelGroepen()
    {
        Mockito.when(catalogus.getDoelgroepenLijst()).thenReturn(doelgroepenTest);
    }

    public void mockitoInOrdeZettenLeergebieden()
    {
        Mockito.when(catalogus.getLeergebiedenLijst()).thenReturn(leergebiedenTest);
    }

    public void mockitoInOrdeZettenFirmas()
    {
        Mockito.when(catalogus.getFirmaLijst()).thenReturn(firmasTest);
    }
}
