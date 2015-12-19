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
package onl.area51.gfs.grib2.section.grid;

import onl.area51.gfs.grib2.section.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import onl.area51.gfs.grib2.io.GribInputStream;
import uk.trainwatch.io.IOFunction;

/**
 * The type of data in {@link Identification}
 * <p>
 * @author peter
 */
public enum GridDefinitionTemplate
{

    LAT_LONG( 0, LatLongGrid::new ),
    ROTATED_LAT_LONG( 1 ),
    STRETCHED_LAT_LONG( 2 ),
    ROTATED_STRETCHED_LAT_LONG( 3 ),
    VARIABLE_RESOLUTION_LAT_LONG( 4 ),
    VARIABLE_RESOLUTION_ROTATED_LAT_LONG( 5 ),

    MERCATOR( 10 ),
    TRANSVERSE_MERCATOR( 11 ),

    POLE_STEREOGRAPHIC( 20 ),

    LAMBERT_CONFORMAL( 30 ),
    ALBERS_EQUAL_AREA( 31 ),

    GAUSSIAN_LAT_LONG( 40 ),
    ROTATED_GAUSSIAN_LAT_LONG( 41 ),
    STRETCHED_GAUSSIAN_LAT_LONG( 42 ),
    ROTATED_STRETCHED_GAUSSIAN_LAT_LONG( 43 ),

    SPHERICAL_HARMINIC_COEF( 50 ),
    ROTATED_SPHERICAL_HARMINIC_COEF( 51 ),
    STRETCHED_SPHERICAL_HARMINIC_COEF( 52 ),
    ROTATED_STRETCHED_SPHERICAL_HARMINIC_COEF( 53 ),

    TRIANGULAR_GRID_ICOSAHEDRON( 100 ),
    GENRAL_UNSTRUCTURED_GRID( 101 ),

    EQUATORIAL_AZIMUTHAL_EQUIDISTANT( 110 ),

    AZIMUTH_RANGE( 120 ),

    LAMBERT_AZIMUTHAL_EQUAL_AREA( 140 ),

    CURVILINEAR_ORTHOGANAL( 204 ),

    CROSS_SECTION_GRID( 1000 ),

    HOVMOLLER_DIAGRAM( 1100 ),

    TIME_SECTION_GRID( 1200 ),

    ROTATED_LAT_LONG_ARAKAWA_STAGGERED_E_GRID( 32768 ),
    ROTATED_LAT_LONG_ARAKAWA_NON_E_GRID( 32769 ),

    MISSING( 65535 ),
    /**
     * Dummy entry used to represent RESERVED disciplines not defined/supported. This value will NOT be present in the GRIB file
     */
    RESERVED( -1 );
    private final int code;
    private final IOFunction<GribInputStream, ? super GridDefinition> constructor;

    private static final Map<Integer, GridDefinitionTemplate> CODES = new ConcurrentHashMap<>();

    static {
        for( GridDefinitionTemplate d: values() ) {
            if( d.code > -1 ) {
                CODES.put( d.code, d );
            }
        }
    }

    public static GridDefinitionTemplate lookup( int code )
    {
        return CODES.getOrDefault( code, RESERVED );
    }

    private GridDefinitionTemplate( int code )
    {
        this( code, gis -> {
            throw new UnsupportedOperationException( code + " not implemented" );
        } );
    }

    private GridDefinitionTemplate( int code, IOFunction<GribInputStream, ? super GridDefinition> constructor )
    {
        this.code = code;
        this.constructor = constructor;
    }

    public int getCode()
    {
        return code;
    }

    public IOFunction<GribInputStream, ? super GridDefinition> getConstructor()
    {
        return constructor;
    }

}
