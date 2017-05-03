package com.netcracker.edu.inventory;

import com.netcracker.edu.inventory.model.*;
import com.netcracker.edu.inventory.model.impl.*;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 30.03.17.
 */
public class AssertUtilities {

    public static void assertSomeDevice(Device expDevice, Device device) throws Exception {
        assertEquals(expDevice.getClass(), device.getClass());
        if (expDevice.getClass() == Battery.class) {
            assertBattery((Battery) expDevice, (Battery) device);
            return;
        }
        if (expDevice.getClass() == Router.class) {
            assertRouter((Router) expDevice, (Router) device);
            return;
        }
        if (expDevice.getClass() == Switch.class) {
            assertSwitch((Switch) expDevice, (Switch) device);
            return;
        }
        if (expDevice.getClass() == WifiRouter.class) {
            assertWifiRouter((WifiRouter) expDevice, (WifiRouter) device);
            return;
        }
    }

    public static void assertDevice(Device expDevice, Device device) throws Exception {
        assertEquals(expDevice.getIn(), device.getIn());
        assertEquals(expDevice.getType(), device.getType());
        assertEquals(expDevice.getModel(), device.getModel());
        assertEquals(expDevice.getManufacturer(), device.getManufacturer());
        assertEquals(expDevice.getProductionDate(), device.getProductionDate());
    }

    public static void assertBattery(Battery expBattery, Battery battery) throws Exception {
        assertDevice(expBattery, battery);
        assertEquals(expBattery.getChargeVolume(), battery.getChargeVolume());
    }

    public static void assertRouter(Router expRouter, Router router) throws Exception {
        assertDevice(expRouter, router);
        assertEquals(expRouter.getDataRate(), router.getDataRate());
    }

    public static void assertSwitch(Switch expSwitch, Switch aSwitch) throws Exception {
        assertRouter(expSwitch, aSwitch);
        assertEquals(expSwitch.getNumberOfPorts(), aSwitch.getNumberOfPorts());
    }

    public static void assertWifiRouter(WifiRouter expWifiRouter, WifiRouter wifiRouter) throws Exception {
        assertRouter(expWifiRouter, wifiRouter);
        assertEquals(expWifiRouter.getSecurityProtocol(), wifiRouter.getSecurityProtocol());
    }

    public static void assertRack(Rack expRack, Rack rack) throws Exception {
        if ((expRack.getLocation() == null) || (rack.getLocation() == null)) {
            assertEquals(expRack.getLocation(), rack.getLocation());
        } else {
            assertEquals(expRack.getLocation().getFullName(), rack.getLocation().getFullName());
            assertEquals(expRack.getLocation().getShortName(), rack.getLocation().getShortName());
        }
        assertEquals(expRack.getSize(), rack.getSize());
        assertEquals(expRack.getTypeOfDevices(), rack.getTypeOfDevices());
        for (int i = 0; i < expRack.getSize(); i++) {
            Device expDevice = expRack.getDevAtSlot(i);
            Device device = rack.getDevAtSlot(i);
            if (expDevice == null) {
                assertNull(device);
            } else {
                assertSomeDevice(expDevice, device);
            }
        }
    }

}
