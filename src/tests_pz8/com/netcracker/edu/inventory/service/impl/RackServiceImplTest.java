package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.AssertUtilities;
import com.netcracker.edu.inventory.CreateUtilities;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.Rack;
import com.netcracker.edu.inventory.model.impl.*;
import com.netcracker.edu.inventory.service.RackService;
import com.netcracker.edu.location.impl.LocationStubImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Created by makovetskyi on 30.03.17.
 */
public class RackServiceImplTest {

    private static final int PIPED_BUFER_SIZE = 1024*4;
    private static final String BINARY_FILE_NAME = "testOutRack.bin";
    private static final String TEXT_FILE_NAME = "testOutRack.txt";
    private static final String OBJECT_FILE_NAME = "testOutRack.obj";

    RackService rackService;

    @Before
    public void setUp() throws Exception {
        rackService = new RackServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void outputInputRack() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream, PIPED_BUFER_SIZE);
        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
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
    public void writeReadRack() throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter, PIPED_BUFER_SIZE);
        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        rackService.writeRack(emptyRack, pipedWriter);
        rackService.writeRack(partlyRack, pipedWriter);
        pipedWriter.close();

        Rack result1 = rackService.readRack(pipedReader);
        Rack result2 = rackService.readRack(pipedReader);
        pipedReader.close();

        AssertUtilities.assertRack(emptyRack, result1);
        AssertUtilities.assertRack(partlyRack, result2);
    }

    @Test
    public void writeRackRackNull() throws Exception {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader(pipedWriter);

        rackService.writeRack(null, pipedWriter);
        pipedWriter.close();

        assertEquals(-1, pipedReader.read());
        pipedReader.close();
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeRackStreamNull() throws Exception {
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        rackService.writeRack(emptyRack, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readRackNull() throws Exception {
        rackService.readRack(null);
    }

    @Test
    public void serializeDeserializeRack() throws Exception {
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream, PIPED_BUFER_SIZE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(pipedOutputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(pipedInputStream);
        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        objectOutputStream.writeObject(emptyRack);
        objectOutputStream.writeObject(partlyRack);
        pipedOutputStream.close();

        Rack result1 = (Rack) objectInputStream.readObject();
        Rack result2 = (Rack) objectInputStream.readObject();
        pipedInputStream.close();

        AssertUtilities.assertRack(emptyRack, result1);
        AssertUtilities.assertRack(partlyRack, result2);
    }

    @Test
    public void outputToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(BINARY_FILE_NAME);
        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        rackService.outputRack(emptyRack, fileOutputStream);
        rackService.outputRack(partlyRack, fileOutputStream);
        fileOutputStream.close();
    }

    @Test
    public void writeToFile() throws Exception {
        FileWriter fileWriter = new FileWriter(TEXT_FILE_NAME);
        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        rackService.writeRack(emptyRack, fileWriter);
        rackService.writeRack(partlyRack, fileWriter);
        fileWriter.close();
    }

    @Test
    public void serializeToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(OBJECT_FILE_NAME);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        Switch aSwitch = CreateUtilities.createSwitch();
        Router router = CreateUtilities.createRouter();
        Rack emptyRack = new RackArrayImpl(0, Device.class);
        Rack partlyRack =  new RackArrayImpl(3, Router.class);
        partlyRack.setLocation(new LocationStubImpl("ua.od.onpu.ics.607.east_wall", "NC_TC_Odessa"));
        partlyRack.insertDevToSlot(aSwitch, 0);
        partlyRack.insertDevToSlot(router, 2);

        objectOutputStream.writeObject(emptyRack);
        objectOutputStream.writeObject(partlyRack);
        fileOutputStream.close();
    }

}