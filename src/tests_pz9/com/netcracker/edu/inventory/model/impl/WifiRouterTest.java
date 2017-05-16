package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 05.10.16.
 */
public class WifiRouterTest {

    WifiRouter defaultWifiRouter;
    WifiRouter wifiRouter;
    String technologyVersion = "802.11g";

    String securityProtocol = "";

    @Before
    public void before() throws Exception {
        defaultWifiRouter = new WifiRouter();
        wifiRouter = CreateUtilities.createWifiRouter();
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void getTechnologyVersion() throws Exception {
        assertNull(defaultWifiRouter.getTechnologyVersion());
        assertEquals(technologyVersion, wifiRouter.getTechnologyVersion());
    }

    @Test
    public void setGetSecurityProtocol() throws Exception {
        defaultWifiRouter.setSecurityProtocol(securityProtocol);
        String result = defaultWifiRouter.getSecurityProtocol();

        assertEquals(securityProtocol, result);
    }

    @Test
    public void setGetWirelessConnection() throws Exception {
        Wireless wireless = CreateUtilities.createWireless();
        wireless.setVersion(1);

        defaultWifiRouter.setWireConnection(wireless);
        Wireless result = (Wireless) defaultWifiRouter.getWireConnection();

        AssertUtilities.assertWireless(wireless, result);
    }

    @Test
    public void getWirePortType() throws Exception {
        assertEquals(ConnectorType.need_init, defaultWifiRouter.getWirePortType());
        assertEquals(ConnectorType.RJ45, wifiRouter.getWirePortType());
    }

    @Test
    public void setGetWireConnection() throws Exception {
        TwistedPair twistedPair = CreateUtilities.createTwistedPair();
        twistedPair.setLength(10);

        defaultWifiRouter.setWireConnection(twistedPair);
        TwistedPair result = (TwistedPair) defaultWifiRouter.getWireConnection();

        AssertUtilities.assertTwistedPair(twistedPair, result);
    }

    @Test
    public void testGetAndFillAllFieldsToArray() throws Exception {
        Device result1 = new WifiRouter();
        result1.fillAllFields(wifiRouter.getAllFieldsToArray());

        AssertUtilities.assertSomeDevice(wifiRouter, result1);
    }

    @Test
    public void testGetAndFillAllFieldsToArray_EmptyDevice() throws Exception {
        Device result1 = new WifiRouter();
        result1.fillAllFields(defaultWifiRouter.getAllFieldsToArray());

        AssertUtilities.assertSomeDevice(defaultWifiRouter, result1);
    }

    @Test
    public void testGetAndFillAllFields() throws Exception {
        WifiRouter result1 = new WifiRouter();
        result1.fillAllFields(wifiRouter.getAllFields());

        AssertUtilities.assertWifiRouter(wifiRouter, result1);
    }

    @Test
    public void testGetAndFillAllFields_EmptyDevice() throws Exception {
        WifiRouter result1 = new WifiRouter();
        result1.fillAllFields(defaultWifiRouter.getAllFields());

        AssertUtilities.assertWifiRouter(defaultWifiRouter, result1);
    }

}
