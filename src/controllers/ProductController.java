/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import domein.Catalogus;
import domein.Product;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javax.persistence.EntityManager;
import util.EntityManagerUtil;

/**
 *
 * @author Maarten
 */
public class ProductController {

    private String filter;
    private ObservableList<Product> producten;
    private FilteredList<Product> filteredProductenlijst;
    private Comparator<Product> byArtikelnaam = (p1, p2) -> p1.getArtikelNaam().compareToIgnoreCase(p2.getArtikelNaam());
    private Comparator<Product> byArtikelnummer = (p1, p2) -> p1.getArtikelNummer().compareToIgnoreCase(p2.getArtikelNummer());
    private Comparator<Product> byAantal = (p1, p2) -> ((Integer) p1.getAantalInCatalogus()).compareTo(p2.getAantalInCatalogus());
    private Comparator<Product> sortOrder = byArtikelnaam.thenComparing(byArtikelnummer).thenComparing(byAantal);
    private EntityManager em = EntityManagerUtil.getEm();
    private Catalogus catalogus = new Catalogus();

    public ProductController() {
 
        producten = FXCollections.observableArrayList(this.catalogus.getProductenLijst());
        filteredProductenlijst = new FilteredList<>(producten , p -> true);

    }
    
    public void setFilter(String filter)
    {
        this.filter = filter;
    }

    public ObservableList<Product> getProducten() {
        producten = FXCollections.observableArrayList(this.catalogus.getProductenLijst());
        return FXCollections.unmodifiableObservableList(producten);
    }

    public void veranderFilter(String filternaam) {
        filteredProductenlijst.setPredicate(product -> {
            if (filternaam == null || filternaam.isEmpty()) {
                return true;
            }
            if (product.getArtikelNaam().toLowerCase().contains(filternaam.toLowerCase())) {
                return true;
            } else if (product.getArtikelNummer().toLowerCase().contains(filternaam.toLowerCase())) {
                return true;
            } else if (product.bevatDoelgroep(filternaam)){
                return true;
            }else if(product.bevatLeergebied(filternaam)){
                return true;
            }
            
            return false;
        });

    }

    public ObservableList<Product> getProductenSortedList() {
        producten = FXCollections.observableArrayList(this.catalogus.getProductenLijst());
        filteredProductenlijst = new FilteredList<>(producten , p -> true);
        veranderFilter(filter);
        return this.filteredProductenlijst.sorted(sortOrder);
    }

}
