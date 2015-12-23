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
public enum Cloud
        implements Product
{

    CICE( 0, "Cloud Ice", "kg m-2", "CICE" ),
    TCDC( 1, "Total Cloud Cover", "%", "TCDC" ),
    CDCON( 2, "Convective Cloud Cover", "%", "CDCON" ),
    LCDC( 3, "Low Cloud Cover", "%", "LCDC" ),
    MCDC( 4, "Medium Cloud Cover", "%", "MCDC" ),
    HCDC( 5, "High Cloud Cover", "%", "HCDC" ),
    CWAT( 6, "Cloud Water", "kg m-2", "CWAT" ),
    CDCA( 7, "Cloud Amount", "%", "CDCA" ),
    CDCT( 8, "Cloud Type", "See Table 4.203", "CDCT" ),
    TMAXT( 9, "Thunderstorm Maximum Tops", "m", "TMAXT" ),
    THUNC( 10, "Thunderstorm Coverage", "See Table 4.204", "THUNC" ),
    CDCB( 11, "Cloud Base", "m", "CDCB" ),
    CDCTOP( 12, "Cloud Top", "m", "CDCTOP" ),
    CEIL( 13, "Ceiling", "m", "CEIL" ),
    CDLYR( 14, "Non-Convective Cloud Cover", "%", "CDLYR" ),
    CWORK( 15, "Cloud Work Function", "J kg-1", "CWORK" ),
    CUEFI( 16, "Convective Cloud Efficiency", "Proportion", "CUEFI" ),
    TCOND( 17, "Total Condensate *", "kg kg-1", "TCOND" ),
    TCOLW( 18, "Total Column-Integrated Cloud Water *", "kg m-2", "TCOLW" ),
    TCOLI( 19, "Total Column-Integrated Cloud Ice *", "kg m-2", "TCOLI" ),
    TCOLC( 20, "Total Column-Integrated Condensate *", "kg m-2", "TCOLC" ),
    FICE( 21, "Ice fraction of total condensate", "Proportion", "FICE" ),
    CDCC( 22, "Cloud Cover", "%", "CDCC" ),
    CDCIMR( 23, "Cloud Ice Mixing Ratio *", "kg kg-1", "CDCIMR" ),
    SUNS( 24, "Sunshine", "Numeric", "SUNS" ),
    CBHE( 25, "Horizontal Extent of Cumulonimbus (CB)", "%", "CBHE" ),
    HCONCB( 26, "Height of Convective Cloud Base", "m", "HCONCB" ),
    HCONCT( 27, "Height of Convective Cloud Top", "m", "HCONCT" ),
    NCONCD( 28, "Number Concentration of Cloud Droplets", "kg-1", "NCONCD" ),
    NCCICE( 29, "Number Concentration of Cloud Ice", "kg-1", "NCCICE" ),
    NDENCD( 30, "Number Density of Cloud Droplets", "m-3", "NDENCD" ),
    NDCICE( 31, "Number Density of Cloud Ice", "m-3", "NDCICE" ),
    FRACCC( 32, "Fraction of Cloud Cover", "Numeric", "FRACCC" ),
    SUNSD( 33, "Sunshine Duration", "s", "SUNSD" ),
    SLWTC( 34, "Surface Long Wave Effective Total Cloudiness", "Numeric", "SLWTC" ),
    SSWTC( 35, "Surface Short Wave Effective Total Cloudiness", "Numeric", "SSWTC" ),
    FSTRPC( 36, "Fraction of Stratiform Precipitation Cover", "Proportion", "FSTRPC" ),
    FCONPC( 37, "Fraction of Convective Precipitation Cover", "Proportion", "FCONPC" ),
    MASSDCD( 38, "Mass Density of Cloud Droplets", "kg m-3", "MASSDCD" ),
    MASSDCI( 39, "Mass Density of Cloud Ice", "kg m-3", "MASSDCI" ),
    CDLYR2( 192, "Non-Convective Cloud Cover", "%", "CDLYR" ),
    CWORK2( 193, "Cloud Work Function", "J kg-1", "CWORK" ),
    CUEFI2( 194, "Convective Cloud Efficiency", "non-dim", "CUEFI" ),
    TCOND2( 195, "Total Condensate", "kg kg-1", "TCOND" ),
    TCOLW2( 196, "Total Column-Integrated Cloud Water", "kg m-2", "TCOLW" ),
    TCOLI2( 197, "Total Column-Integrated Cloud Ice", "kg m-2", "TCOLI" ),
    TCOLC2( 198, "Total Column-Integrated Condensate", "kg m-2", "TCOLC" ),
    FICE2( 199, "Ice fraction of total condensate", "non-dim", "FICE" ),
    MFLUX2( 200, "Convective Cloud Mass Flux", "Pa s-1", "MFLUX" ),
    SUNSD2( 201, "Sunshine Duration", "s", "SUNSD" ),

    // Product Discipline 10 - Oceanographic Products
    MISSING( 255, "Missing", "", "" ),
    RESERVED( -1, "Reserved", "", "" );

    private final int code;
    private final String label;
    private final String unit;
    private final String abbrev;

    private static final Map<Integer, Cloud> CODES;

    static {
        CODES = Stream.of( values() )
                .filter( d -> d != MISSING && d != RESERVED )
                .collect( Collectors.toMap( Cloud::getCode, Function.identity() ) );
    }

    public static Cloud lookup( int code )
    {
        if( code == 255 ) {
            return MISSING;
        }
        return CODES.getOrDefault( code, RESERVED );
    }

    private Cloud( int code, String label, String unit, String abbrev )
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
