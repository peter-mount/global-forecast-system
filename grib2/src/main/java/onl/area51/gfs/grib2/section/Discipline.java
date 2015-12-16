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
package onl.area51.gfs.grib2.section;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Discipline of the Grib file
 * <p>
 * @author peter
 */
public enum Discipline
{
    METEOROLOGICAL( 0 ),
    HYDROLOGICAL( 1 ),
    LAND_SURFACE( 2 ),
    SPACE( 3 ),
    SPACE_WEATHER( 4 ),
    OCEANOGRAPHIC( 10 ),
    MISSING( 255 ),
    /**
     * Dummy entry used to represent RESERVED disciplines not defined/supported. This value will NOT be present in the GRIB file
     */
    RESERVED( -1 );
    private final int code;

    private static final Map<Integer, Discipline> CODES = new ConcurrentHashMap<>();

    static {
        for( Discipline d: values() ) {
            if( d.code > -1 ) {
                CODES.put( d.code, d );
            }
        }
    }

    public static Discipline lookup( int code )
    {
        return CODES.getOrDefault( code, RESERVED );
    }

    private Discipline( int code )
    {
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

}
