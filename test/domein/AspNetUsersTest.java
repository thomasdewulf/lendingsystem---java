/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Maarten
 */
public class AspNetUsersTest
{
    private AspNetUsers user;
    private AspNetUsers user2;
    
    @Before
    public void before()
    {
        user = new AspNetUsers("naam", "voornaam", "email");
        user2 = new AspNetUsers("gebruikersNummer", "naam", "voornaam", "foto", "email", "userName");
    }
    
    @Test
    public void aspNetUsersWordtCorrectAangemaakt()
    {
        Assert.assertEquals("email",user.getEmail());
        Assert.assertEquals("email", user2.getEmail());
        Assert.assertEquals("gebruikersNummer", user2.getGebruikersNummer());
        Assert.assertNotNull(user);
        Assert.assertNotNull(user2);
    }
    
}
