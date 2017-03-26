package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;

import java.util.logging.Logger;

public class Battery extends AbstractDevice implements Device {

   static protected Logger LOGGER = Logger.getLogger(Battery.class.getName());

   protected int chargeVolume;

   public int getChargeVolume() {
      return chargeVolume;
   }

   public void setChargeVolume(int chargeVolume) {
      this.chargeVolume = chargeVolume;
   }
}
