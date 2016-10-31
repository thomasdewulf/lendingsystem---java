/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.opencsv.CSVReader;
import domein.Catalogus;
import domein.Doelgroep;
import domein.Firma;
import domein.Leergebied;
import domein.Product;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javax.persistence.NoResultException;

/**
 *
 * @author dewul
 */
public class ProductToevoegenController extends ProductBeherenController
{

    public ProductToevoegenController()
    {
        doelgroepKandidaten = FXCollections.observableArrayList(catalogus.getDoelgroepenLijst());
        gekozenDoelgroepen = FXCollections.observableArrayList();
        leergebiedKandidaten = FXCollections.observableArrayList(catalogus.getLeergebiedenLijst());
        gekozenLeergebieden = FXCollections.observableArrayList();
        firmas = FXCollections.observableArrayList(catalogus.getFirmaLijst());
    }

    public ProductToevoegenController(Catalogus catalogus)
    {
        this.catalogus = catalogus;
        doelgroepKandidaten = FXCollections.observableArrayList(this.catalogus.getDoelgroepenLijst());
        gekozenDoelgroepen = FXCollections.observableArrayList();
        leergebiedKandidaten = FXCollections.observableArrayList(this.catalogus.getLeergebiedenLijst());
        gekozenLeergebieden = FXCollections.observableArrayList();
        firmas = FXCollections.observableArrayList(this.catalogus.getFirmaLijst());
    }

    @Override
    public void beheerProduct(String foto, String artikelNaam, String omschrijving, String artikelNummer, double prijs, int aantalInCatalogus, int aantalProductStukken, boolean uitleenbaar, String plaats, String origineleArtikelnaam)
    {

        Product product = catalogus.getProductByName(artikelNaam);
        if (product == null)
        {
            em.getTransaction().begin();
            Product nieuwProduct = new Product(super.getGekozenDoelgroepen(), super.getGekozenLeergebieden(), super.getGekozenFirma(), foto, artikelNaam, omschrijving, artikelNummer, prijs, aantalInCatalogus, aantalProductStukken, uitleenbaar, plaats);
            System.out.println(nieuwProduct);
            em.persist(nieuwProduct);
            em.getTransaction().commit();
        } else
        {
            System.out.println("Product bestaat al");
        }
    }

    public void leesCSV(File file)
    {
        try
        {
            CSVReader reader = new CSVReader(new FileReader(file), ';');
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null)
            {
                //em.getTransaction().begin();
                Product product = new Product();
                product.setArtikelNaam(nextLine[0]);
                product.setArtikelNummer(nextLine[1]);
                product.setOmschrijving(nextLine[2]);
                product.setPrijs(Double.parseDouble(nextLine[3]));
                product.setAantalInCatalogus(Integer.parseInt(nextLine[4]));
                product.setAantalProductStukken(Integer.parseInt(nextLine[5]));
                product.setUitleenbaar(Boolean.parseBoolean(nextLine[6]));
                String[] doelgroepen = nextLine[7].split(",");
                String[] leergebieden = nextLine[8].split(",");
                Collection<Doelgroep> doelgroepCollection = new ArrayList<>();
                Collection<Leergebied> leergebiedCollection = new ArrayList<>();
                Doelgroep doelgroep = null;
                Leergebied leergebied = null;
                //Zoeken naar doelgroepen en leergebieden -> Aanmaken indien onbestaande?
                for (String doelgroepen1 : doelgroepen)
                {

                    try
                    {
                        doelgroep = em.createNamedQuery("Doelgroep.zoekDoelgroepOpNaam", Doelgroep.class).setParameter("naam", doelgroepen1).getSingleResult();
                    } catch (NoResultException ex)
                    {
//Do nothing
                    }
                    if (doelgroep != null)
                    {
                        doelgroepCollection.add(doelgroep);
                    } else
                    {
                        doelgroep = new Doelgroep(doelgroepen1);
                        //em.persist(doelgroep);
                        doelgroepCollection.add(doelgroep);//Persisteren -> Aparte methode?
                    }
                }
                if (!doelgroepCollection.isEmpty())
                {
                    product.setDoelgroepen(doelgroepCollection);
                }
                for (String leergebiedenS : leergebieden)
                {
                    try
                    {
                        leergebied = em.createNamedQuery("Leergebied.zoekLeergebiedOpNaam", Leergebied.class).setParameter("naam", leergebiedenS).getSingleResult();
                    } catch (NoResultException ex)
                    {
                        //Do Nothing
                    }
                    if (leergebied != null)
                    {
                        leergebiedCollection.add(leergebied);
                    } else
                    {
                        leergebied = new Leergebied(leergebiedenS);
                        //em.persist(leergebied);
                        leergebiedCollection.add(leergebied);
                    }
                }
                if (!leergebiedCollection.isEmpty())
                {
                    product.setLeergebieden(leergebiedCollection);
                }

                String firmaNaam = nextLine[9];
                Firma firma = null;
                try
                {
                    firma = em.createNamedQuery("Firma.zoekFirmaOpnaam", Firma.class).setParameter("naam", firmaNaam).getSingleResult();
                } catch (NoResultException ex)
                {
                    //Do nothing
                }
                if (firma == null)
                {
                    firma = new Firma(firmaNaam, nextLine[10], nextLine[11], nextLine[12]);
                    //em.persist(firma);
                }
                product.setFirma(firma);
                System.out.println(product);
                //            em.persist(product);
//            em.getTransaction().commit();
            }
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(ProductToevoegenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(ProductToevoegenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
