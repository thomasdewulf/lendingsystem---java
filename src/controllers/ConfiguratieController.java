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
import domein.Product;
import domein.SysteemConfiguratie;
import domein.Tijdstip;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import util.EntityManagerUtil;

/**
 *
 * @author Remko
 */
public class ConfiguratieController {

    private ObservableList<Doelgroep> doelgroepen;
    private ObservableList<Leergebied> leergebieden;
    private ObservableList<Email> emails;
    private Catalogus catalogus = new Catalogus();
    private EntityManager em = EntityManagerUtil.getEm();
    private SysteemConfiguratie systeemConfiguratie;
    private Comparator<Leergebied> byLeergebiedNaam= (l1, l2) -> l1.getNaam().compareToIgnoreCase(l2.getNaam());
    private Comparator<Doelgroep> byDoelgroepNaam = (d1,d2) -> d1.getNaam().compareToIgnoreCase(d2.getNaam());

    public ConfiguratieController() {
        doelgroepen = FXCollections.observableArrayList(catalogus.getDoelgroepenLijst());
        leergebieden = FXCollections.observableArrayList(catalogus.getLeergebiedenLijst());
        emails = FXCollections.observableArrayList(catalogus.getEmails());
        systeemConfiguratie = this.catalogus.getSysteemConfiguratie();
    }

    public ConfiguratieController(Catalogus catalogus) {
        this.catalogus = catalogus;
        doelgroepen = FXCollections.observableArrayList(catalogus.getDoelgroepenLijst());
        leergebieden = FXCollections.observableArrayList(catalogus.getLeergebiedenLijst());
        emails = FXCollections.observableArrayList(catalogus.getEmails());
        systeemConfiguratie = this.catalogus.getSysteemConfiguratie();
    }

    public ObservableList<Doelgroep> getDoelgroepen() {
        return doelgroepen;
    }

    public ObservableList<Leergebied> getLeergebieden() {
        return leergebieden;
    }

    public ObservableList<Leergebied> getSortedLeergebieden() {
        return leergebieden.sorted(byLeergebiedNaam);
    }
    public ObservableList<Doelgroep> getSortedDoelgroepen(){
        return doelgroepen.sorted(byDoelgroepNaam);
    }
    public ObservableList<Email> getEmails() {
        return emails;
    }

    public SysteemConfiguratie getSysteemConfiguratie() {
        return systeemConfiguratie;
    }

    public void addDoelgroep(Doelgroep d) {
        em.getTransaction().begin();
        em.persist(d);
        em.getTransaction().commit();
        this.doelgroepen.add(d);
    }

    public boolean bevatDoelgroep(Doelgroep d) {
        return this.doelgroepen.contains(d);
    }

    public void verwijderDoelgroep(Doelgroep d) {
        em.getTransaction().begin();
        em.remove(d);
        em.getTransaction().commit();
        this.doelgroepen.remove(d);
    }

    public boolean bevatLeergebied(Leergebied l) {
        return this.leergebieden.contains(l);
    }

    public void addLeergebied(Leergebied l) {
        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
        this.leergebieden.add(l);
    }

    public void verwijderLeergebied(Leergebied l) {
        em.getTransaction().begin();
        em.remove(l);
        em.getTransaction().commit();
        this.leergebieden.remove(l);
    }

    public void wijzigConfiguratie(Tijdstip afhalen, Tijdstip ophalen, int maxVerleng, int resPeriode) {
        this.systeemConfiguratie.setMaximaleVerlenging(maxVerleng);
        this.systeemConfiguratie.setReservatiePeriode(resPeriode);
        this.systeemConfiguratie.setMomentOphalen(ophalen);
        this.systeemConfiguratie.setMomentBinnenbrengen(afhalen);
        em.getTransaction().begin();
        em.merge(this.systeemConfiguratie);
        em.getTransaction().commit();
    }

}
