/*
 * JABM - Java Agent-Based Modeling Toolkit
 * Copyright (C) 2013 Steve Phelps
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */
package net.sourceforge.jabm.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * <p>
 * A set of property bindings that are initialised from system properties passed on the command line (typically using
 * the "-D" option). All property names are assumed to be in the form <code>basename.suffix</code> where
 * <code>basename</code> is common to all properties. The <code>basename</code>
 * will typically correspond to the name of a class or application. When constructing the class a set of legal suffixes
 * is specified. If a system property is found with the specified <code><basename></code> but an illegal suffix then an
 * <code>IllegalArgumentExpcetion</code> will be thrown.
 *
 * @author sphelps
 * @see <code>System.getProperty()</code>
 * </p>
 */
public class SystemProperties extends Properties {

    public static final String PROPERTY_BASE = "jabm";

    public static final String PROPERTY_BASE_DIR_NAME = "basedirname";
    public static final String PROPERTY_PROP_FILE = "propertyfile";
    public static final String PROPERTY_CONFIG_ONLY = "configonly";
    public static final String PROPERTY_VAR_FILE = "varfile";
    public static final String PROPERTY_CONFIG = "config";
    public static final String PROPERTY_SEEDS = "genseeds";
    public static final String PROPERTY_SEED_MASK = "seedmask";
    public static final String[] jabmProperties = new String[]{
      PROPERTY_SEEDS, PROPERTY_SEED_MASK, PROPERTY_BASE_DIR_NAME,
      PROPERTY_PROP_FILE, PROPERTY_CONFIG_ONLY, PROPERTY_VAR_FILE,
      PROPERTY_CONFIG};

    protected static SystemProperties jabsConfiguration = null;

    private final String baseName;

    private final Set<String> propertyNames;

    public SystemProperties(String baseName, Set<String> propertyNames,
      HashMap<String, String> defaultBindings)
      throws IllegalArgumentException {
        this.baseName = baseName;
        this.propertyNames = propertyNames;
        readSystemProperties();
    }

    public SystemProperties(String baseName, Collection<String> propertyNames) {
        this(baseName, new HashSet<>(propertyNames), new HashMap<>());
    }

    public SystemProperties(String baseName, String[] propertyNames) {
        this(baseName, Arrays.asList(propertyNames));
    }

    public void readSystemProperties() throws IllegalArgumentException {
        Properties systemProperties = System.getProperties();
        for (Object property : systemProperties.keySet()) {
            String propertyName = property.toString();
            if (propertyName.startsWith(baseName + ".")) {
                propertyName = propertyName.substring(baseName.length() + 1);
                if (propertyNames.contains(propertyName)) {
                    String fullPropertyName = baseName + "." + propertyName;
                    final String value = System.getProperty(fullPropertyName);
                    if (value != null) {
                        setProperty(propertyName, value);
                    }
                } else {
                    throw new IllegalArgumentException("Invalid property: " + propertyName);
                }
            }
        }
    }

    public static SystemProperties jabsConfiguration() {
        if (jabsConfiguration == null) {
            jabsConfiguration = new SystemProperties(PROPERTY_BASE, jabmProperties);
        }
        return jabsConfiguration;
    }
}
