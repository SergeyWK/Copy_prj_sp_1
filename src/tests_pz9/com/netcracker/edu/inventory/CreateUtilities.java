package com.netcracker.edu.inventory;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.impl.*;

import java.util.Date;

/**
 * Created by makovetskyi on 30.03.17.
 */
public class CreateUtilities {

    public static Battery createBattery() {
        Battery battery = new Battery();
        battery.setIn(4);
        battery.setManufacturer("");
        battery.setModel("Smart-UPS 750 ВА 230 В U2");
        battery.setProductionDate(new Date());
        battery.setChargeVolume(5000);
        return battery;
    }

    public static Router createRouter() {
        Router router = new Router();
        router.setIn(2);
        router.setManufacturer("Cisco");
        router.setDataRate(1000);
        return router;
    }

    public static Switch createSwitch() {
        return createSwitch(7);
    }

    public static Switch createSwitch(int in) {
        Switch aSwitch = new Switch(ConnectorType.RJ45);
        aSwitch.setIn(in);
        aSwitch.setModel("null");
        aSwitch.setManufacturer("D-Link");
        aSwitch.setDataRate(1000000);
        aSwitch.setNumberOfPorts(16);
        return aSwitch;
    }

    public static WifiRouter createWifiRouter() {
        return createWifiRouter(5);
    }

    public static WifiRouter createWifiRouter(int in) {
        WifiRouter wifiRouter = new WifiRouter("802.11g", ConnectorType.RJ45);
        wifiRouter.setIn(in);
        wifiRouter.setModel(null);
        wifiRouter.setManufacturer("D-link");
        wifiRouter.setSecurityProtocol(" ");
        return wifiRouter;
    }

    public static TwistedPair createTwistedPair() {
        return createTwistedPair(4);
    }

    public static TwistedPair createTwistedPair(int serialNumber) {
        TwistedPair twistedPair = new TwistedPair(TwistedPair.Type.UTP, 100);
        twistedPair.setStatus(Connection.READY);
        return twistedPair;
    }

    public static OpticFiber createOpticFiber() {
        OpticFiber opticFiber = new OpticFiber(OpticFiber.Mode.single, 1000);
        opticFiber.setStatus(Connection.ON_BUILD);
        return opticFiber;
    }

    public static Wireless createWireless() {
        return createWireless(3);
    }

    public static Wireless createWireless(int serialNumber) {
        Wireless wireless = new Wireless(3, "802.11g");
        wireless.setStatus(Connection.USED);
        wireless.setProtocol("WPA");
        wireless.setVersion(2);
        return wireless;
    }

    public static ThinCoaxial createThinCoaxial() {
        return createThinCoaxial(5);
    }

    public static ThinCoaxial createThinCoaxial(int serialNumber) {
        ThinCoaxial thinCoaxial = new ThinCoaxial(5);
        thinCoaxial.setStatus(Connection.USED);
        return thinCoaxial;
    }

}