package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 13.11.16.
 */
public class TwistedPairTest {

    TwistedPair<Switch, WifiRouter> defaultTwistedPair;
    TwistedPair<Switch, WifiRouter> twistedPair;
    String defaultStatus = Connection.PLANED;
    String status = Connection.READY;
    int defaultLength = 0;
    int length = 50;
    TwistedPair.Type defaultType = TwistedPair.Type.need_init;
    TwistedPair.Type type = TwistedPair.Type.UTP;

    @Before
    public void setUp() throws Exception {
        defaultTwistedPair = new TwistedPair();
        twistedPair = CreateUtilities.createTwistedPair();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getType() throws Exception {
        assertEquals(defaultType, defaultTwistedPair.getType());
        assertEquals(type, twistedPair.getType());
    }

    @Test
    public void defaultLength() throws Exception {
        int result = defaultTwistedPair.getLength();

        assertEquals(defaultLength, result);
    }

    @Test
    public void setGetLength() throws Exception {
        defaultTwistedPair.setLength(length);
        int result = defaultTwistedPair.getLength();

        assertEquals(length, result);
    }

    @Test
    public void getAPointConnectorType() throws Exception {
        assertEquals(ConnectorType.RJ45, defaultTwistedPair.getAPointConnectorType());
        assertEquals(ConnectorType.RJ45, twistedPair.getAPointConnectorType());
    }

    @Test
    public void getBPointConnectorType() throws Exception {
        assertEquals(ConnectorType.RJ45, defaultTwistedPair.getBPointConnectorType());
        assertEquals(ConnectorType.RJ45, twistedPair.getBPointConnectorType());
    }

    @Test
    public void setGetAPoint() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitch();

        defaultTwistedPair.setAPoint(aSwitch);
        Switch result = defaultTwistedPair.getAPoint();

        AssertUtilities.assertSwitch(aSwitch, result);
    }

    @Test
    public void setGetBPoint() throws Exception {
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();

        defaultTwistedPair.setBPoint(wifiRouter);
        WifiRouter result = defaultTwistedPair.getBPoint();

        AssertUtilities.assertWifiRouter(wifiRouter, result);
    }

    @Test
    public void defaultStatus() throws Exception {
        String result = defaultTwistedPair.getStatus();

        assertEquals(defaultStatus, result);
    }

    @Test
    public void setGetStatus() throws Exception {
        defaultTwistedPair.setStatus(status);
        String result = defaultTwistedPair.getStatus();

        assertEquals(status, result);
    }

    @Deprecated
    @Test
    public void testGetAndFillAllFieldsArray() throws Exception {
        TwistedPair result1 = new TwistedPair();
        result1.fillAllFields(twistedPair.getAllFieldsToArray());

        AssertUtilities.assertTwistedPair(twistedPair, result1);
    }

    @Deprecated
    @Test
    public void testGetAndFillAllFieldsArray_EmptyConnection() throws Exception {
        TwistedPair result1 = new TwistedPair();
        result1.fillAllFields(defaultTwistedPair.getAllFieldsToArray());

        AssertUtilities.assertTwistedPair(defaultTwistedPair, result1);
    }

    @Test
    public void testGetAndFeelAllFields() throws Exception {
        TwistedPair result1 = new TwistedPair();
        result1.fillAllFields(twistedPair.getAllFields());

        AssertUtilities.assertTwistedPair(twistedPair, result1);
    }

    @Test
    public void testGetAndFeelAllFields_EmptyConnection() throws Exception {
        TwistedPair result1 = new TwistedPair();
        result1.fillAllFields(defaultTwistedPair.getAllFields());

        AssertUtilities.assertTwistedPair(defaultTwistedPair, result1);
    }

}