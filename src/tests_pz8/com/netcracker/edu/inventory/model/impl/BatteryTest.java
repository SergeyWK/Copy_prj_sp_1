package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 05.10.16.
 */
public class BatteryTest {

    Battery battery;

    int chargeVolume = 5;

    @Before
    public void before() throws Exception {
        battery = new Battery();
    }

    @After
    public void after() throws Exception {
        battery = null;
    }

    @Test
    public void setGetChargeVolume() throws Exception {
        battery.setChargeVolume(chargeVolume);
        int result = battery.getChargeVolume();

        assertEquals(chargeVolume, result);
    }

    @Test
    public void testGetAndFillAllFieldsToArray() throws Exception {
        battery = CreateUtilities.createBattery();

        Device result1 = new Battery();
        result1.fillAllFields(battery.getAllFieldsToArray());

        AssertUtilities.assertSomeDevice(battery, result1);
    }

    @Test
    public void testGetAndFillAllFieldsToArray_EmptyDevice() throws Exception {
        Device result1 = new Battery();
        result1.fillAllFields(battery.getAllFieldsToArray());

        AssertUtilities.assertSomeDevice(battery, result1);
    }

}
