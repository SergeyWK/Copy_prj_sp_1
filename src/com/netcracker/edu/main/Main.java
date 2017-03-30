package com.netcracker.edu.main;


import com.netcracker.edu.inventory.model.impl.Battery;
import com.netcracker.edu.inventory.model.impl.RackArrayImpl;
import com.netcracker.edu.inventory.model.impl.Router;

import java.io.IOException;
import java.util.logging.LogManager;

public class Main {
    public static void print(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }

    public static void sort(String[] args) {
        for (int i = 0; i < args.length; i++) {
            for (int j = i + 1; j < args.length; j++) {
                if (args[j].compareTo(args[i]) < 0) {
                    String s = args[i];
                    args[i] = args[j];
                    args[j] = s;
                }
            }
        }
    }


/*
    public static void sort(String[] args) {
        for (int i = 0; i < args.length; i++) {
            for (int j = i + 1; j < args.length; j++){
            int result = compareByName(args[j], args[i]);
                if (result < 0) {
                    String s = args[i];
                    args[i] = args[j];
                    args[j] = s;
                }
            }
        }
    }
    private static int compareByName(String firstName, String secondName){
        return firstName.compareTo(secondName);
    }
*/


    public static void main(String[] args) {
   /* Main.print(args);
    System.out.println("-----------------");
    System.out.println("После сортировки: ");
    Main.sort(args);
    Main.print(args);*/

 /*  try {
       LogManager.getLogManager().readConfiguration(
               Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }*/



   RackArrayImpl rack = new RackArrayImpl(20);
   //RackArrayImpl rack2 = new RackArrayImpl(-38);
   System.out.println("Полная емкость " + rack.getSize());
   System.out.println("-----------------");
   System.out.println("-----------------");
   Router r = new Router();
   Router r1 = new Router();
   Router r2 = new Router();
   Router r3 = new Router();
   Router r4 = new Router();
   r.setIn(5);
   r1.setIn(2);
   r.setDataRate(1000);
   //r2.setIn(-6);
   //r3.setIn(5);
   r3.setIn(-7);
    r4.setIn(0);


    System.out.println("Invent number :" + r4.getIn());
   //System.out.println("Inv number R " + r.getIn());
   //System.out.println("Inv number R1 " + r1.getIn());
   //System.out.println("Inv number R2 " + r2.getIn());
   //System.out.println("скорость данных " + r.getDataRate());
   rack.insertDevToSlot(r, 7);
   rack.insertDevToSlot(r1, 6);
   //rack.insertDevToSlot(r2, 4);
   //rack.insertDevToSlot(r, 20);
   //rack.insertDevToSlot(r, 6);
   //rack.insertDevToSlot(null, 6);
   //rack.insertDevToSlot(r, -1);
   //rack.insertDevToSlot(null, -6);


   //System.out.println(rack.getDevAtSlot(7));
   //System.out.println(rack.getDevAtSlot(27));
   //System.out.println(rack.getDevAtSlot(8));
   //System.out.println(rack.getDevAtSlot(-5));
   //System.out.println(rack.getDevAtSlot(0));
   System.out.println("-------after insert----------");
   System.out.println("free size = " + rack.getFreeSize());

   //rack.removeDevFromSlot(9);
   //rack.removeDevFromSlot(6);
   //rack.removeDevFromSlot(26);
   //rack.removeDevFromSlot(7);
   //rack.removeDevFromSlot(-5);
   System.out.println("-------after remov----------");
   System.out.println("free size = "+ rack.getFreeSize());
   System.out.println("получение номера");
       // System.out.println(rack.getDevByIN(5));
        //System.out.println(rack.getDevByIN(9));
        //System.out.println(rack.getDevByIN(2));

        System.out.println();
    }
}