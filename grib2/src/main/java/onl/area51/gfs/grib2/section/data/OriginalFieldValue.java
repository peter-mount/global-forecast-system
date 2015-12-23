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

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * @author peter
 */
public enum OriginalFieldValue
{

    FLOATING_POINT( 0, "Floating Point" ),
    INTEGER( 1, "Integer" ),

    MISSING( 255, "Missing" ),
    RESERVED( -1, "Reserved" );

    private final int code;
    private final String label;

    private static final Map<Integer, OriginalFieldValue> CODES;

    static {
        CODES = Stream.of( values() )
                .filter( d -> d != MISSING && d != RESERVED )
                .collect( Collectors.toMap( OriginalFieldValue::getCode, Function.identity() ) );
    }

    public static OriginalFieldValue lookup( int code )
    {
        if( code == 255 ) {
            return MISSING;
        }
        return CODES.getOrDefault( code, RESERVED );
    }

    private OriginalFieldValue( int code, String label )
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
