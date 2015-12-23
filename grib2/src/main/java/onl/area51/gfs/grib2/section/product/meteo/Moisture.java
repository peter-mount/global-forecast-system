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
public enum Moisture
        implements Product
{

    SPFH( 0, "Specific Humidity", "kg kg-1", "SPFH" ),
    RH( 1, "Relative Humidity", "%", "RH" ),
    MIXR( 2, "Humidity Mixing Ratio", "kg kg-1", "MIXR" ),
    PWAT( 3, "Precipitable Water", "kg m-2", "PWAT" ),
    VAPP( 4, "Vapour Pressure", "Pa", "VAPP" ),
    SATD( 5, "Saturation Deficit", "Pa", "SATD" ),
    EVP( 6, "Evaporation", "kg m-2", "EVP" ),
    PRATE( 7, "Precipitation Rate *", "kg m-2 s-1", "PRATE" ),
    APCP( 8, "Total Precipitation ***", "kg m-2", "APCP" ),
    NCPCP( 9, "Large-Scale Precipitation (non-convective) ***", "kg m-2", "NCPCP" ),
    ACPCP( 10, "Convective Precipitation ***", "kg m-2", "ACPCP" ),
    SNOD( 11, "Snow Depth", "m", "SNOD" ),
    SRWEQ( 12, "Snowfall Rate Water Equivalent *", "kg m-2 s-1", "SRWEQ" ),
    WEASD( 13, "Water Equivalent of Accumulated Snow Depth ***", "kg m-2", "WEASD" ),
    SNOC( 14, "Convective Snow ***", "kg m-2", "SNOC" ),
    SNOL( 15, "Large-Scale Snow ***", "kg m-2", "SNOL" ),
    SNOM( 16, "Snow Melt", "kg m-2", "SNOM" ),
    SNOAG( 17, "Snow Age", "day", "SNOAG" ),
    ABSH( 18, "Absolute Humidity", "kg m-3", "ABSH" ),
    PTYPE( 19, "Precipitation Type", "See Table 4.201", "PTYPE" ),
    ILIQW( 20, "Integrated Liquid Water", "kg m-2", "ILIQW" ),
    TCOND( 21, "Condensate", "kg kg-1", "TCOND" ),
    CLWMR( 22, "Cloud Mixing Ratio", "kg kg-1", "CLWMR" ),
    ICMR( 23, "Ice Water Mixing Ratio", "kg kg-1", "ICMR" ),
    RWMR( 24, "Rain Mixing Ratio", "kg kg-1", "RWMR" ),
    SNMR( 25, "Snow Mixing Ratio", "kg kg-1", "SNMR" ),
    MCONV( 26, "Horizontal Moisture Convergence", "kg kg-1 s-1", "MCONV" ),
    MAXRH( 27, "Maximum Relative Humidity *", "%", "MAXRH" ),
    MAXAH( 28, "Maximum Absolute Humidity *", "kg m-3", "MAXAH" ),
    ASNOW( 29, "Total Snowfall ***", "m", "ASNOW" ),
    PWCAT( 30, "Precipitable Water Category", "See Table 4.202", "PWCAT" ),
    HAIL( 31, "Hail", "m", "HAIL" ),
    GRLE( 32, "Graupel", "kg kg-1", "GRLE" ),
    CRAIN( 33, "Categorical Rain", "Code table 4.222", "CRAIN" ),
    CFRZR( 34, "Categorical Freezing Rain", "Code table 4.222", "CFRZR" ),
    CICEP( 35, "Categorical Ice Pellets", "Code table 4.222", "CICEP" ),
    CSNOW( 36, "Categorical Snow", "Code table 4.222", "CSNOW" ),
    CPRAT( 37, "Convective Precipitation Rate", "kg m-2 s-1", "CPRAT" ),
    MDIVER( 38, "Horizontal Moisture Divergence", "kg kg-1 s-1", "MDIVER" ),
    CPOFP( 39, "Percent frozen precipitation", "%", "CPOFP" ),
    PEVAP( 40, "Potential Evaporation", "kg m-2", "PEVAP" ),
    PEVPR( 41, "Potential Evaporation Rate", "W m-2", "PEVPR" ),
    SNOWC( 42, "Snow Cover", "%", "SNOWC" ),
    FRAIN( 43, "Rain Fraction of Total Cloud Water", "Proportion", "FRAIN" ),
    RIME( 44, "Rime Factor", "Numeric", "RIME" ),
    TCOLR( 45, "Total Column Integrated Rain", "kg m-2", "TCOLR" ),
    TCOLS( 46, "Total Column Integrated Snow", "kg m-2", "TCOLS" ),
    LSWP( 47, "Large Scale Water Precipitation (Non-Convective) ***", "kg m-2", "LSWP" ),
    CWP( 48, "Convective Water Precipitation ***", "kg m-2", "CWP" ),
    TWATP( 49, "Total Water Precipitation ***", "kg m-2", "TWATP" ),
    TSNOWP( 50, "Total Snow Precipitation ***", "kg m-2", "TSNOWP" ),
    TCWAT( 51, "Total Column Water (Vertically integrated total water (vapour+cloud water/ice)", "kg m-2", "TCWAT" ),
    TPRATE( 52, "Total Precipitation Rate **", "kg m-2 s-1", "TPRATE" ),
    TSRWE( 53, "Total Snowfall Rate Water Equivalent **", "kg m-2 s-1", "TSRWE" ),
    LSPRATE( 54, "Large Scale Precipitation Rate", "kg m-2 s-1", "LSPRATE" ),
    CSRWE( 55, "Convective Snowfall Rate Water Equivalent", "kg m-2 s-1", "CSRWE" ),
    LSSRWE( 56, "Large Scale Snowfall Rate Water Equivalent", "kg m-2 s-1", "LSSRWE" ),
    TSRATE( 57, "Total Snowfall Rate", "m s-1", "TSRATE" ),
    CSRATE( 58, "Convective Snowfall Rate", "m s-1", "CSRATE" ),
    LSSRATE( 59, "Large Scale Snowfall Rate", "m s-1", "LSSRATE" ),
    SDWE( 60, "Snow Depth Water Equivalent", "kg m-2", "SDWE" ),
    SDEN( 61, "Snow Density", "kg m-3", "SDEN" ),
    SEVAP( 62, "Snow Evaporation", "kg m-2", "SEVAP" ),
    TCIWV( 64, "Total Column Integrated Water Vapour", "kg m-2", "TCIWV" ),
    RPRATE( 65, "Rain Precipitation Rate", "kg m-2 s-1", "RPRATE" ),
    SPRATE( 66, "Snow Precipitation Rate", "kg m-2 s-1", "SPRATE" ),
    FPRATE( 67, "Freezing Rain Precipitation Rate", "kg m-2 s-1", "FPRATE" ),
    IPRATE( 68, "Ice Pellets Precipitation Rate", "kg m-2 s-1", "IPRATE" ),
    TCOLW( 69, "Total Column Integrate Cloud Water", "kg m-2", "TCOLW" ),
    TCOLI( 70, "Total Column Integrate Cloud Ice", "kg m-2", "TCOLI" ),
    HAILMXR( 71, "Hail Mixing Ratio", "kg kg-1", "HAILMXR" ),
    TCOLH( 72, "Total Column Integrate Hail", "kg m-2", "TCOLH" ),
    HAILPR( 73, "Hail Prepitation Rate", "kg m-2 s-1", "HAILPR" ),
    TCOLG( 74, "Total Column Integrate Graupel", "kg m-2", "TCOLG" ),
    GPRATE( 75, "Graupel (Snow Pellets) Prepitation Rate", "kg m-2 s-1", "GPRATE" ),
    CRRATE( 76, "Convective Rain Rate", "kg m-2 s-1", "CRRATE" ),
    LSRRATE( 77, "Large Scale Rain Rate", "kg m-2 s-1", "LSRRATE" ),
    TCOLWA( 78, "Total Column Integrate Water (All components including precipitation)", "kg m-2", "TCOLWA" ),
    EVARATE( 79, "Evaporation Rate", "kg m-2 s-1", "EVARATE" ),
    TOTCON( 80, "Total Condensate", "kg kg-1", "TOTCON" ),
    TCICON( 81, "Total Column-Integrate Condensate", "kg m-2", "TCICON" ),
    CIMIXR( 82, "Cloud Ice Mixing Ratio", "kg kg-1", "CIMIXR" ),
    SCLLWC( 83, "Specific Cloud Liquid Water Content", "kg kg-1", "SCLLWC" ),
    SCLIWC( 84, "Specific Cloud Ice Water Content", "kg kg-1", "SCLIWC" ),
    SRAINW( 85, "Specific Rain Water Content", "kg kg-1", "SRAINW" ),
    SSNOWW( 86, "Specific Snow Water Content", "kg kg-1", "SSNOWW" ),
    TKMFLX( 90, "Total Kinematic Moisture Flux", "kg kg-1 m s-1", "TKMFLX" ),
    UKMFLX( 91, "U-component (zonal) Kinematic Moisture Flux", "kg kg-1 m s-1", "UKMFLX" ),
    VKMFLX( 92, "V-component (meridional) Kinematic Moisture Flux", "kg kg-1 m s-1", "VKMFLX" ),
    RHWATER( 93, "Relative Humidity With Respect to Water", "%", "RHWATER" ),
    RHICE( 94, "Relative Humidity With Respect to Ice", "%", "RHICE" ),
    FZPRATE( 95, "Freezing or Frozen Precipitation Rate", "kg m-2 s-1", "FZPRATE" ),
    MASSDR( 96, "Mass Density of Rain", "kg m-3", "MASSDR" ),
    MASSDS( 97, "Mass Density of Snow", "kg m-3", "MASSDS" ),
    MASSDG( 98, "Mass Density of Graupel", "kg m-3", "MASSDG" ),
    MASSDH( 99, "Mass Density of Hail", "kg m-3", "MASSDH" ),
    SPNCR( 100, "Specific Number Concentration of Rain", "kg-1", "SPNCR" ),
    SPNCS( 101, "Specific Number Concentration of Snow", "kg-1", "SPNCS" ),
    SPNCG( 102, "Specific Number Concentration of Graupel", "kg-1", "SPNCG" ),
    SPNCH( 103, "Specific Number Concentration of Hail", "kg-1", "SPNCH" ),
    NUMDR( 104, "Number Density of Rain", "m-3", "NUMDR" ),
    NUMDS( 105, "Number Density of Snow", "m-3", "NUMDS" ),
    NUMDG( 106, "Number Density of Graupel", "m-3", "NUMDG" ),
    NUMDH( 107, "Number Density of Hail", "m-3", "NUMDH" ),
    CRAIN2( 192, "Categorical Rain", "Code table 4.222", "CRAIN" ),
    CFRZR2( 193, "Categorical Freezing Rain", "Code table 4.222", "CFRZR" ),
    CICEP2( 194, "Categorical Ice Pellets", "Code table 4.222", "CICEP" ),
    CSNOW2( 195, "Categorical Snow", "Code table 4.222", "CSNOW" ),
    CPRAT2( 196, "Convective Precipitation Rate", "kg m-2 s-1", "CPRAT" ),
    MCONV2( 197, "Horizontal Moisture Divergence", "kg kg-1 s-1", "MCONV" ),
    MINRH( 198, "Minimum Relative Humidity", "%", "MINRH" ),
    PEVAP2( 199, "Potential Evaporation", "kg m-2", "PEVAP" ),
    PEVPR2( 200, "Potential Evaporation Rate", "W m-2", "PEVPR" ),
    SNOWC2( 201, "Snow Cover", "%", "SNOWC" ),
    FRAIN2( 202, "Rain Fraction of Total Liquid Water", "non-dim", "FRAIN" ),
    RIME2( 203, "Rime Factor", "non-dim", "RIME" ),
    TCOLR2( 204, "Total Column Integrated Rain", "kg m-2", "TCOLR" ),
    TCOLS2( 205, "Total Column Integrated Snow", "kg m-2", "TCOLS" ),
    TIPD( 206, "Total Icing Potential Diagnostic", "non-dim", "TIPD" ),
    NCIP( 207, "Number concentration for ice particles", "non-dim", "NCIP" ),
    SNOT( 208, "Snow temperature", "K", "SNOT" ),
    TCLSW( 209, "Total column-integrated supercooled liquid water", "kg m-2", "TCLSW" ),
    TCOLM( 210, "Total column-integrated melting ice", "kg m-2", "TCOLM" ),
    EMNP( 211, "Evaporation - Precipitation", "cm/day", "EMNP" ),
    SBSNO( 212, "Sublimation (evaporation from snow)", "W m-2", "SBSNO" ),
    CNVMR( 213, "Deep Convective Moistening Rate", "kg kg-1 s-1", "CNVMR" ),
    SHAMR( 214, "Shallow Convective Moistening Rate", "kg kg-1 s-1", "SHAMR" ),
    VDFMR( 215, "Vertical Diffusion Moistening Rate", "kg kg-1 s-1", "VDFMR" ),
    CONDP( 216, "Condensation Pressure of Parcali, Lifted From Indicate Surface", "Pa", "CONDP" ),
    LRGMR( 217, "Large scale moistening rate", "kg kg-1 s-1", "LRGMR" ),
    QZ0( 218, "Specific humidity at top of viscous sublayer", "kg kg-1", "QZ0" ),
    QMAX( 219, "Maximum specific humidity at 2m", "kg kg-1", "QMAX" ),
    QMIN( 220, "Minimum specific humidity at 2m", "kg kg-1", "QMIN" ),
    ARAIN( 221, "Liquid precipitation (Rainfall)", "kg m-2", "ARAIN" ),
    SNOWT( 222, "Snow temperature, depth-avg", "K", "SNOWT" ),
    APCPN( 223, "Total precipitation (nearest grid point)", "kg m-2", "APCPN" ),
    ACPCPN( 224, "Convective precipitation (nearest grid point)", "kg m-2", "ACPCPN" ),
    FRZR( 225, "Freezing Rain", "kg m-2", "FRZR" ),
    PWTHER( 226, "Pblackominant Weather (see Note 1)", "Numeric", "PWTHER" ),
    FROZR( 227, "Frozen Rain", "kg m-2", "FROZR" ),
    TSNOW( 241, "Total Snow", "kg m-2", "TSNOW" ),
    RHPW( 242, "Relative Humidity with Respect to Precipitable Water", "%", "RHPW" ),

    // Product Discipline 10 - Oceanographic Products
    MISSING( 255, "Missing", "", "" ),
    RESERVED( -1, "Reserved", "", "" );

    private final int code;
    private final String label;
    private final String unit;
    private final String abbrev;

    private static final Map<Integer, Moisture> CODES;

    static {
        CODES = Stream.of( values() )
                .filter( d -> d != MISSING && d != RESERVED )
                .collect( Collectors.toMap( Moisture::getCode, Function.identity() ) );
    }

    public static Moisture lookup( int code )
    {
        if( code == 255 ) {
            return MISSING;
        }
        return CODES.getOrDefault( code, RESERVED );
    }

    private Moisture( int code, String label, String unit, String abbrev )
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
