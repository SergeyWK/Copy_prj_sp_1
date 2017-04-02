package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.RackService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 30.03.17.
 */
public class RackServiceImplTest {

    private static final String BINARY_FILE_NAME = "testOutRack.bin";

    RackService rackService;

    @Before
    public void setUp() throws Exception {
        rackService = new RackServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = NotImplementedException.class)
    public void writeRack() throws Exception {
        rackService.writeRack(null, null);
    }

    @Test(expected = NotImplementedException.class)
    public void readRack() throws Exception {
        rackService.readRack(null);
    }

    @Test
    public void outputInputRack() throws Exception {

        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        router.setIn(5);
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        rackService.outputRack(emptyRack, pipedOutputStream);
        rackService.outputRack(partlyRack, pipedOutputStream);
        pipedOutputStream.close();

        Rack result1 = rackService.inputRack(pipedInputStream);
        Rack result2 = rackService.inputRack(pipedInputStream);
        pipedInputStream.close();

        AssertUtilities.assertRack(emptyRack, result1);
        AssertUtilities.assertRack(partlyRack, result2);
    }

    @Test
    public void outputRackRackNull() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        rackService.outputRack(null, pipedOutputStream);
        pipedOutputStream.close();

        assertEquals(-1, pipedInputStream.read());
        pipedInputStream.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void outputRackStreamNull() throws Exception {
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        rackService.outputRack(emptyRack, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputRackNull() throws Exception {
        rackService.inputRack(null);
    }

    @Test
    public void outputToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(BINARY_FILE_NAME);
        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        router.setIn(5);
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        rackService.outputRack(emptyRack, fileOutputStream);
        rackService.outputRack(partlyRack, fileOutputStream);
        fileOutputStream.close();
    }

}