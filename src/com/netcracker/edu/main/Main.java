package com.netcracker.edu.main;


import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.RackArrayImpl;
import com.netcracker.edu.inventory.service.DeviceService;

import java.io.IOException;
import java.io.PipedWriter;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.LogManager;

public class Main {
    DeviceService deviceService;

    public static Battery createBattery() {
        Battery battery = new Battery();
        battery.setIn(7);
        battery.setManufacturer("");
        battery.setModel("Smart-UPS 750 ВА 230 В U2");
        battery.setProductionDate(new Date());
        battery.setChargeVolume(5000);
        return battery;
    }

    public void wReadDevice() throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        Battery battery1 = Main.createBattery();
        deviceService.writeDevice(battery1,pipedWriter);
       // pipedWriter.close();
    }



    public static void main(String[] args) throws Exception {

        try {
            LogManager.getLogManager().readConfiguration(
                    Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }

        RackArrayImpl rack = new RackArrayImpl(20);
        //RackArrayImpl rack2 = new RackArrayImpl(-38);
        RackArrayImpl rack3 = new RackArrayImpl(40);
        RackArrayImpl rack4 = new RackArrayImpl(50, Battery.class);
        RackArrayImpl rack5 = new RackArrayImpl(90, Device.class);
        //RackArrayImpl rack6 = new RackArrayImpl(35, null);
        System.out.println("Полная емкость Rack - " + rack.getSize());
        System.out.println("Полная емкость Rack4 - " + rack4.getSize());
        System.out.println("Полная емкость Rack5 - " + rack5.getSize());
        System.out.println("-----------------");
//        Router router = new Router();
//        Router r1 = new Router();
//        Router r2 = new Router();
//        Router r3 = new Router();
//        Router r4 = new Router();
//        Router r5 = new Router();
//        Battery battery1 = Main.createBattery();
//        Battery b2 = new Battery();
//        router.setIn(5);


       // System.out.println(new Main().wReadDevice());


        //        r1.setIn(2);
       /* //r1.setIn(8);
        r3.setIn(16);
        r4.setIn(25);

        b1.setIn(10);
        b2.setIn(12);*/

        //r4.setIn(4);
        //System.out.println("Inv number R " + r.getIn());
        //System.out.println("Inv number R1 " + r1.getIn());
        //System.out.println("Inv number R2 " + r2.getIn());
        //System.out.println("скорость данных " + r.getDataRate());
//        rack4.getTypeOfDevices();
//
//        rack.insertDevToSlot(r, 6);
//        rack.insertDevToSlot(r, 7);
        //rack.insertDevToSlot(r, 20);
        //rack.insertDevToSlot(r1, 6);
        //rack.insertDevToSlot(r2, 4);
//        rack.insertDevToSlot(r3, 18);
//        rack.insertDevToSlot(b1, 15);
        //rack.insertDevToSlot(null, 12);
        //rack.insertDevToSlot(r, -1);
        //rack.insertDevToSlot(null, -6);

//        rack4.insertDevToSlot(b1, 15);
//        rack4.insertDevToSlot(b2, 16);
        //rack4.insertDevToSlot(r,25);
        //rack4.insertDevToSlot(r2, 40);
        //rack4.insertDevToSlot(r3,20);
        //rack4.insertDevToSlot(r4,15);

        //rack4.insertDevToSlot(r2,17);

//        rack5.insertDevToSlot(r1, 6);
//        rack5.insertDevToSlot(b1, 15);
//        rack5.insertDevToSlot(b1, 12);

        //rack5.insertDevToSlot(null,15);


        //System.out.println(rack.getDevAtSlot(7));
        //System.out.println(rack.getDevAtSlot(27));
        //System.out.println(rack.getDevAtSlot(8));
        //System.out.println(rack.getDevAtSlot(-5));
        //System.out.println(rack.getDevAtSlot(0));
//        System.out.println("-------after insert----------");
//        System.out.println("Free size Rack no type= " + rack.getFreeSize());
//        System.out.println("");
//        System.out.println("Free size Rack4 Battery = " + rack4.getFreeSize());
//        System.out.println("");
//        System.out.println("Free size Rack5 Device = " + rack5.getFreeSize());

        //rack4.removeDevFromSlot(20);
        //rack.removeDevFromSlot(6);
        //rack.removeDevFromSlot(26);
        //rack.removeDevFromSlot(7);
        //rack.removeDevFromSlot(4);
        //rack.removeDevFromSlot(-4);
//        System.out.println("-------after remov----------");
//        System.out.println("Free size no type = " + rack.getFreeSize());
//        System.out.println("");
//        System.out.println("Free size Rack4 Battery = " + rack4.getFreeSize());
//        System.out.println("");
//        System.out.println("Free size Rack5 Device = " + rack5.getFreeSize());
//
//        System.out.println("получение номера");
        // System.out.println(rack.getDevByIN(5));
        //System.out.println(rack.getDevByIN(9));
        //System.out.println(rack.getDevByIN(2));

        System.out.println();
    }
}