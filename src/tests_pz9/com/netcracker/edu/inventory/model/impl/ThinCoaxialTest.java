package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 13.11.16.
 */
public class ThinCoaxialTest {

    ThinCoaxial<Router> defaultThinCoaxial;
    ThinCoaxial<Router> thinCoaxial;
    String defaultStatus = Connection.PLANED;
    String status = Connection.USED;
    int defaultCurSize = 0;
    int curSize = 0;
    int defaultMaxSize = 0;
    int maxSize = 5;

    @Before
    public void setUp() throws Exception {
        defaultThinCoaxial = new ThinCoaxial();
        thinCoaxial = CreateUtilities.createThinCoaxial();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getConnectorType() throws Exception {
        assertEquals(ConnectorType.TConnector, defaultThinCoaxial.getConnectorType());
        assertEquals(ConnectorType.TConnector, thinCoaxial.getConnectorType());
    }

    @Test
    public void addDevice() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitch();
        ThinCoaxial<Router> thinCoaxial = new ThinCoaxial<Router>(6);
        boolean result1 = thinCoaxial.addDevice(aSwitch);

        Set<Router> result2 = thinCoaxial.getAllDevices();

        assertTrue(result1);
        assertTrue(result2.contains(aSwitch));
    }

    @Test
    public void removeDevice() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitch();
        ThinCoaxial<Router> thinCoaxial = new ThinCoaxial<Router>(6);
        thinCoaxial.addDevice(aSwitch);
        boolean result1 = thinCoaxial.containDevice(aSwitch);
        thinCoaxial.removeDevice(aSwitch);
        boolean result2 = thinCoaxial.containDevice(aSwitch);
        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    public void containDevice() throws Exception {
        Switch aSwitch = CreateUtilities.createSwitch();
        ThinCoaxial<Router> thinCoaxial = new ThinCoaxial<Router>(6);
        boolean result1 = thinCoaxial.containDevice(aSwitch);
        thinCoaxial.addDevice(aSwitch);
        boolean result2 = thinCoaxial.containDevice(aSwitch);
        assertFalse(result1);
        assertTrue(result2);
    }

    @Test
    public void defaultAllDevices() throws Exception {
        Set<Router> result = defaultThinCoaxial.getAllDevices();

        assertEquals(new HashSet<Router>(), result);
    }

    @Test
    public void getAllDevices() throws Exception {
        Set<Router> expSet = new HashSet<Router>();
        Router router = CreateUtilities.createRouter();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();
        Switch aSwitch = CreateUtilities.createSwitch();
        expSet.add(router);
        expSet.add(wifiRouter);
        expSet.add(aSwitch);
        ThinCoaxial<Router> thinCoaxial = new ThinCoaxial<Router>(6);
        thinCoaxial.addDevice(aSwitch);
        thinCoaxial.addDevice(router);
        thinCoaxial.addDevice(aSwitch);
        thinCoaxial.addDevice(wifiRouter);
        thinCoaxial.addDevice(router);

        Set<Router> result = thinCoaxial.getAllDevices();

        assertEquals(expSet, result);
    }

    @Test
    public void getCurSize() throws Exception {
        assertEquals(defaultCurSize, defaultThinCoaxial.getCurSize());
        assertEquals(curSize, thinCoaxial.getCurSize());
    }

    @Test
    public void getMaxSize() throws Exception {
        assertEquals(defaultMaxSize, defaultThinCoaxial.getMaxSize());
        assertEquals(maxSize, thinCoaxial.getMaxSize());
    }

    @Test
    public void defaultStatus() throws Exception {
        String result = defaultThinCoaxial.getStatus();

        assertEquals(defaultStatus, result);
    }

    @Test
    public void setGetStatus() throws Exception {
        defaultThinCoaxial.setStatus(status);
        String result = defaultThinCoaxial.getStatus();

        assertEquals(status, result);
    }

    @Deprecated
    @Test
    public void testGetAndFillAllFieldsArray() throws Exception {
        ThinCoaxial result1 = new ThinCoaxial();
        result1.fillAllFields(thinCoaxial.getAllFieldsToArray());

        AssertUtilities.assertThinCoaxial(thinCoaxial, result1);
    }

    @Deprecated
    @Test
    public void testGetAndFillAllFieldsArray_EmptyConnection() throws Exception {
        ThinCoaxial result1 = new ThinCoaxial();
        result1.fillAllFields(defaultThinCoaxial.getAllFieldsToArray());

        AssertUtilities.assertThinCoaxial(defaultThinCoaxial, result1);
    }

    @Test
    public void testGetAndFillAllFields() throws Exception {
        ThinCoaxial result1 = new ThinCoaxial();
        result1.fillAllFields(thinCoaxial.getAllFields());

        AssertUtilities.assertThinCoaxial(thinCoaxial, result1);
    }

    @Test
    public void testGetAndFillAllFields_EmptyConnection() throws Exception {
        ThinCoaxial result1 = new ThinCoaxial();
        result1.fillAllFields(defaultThinCoaxial.getAllFields());

        AssertUtilities.assertThinCoaxial(defaultThinCoaxial, result1);
    }

}