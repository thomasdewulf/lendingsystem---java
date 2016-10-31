/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import domein.AspNetUsers;
import domein.Catalogus;
import domein.Product;
import domein.ReservatieLijn;
import domein.ReservatieStatus;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.EntityManager;
import util.EntityManagerUtil;

/**
 *
 * @author De Puysseleyr
 */
public abstract class ReservatieBeherenController {
    protected ObservableList<Product> productKandidaten;
    protected ObservableList<Product> gekozenProducten;
    protected ObservableList<ReservatieLijn> gekozenReservatieLijnen;
//    protected ObservableList<ReservatieLijn> gekozenPersoneel;
    protected ObservableList<AspNetUsers> personeel;
    protected AspNetUsers gekozenPersoneel;
    protected StringProperty personeelProperty; //= new SimpleStringProperty(String.format("%s","Nog niemand gekozen"));
    protected Catalogus catalogus = new Catalogus();
    protected EntityManager em = EntityManagerUtil.getEm();
    
    
    public abstract void voegReservatieToe(Date startDatum, Date eindDatum, Date aanmaakDatum, ReservatieStatus reservatieStatus, String discriminator,
            Collection<ReservatieLijn> reservatieLijnen, AspNetUsers user);
    
    
    
    
     public ObservableList<Product> getProductKandidaten() {
        return FXCollections.unmodifiableObservableList(productKandidaten);
    }

    public ObservableList<Product> getGekozenProducten() {
        return FXCollections.unmodifiableObservableList(gekozenProducten);
    }
    
     public ObservableList<ReservatieLijn> getGekozenReservatieLijnen() {
        return gekozenReservatieLijnen;
    }
    
    

    public AspNetUsers getGekozenPersoneel() {
        return gekozenPersoneel;
    }
    


    public StringProperty getPersoneelProperty() {
        if(gekozenPersoneel == null)
        {
            return new SimpleStringProperty(String.format("%s","Nog niemand gekozen"));
        }
        return personeelProperty;
    }
    
    
    

    public void setPersoneelProperty(StringProperty personeelProperty) {
        this.personeelProperty = personeelProperty;
    }
    
   
    
    public ObservableList<AspNetUsers> getAllePersoneel()
    {
        return FXCollections.unmodifiableObservableList(personeel);
    }
    
    public void addKandidaatProduct(Product product)
    {
        productKandidaten.add(product);
    }
    
    public void removeGekozenProducten(ObservableList<Product> teVerwijderen) {
        gekozenProducten.removeAll(teVerwijderen);
    }
    
    public void setGekozenPersoneel(AspNetUsers u) {
        gekozenPersoneel = u;
    }
    
    public void addReservatieLijn(ReservatieLijn lijn)
    {
        
        gekozenReservatieLijnen.add(lijn);
    }
    
    public void removeReservatieLijnen(List<ReservatieLijn> teVerwijderen)
    {
        gekozenReservatieLijnen.removeAll(teVerwijderen);
    }
    
    public void removeReservatieLijn(ReservatieLijn lijn)
    {
        gekozenReservatieLijnen.remove(lijn);
    }

    public void setGekozenReservatieLijnen(ObservableList<ReservatieLijn> gekozenReservatieLijnen) {
        this.gekozenReservatieLijnen = gekozenReservatieLijnen;
    }
    
    
    
    
    public void pasAantalAan(int aantalBestellen, ReservatieLijn lijn)
    {
        lijn.setAantal(aantalBestellen);
    }
    
    public void pasAantalBeschikbaarAan(int aantalBestellen, ReservatieLijn lijn)
    {
        int aantalBeschikbaar = lijn.getProduct().getAantalInCatalogus();
        lijn.getProduct().setAantalInCatalogus(aantalBeschikbaar-aantalBestellen);
    }
    
    
    
    
}
