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

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import onl.area51.gfs.grib2.io.GribInputStream;
import onl.area51.gfs.grib2.section.grid.GridDefinition;

/**
 *
 * @author peter
 */
public enum SectionType
{

    IDENTIFICATION( 1 )
            {
                @Override
                public Section read( GribInputStream gis )
                throws IOException
                {
                    return new Identification( gis );
                }

            },
    LOCAL_USE( 2 )
            {
                @Override
                public Section read( GribInputStream gis )
                throws IOException
                {
                    return new LocalUse( gis );
                }

            },
    GRID_DEFINITION( 3 )
            {
                @Override
                public Section read( GribInputStream gis )
                throws IOException
                {
                    return GridDefinition.create( gis );
                }

            },
    PRODUCT_DEFINITION( 4 )
            {
                @Override
                public Section read( GribInputStream gis )
                throws IOException
                {
                    return new ProductDefinition( gis );
                }

            },
    DATA_REPRESENTATION( 5 )
            {
                @Override
                public Section read( GribInputStream gis )
                throws IOException
                {
                    return new DataRepresentation( gis );
                }

            },
    BIT_MAP( 6 )
            {
                @Override
                public Section read( GribInputStream gis )
                throws IOException
                {
                    return new BitMap( gis );
                }

            },
    DATA( 7 )
            {
                @Override
                public Section read( GribInputStream gis )
                throws IOException
                {
                    return new Data( gis );
                }

            };

    private final int code;

    private static final Map<Integer, SectionType> CODES = new ConcurrentHashMap<>();

    static {
        for( SectionType d: values() ) {
            if( d.code > -1 ) {
                CODES.put( d.code, d );
            }
        }
    }

    public static SectionType lookup( int code )
    {
        return CODES.get( code );
    }

    private SectionType( int code )
    {
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

    public abstract Section read( GribInputStream gis )
            throws IOException;
}
