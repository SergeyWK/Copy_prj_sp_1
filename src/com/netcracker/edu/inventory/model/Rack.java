package com.netcracker.edu.inventory.model;

/**
 * The interface Rack describe contract of mutable device-holder,
 * with limited volume of slots. Slots ara ordered and can fills and released randomly. *
 *
 * Created by makovetskyi on 05.10.2016.
 */
public interface Rack {
    /**
     *
     * @return full capacity of rack
     */
    int getSize();

    /**
     *
     * @return free capacity of rack
     */
    int getFreeSize();

    /**
     * Get device object at specified slot of rack
     *
     * @param index - index of slot
     * @return device object at slot - if slot are full
     *         null - if slot are empty
     */
    Device getDevAtSlot(int index);

    /**
     * Insert device object to specified slot of rack
     * Can't insert to full slot
	 * Can't insert device object with incorrect IN (<0) or IN by default (0)
     *
     * @param device - device object for insert
     * @param index - index of slot
     * @return true - if device was inserted
     *         false - if not
     */
    boolean insertDevToSlot(Device device, int index);

    /**
     * Remove device object from specified slot of rack
     *
     * @param index - index of slot
     * @return device object that has been removed - if slot was full
     *         null - if slot was empty
     */
    Device removeDevFromSlot(int index);

    /**
     * Get device object with specified identification number
     *
     * @param in - identification number
     * @return device object with specified identification number - if it is present in rack
     *         null - if it is absent in rack
     */
    Device getDevByIN(int in);
}