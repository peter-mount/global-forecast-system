/*
 * Copyright 2015 peter.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package onl.area51.gfs.grib2.section.product;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author peter
 */
public enum FixedSurfaceType
{

    GROUND_WATER_SURFACE( 1, "Ground/Water surface" ),
    CLOUD_BASE( 2, "Cloud Base level" ),
    LEVEL_CLOUD_TOP( 3, "Level of cloud tops" ),
    LEVEL_0C_ISOTHERM( 4, "Level of 0°C isotherm" ),
    LEVEL_ADIABATIC_CONDENSATION( 5, "Level of adiabatic condensation lifted from the surface" ),
    MAX_WIND_LEVEL( 6, "Maximum wind level" ),
    TROPOPAUSE( 7, "Tropopause" ),
    NOMINAL_TOP_ATMOSPHERE( 8, "Nominal top of the atmosphere" ),
    SEA_BOTTOM( 9, "Sea bottom" ),
    ISOTHERMAL_LEVEL( 20, "Isothermal level", "K" ),
    ISOBARIC_SURFACE( 100, "Isobaric surface", "Pa" ),
    MEAN_SEA_LEVEL( 101, "Mean sea level" ),
    SPECIFIC_ALT_ABOVE_MEAN_SEA_LEVEL( 102, "Specific height above mean sea level", "m" ),
    SPECIFIC_HEIGHT_ABOVE_GROUND( 103, "Specific height above ground", "m" ),
    SIGMA( 104, "Sigma", "sigma" ),
    HYBRID( 105, "Hybrid" ),
    DEPTH_BELOW_LAND( 106, "Depth below land", "m" ),
    ISENTROPIC_THETA( 107, "Isentropic (theta) level", "K" ),
    LEVEL_AT_SPECIFIED_PRESSURE_DIFFERENCE_FROM_GROUND( 108, "Level at specified pressure difference from ground to level", "Pa" ),
    POTENTIAL_VORTICITY_SURFACE( 109, "Potential vorticity surface", "K m2 kg-1 s-1" ),
    ETA( 111, "Eta level" ),
    MIXED_LAYER_DEPTH( 117, "Mixed layer depth", "m" ),
    DEPTH_BELOW_SEA_LEVEL( 160, "Depth below sea level", "m" ),
    RESERVED( -1, "Reserved" ),
    MISSING( 255, "Missing" );

    private final int code;
    private final String label;
    private final String unit;

    private static final Map<Integer, FixedSurfaceType> CODES = new ConcurrentHashMap<>();

    static {
        for( FixedSurfaceType d: values() ) {
            if( d != MISSING && d != RESERVED ) {
                CODES.put( d.code, d );
            }
        }
    }

    public static FixedSurfaceType lookup( int code )
    {
        if( code == 255 ) {
            return MISSING;
        }
        return CODES.getOrDefault( code, RESERVED );
    }

    private FixedSurfaceType( int code, String label )
    {
        this( code, label, "" );
    }

    private FixedSurfaceType( int code, String label, String unit )
    {
        this.code = code;
        this.label = label;
        this.unit = unit;
    }

    public int getCode()
    {
        return code;
    }

    public String getLabel()
    {
        return label;
    }

    public String getUnit()
    {
        return unit;
    }

}
