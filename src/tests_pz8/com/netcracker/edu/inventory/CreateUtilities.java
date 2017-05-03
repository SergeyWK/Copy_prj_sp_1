package com.netcracker.edu.inventory;

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
        Switch aSwitch = new Switch();
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
        WifiRouter wifiRouter = new WifiRouter();
        wifiRouter.setIn(in);
        wifiRouter.setModel(null);
        wifiRouter.setManufacturer("D-link");
        wifiRouter.setSecurityProtocol(" ");
        return wifiRouter;
    }

}
