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
package onl.area51.gfs.grib2.section.data;

import onl.area51.gfs.grib2.section.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Significance of Reference Time
 * <p>
 * @author peter
 */
public enum TemplateType
{

    GRID_POINT_SIMPLE( 0 ),
    MATRIX_VALUE_AT_GRID_POINT( 1 ),
    GRID_POINT_COMPLEX( 2 ),
    GRID_POINT_COMPLEX_SPATIAL( 3 ),
    GRID_POINT_IEEE( 4 ),
    GRID_POINT_JPEG2000( 40 ),
    GRID_POINT_PNG( 41 ),
    SPECTRAL_DATA_SIMPLE( 50 ),
    SPECTRAL_DATA_COMPLEX( 51 ),
    GRID_POINT_SIMPLE_LOG( 61 ),
    RUN_LENGTH( 200 ),
    MISSING( 65535 ),
    /**
     * Dummy entry used to represent RESERVED disciplines not defined/supported. This value will NOT be present in the GRIB file
     */
    RESERVED( -1 );
    private final int code;

    private static final Map<Integer, TemplateType> CODES = new ConcurrentHashMap<>();

    static {
        for( TemplateType d: values() ) {
            if( d.code > -1 ) {
                CODES.put( d.code, d );
            }
        }
    }

    public static TemplateType lookup( int code )
    {
        return CODES.getOrDefault( code, RESERVED );
    }

    private TemplateType( int code )
    {
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

}
