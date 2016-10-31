/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import util.EntityManagerUtil;

/**
 *
 * @author Remko
 */
public class Catalogus
{

    private List<Product> productenLijst = new ArrayList<>();
    private List<Doelgroep> doelgroepenLijst = new ArrayList<>();
    private List<Leergebied> leergebiedenLijst = new ArrayList<>();
    private List<Firma> firmaLijst = new ArrayList<>();
    private List<Reservatie> reservatieLijst = new ArrayList<>();
    private List<AspNetUsers> userLijst = new ArrayList<>();
    private List<Beheerder> beheerderLijst = new ArrayList<>();
    private EntityManager em = EntityManagerUtil.getEm();
    private SysteemConfiguratie systeemConfiguratie;
    private List<Email> emails = new ArrayList<>();

    public Catalogus()
    {
        doelgroepenLijst = getDoelgroepenLijst();
        firmaLijst = getFirmaLijst();
        productenLijst = getProductenLijst() ;
        leergebiedenLijst = getLeergebiedenLijst() ;
        reservatieLijst = getReservatieLijst();
        userLijst = getUserLijst();
        systeemConfiguratie = getSysteemConfiguratie();
        emails = getEmails();
        beheerderLijst = getBeheerders();
    }

    public List<Reservatie> getReservatieLijst() {
        return em.createNamedQuery("Reservatie.geefAlleReservaties",Reservatie.class).getResultList();
    }

    public List<Product> getProductenLijst()
    {
        return em.createNamedQuery("Product.alleProducten", Product.class).getResultList();
    }

    public List<Doelgroep> getDoelgroepenLijst()
    {
        return em.createNamedQuery("Doelgroep.geefAlleDoelgroepen", Doelgroep.class).getResultList();
    }

    public List<AspNetUsers> getUserLijst() {
        return em.createNamedQuery("AspNetUsers.geefAlleUsers",AspNetUsers.class).getResultList();
    }

    public List<Leergebied> getLeergebiedenLijst()
    {
        return em.createNamedQuery("Leergebied.geefAlleLeergebieden", Leergebied.class).getResultList();
    }

    public List<Firma> getFirmaLijst()
    {
        return em.createNamedQuery("Firma.geefAlleFirmas", Firma.class).getResultList();
    }
    
    public List<Beheerder> getBeheerders()
    {
        return em.createNamedQuery("Beheerder.geefBeheerders", Beheerder.class)
                .setParameter("beheerder", BeheerderType.BEHEERDER)
                .setParameter("hoofdbeheerder", BeheerderType.HOOFDBEHEERDER)
                .getResultList();
    }
    
    public Beheerder geefBeheerder(String email)
    {
        try{
        return em.createNamedQuery("Beheerder.geefBeheerderMetEmail", Beheerder.class).setParameter("email", email).getSingleResult();
        }catch(Exception ex){
            return null;
        }
    }
    
    public boolean bevatBeheerder(String email)
    {
        return geefBeheerder(email) != null;
    }

    public Product getProductByName(String artikelNaam)
    {
        try{
        return em.createNamedQuery("Product.geefProductMetArtikelnaam", Product.class).setParameter("artikelNaam", artikelNaam).getSingleResult();
        }catch(NoResultException ex){return null;}
    }

    public SysteemConfiguratie getSysteemConfiguratie() {
        return em.createNamedQuery("SysteemConfiguratie.geefSysteemConfiguratie",SysteemConfiguratie.class).getSingleResult();
    }

    public List<Email> getEmails() {
        return em.createNamedQuery("Email.geefAlleEmails",Email.class).getResultList();
    }
    
    
    public boolean bevatProduct(String productNaam){
      return getProductByName(productNaam) != null;
    }

    public boolean bevatFirma(Firma naam) {
       return this.firmaLijst.contains(naam);
    }

    public boolean bevatLeergebied(Leergebied naam) {
        return this.leergebiedenLijst.contains(naam);
    }

    public boolean bevatDoelgroep(Doelgroep naam) {
        return this.doelgroepenLijst.contains(naam);
    }
}
