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
import domein.ToevoegenType;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import util.EntityManagerUtil;

/**
 *
 * @author Remko
 */
public abstract class ProductBeherenController {
    protected ObservableList<Doelgroep> doelgroepKandidaten;
    protected ObservableList<Doelgroep> gekozenDoelgroepen;
    protected ObservableList<Leergebied> leergebiedKandidaten;
    protected ObservableList<Leergebied> gekozenLeergebieden;
    protected ObservableList<Firma> firmas;
    protected Firma gekozenFirma;
    protected Catalogus catalogus = new Catalogus();
    protected EntityManager em = EntityManagerUtil.getEm();

    public abstract void beheerProduct(String foto, String artikelNaam, String omschrijving, String artikelNummer, double prijs,
            int aantalInCatalogus, int aantalProductStukken, boolean uitleenbaar, String plaats, String origineleArtikelnaam);
    
    //Test
    public void verwijderProduct(Product p)
    {
        em.getTransaction().begin();
        Product product = catalogus.getProductByName(p.getArtikelNaam());
        if(product != null)
        {
          em.remove(em.merge(product));  
            System.out.println("Product verwijdert.");
        }
        em.getTransaction().commit();
    }

    public ObservableList<Doelgroep> getKandidaatDoelgroepen() {
        return FXCollections.unmodifiableObservableList(doelgroepKandidaten);
    }

    public ObservableList<Doelgroep> getGekozenDoelgroepen() {
        return FXCollections.unmodifiableObservableList(gekozenDoelgroepen);
    }

    public ObservableList<Firma> getAlleFirmas() {
        return FXCollections.unmodifiableObservableList(firmas);
    }
      public void addKandidaatLeergebied(Leergebied d) {
        leergebiedKandidaten.add(d);
    }

    public void removeGekozenLeergebieden(ObservableList<Leergebied> teVerwijderen) {
        gekozenLeergebieden.removeAll(teVerwijderen);
    }
    public void addGekozenDoelgroep(Doelgroep selectie) {
        gekozenDoelgroepen.add(selectie);
    }

    public void removeKandidaatDoelgroep(Doelgroep selectie) {
        doelgroepKandidaten.remove(selectie);
    }

    public void addKandidaatDoelgroep(Doelgroep d) {
        doelgroepKandidaten.add(d);
    }

    public void setGekozenFirma(Firma f) {
        gekozenFirma = f;
    }
    
    public Firma getGekozenFirma()
    {
        return gekozenFirma;
    }

    public void removeGekozenDoelgroepen() {
        gekozenDoelgroepen.clear();
    }

    public boolean geenDoelgroepKandidaten() {
        return doelgroepKandidaten.isEmpty() || doelgroepKandidaten == null;
    }

    public boolean geenGekozenDoelgroep() {
        return gekozenDoelgroepen.isEmpty() ||gekozenDoelgroepen == null;
    }

    public <E> void addObserver(ListChangeListener<E> e, ToevoegenType soort) {
        switch (soort) {
            case GEKOZENDOELGROEP:
                gekozenDoelgroepen.addListener((ListChangeListener<Doelgroep>) e);
                break;
            case KANDIDAATDOELGROEP:
                doelgroepKandidaten.addListener((ListChangeListener<Doelgroep>) e);
                break;
            case GEKOZENLEERGEBIED:
                gekozenLeergebieden.addListener((ListChangeListener<Leergebied>) e);
                break;
            case KANDIDAATLEERGEBIED:
                leergebiedKandidaten.addListener((ListChangeListener<Leergebied>) e);
                break;
        }
    }

    public ObservableList<Leergebied> getKandidaatLeergebieden() {
        return FXCollections.unmodifiableObservableList(leergebiedKandidaten);
    }

    public ObservableList<Leergebied> getGekozenLeergebieden() {
        return FXCollections.unmodifiableObservableList(gekozenLeergebieden);
    }

    public void addGekozenLeergebied(Leergebied selectie) {
        this.gekozenLeergebieden.add(selectie);
    }

    public void removeKandidaatLeergebied(Leergebied selectie) {
        this.leergebiedKandidaten.remove(selectie);
    }
  
    public boolean geenLeergebiedKandidaten() {
        return this.leergebiedKandidaten.isEmpty() || leergebiedKandidaten == null;
    }

    public boolean geenGekozenLeergebied() {
        return this.gekozenLeergebieden.isEmpty() || this.gekozenLeergebieden == null;
    }

    public void removeGekozenLeergebied(Leergebied selectie) {
        this.gekozenLeergebieden.remove(selectie);
    }

    public void removeAlleGekozenLeergebieden() {
        this.gekozenLeergebieden.clear();
    }
    
    public void removeAlleGekozenDoelgroepen() {
        this.gekozenDoelgroepen.clear();
    }
    
    public void removeAlleKandidaatDoelgroepen(){
        this.doelgroepKandidaten.clear();
    }

    public void removeGekozenDoelgroep(Doelgroep selectie) {
        this.gekozenDoelgroepen.remove(selectie);
    }

    public void addFirma(Firma firma){
        em.getTransaction().begin();
        em.persist(firma);
        em.getTransaction().commit();
        this.firmas.add(firma);
    }

    public boolean bevatFirma(Firma naam) {
      return this.catalogus.bevatFirma(naam) || this.firmas.contains(naam);
    }

    public boolean bevatLeergebied(Leergebied naam) {
        return this.catalogus.bevatLeergebied(naam)|| this.leergebiedKandidaten.contains(naam) ||  this.gekozenLeergebieden.contains(naam) ;
    }
    public boolean bevatDoelgroep(Doelgroep naam) {
        return this.catalogus.bevatDoelgroep(naam) || this.doelgroepKandidaten.contains(naam) || this.gekozenDoelgroepen.contains(naam);
    }

    public boolean bevatProduct(String productNaam) {
        return this.catalogus.bevatProduct(productNaam);
    }

    public void addLeergebied(Leergebied l) {
        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
        
    }

    public void addDoelgroep(Doelgroep d) {
        em.getTransaction().begin();
        em.persist(d);
        em.getTransaction().commit();
    }
    
}
