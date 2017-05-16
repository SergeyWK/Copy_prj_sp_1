package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.Router;
import com.netcracker.edu.inventory.model.impl.Switch;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import com.netcracker.edu.inventory.service.ConnectionService;
import com.netcracker.edu.inventory.service.DeviceService;
import com.netcracker.edu.inventory.service.RackService;
import com.netcracker.edu.inventory.service.Service;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 05.10.16.
 */
public class ServiceImplTest {

    Service service;
    Battery b;
    Router r;
    Switch s;
    WifiRouter wr;
    Device tn;
    Device ab;

    @Before
    public void before() throws Exception {
        service = new ServiceImpl();

        //for sort and filtrate tests
        b = new Battery();
        b.setType(Battery.class.getSimpleName());
        b.setManufacturer("APC");
        b.setModel("Smart-UPS 750 ВА 230 В U2");
        r = new Router();
        r.setType(Router.class.getSimpleName());
        r.setManufacturer("Cisco");
        r.setModel("NCS 5011 Router Data Sheet");
        s = new Switch();
        s.setType(Switch.class.getSimpleName());
        s.setManufacturer("D-Link");
        s.setModel("DES-1050G");
        wr = new WifiRouter();
        wr.setType(WifiRouter.class.getSimpleName());
        wr.setManufacturer("D-Link");
        wr.setModel("DWL-2600AP");
        tn = new Battery();
        Device ab = new Battery();
        ab.setType(new StringBuilder("Bat").append("tery").toString());
        ab.setManufacturer(new StringBuilder("A").append("PC").toString());
        ab.setModel(new StringBuilder("Smart-UPS 750").append(" ВА 230 В U2").toString());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Deprecated
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

    @Deprecated
    @Test
    public void sortByProductionDate() throws Exception {
        Battery b0 = new Battery();
        Battery b1 = new Battery();
        b1.setProductionDate(new Date(1));
        Battery b2 = new Battery();
        b2.setProductionDate(new Date(2));
        Battery b3 = new Battery();
        b3.setProductionDate(new Date(3));
        Device[] devices = new Device[] {null, b2, b1, b0, null, b3, b2, b0, null};
        Device[] expResult = new Device[] {b1, b2, b2, b3, b0, b0, null, null, null};

        service.sortByProductionDate(devices);

        assertArrayEquals(expResult, devices);
    }

    @Deprecated
    @Test
    public void filtrateByType() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, r, null, null, r, null, null, null, null, null};

        service.filtrateByType(devices, Router.class.getSimpleName());

        assertArrayEquals(expResult, devices);
    }

    @Deprecated
    @Test
    public void filtrateByType_TypeNull() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, null, null, tn, null, null, tn, null, null, null};

        service.filtrateByType(devices, null);

        assertArrayEquals(expResult, devices);
    }

    @Deprecated
    @Test
    public void filtrateByType_TypeAnotherString() throws Exception {
        Device[] devices = new Device[] {null, r, b, ab, r, s, ab, null, wr, b};
        Device[] expResult = new Device[] {null, null, b, ab, null, null, ab, null, null, b};

        service.filtrateByType(devices, "Battery");

        assertArrayEquals(expResult, devices);
    }

    @Deprecated
    @Test
    public void filtrateByManufacturer() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, r, null, null, r, null, null, null, null, null};

        service.filtrateByManufacturer(devices, "Cisco");

        assertArrayEquals(expResult, devices);
    }

    @Deprecated
    @Test
    public void filtrateByManufacturer_ManufacturerNull() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, null, null, tn, null, null, tn, null, null, null};

        service.filtrateByManufacturer(devices, null);

        assertArrayEquals(expResult, devices);
    }

    @Deprecated
    @Test
    public void filtrateByManufacturer_ManufacturerAnotherString() throws Exception {
        Device[] devices = new Device[] {null, r, b, ab, r, s, ab, null, wr, b};
        Device[] expResult = new Device[] {null, null, b, ab, null, null, ab, null, null, b};

        service.filtrateByManufacturer(devices, "APC");

        assertArrayEquals(expResult, devices);
    }

    @Deprecated
    @Test
    public void filtrateByModel() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, r, null, null, r, null, null, null, null, null};

        service.filtrateByModel(devices, "NCS 5011 Router Data Sheet");

        assertArrayEquals(expResult, devices);
    }

    @Deprecated
    @Test
    public void filtrateByModel_ModelNull() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, null, null, tn, null, null, tn, null, null, null};

        service.filtrateByModel(devices, null);

        assertArrayEquals(expResult, devices);
    }

    @Deprecated
    @Test
    public void filtrateByModel_ModelAnotherString() throws Exception {
        Device[] devices = new Device[] {null, r, b, ab, r, s, ab, null, wr, b};
        Device[] expResult = new Device[] {null, null, b, ab, null, null, ab, null, null, b};

        service.filtrateByModel(devices, "Smart-UPS 750 ВА 230 В U2");

        assertArrayEquals(expResult, devices);
    }

    @Deprecated
    @Test
    public void isValidDeviceForInsertToRack() throws Exception {
        Battery battery = new Battery();
        battery.setIn(5);

        boolean result = service.isValidDeviceForInsertToRack(battery);

        assertTrue(result);
    }

    @Deprecated
    @Test
    public void isValidDeviceForInsertToRack_DeviceNull() throws Exception {
        boolean result = service.isValidDeviceForInsertToRack(null);

        assertFalse(result);
    }

    @Deprecated
    @Test
    public void isValidDeviceForInsertToRack_IN0() throws Exception {
        Battery battery = new Battery();

        boolean result = service.isValidDeviceForInsertToRack(battery);

        assertFalse(result);
    }

    @Test
    public void getDeviceService() throws Exception {
        DeviceService deviceService = service.getDeviceService();

        assertNotNull(deviceService);
    }

    @Test
    public void getConnectionService() throws Exception {
        ConnectionService connectionService = service.getConnectionService();

        assertNotNull(connectionService);
    }

    @Test
    public void getRackService() throws Exception {
        RackService rackService = service.getRackService();

        assertNotNull(rackService);
    }

}