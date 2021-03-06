package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 05.10.16.
 */
public class SwitchTest {

    Switch defaultSwitch;
    Switch aSwitch;

    int numberOfPorts = 0;

    @Before
    public void before() throws Exception {
        defaultSwitch = new Switch();
        aSwitch = CreateUtilities.createSwitch();
    }

    @After
    public void after() throws Exception {
        aSwitch = null;
    }

    @Test
    public void getPortsType() throws Exception {
        assertEquals(ConnectorType.need_init, defaultSwitch.getPortsType());
        assertEquals(ConnectorType.RJ45, aSwitch.getPortsType());
    }

    @Test
    public void setGetNumberOfPorts() throws Exception {
        defaultSwitch.setNumberOfPorts(numberOfPorts);
        int result = defaultSwitch.getNumberOfPorts();

        assertEquals(numberOfPorts, result);
    }

    @Test
    public void setGetPortConnection() throws Exception {
        TwistedPair twistedPair = CreateUtilities.createTwistedPair();
        twistedPair.setLength(10);

        aSwitch.setPortConnection(twistedPair, 1);
        TwistedPair result = (TwistedPair) aSwitch.getPortConnection(1);

        AssertUtilities.assertTwistedPair(twistedPair, result);
    }

    @Test
    public void defaultAllPortConnections() throws Exception {
        List<Connection> result = defaultSwitch.getAllPortConnections();

        assertEquals(new ArrayList<Connection>(), result);
    }

    @Test
    public void getAllPortConnections() throws Exception {
        List<Connection> expList = new ArrayList<Connection>(6);
        TwistedPair twistedPair1 = CreateUtilities.createTwistedPair();
        TwistedPair twistedPair2 = CreateUtilities.createTwistedPair();
        TwistedPair twistedPair3 = CreateUtilities.createTwistedPair();
        expList.add(twistedPair1);
        expList.add(null);
        expList.add(null);
        expList.add(twistedPair2);
        expList.add(null);
        expList.add(twistedPair3);
        aSwitch.setNumberOfPorts(6);
        aSwitch.setPortConnection(twistedPair1, 0);
        aSwitch.setPortConnection(twistedPair2, 3);
        aSwitch.setPortConnection(twistedPair1, 0);
        aSwitch.setPortConnection(twistedPair3, 5);

        List<Connection> result = aSwitch.getAllPortConnections();

        assertEquals(expList, result);
    }

    @Test
    public void testGetAndFillAllFieldsToArray() throws Exception {
        Device result1 = new Switch();
        result1.fillAllFields(aSwitch.getAllFieldsToArray());

        AssertUtilities.assertSomeDevice(aSwitch, result1);
    }

    @Test
    public void testGetAndFillAllFieldsToArray_EmptyDevice() throws Exception {
        Device result1 = new Switch();
        result1.fillAllFields(defaultSwitch.getAllFieldsToArray());

        AssertUtilities.assertSomeDevice(defaultSwitch, result1);
    }

    @Test
    public void testGetAndFillAllFields() throws Exception {
        Switch result1 = new Switch();
        result1.fillAllFields(aSwitch.getAllFields());

        AssertUtilities.assertSwitch(aSwitch, result1);
    }

    @Test
    public void testGetAndFillAllFields_EmptyDevice() throws Exception {
        Switch result1 = new Switch();
        result1.fillAllFields(defaultSwitch.getAllFields());

        AssertUtilities.assertSwitch(defaultSwitch, result1);
    }

}
