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
 * GRIB2 - TABLE 4.2-0-2
 * <p>
 * PARAMETERS FOR DISCIPLINE 0
 * CATEGORY 2
 * <p>
 * (Meteorological products, Momentum category)
 * <p>
 * In Section 0, Octet 7 = 0
 * In Section 4, Octet 10 = 2
 * <p>
 * http://www.nco.ncep.noaa.gov/pmb/docs/grib2/grib2_table4-2-0-2.shtml
 * <p>
 * @author peter
 */
public enum Momentum
        implements Product
{

    WIND( 1, "Wind Speed", "m s-1", "WIND" ),
    UGRD( 2, "U-Component of Wind", "m s-1", "UGRD" ),
    VGRD( 3, "V-Component of Wind", "m s-1", "VGRD" ),
    STRM( 4, "Stream Function", "m2 s-1", "STRM" ),
    VPOT( 5, "Velocity Potential", "m2 s-1", "VPOT" ),
    MNTSF( 6, "Montgomery Stream Function", "m2 s-2", "MNTSF" ),
    SGCVV( 7, "Sigma Coordinate Vertical Velocity", "s-1", "SGCVV" ),
    VVEL( 8, "Vertical Velocity (Pressure)", "Pa s-1", "VVEL" ),
    DZDT( 9, "Vertical Velocity (Geometric)", "m s-1", "DZDT" ),
    ABSV( 10, "Absolute Vorticity", "s-1", "ABSV" ),
    ABSD( 11, "Absolute Divergence", "s-1", "ABSD" ),
    RELV( 12, "Relative Vorticity", "s-1", "RELV" ),
    RELD( 13, "Relative Divergence", "s-1", "RELD" ),
    PVORT( 14, "Potential Vorticity", "K m2 kg-1 s-1", "PVORT" ),
    VUCSH( 15, "Vertical U-Component Shear", "s-1", "VUCSH" ),
    VVCSH( 16, "Vertical V-Component Shear", "s-1", "VVCSH" ),
    UFLX( 17, "Momentum Flux, U-Component", "N m-2", "UFLX" ),
    VFLX( 18, "Momentum Flux, V-Component", "N m-2", "VFLX" ),
    WMIXE( 19, "Wind Mixing Energy", "J", "WMIXE" ),
    BLYDP( 20, "Boundary Layer Dissipation", "W m-2", "BLYDP" ),
    MAXGUST( 21, "Maximum Wind Speed *", "m s-1", "MAXGUST" ),
    GUST( 22, "Wind Speed (Gust)", "m s-1", "GUST" ),
    UGUST( 23, "U-Component of Wind (Gust)", "m s-1", "UGUST" ),
    VGUST( 24, "V-Component of Wind (Gust)", "m s-1", "VGUST" ),
    VWSH( 25, "Vertical Speed Shear", "s-1", "VWSH" ),
    MFLX( 26, "Horizontal Momentum Flux", "N m-2", "MFLX" ),
    USTM( 27, "U-Component Storm Motion", "m s-1", "USTM" ),
    VSTM( 28, "V-Component Storm Motion", "m s-1", "VSTM" ),
    CD( 29, "Drag Coefficient", "Numeric", "CD" ),
    FRICV( 30, "Frictional Velocity", "m s-1", "FRICV" ),
    TDCMOM( 31, "Turbulent Diffusion Coefficient for Momentum", "m2 s-1", "TDCMOM" ),
    ETACVV( 32, "Eta Coordinate Vertical Velocity", "s-1", "ETACVV" ),
    WINDF( 33, "Wind Fetch", "m", "WINDF" ),
    NWIND( 34, "Normal Wind Component **", "m s-1", "NWIND" ),
    TWIND( 35, "Tangential Wind Component **", "m s-1", "TWIND" ),
    AFRWE( 36, "Amplitude Function for Rossby Wave Envelope for Meridional Wind ***", "m s-1", "AFRWE" ),
    NTSS( 37, "Northward Turbulent Surface Stress ****", "N m-2 s", "NTSS" ),
    ETSS( 38, "Eastward Turbulent Surface Stress ****", "N m-2 s", "ETSS" ),
    VWSH2( 192, "Vertical Speed Shear", "s-1", "VWSH" ),
    MFLX2( 193, "Horizontal Momentum Flux", "N m-2", "MFLX" ),
    USTM2( 194, "U-Component Storm Motion", "m s-1", "USTM" ),
    VSTM2( 195, "V-Component Storm Motion", "m s-1", "VSTM" ),
    CD2( 196, "Drag Coefficient", "non-dim", "CD" ),
    FRICV2( 197, "Frictional Velocity", "m s-1", "FRICV" ),
    LAUV( 198, "Latitude of U Wind Component of Velocity", "deg", "LAUV" ),
    LOUV( 199, "Longitude of U Wind Component of Velocity", "deg", "LOUV" ),
    LAVV( 200, "Latitude of V Wind Component of Velocity", "deg", "LAVV" ),
    LOVV( 201, "Longitude of V Wind Component of Velocity", "deg", "LOVV" ),
    LAPP( 202, "Latitude of Presure Point", "deg", "LAPP" ),
    LOPP( 203, "Longitude of Presure Point", "deg", "LOPP" ),
    VEDH( 204, "Vertical Eddy Diffusivity Heat exchange", "m2 s-1", "VEDH" ),
    COVMZ( 205, "Covariance between Meridional and Zonal - Components of the wind.", "m2 s-2", "COVMZ" ),
    COVTZ( 206, "Covariance between Temperature and Zonal - Components of the wind.", "K*m s-1", "COVTZ" ),
    COVTM( 207, "Covariance between Temperature and Meridional - Components of the wind.", "K*m s-1", "COVTM" ),
    VDFUA( 208, "Vertical Diffusion Zonal Acceleration", "m s-2", "VDFUA" ),
    VDFVA( 209, "Vertical Diffusion Meridional Acceleration", "m s-2", "VDFVA" ),
    GWDU( 210, "Gravity wave drag zonal acceleration", "m s-2", "GWDU" ),
    GWDV( 211, "Gravity wave drag meridional acceleration", "m s-2", "GWDV" ),
    CNVU( 212, "Convective zonal momentum mixing acceleration", "m s-2", "CNVU" ),
    CNVV( 213, "Convective meridional momentum mixing acceleration", "m s-2", "CNVV" ),
    WTEND( 214, "Tendency of vertical velocity", "m s-2", "WTEND" ),
    OMGALF( 215, "Omega (Dp/Dt) divide by density", "K", "OMGALF" ),
    CNGWDU( 216, "Convective Gravity wave drag zonal acceleration", "m s-2", "CNGWDU" ),
    CNGWDV( 217, "Convective Gravity wave drag meridional acceleration", "m s-2", "CNGWDV" ),
    LMV( 218, "Velocity Point Model Surface", "", "LMV" ),
    PVMWW( 219, "Potential Vorticity (Mass-Weighted)", "1/s/m", "PVMWW" ),
    MAXUVV( 220, "Hourly Maximum of Upward Vertical Velocity - in the lowest 400hPa", "m s-1", "MAXUVV" ),
    MAXDVV( 221, "Hourly Maximum of Downward Vertical Velocity - in the lowest 400hPa", "m s-1", "MAXDVV" ),
    MAXUW( 222, "U Component of Hourly Maximum 10m Wind Speed", "m s-1", "MAXUW" ),
    MAXVW( 223, "V Component of Hourly Maximum 10m Wind Speed", "m s-1", "MAXVW" ),
    VRATE( 224, "Ventilation Rate", "m2 s-1", "VRATE" ),

    // Product Discipline 10 - Oceanographic Products
    MISSING( 255, "Missing", "", "" ),
    RESERVED( -1, "Reserved", "", "" );

    private final int code;
    private final String label;
    private final String unit;
    private final String abbrev;

    private static final Map<Integer, Momentum> CODES;

    static {
        CODES = Stream.of( values() )
                .filter( d -> d != MISSING && d != RESERVED )
                .collect( Collectors.toMap( Momentum::getCode, Function.identity() ) );
    }

    public static Momentum lookup( int code )
    {
        if( code == 255 ) {
            return MISSING;
        }
        return CODES.getOrDefault( code, RESERVED );
    }

    private Momentum( int code, String label, String unit, String abbrev )
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
