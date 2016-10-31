/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Maarten
 */
public class SysteemConfiguratieTest
{
    private SysteemConfiguratie sysconfig;
    
    @Before
    public void before()
    {
        sysconfig = new SysteemConfiguratie();
        sysconfig.setMaximaleVerlenging(2);
        sysconfig.setMomentBinnenbrengen(Tijdstip.WOENSDAGOCHTEND);
        sysconfig.setMomentOphalen(Tijdstip.MAANDAGOCHTEND);
        sysconfig.setPlaats("0010");
        sysconfig.setReservatiePeriode(1);
    }
    
    @Test
    public void getReservatiePeriodeGeeftDeReservatiePeriodeTerug()
    {
        Assert.assertEquals(1, sysconfig.getReservatiePeriode());
    }
    
    @Test
    public void getMaximaleVerlengingGeeftDeMaximaleVerlengingTerug()
    {
        Assert.assertEquals(2, sysconfig.getMaximaleVerlenging());
    }
    
    @Test
    public void getMomentOphalenGeeftHetMomentVanOphalenTerug()
    {
        Assert.assertEquals(Tijdstip.MAANDAGOCHTEND, sysconfig.getMomentOphalen());
    }
    
    @Test
    public void getMomentBinennBrengenGeeftHetMomentVanBinnenBrengenTerug()
    {
        Assert.assertEquals(Tijdstip.WOENSDAGOCHTEND, sysconfig.getMomentBinnenbrengen());
    }
    
    @Test
    public void getPlaatsGeeftDePlaatsTerug()
    {
        Assert.assertEquals("0010", sysconfig.getPlaats());
    }
            
    @Test
    public void setReservatiePeriodeSetDeReservatiePeriode()
    {
        sysconfig.setReservatiePeriode(3);
        Assert.assertEquals(3, sysconfig.getReservatiePeriode());
    }
    
    @Test
    public void setMaximaleVerlengingSetDeMaximaleVerlenging()
    {
        sysconfig.setMaximaleVerlenging(4);
        Assert.assertEquals(4, sysconfig.getMaximaleVerlenging());
    }
    
    @Test
    public void setMomentOphalenSetHetMomentVanOphalen()
    {
        sysconfig.setMomentOphalen(Tijdstip.MAANDAGMIDDAG);
        Assert.assertEquals(Tijdstip.MAANDAGMIDDAG, sysconfig.getMomentOphalen());
    }
    
    @Test
    public void setMomentBinennBrengenSetHetMomentVanBinnenBrengen()
    {
        sysconfig.setMomentBinnenbrengen(Tijdstip.DONDERDAGNAMIDDAG);
        Assert.assertEquals(Tijdstip.DONDERDAGNAMIDDAG, sysconfig.getMomentBinnenbrengen());
    }
    
    @Test
    public void setPlaatsSetDePlaats()
    {
        sysconfig.setPlaats("1234");
        Assert.assertEquals("1234", sysconfig.getPlaats());
    }
}
