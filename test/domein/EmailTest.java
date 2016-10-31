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
public class EmailTest
{
    private Email email;
    
    @Before
    public void before()
    {
        email = new Email(0, "header", "body", "footer", "subject");
    }
    
    @Test
    public void emailWordtCorrectAangemaakt()
    {
        Assert.assertNotNull(email);
        Assert.assertEquals("header", email.getHeader());
    }
    
}
