/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import domein.Doelgroep;
import domein.Leergebied;
import domein.Product;
import java.util.List;
import javafx.collections.FXCollections;

/**
 *
 * @author Remko
 */
public class ProductWijzigenController extends ProductBeherenController {

    public ProductWijzigenController(Product product) {
        List<Doelgroep> lijstDoelgroepKandidaten = catalogus.getDoelgroepenLijst();
        lijstDoelgroepKandidaten.removeAll(product.getDoelgroepen());
        doelgroepKandidaten = FXCollections.observableArrayList(lijstDoelgroepKandidaten);
        gekozenDoelgroepen = FXCollections.observableArrayList(product.getDoelgroepen());
        List<Leergebied> lijstLeergebiedKandidaten = catalogus.getLeergebiedenLijst();
        lijstLeergebiedKandidaten.removeAll(product.getLeergebieden());
        leergebiedKandidaten = FXCollections.observableArrayList(lijstLeergebiedKandidaten);
        gekozenLeergebieden = FXCollections.observableArrayList(product.getLeergebieden());
        firmas = FXCollections.observableArrayList(catalogus.getFirmaLijst());
        gekozenFirma = product.getFirma();
    }

    @Override
    public void beheerProduct(String foto, String artikelNaam, String omschrijving, String artikelNummer, double prijs, int aantalInCatalogus, int aantalProductStukken, boolean uitleenbaar, String plaats, String origineleArtikelnaam) {
        //Voor het definitief wijzigen in de databank: alles in deze methode uit commentaar halen.
        
        em.getTransaction().begin();
        Product product = catalogus.getProductByName(origineleArtikelnaam);
        System.out.println("Oud Product: " + product);
        product.setFoto(foto);
        product.setArtikelNaam(artikelNaam);
        product.setOmschrijving(omschrijving);
        product.setArtikelNummer(artikelNummer);
        product.setPrijs(prijs);
        product.setAantalInCatalogus(aantalInCatalogus);
        product.setAantalProductStukken(aantalProductStukken);
        product.setUitleenbaar(uitleenbaar);
        product.setPlaats(plaats);
        product.setDoelgroepen(super.getGekozenDoelgroepen());
        product.setLeergebieden(super.getGekozenLeergebieden());
        product.setFirma(super.getGekozenFirma());
        System.out.println("Gewijzigd Product: " + product);
       em.merge(product);
       em.getTransaction().commit();  
    }

}
