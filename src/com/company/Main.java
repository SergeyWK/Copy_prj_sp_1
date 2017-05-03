package com.company;

public class Main {

    public static void main(String[] args) {

    }

    public static <T> void fillObjects(GFiveObjects<T> gFiveObjects, T o) {
        for (int i = 0; i < 5; i++) {
            gFiveObjects.setObject(o, i);
        }
    }

    public static <T extends Number> void fillNumbers(GFiveNumbers<T> gFiveNumbers, T number) {
        for (int i = 0; i < 5; i++) {
            gFiveNumbers.setNumber(number, i);
        }
    }

}
