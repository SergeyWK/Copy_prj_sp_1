package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.Router;
import com.netcracker.edu.inventory.model.impl.Switch;
import com.netcracker.edu.inventory.model.impl.WifiRouter;
import com.netcracker.edu.inventory.service.DeviceService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.FileOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 30.03.17.
 */
public class DeviceServiceImplTest {

    private static final int PIPED_BUFFER_SIZE = 1024*4;
    private static final String BINARY_FILE_NAME = "testOutDevice.bin";

    DeviceService deviceService;
    Battery b;
    Router r;
    Switch s;
    WifiRouter wr;
    Device tn;
    Device ab;

    @Before
    public void before() throws Exception {
        deviceService = new ServiceImpl().getDeviceService();

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

        deviceService.sortByIN(devices);

        assertArrayEquals(expResult, devices);
    }

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

        deviceService.sortByProductionDate(devices);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, r, null, null, r, null, null, null, null, null};

        deviceService.filtrateByType(devices, Router.class.getSimpleName());

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType_TypeNull() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, null, null, tn, null, null, tn, null, null, null};

        deviceService.filtrateByType(devices, null);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByType_TypeAnotherString() throws Exception {
        Device[] devices = new Device[] {null, r, b, ab, r, s, ab, null, wr, b};
        Device[] expResult = new Device[] {null, null, b, ab, null, null, ab, null, null, b};

        deviceService.filtrateByType(devices, "Battery");

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByManufacturer() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, r, null, null, r, null, null, null, null, null};

        deviceService.filtrateByManufacturer(devices, "Cisco");

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByManufacturer_ManufacturerNull() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, null, null, tn, null, null, tn, null, null, null};

        deviceService.filtrateByManufacturer(devices, null);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByManufacturer_ManufacturerAnotherString() throws Exception {
        Device[] devices = new Device[] {null, r, b, ab, r, s, ab, null, wr, b};
        Device[] expResult = new Device[] {null, null, b, ab, null, null, ab, null, null, b};

        deviceService.filtrateByManufacturer(devices, "APC");

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByModel() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, r, null, null, r, null, null, null, null, null};

        deviceService.filtrateByModel(devices, "NCS 5011 Router Data Sheet");

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByModel_ModelNull() throws Exception {
        Device[] devices = new Device[] {null, r, b, tn, r, s, tn, null, wr, b};
        Device[] expResult = new Device[] {null, null, null, tn, null, null, tn, null, null, null};

        deviceService.filtrateByModel(devices, null);

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void filtrateByModel_ModelAnotherString() throws Exception {
        Device[] devices = new Device[] {null, r, b, ab, r, s, ab, null, wr, b};
        Device[] expResult = new Device[] {null, null, b, ab, null, null, ab, null, null, b};

        deviceService.filtrateByModel(devices, "Smart-UPS 750 ВА 230 В U2");

        assertArrayEquals(expResult, devices);
    }

    @Test
    public void isValidDeviceForInsertToRack() throws Exception {
        Battery battery = new Battery();
        battery.setIn(5);

        boolean result = deviceService.isValidDeviceForInsertToRack(battery);

        assertTrue(result);
    }

    @Test
    public void isValidDeviceForInsertToRack_DeviceNull() throws Exception {
        boolean result = deviceService.isValidDeviceForInsertToRack(null);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForInsertToRack_IN0() throws Exception {
        Battery battery = new Battery();

        boolean result = deviceService.isValidDeviceForInsertToRack(battery);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForOutputToStream() throws Exception {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("none");

        boolean result = deviceService.isValidDeviceForOutputToStream(wifiRouter);

        assertTrue(result);
    }

    @Test
    public void isValidDeviceForOutputToStream_DeviceNull() throws Exception {
        boolean result = deviceService.isValidDeviceForOutputToStream(null);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForOutputToStream_DeviceAttributeInvalid() throws Exception {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("Super\nPuper");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("none");

        boolean result = deviceService.isValidDeviceForOutputToStream(wifiRouter);

        assertFalse(result);
    }

    @Test
    public void isValidDeviceForOutputToStream_ChildAttributeInvalid() throws Exception {
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(5);
        wifiRouter.setModel("Super&Puper");
        wifiRouter.setDataRate(10);
        wifiRouter.setSecurityProtocol("no\nne");

        boolean result = deviceService.isValidDeviceForOutputToStream(wifiRouter);

        assertFalse(result);
    }

    @Test(expected = NotImplementedException.class)
    public void writeDevice() throws Exception {
        deviceService.writeDevice(null, null);
    }

    @Test(expected = NotImplementedException.class)
    public void readDevice() throws Exception {
        deviceService.readDevice(null);
    }

    @Test
    public void outputInputDevice() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream, PIPED_BUFFER_SIZE);
        Battery battery = CreateUtilities.createBattery();
        Router router = CreateUtilities.createRouter();
        Switch aSwitch = CreateUtilities.createSwitch();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();

        deviceService.outputDevice(battery, pipedOutputStream);
        deviceService.outputDevice(router, pipedOutputStream);
        deviceService.outputDevice(aSwitch, pipedOutputStream);
        deviceService.outputDevice(wifiRouter, pipedOutputStream);
        pipedOutputStream.close();

        Device result1 = deviceService.inputDevice(pipedInputStream);
        Device result2 = deviceService.inputDevice(pipedInputStream);
        Device result3 = deviceService.inputDevice(pipedInputStream);
        Device result4 = deviceService.inputDevice(pipedInputStream);
        pipedInputStream.close();

        assertEquals(Battery.class, result1.getClass());
        AssertUtilities.assertBattery(battery, (Battery) result1);
        assertEquals(Router.class, result2.getClass());
        AssertUtilities.assertRouter(router, (Router) result2);
        assertEquals(Switch.class, result3.getClass());
        AssertUtilities.assertSwitch(aSwitch, (Switch) result3);
        assertEquals(WifiRouter.class, result4.getClass());
        AssertUtilities.assertWifiRouter(wifiRouter, (WifiRouter) result4);
    }

    @Test
    public void outputDeviceNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        deviceService.outputDevice(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void outputDeviceStreamNull() throws Exception {
        deviceService.outputDevice(CreateUtilities.createSwitch(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputDeviceStreamNull() throws Exception {
        deviceService.inputDevice(null);
    }

    @Test
    public void outputToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(BINARY_FILE_NAME);
        Battery battery = CreateUtilities.createBattery();
        Router router = CreateUtilities.createRouter();
        Switch aSwitch = CreateUtilities.createSwitch();
        WifiRouter wifiRouter = CreateUtilities.createWifiRouter();

        deviceService.outputDevice(battery, fileOutputStream);
        deviceService.outputDevice(router, fileOutputStream);
        deviceService.outputDevice(aSwitch, fileOutputStream);
        deviceService.outputDevice(wifiRouter, fileOutputStream);
        fileOutputStream.close();
    }

}