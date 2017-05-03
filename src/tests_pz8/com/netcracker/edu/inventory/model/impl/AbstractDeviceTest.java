package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 05.10.16.
 */
public class AbstractDeviceTest {

    Device device;

    int in = 5;
    String type = "Bridge";
    String manufacturer = "D-Link";
    String model = "none";
    Date productionDate = new Date();
	
	int newIn0 = 0;
	int newInNot0 = 6;
	int newLess0 = -6;

    @Before
    public void before() throws Exception {
        device = new AbstractDevice() {};
    }

    @After
    public void after() throws Exception {
        device = null;
    }

    @Test
    public void setGetIn() throws Exception {
        device.setIn(in);
        int result = device.getIn();

        assertEquals(in, result);
    }

    @Test
    public void setIn_PrevNot0_newNot0() throws Exception {
        device.setIn(in);
		
		device.setIn(newInNot0);
        int result = device.getIn();

        assertEquals(in, result);
    }

    @Test
    public void setIn_PrevNot0_new0() throws Exception {
        device.setIn(in);
		
		device.setIn(newIn0);
        int result = device.getIn();

        assertEquals(in, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setIn_newLess0() throws Exception {
        device.setIn(0);
		
		device.setIn(newLess0);
        int result = device.getIn();

        assertEquals(0, result);
    }

    @Test
    public void setGetType() throws Exception {
        device.setType(type);
        String result = device.getType();

        assertEquals(type, result);
    }

    @Test
    public void setGetManufacturer() throws Exception {
        device.setManufacturer(manufacturer);
        String result = device.getManufacturer();

        assertEquals(manufacturer, result);
    }

    @Test
    public void setGetModel() throws Exception {
        device.setModel(model);
        String result = device.getModel();

        assertEquals(model, result);
    }

    @Test
    public void setGetProductionDate() throws Exception {
        device.setProductionDate(productionDate);
        Date result = device.getProductionDate();

        assertEquals(productionDate, result);
    }

}