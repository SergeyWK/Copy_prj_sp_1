/*
package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.Router;
import com.netcracker.edu.inventory.model.impl.Switch;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import com.netcracker.edu.inventory.service.Service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

*/
/**
 * Created by makovetskyi on 05.10.16.
 *//*

public class ServiceImplTest {

    Service service;

    @Before
    public void before() throws Exception {
        service = new ServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void sortByIN() throws Exception {
        Battery b0 = new Battery();
        Battery b1 = new Battery();
        b1.setIn(1);
        Battery b2 = new Battery();
        b2.setIn(2);
        Battery b3 = new Battery();
        b3.setIn(3);
        Device[] devices = new Device[] {null, b2, b1, b0, null, b3, b2, b0, null};
        Device[] expResult = new Device[] {b1, b2, b2, b3, b0, b0, null, null, null};

        service.sortByIN(devices);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType() throws Exception {
        Battery b = new Battery();
        b.setType(Battery.class.getSimpleName());
        Router r = new Router();
        r.setType(Router.class.getSimpleName());
        Switch s = new Switch();
        s.setType(Switch.class.getSimpleName());
        WifiRouter wr = new WifiRouter();
        wr.setType(WifiRouter.class.getSimpleName());
        Device tn = new Battery();
        tn.setType(null);
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, r, null, null, r, null, null, null, null, null};

        service.filtrateByType(devices, Router.class.getSimpleName());

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType_TypeNull() throws Exception {
        Battery b = new Battery();
        b.setType(Battery.class.getSimpleName());
        Router r = new Router();
        r.setType(Router.class.getSimpleName());
        Switch s = new Switch();
        s.setType(Switch.class.getSimpleName());
        WifiRouter wr = new WifiRouter();
        wr.setType(WifiRouter.class.getSimpleName());
        Device tn = new Battery();
        tn.setType(null);
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, null, null, tn, null, null, tn, null, null, null};

        service.filtrateByType(devices, null);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType_TypeAnotherString() throws Exception {
        Battery b = new Battery();
        b.setType(Battery.class.getSimpleName());
        Router r = new Router();
        r.setType(Router.class.getSimpleName());
        Switch s = new Switch();
        s.setType(Switch.class.getSimpleName());
        WifiRouter wr = new WifiRouter();
        wr.setType(WifiRouter.class.getSimpleName());
        Device ab = new Battery();
        ab.setType(new StringBuilder("Bat").append("tery").toString());
        Device[] devices = new Device[] {null, r, b, ab, r, s, ab, null, wr, b};
        Device[] expResult = new Device[] {null, null, b, ab, null, null, ab, null, null, b};

        service.filtrateByType(devices, "Battery");

        assertArrayEquals(expResult, devices);
    }

}*/
