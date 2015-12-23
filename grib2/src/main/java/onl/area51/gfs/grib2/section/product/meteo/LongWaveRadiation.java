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
package onl.area51.gfs.grib2.section.product.meteo;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import onl.area51.gfs.grib2.section.product.Product;

/**
 * GRIB2 - TABLE 4.2-0-1
 * <p>
 * PARAMETERS FOR DISCIPLINE 0
 * CATEGORY 1
 * <p>
 * (Meteorological products, Moisture category)
 * <p>
 * In Section 0, Octet 7 = 0
 * In Section 4, Octet 10 = 1
 * <p>
 * <p>
 * <p>
 * http://www.nco.ncep.noaa.gov/pmb/docs/grib2/grib2_table4-2-0-1.shtml
 * <p>
 * @author peter
 */
public enum LongWaveRadiation
        implements Product
{

    NLWRS( 0, "Net Long-Wave Radiation Flux (Surface)*", "W m-2", "NLWRS" ),
    NLWRT( 1, "Net Long-Wave Radiation Flux (Top of Atmosphere)*", "W m-2", "NLWRT" ),
    LWAVR( 2, "Long-Wave Radiation Flux*", "W m-2", "LWAVR" ),
    DLWRF( 3, "Downward Long-Wave Rad. Flux", "W m-2", "DLWRF" ),
    ULWRF( 4, "Upward Long-Wave Rad. Flux", "W m-2", "ULWRF" ),
    NLWRF( 5, "Net Long-Wave Radiation Flux", "W m-2", "NLWRF" ),
    NLWRCS( 6, "Net Long-Wave Radiation Flux, Clear Sky", "W m-2", "NLWRCS" ),
    BRTEMP( 7, "Brightness Temperature", "K", "BRTEMP" ),
    DLWRF2( 192, "Downward Long-Wave Rad. Flux", "W m-2", "DLWRF" ),
    ULWRF2( 193, "Upward Long-Wave Rad. Flux", "W m-2", "ULWRF" ),
    LWHR( 194, "Long-Wave Radiative Heating Rate", "K s-1", "LWHR" ),
    CSULF( 195, "Clear Sky Upward Long Wave Flux", "W m-2", "CSULF" ),
    CSDLF( 196, "Clear Sky Downward Long Wave Flux", "W m-2", "CSDLF" ),
    CFNLF( 197, "Cloud Forcing Net Long Wave Flux", "W m-2", "CFNLF" ),

    // Product Discipline 10 - Oceanographic Products
    MISSING( 255, "Missing", "", "" ),
    RESERVED( -1, "Reserved", "", "" );

    private final int code;
    private final String label;
    private final String unit;
    private final String abbrev;

    private static final Map<Integer, LongWaveRadiation> CODES;

    static {
        CODES = Stream.of( values() )
                .filter( d -> d != MISSING && d != RESERVED )
                .collect( Collectors.toMap( LongWaveRadiation::getCode, Function.identity() ) );
    }

    public static LongWaveRadiation lookup( int code )
    {
        if( code == 255 ) {
            return MISSING;
        }
        return CODES.getOrDefault( code, RESERVED );
    }

    private LongWaveRadiation( int code, String label, String unit, String abbrev )
    {
        this.code = code;
        this.label = label;
        this.unit = unit;
        this.abbrev = abbrev;
    }

    @Override
    public int getCode()
    {
        return code;
    }

    @Override
    public String getLabel()
    {
        return label;
    }

    @Override
    public String getUnit()
    {
        return unit;
    }

    @Override
    public String getAbbrev()
    {
        return abbrev;
    }

}
