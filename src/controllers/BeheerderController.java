/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import domein.Beheerder;
import domein.BeheerderType;
import domein.Catalogus;
import domein.Product;
import java.awt.PageAttributes;
import java.util.Comparator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import util.EntityManagerUtil;

/**
 *
 * @author Maarten
 */
public class BeheerderController {

    private ObservableList<Beheerder> beheerders;
    private Catalogus catalogus;
    private Comparator<Beheerder> byEmail = (b1, b2) -> b1.getEmail().compareToIgnoreCase(b2.getEmail());
    private Comparator<Beheerder> byNaam = (b1, b2) -> b1.getNaam().compareToIgnoreCase(b2.getNaam());
    private Comparator<Beheerder> byVoornaam = (b1, b2) -> b1.getVoornaam().compareToIgnoreCase(b2.getVoornaam());
    private Comparator<Beheerder> sortOrder = byEmail.thenComparing(byNaam).thenComparing(byVoornaam);
    private EntityManager em = EntityManagerUtil.getEm();
    
    public BeheerderController() {
        catalogus = new Catalogus();
        beheerders = FXCollections.observableList(catalogus.getBeheerders());
    }
    
    public BeheerderController(Catalogus catalogus) {
        this.catalogus = catalogus;
        beheerders = FXCollections.observableList(catalogus.getBeheerders());
    }
    
    public ObservableList<Beheerder> getBeheerders() {
        //beheerders = FXCollections.observableList(catalogus.getBeheerders());
        return beheerders.filtered(b -> !b.isHoofdBeheerder()).sorted(sortOrder);
    }
    
    public boolean isBeheerder(Beheerder b) {
        return beheerders.contains(b);
    }
    
    public Beheerder geefBeheerder(String email) {
        return catalogus.geefBeheerder(email);
    }
    
    public void verwijderBeheerder(Beheerder hulp) {
        em.getTransaction().begin();
        Beheerder beheerder = geefBeheerder(hulp.getEmail());
        if(beheerder != null)
        {
            beheerders.remove(beheerder);
            em.remove(hulp);
        }
        em.getTransaction().commit();
    }
    
    public void voegBeheerderToe(Beheerder beheerder) {
        em.getTransaction().begin();
        em.persist(beheerder);
        em.getTransaction().commit();
        beheerders.add(beheerder);
    }
    
    public void verwijderBeheerders(List<Beheerder> lijstBeheerders) {
        this.beheerders.removeAll(lijstBeheerders);
    }
    
    public void persisteerBeheerders(List<Beheerder> lijstBeheerders) {
        em.getTransaction().begin();
        lijstBeheerders.forEach(b -> em.persist(b));
        em.getTransaction().commit();
    }
    
    public boolean bevatBeheerder(Beheerder b) {
        return this.beheerders.contains(b);
    }
    
    public void wijzigBeheerder(Beheerder selectie, Beheerder b) {
        selectie.setEmail(b.getEmail());
        selectie.setNaam(b.getNaam());
        selectie.setVoornaam(b.getVoornaam());
        em.getTransaction().begin();
        em.merge(selectie);
        em.getTransaction().commit();
    }
    
}
