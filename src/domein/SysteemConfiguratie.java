/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Remko
 */
@Entity
@NamedQuery(name = "SysteemConfiguratie.geefSysteemConfiguratie",query = "select s from SysteemConfiguratie s")
public class SysteemConfiguratie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int reservatiePeriode;
    private int maximaleVerlenging;
    private Tijdstip momentOphalen;
    private Tijdstip momentBinnenbrengen;
    private String plaats;
    
//    @OneToMany(mappedBy = "systeemConfiguratieId")
//    //@JoinTable(name = "Email", joinColumns = @JoinColumn(name = "EmailId"), inverseJoinColumns = @JoinColumn(name = "SysteemConfiguratieId"))
//    private List<Email> emails;
//  
//    @OneToMany(mappedBy = "systeemConfiguratieId")
//    //@JoinTable(name = "SYSTEEMCONFIGURATIE_DOELGROEP", joinColumns = @JoinColumn(name = "DoelgroepId"), inverseJoinColumns = @JoinColumn(name = "SysteemConfiguratieId"))
//    private List<Doelgroep> doelgroepen;
//    @OneToMany(mappedBy = "systeemConfiguratieId")
//    //@JoinTable(name = "Leergebied", joinColumns = @JoinColumn(name = "LeergebiedId"), inverseJoinColumns = @JoinColumn(name = "SysteemConfiguratieId"))
//    private List<Leergebied> leergebieden;
    public SysteemConfiguratie() {
    }

    public void setReservatiePeriode(int reservatiePeriode) {
        this.reservatiePeriode = reservatiePeriode;
    }

    public void setMaximaleVerlenging(int maximaleVerlenging) {
        this.maximaleVerlenging = maximaleVerlenging;
    }

    public void setMomentOphalen(Tijdstip momentOphalen) {
        this.momentOphalen = momentOphalen;
    }

    public void setMomentBinnenbrengen(Tijdstip momentBinnenbrengen) {
        this.momentBinnenbrengen = momentBinnenbrengen;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }
    
    public int getReservatiePeriode() {
        return reservatiePeriode;
    }

    public int getMaximaleVerlenging() {
        return maximaleVerlenging;
    }

    public Tijdstip getMomentOphalen() {
        return momentOphalen;
    }

    public Tijdstip getMomentBinnenbrengen() {
        return momentBinnenbrengen;
    }

    public String getPlaats() {
        return plaats;
    }

//    public List<Email> getEmails() {
//        return emails;
//    }
//
//    public void setEmails(List<Email> emails) {
//        this.emails = emails;
//    }
//
//    public List<Doelgroep> getDoelgroepen() {
//        return doelgroepen;
//    }
//
//    public void setDoelgroepen(List<Doelgroep> doelgroepen) {
//        this.doelgroepen = doelgroepen;
//    }
//
//    public List<Leergebied> getLeergebieden() {
//        return leergebieden;
//    }
//
//    public void setLeergebieden(List<Leergebied> leergebieden) {
//        this.leergebieden = leergebieden;
//    }
    
}
