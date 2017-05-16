package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 13.11.16.
 */
public class WirelessTest {

    Wireless<WifiRouter, WifiRouter> defaultWireless;
    Wireless<WifiRouter, WifiRouter> wireless;
    String defaultStatus = Connection.PLANED;
    String status = Connection.USED;
    String defaultTechnology;
    String technology = "802.11g";
    String defaultSecurityProtocol;
    String securityProtocol = "WPA";
    int defaultVersion = 0;
    int version = 2;
    int defaultBCapacity = 0;
    int bCapacity = 3;

    @Before
    public void setUp() throws Exception {
        defaultWireless = new Wireless();
        wireless = CreateUtilities.createWireless();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getTechnology() throws Exception {
        assertNull(defaultWireless.getTechnology());
        assertEquals(technology, wireless.getTechnology());
    }

    @Test
    public void defaultProtocol() throws Exception {
        assertNull(defaultWireless.getProtocol());
    }

    @Test
    public void setGetProtocol() throws Exception {
        defaultWireless.setProtocol(securityProtocol);
        String result = defaultWireless.getProtocol();

        assertEquals(securityProtocol, result);
    }

    @Test
    public void defaultVersion() throws Exception {
        assertEquals(defaultVersion, defaultWireless.getVersion());
    }

    @Test
    public void setGetVersion() throws Exception {
        defaultWireless.setVersion(version);
        int result = defaultWireless.getVersion();

        assertEquals(version, result);
    }

    @Test
    public void getAPointConnectorType() throws Exception {
        assertEquals(ConnectorType.Wireless, defaultWireless.getAPointConnectorType());
        assertEquals(ConnectorType.Wireless, wireless.getAPointConnectorType());
    }

    @Test
    public void getBPointConnectorType() throws Exception {
        assertEquals(ConnectorType.Wireless, defaultWireless.getBPointConnectorType());
        assertEquals(ConnectorType.Wireless, wireless.getBPointConnectorType());
    }

    @Test
    public void setGetAPoint() throws Exception {
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();

        defaultWireless.setAPoint(wifiRouter);
        WifiRouter result = defaultWireless.getAPoint();

        AssertUtilities.assertWifiRouter(wifiRouter, result);
    }

    @Test
    public void defaultBPoints() throws Exception {
        List<WifiRouter> result = defaultWireless.getBPoints();

        assertEquals(new ArrayList<WifiRouter>(), result);
    }

    @Test
    public void setGetBPoints() throws Exception {
        List<WifiRouter> devices = wireless.getBPoints();

        defaultWireless.setBPoints(devices);
        List<WifiRouter> result = defaultWireless.getBPoints();

        assertEquals(devices, result);
    }

    @Test
    public void getBCapacity() throws Exception {
        assertEquals(defaultBCapacity, defaultWireless.getBCapacity());
        assertEquals(bCapacity, wireless.getBCapacity());
    }

    @Test
    public void setGetBPoint() throws Exception {
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();
        wifiRouter.setSecurityProtocol("WEP");

        wireless.setBPoint(wifiRouter, 2);
        WifiRouter result = wireless.getBPoint(2);

        AssertUtilities.assertWifiRouter(wifiRouter, result);
    }

    @Test
    public void defaultStatus() throws Exception {
        String result = defaultWireless.getStatus();

        assertEquals(defaultStatus, result);
    }

    @Test
    public void setGetStatus() throws Exception {
        defaultWireless.setStatus(status);
        String result = defaultWireless.getStatus();

        assertEquals(status, result);
    }

    @Deprecated
    @Test
    public void testGetAndFillAllFieldsArray() throws Exception {
        Wireless result1 = new Wireless();
        result1.fillAllFields(wireless.getAllFieldsToArray());

        AssertUtilities.assertWireless(wireless, result1);
    }

    @Deprecated
    @Test
    public void testGetAndFillAllFieldsArray_EmptyConnection() throws Exception {
        Wireless result1 = new Wireless();
        result1.fillAllFields(defaultWireless.getAllFieldsToArray());

        AssertUtilities.assertWireless(defaultWireless, result1);
    }

    @Test
    public void testGetAndFeelAllFields() throws Exception {
        Wireless result1 = new Wireless();
        result1.fillAllFields(wireless.getAllFields());

        AssertUtilities.assertWireless(wireless, result1);
    }

    @Test
    public void testGetAndFeelAllFields_EmptyConnection() throws Exception {
        Wireless result1 = new Wireless();
        result1.fillAllFields(defaultWireless.getAllFields());

        AssertUtilities.assertWireless(defaultWireless, result1);
    }

}