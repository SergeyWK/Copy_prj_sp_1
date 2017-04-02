package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 05.10.16.
 */
public class SwitchTest {

    Switch aSwitch;

    int numberOfPorts = 0;

    @Before
    public void before() throws Exception {
        aSwitch = new Switch();
    }

    @After
    public void after() throws Exception {
        aSwitch = null;
    }

    @Test
    public void setGetNumberOfPorts() throws Exception {
        aSwitch.setNumberOfPorts(numberOfPorts);
        int result = aSwitch.getNumberOfPorts();

        assertEquals(numberOfPorts, result);
    }

}