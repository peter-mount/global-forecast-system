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
 * GRIB2 - CODE TABLE 4.0
 * <p>
 * PRODUCT DEFINITION TEMPLATE NUMBER
 * <p>
 * In Section 4, Octet 8-9
 * <p>
 * http://www.nco.ncep.noaa.gov/pmb/docs/grib2/grib2_table4-0.shtml
 * <p>
 * @author peter
 */
public enum Temperature
        implements Product
{

    TMP( 0, "Temperature", "K", "TMP" ),
    VTMP( 1, "Virtual Temperature", "K", "VTMP" ),
    POT( 2, "Potential Temperature", "K", "POT" ),
    EPOT( 3, "Pseudo-Adiabatic Potential Temperature", "K", "EPOT" ),
    /**
     * Parameter deprecated - See Regulation 92.6.2 and use another parameter instead.
     * <p>
     * @deprecated
     */
    @Deprecated
    TMAX( 4, "Maximum Temperature", "K", "TMAX" ),
    /**
     * Parameter deprecated - See Regulation 92.6.2 and use another parameter instead.
     * <p>
     * @deprecated
     */
    @Deprecated
    TMIN( 5, "Minimum Temperature", "K", "TMIN" ),
    DPT( 6, "Dew Point Temperature", "K", "DPT" ),
    DEPR( 7, "Dew Point Depression (or Deficit)", "K", "DEPR" ),
    LAPR( 8, "Lapse Rate", "K m-1", "LAPR" ),
    TMPA( 9, "Temperature Anomaly", "K", "TMPA" ),
    LHTFL( 10, "Latent Heat Net Flux", "W m-2", "LHTFL" ),
    SHTFL( 11, "Sensible Heat Net Flux", "W m-2", "SHTFL" ),
    HEATX( 12, "Heat Index", "K", "HEATX" ),
    WCF( 13, "Wind Chill Factor", "K", "WCF" ),
    /**
     * Parameter deprecated - See Regulation 92.6.2 and use another parameter instead.
     * <p>
     * @deprecated
     */
    @Deprecated
    MINDPD( 14, "Minimum Dew Point Depression", "K", "MINDPD" ),
    VPTMP( 15, "Virtual Potential Temperature", "K", "VPTMP" ),
    SNOHF( 16, "Snow Phase Change Heat Flux", "W m-2", "SNOHF" ),
    SKINT( 17, "Skin Temperature", "K", "SKINT" ),
    SNOT( 18, "Snow Temperature (top of snow)", "K", "SNOT" ),
    TTCHT( 19, "Turbulent Transfer Coefficient for Heat", "Numeric", "TTCHT" ),
    TDCHT( 20, "Turbulent Diffusion Coefficient for Heat", "m2s-1", "TDCHT" ),
    /**
     * Apparent temperature is the perceived outdoor temperature, caused by a combination of phenomena, such as air temperature, relative humidity and wind
     * speed.
     */
    APTMP( 21, "Apparent Temperature", "K", "APTMP" ),
    // TODO check 192 as it's also defined as 16
    SNOHF2( 192, "Snow Phase Change Heat Flux", "W m-2", "SNOHF" ),
    TTRAD( 193, "Temperature Tendency by All Radiation", "K s-1", "TTRAD" ),
    REV( 194, "Relative Error Variance", "", "REV" ),
    LRGHR( 195, "Large Scale Condensate Heating Rate", "K s-1", "LRGHR" ),
    CNVHR( 196, "Deep Convective Heating Rate", "K s-1", "CNVHR" ),
    THFLX( 197, "Total Downward Heat Flux at Surface", "W m-2", "THFLX" ),
    TTDIA( 198, "Temperature Tendency by All Physics", "K s-1", "TTDIA" ),
    TTPHY( 199, "Temperature Tendency by Non-radiation Physics", "K s-1", "TTPHY" ),
    TSD1D( 200, "Standard Dev. of IR Temp. over 1x1 deg. area", "K", "TSD1D" ),
    SHAHR( 201, "Shallow Convective Heating Rate", "K s-1", "SHAHR" ),
    VDFHR( 202, "Vertical Diffusion Heating rate", "K s-1", "VDFHR" ),
    THZ0( 203, "Potential Temperature at Top of Viscous Sublayer", "K", "THZ0" ),
    TCHP( 204, "Tropical Cyclone Heat Potential", "Jm-2K", "TCHP" ),

    // Product Discipline 10 - Oceanographic Products
    MISSING( 255, "Missing", "", "" ),
    RESERVED( -1, "Reserved", "", "" );

    private final int code;
    private final String label;
    private final String unit;
    private final String abbrev;

    private static final Map<Integer, Temperature> CODES;

    static {
        CODES = Stream.of( values() )
                .filter( d -> d != MISSING && d != RESERVED )
                .collect( Collectors.toMap( Temperature::getCode, Function.identity() ) );
    }

    public static Temperature lookup( int code )
    {
        if( code == 255 ) {
            return MISSING;
        }
        return CODES.getOrDefault( code, RESERVED );
    }

    private Temperature( int code, String label, String unit, String abbrev )
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
