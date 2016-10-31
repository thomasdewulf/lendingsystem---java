package domein;

import java.io.Serializable;
import java.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@NamedQueries(
        {
            @NamedQuery(name = "Product.alleProducten", query = "select p from Product p"),
            @NamedQuery(name = "Product.geefProductMetArtikelnaam", query = "select p from Product p where p.artikelnaam = :artikelNaam")
        })
public class Product implements Serializable
{

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ProductDoelgroeps", joinColumns = @JoinColumn(name = "DoelgroepId"), inverseJoinColumns = @JoinColumn(name = "ProductId"))
    private Collection<Doelgroep> doelgroepen;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ProductLeergebieds", joinColumns = @JoinColumn(name = "LeergebiedId"), inverseJoinColumns = @JoinColumn(name = "ProductId"))
    private Collection<Leergebied> leergebieden;
    @OneToMany(mappedBy = "product")
    private Collection<ReservatieLijn> reservatieLijnen;
    //private int firmaId;
    @OneToOne
    @JoinColumn(name = "FIRMAID")
    private Firma firma;
    private String foto;
    private String artikelnaam;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int productId;
    private String omschrijving;
    private String artikelnummer;
    private double prijs;
    private int aantalInCatalogus;
    private int aantalProductStukken;
    private boolean uitleenbaar;
    @Transient
    private String plaats;

    public Product()
    {
    }

    public Product(Collection<Doelgroep> doelgroepen, Collection<Leergebied> leergebieden, Firma firma, String foto, String artikelNaam, String omschrijving, String artikelNummer, double prijs, int aantalInCatalogus, int aantalProductStukken, boolean uitleenbaar, String plaats)
    {
        this.doelgroepen = doelgroepen;
        this.leergebieden = leergebieden;
        this.firma = firma;
        this.foto = foto;
        this.artikelnaam = artikelNaam;
        this.omschrijving = omschrijving;
        this.artikelnummer = artikelNummer;
        this.prijs = prijs;
        this.aantalInCatalogus = aantalInCatalogus;
        this.aantalProductStukken = aantalProductStukken;
        this.uitleenbaar = uitleenbaar;
        this.plaats = plaats;
    }

    public String getArtikelNaam()
    {
        return artikelnaam;
    }

    public void setArtikelNaam(String artikelNaam)
    {
        this.artikelnaam = artikelNaam;
    }

    public String getArtikelNummer()
    {
        return artikelnummer;
    }

    public void setArtikelNummer(String artikelNummer)
    {
        this.artikelnummer = artikelNummer;
    }

    public Collection<Doelgroep> getDoelgroepen()
    {
        return doelgroepen;
    }

    public Collection<Leergebied> getLeergebieden()
    {
        return leergebieden;
    }

    public Firma getFirma()
    {
        return firma;
    }

    public String getFoto()
    {
        return foto;
    }

    public String getOmschrijving()
    {
        return omschrijving;
    }

    public double getPrijs()
    {
        return prijs;
    }

    public int getAantalInCatalogus()
    {
        return aantalInCatalogus;
    }

    public int getAantalProductStukken()
    {
        return aantalProductStukken;
    }

    public boolean isUitleenbaar()
    {
        return uitleenbaar;
    }

    public String getPlaats()
    {
        return plaats;
    }

    public void setDoelgroepen(Collection<Doelgroep> doelgroepen)
    {
        this.doelgroepen = doelgroepen;
    }

    public void setLeergebieden(Collection<Leergebied> leergebieden)
    {
        this.leergebieden = leergebieden;
    }

    public void setFoto(String foto)
    {
        this.foto = foto;
    }

    public void setOmschrijving(String omschrijving)
    {
        this.omschrijving = omschrijving;
    }

    public void setPrijs(double prijs)
    {
        this.prijs = prijs;
    }

    public void setAantalInCatalogus(int aantalInCatalogus)
    {
        this.aantalInCatalogus = aantalInCatalogus;
    }

    public void setAantalProductStukken(int aantalProductStukken)
    {
        this.aantalProductStukken = aantalProductStukken;
    }

    public void setUitleenbaar(boolean uitleenbaar)
    {
        this.uitleenbaar = uitleenbaar;
    }

    public void setPlaats(String plaats)
    {
        this.plaats = plaats;
    }

    public void setFirma(Firma firma)
    {
        this.firma = firma;
    }

    public StringProperty naamProperty()
    {
        return new SimpleStringProperty(artikelnaam);
    }

    public StringProperty aantalProperty()
    {
        return new SimpleStringProperty(String.format("%d", aantalInCatalogus));
    }

    public StringProperty artikelnummerProperty()
    {
        return new SimpleStringProperty(artikelnummer);
    }

    public boolean bevatDoelgroep(String doelgroep)
    {
        return this.doelgroepen.stream().map(Doelgroep::getNaam).map(String::toLowerCase).anyMatch(naam -> naam.contains(doelgroep.toLowerCase()));
    }

    public boolean bevatLeergebied(String leergebied)
    {
        return this.leergebieden.stream().map(Leergebied::getNaam).map(String::toLowerCase).anyMatch(naam -> naam.contains(leergebied));
    }

    public void setReservatieLijnen(Collection<ReservatieLijn> reservatieLijnen) {
        this.reservatieLijnen = reservatieLijnen;
    }

    @Override
    public String toString()
    {
        return "Product{" + "doelgroepen=" + doelgroepen + ", leergebieden=" + leergebieden + ", firma=" + firma + ", foto=" + foto + ", artikelNaam=" + artikelnaam + ", productId=" + productId + ", omschrijving=" + omschrijving + ", artikelNummer=" + artikelnummer + ", prijs=" + prijs + ", aantalInCatalogus=" + aantalInCatalogus + ", aantalProductStukken=" + aantalProductStukken + ", uitleenbaar=" + uitleenbaar + ", plaats=" + plaats + '}' + "\n";
    }

}
