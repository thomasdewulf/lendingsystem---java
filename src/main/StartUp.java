/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domein.SysteemConfiguratie;
import domein.Tijdstip;
import gui.LoginPaneelController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import util.EntityManagerUtil;

/**
 *
 * @author dewul
 */
public class StartUp extends Application
{

    public static void main(String[] args)
    {
        {
            launch(args);

            //Sysconfig invoeren
//            EntityManager entityManager = EntityManagerUtil.getEm();
//            entityManager.getTransaction().begin();
//            SysteemConfiguratie s = new SysteemConfiguratie();
//            s.setReservatiePeriode(5);
//            s.setMaximaleVerlenging(14);
//            s.setMomentOphalen(Tijdstip.MAANDAGOCHTEND);
//            s.setMomentBinnenbrengen(Tijdstip.VRIJDAGNAMIDDAG);
//
//            s.setPlaats("0000");
//            entityManager.persist(s);
//            entityManager.getTransaction().commit();
//            entityManager.close();
            //ALTER TABLE MAAR 1 KEER UITVOEREN
//            EntityManager entityManager = EntityManagerUtil.getEm();
//            entityManager.getTransaction().begin();
//            int rijen = entityManager.createNativeQuery("ALTER TABLE AspNetUsers DROP COLUMN BeheerderType").executeUpdate();
//            entityManager.getTransaction().commit();
//            entityManager.close();
//            System.out.printf("Alter succevol, %d rijen aangepast", rijen);
//            System.exit(0);
            //Create table
//            EntityManager entityManager = EntityManagerUtil.getEm();
//            entityManager.getTransaction().begin();
//            int rijen = entityManager.createNativeQuery("CREATE TABLE Beheerder (Id int NOT NULL IDENTITY(1,1) PRIMARY KEY, Naam varchar(50), Voornaam varchar(50), Email varchar(128) NOT NULL, BeheerderType varchar(50) NOT NULL);").executeUpdate();
//            entityManager.getTransaction().commit();
//            entityManager.close();
//            System.out.printf("Tabel succevol, %d rijen aangepast", rijen);
//            System.exit(0);
            //Gebruiker toevoegen als hoofdbeheerder
//            EntityManager entityManager = EntityManagerUtil.getEm();
//            entityManager.getTransaction().begin();
//            Beheerder beheerder1 = new Beheerder("Van Meersche", "Maarten", "maarten.vanmeersche.v4698@student.hogent.be", BeheerderType.HOOFDBEHEERDER);
//            Beheerder beheerder2 = new Beheerder("Vermaeren", "Remko", "remko.vermaeren.v7260@student.hogent.be", BeheerderType.HOOFDBEHEERDER);
//            Beheerder beheerder3 = new Beheerder("De Puysseleer", "Pol", "", BeheerderType.HOOFDBEHEERDER);
//            Beheerder beheerder4 = new Beheerder("de Wulf", "Thomas", "thomas.dewulf.v4732@student.hogent.be", BeheerderType.HOOFDBEHEERDER);
//            entityManager.persist(beheerder1);
//            entityManager.persist(beheerder2);
//            entityManager.persist(beheerder3);
//            entityManager.persist(beheerder4);
//            entityManager.getTransaction().commit();
//            entityManager.close();
//            System.exit(0);
            //Create sysconfig
//            EntityManager em = EntityManagerUtil.getEm();
//            em.getTransaction().begin();
//            em.createNativeQuery("CREATE TABLE SysteemConfiguratie "
//                    + "(Id int NOT NULL IDENTITY(1,1) PRIMARY KEY, "
//                    + "ReservatiePeriode int NOT NULL, MaximaleVerlenging int NOT NULL, "
//                    + "MomentOphalen varchar(50) NOT NULL,  "
//                    + "MomentBinnenbrengen varchar(50) NOT NULL,"
//                    + "Plaats varchar(50) NOT NULL);").executeUpdate();
//            em.getTransaction().commit();
//            em.close();
//            System.out.println("create sysconfig");
//            System.exit(0);
//            Alter leergebied, doelgroep en email
//            EntityManager em = EntityManagerUtil.getEm();
//            em.getTransaction().begin();
//            em.createNativeQuery("ALTER TABLE Doelgroep "
//                    + "ADD SysteemConfiguratieId int FOREIGN KEY REFERENCES SysteemConfiguratie(Id)").executeUpdate();
//            em.createNativeQuery("ALTER TABLE Leergebied "
//                    + "ADD SysteemConfiguratieId int FOREIGN KEY REFERENCES SysteemConfiguratie(Id)").executeUpdate();
//            em.createNativeQuery("ALTER TABLE Email "
//                    + "ADD SysteemConfiguratieId int FOREIGN KEY REFERENCES SysteemConfiguratie(Id)").executeUpdate();
//            em.getTransaction().commit();
//            em.close();
//            System.out.println("alter doelgroep, leergebied, email");
//            System.exit(0);

 //ALTER TABLE MAAR 1 KEER UITVOEREN
//            EntityManager entityManager = EntityManagerUtil.getEm();
//            entityManager.getTransaction().begin();
//            entityManager.createNativeQuery("ALTER TABLE Doelgroep DROP CONSTRAINT FK__Doelgroep__Syste__6E01572D").executeUpdate();
//            int rijen = entityManager.createNativeQuery("ALTER TABLE Doelgroep DROP COLUMN SysteemConfiguratieId").executeUpdate();
//            entityManager.createNativeQuery("ALTER TABLE Leergebied DROP CONSTRAINT FK__Leergebie__Syste__6EF57B66").executeUpdate();
//            rijen += entityManager.createNativeQuery("ALTER TABLE Leergebied DROP COLUMN SysteemConfiguratieId").executeUpdate();
//            entityManager.createNativeQuery("ALTER TABLE Email DROP CONSTRAINT FK__Email__SysteemCo__6FE99F9F").executeUpdate();
//            rijen += entityManager.createNativeQuery("ALTER TABLE Email DROP COLUMN SysteemConfiguratieId").executeUpdate();
//            entityManager.getTransaction().commit();
//            entityManager.close();
//            System.out.printf("Alter succevol, %d rijen aangepast", rijen);
//            System.exit(0);

//            EntityManager em = EntityManagerUtil.getEm();
//            em.getTransaction().begin();
//            em.createNativeQuery("ALTER TABLE Beheerder ADD wachtwoord varchar(MAX)").executeUpdate();
//            em.getTransaction().commit();
//            em.close();
//            System.exit(0);
        }
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        //werkend
        Scene scene = new Scene(new LoginPaneelController(stage));
        stage.setScene(scene);

        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());

