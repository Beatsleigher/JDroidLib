/*
 * Copyright (C) 2014 beatsleigher.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package JDroidLib.android.device;

import JDroidLib.android.controllers.ADBController;

import java.util.*;
import java.io.*;

/**
 * Represents a device's CPU (Central Processing Unit) (Its processor, you idiot...)
 * @author beatsleigher
 */
public class CPU {
    
    private ADBController adbController = null;
    
    private final Device device;
    private String[] cpuLoad = null;
    private List<String>cpuUsage = null;
    
    /**
     * Default constructor for @see CPU.
     * Instantiates this class.
     * @param device
     * @param adbController 
     */
    CPU(Device device, ADBController adbController) {
        this.device = device;
        this.adbController = adbController;
        cpuUsage = new ArrayList();
    }
    
    /**
     * Update CPU Usage list and cpu load.
     * @throws IOException 
     */
    private void update() throws IOException {
        String[] cmd = {"dumpsys", "cpuinfo"};
        String raw = adbController.executeADBCommand(true, false, device, cmd);
        BufferedReader reader = new BufferedReader(new StringReader(raw));
        String line = "";
        cpuUsage.clear();
        
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Load: ")) {
                String[] arr = line.split("\\:"), arr0 = arr[1].trim().split("\\/");
                cpuLoad = arr0;
            }
            if (line.startsWith("CPU usage from ")) continue;
            cpuUsage.add(line);
        }
    }
    
    /**
     * Gets the last known CPU-usage from the device.
     * @return The last known CPU usage of the device. 
     * @throws IOException If an error occurs while updating the information.
     */
    public List<String> getCPUUsage() throws IOException { update(); return cpuUsage;}
    
    /**
     * Gets the current CPU load.
     * @return The current CPU load.
     * @throws IOException If an error occurs while updating the information.
     */
    public String[] getCPULoad() throws IOException { update(); return cpuLoad;}
    
}
