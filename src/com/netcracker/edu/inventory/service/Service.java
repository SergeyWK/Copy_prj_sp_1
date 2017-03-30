package com.netcracker.edu.inventory.service;

import com.netcracker.edu.inventory.model.Device;

/**The interface Service describe list of services of Inventory component
 *
 * Created by makovetskyi on 05.10.2016.
 */
public interface Service {

    /**
     * Sort array of Device-s by identification number.
     *
     * @param devices - array of Device-s, that need to be sorted
     */
    void sortByIN(Device[] devices);

    /**
     * Sort array of Device-s by production date.
     *
     * @param devices - array of Device-s, that need to be sorted
     */
    void sortByProductionDate(Device[] devices);

    /**
     * Filtrate array of Device-s by type
     *
     * @param devices - array of Device-s, that need to be filtrated
     * @param type - type of Devices, that will remain in the array after filtering
     */
    void filtrateByType(Device[] devices, String type);

    /**
     * Filtrate array of Device-s by manufacturer
     *
     * @param devices - array of Device-s, that need to be filtrated
     * @param manufacturer - manufacturer of Devices, that will remain in the array after filtering
     */
    void filtrateByManufacturer(Device[] devices, String manufacturer);

    /**
     * Filtrate array of Device-s by model
     *
     * @param devices - array of Device-s, that need to be filtrated
     * @param model - model of Devices, that will remain in the array after filtering
     */
    void filtrateByModel(Device[] devices, String model);

    /**
     * Method check validity of device for insert to rack
     *
     * @param device - validated device
     * @return true - if device is valid
     *         false - if device is not valid
     */
    boolean isValidDeviceForInsertToRack(Device device);

}
