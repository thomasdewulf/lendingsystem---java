/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Thomas
 */
public class EntityManagerUtil
{

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
    private static final EntityManager em = emf.createEntityManager();

    public static EntityManagerFactory getEmf()
    {
        return emf;
    }

    public static EntityManager getEm()
    {
        return em;
    }

}
