/*******************************************************************************
 * Copyright (c) 2006, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Qi Liang (IBM Corporation)
*******************************************************************************/
package com.ibm.examples.chart.data;

/**
 * Provides the information of technician and saler of one city.
 * 
 * @author Qi Liang
 */
public class Record {

    /**
     * City name.
     */
    private String city = null;

    /**
     * Technician number.
     */
    private int technicians = 0;

    /**
     * Saler number.
     */
    private int salers = 0;

    /**
     * Constructor.
     * 
     * @param city
     *            city name
     * @param technicians
     *            technician number
     * @param saler
     *            saler numbr
     */
    public Record(String city, int technicians, int saler) {
        this.city = city;
        this.technicians = technicians;
        this.salers = saler;
    }

    /**
     * Returns city name.
     * 
     * @return city name
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns technician number.
     * 
     * @return technician number
     */
    public int getTechnicians() {
        return technicians;
    }

    /**
     * Returns saler number.
     * 
     * @return saler number
     */
    public int getSalers() {
        return salers;
    }
}
