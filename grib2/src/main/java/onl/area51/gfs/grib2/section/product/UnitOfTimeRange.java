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
public enum UnitOfTimeRange
{

    MINUTE( 0, "Minute" ),
    HOUR( 1, "Hour" ),
    DAY( 2, "Day" ),
    MONTH( 3, "Month" ),
    YEAR( 4, "Year" ),
    DECADE( 5, "Decade" ),
    NORMAL( 6, "Normal (30yr)" ),
    CENTURY( 7, "Century" ),
    HR3( 10, "3hrs" ),
    HR6( 11, "6hrs" ),
    HR12( 12, "12hrs" ),
    SECOND( 13, "Second" ),
    RESERVED( -1, "Reserved" ),
    MISSING( 255, "Missing" );

    private final int code;
    private final String label;

    private static final Map<Integer, UnitOfTimeRange> CODES = new ConcurrentHashMap<>();

    static {
        for( UnitOfTimeRange d: values() ) {
            if( d != MISSING && d != RESERVED ) {
                CODES.put( d.code, d );
            }
        }
    }

    public static UnitOfTimeRange lookup( int code )
    {
        if( code == 255 ) {
            return MISSING;
        }
        return CODES.getOrDefault( code, RESERVED );
    }

    private UnitOfTimeRange( int code, String label )
    {
        this.code = code;
        this.label = label;
    }

    public int getCode()
    {
        return code;
    }

    public String getLabel()
    {
        return label;
    }

}
