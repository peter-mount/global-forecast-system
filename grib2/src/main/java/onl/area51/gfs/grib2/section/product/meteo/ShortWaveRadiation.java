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
public enum ShortWaveRadiation
        implements Product
{

    NSWRS( 0, "Net Short-Wave Radiation Flux (Surface)*", "W m-2", "NSWRS" ),
    NSWRT( 1, "Net Short-Wave Radiation Flux (Top of Atmosphere)*", "W m-2", "NSWRT" ),
    SWAVR( 2, "Short-Wave Radiation Flux*", "W m-2", "SWAVR" ),
    GRAD( 3, "Global Radiation Flux", "W m-2", "GRAD" ),
    BRTMP( 4, "Brightness Temperature", "K", "BRTMP" ),
    LWRAD( 5, "Radiance (with respect to wave number)", "W m-1 sr-1", "LWRAD" ),
    SWRAD( 6, "Radiance (with respect to wavelength)", "W m-3 sr-1", "SWRAD" ),
    DSWRF( 7, "Downward Short-Wave Radiation Flux", "W m-2", "DSWRF" ),
    USWRF( 8, "Upward Short-Wave Radiation Flux", "W m-2", "USWRF" ),
    NSWRF( 9, "Net Short Wave Radiation Flux", "W m-2", "NSWRF" ),
    PHOTAR( 10, "Photosynthetically Active Radiation", "W m-2", "PHOTAR" ),
    NSWRFCS( 11, "Net Short-Wave Radiation Flux, Clear Sky", "W m-2", "NSWRFCS" ),
    DWUVR( 12, "Downward UV Radiation", "W m-2", "DWUVR" ),
    UVIUCS( 50, "UV Index (Under Clear Sky)**", "Numeric", "UVIUCS" ),
    UVI( 51, "UV Index**", "W m-2", "UVI" ),
    DSWRF2( 192, "Downward Short-Wave Radiation Flux", "W m-2", "DSWRF" ),
    USWRF2( 193, "Upward Short-Wave Radiation Flux", "W m-2", "USWRF" ),
    DUVB( 194, "UV-B Downward Solar Flux", "W m-2", "DUVB" ),
    CDUVB( 195, "Clear sky UV-B Downward Solar Flux", "W m-2", "CDUVB" ),
    CSDSF( 196, "Clear Sky Downward Solar Flux", "W m-2", "CSDSF" ),
    SWHR( 197, "Solar Radiative Heating Rate", "K s-1", "SWHR" ),
    CSUSF( 198, "Clear Sky Upward Solar Flux", "W m-2", "CSUSF" ),
    CFNSF( 199, "Cloud Forcing Net Solar Flux", "W m-2", "CFNSF" ),
    VBDSF( 200, "Visible Beam Downward Solar Flux", "W m-2", "VBDSF" ),
    VDDSF( 201, "Visible Diffuse Downward Solar Flux", "W m-2", "VDDSF" ),
    NBDSF( 202, "Near IR Beam Downward Solar Flux", "W m-2", "NBDSF" ),
    NDDSF( 203, "Near IR Diffuse Downward Solar Flux", "W m-2", "NDDSF" ),
    DTRF( 204, "Downward Total Radiation Flux", "W m-2", "DTRF" ),
    UTRF( 205, "Upward Total Radiation Flux", "W m-2", "UTRF" ),

    // Product Discipline 10 - Oceanographic Products
    MISSING( 255, "Missing", "", "" ),
    RESERVED( -1, "Reserved", "", "" );

    private final int code;
    private final String label;
    private final String unit;
    private final String abbrev;

    private static final Map<Integer, ShortWaveRadiation> CODES;

    static {
        CODES = Stream.of( values() )
                .filter( d -> d != MISSING && d != RESERVED )
                .collect( Collectors.toMap( ShortWaveRadiation::getCode, Function.identity() ) );
    }

    public static ShortWaveRadiation lookup( int code )
    {
        if( code == 255 ) {
            return MISSING;
        }
        return CODES.getOrDefault( code, RESERVED );
    }

    private ShortWaveRadiation( int code, String label, String unit, String abbrev )
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