        //end werkend
//        Database testing
//        EntityManager entityManager = EntityManagerUtil.getEm();
//        entityManager.getTransaction().begin();
//        List<AspNetUsers> users = entityManager.createNamedQuery("AspNetUsers.geefAlleUsers", AspNetUsers.class).getResultList();
//        AspNetUsers user = users.get(0);
//        TypedQuery<Product> queryP = entityManager.createNamedQuery("Product.geefAlleProducten", Product.class);
//        productList = queryP.getResultList();
//        Product p = productList.get(0);
//        Date d1 = new Date(2016, 5, 2);
//        Date d2 = new Date(2016, 5, 5);
//        Date d3 = new Date(2016, 27, 4);
//        Collection<ReservatieLijn> lijnen = new ArrayList<>();
//        ReservatieLijn lijn = new ReservatieLijn(10, 2, p);
//        lijnen.add(lijn);
//        Reservatie r = new Reservatie(10, d3, d3, d3, user, lijnen);
//        lijn.setReservatie(r);
//        p.setReservatieLijnen(lijnen);
//        List<Reservatie> reservaties = new ArrayList<>();
//        reservaties.add(r);
//        user.setReservaties(reservaties);
//        entityManager.persist(lijn);
//        entityManager.persist(r);
//        entityManager.persist(p);
//        entityManager.persist(user);
//        entityManager.getTransaction().commit();
//        entityManager.close();
//        EntityManagerUtil.getEmf().close();
//         List<Reservatie> reserv = entityManager.createNamedQuery("Reservatie.geefAlleReservaties", Reservatie.class).getResultList();
//         Reservatie r0 = reserv.get(0);
//         System.out.printf("%s",r0);
//         System.out.printf("%s",user);
    }
}
