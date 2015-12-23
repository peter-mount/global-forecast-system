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
public enum Mass
        implements Product
{

    PRES( 0, "Pressure", "Pa", "PRES" ),
    PRMSL( 1, "Pressure Reduced to MSL", "Pa", "PRMSL" ),
    PTEND( 2, "Pressure Tendency", "Pa s-1", "PTEND" ),
    ICAHT( 3, "ICAO Standard Atmosphere Reference Height", "m", "ICAHT" ),
    GP( 4, "Geopotential", "m2 s-2", "GP" ),
    HGT( 5, "Geopotential Height", "gpm", "HGT" ),
    DIST( 6, "Geometric Height", "m", "DIST" ),
    HSTDV( 7, "Standard Deviation of Height", "m", "HSTDV" ),
    PRESA( 8, "Pressure Anomaly", "Pa", "PRESA" ),
    GPA( 9, "Geopotential Height Anomaly", "gpm", "GPA" ),
    DEN( 10, "Density", "kg m-3", "DEN" ),
    ALTS( 11, "Altimeter Setting", "Pa", "ALTS" ),
    THICK( 12, "Thickness", "m", "THICK" ),
    PRESALT( 13, "Pressure Altitude", "m", "PRESALT" ),
    DENALT( 14, "Density Altitude", "m", "DENALT" ),
    _5WAVH( 15, "5-Wave Geopotential Height", "gpm", "5WAVH" ),
    U_GWD( 16, "Zonal Flux of Gravity Wave Stress", "N m-2", "U-GWD" ),
    V_GWD( 17, "Meridional Flux of Gravity Wave Stress", "N m-2", "V-GWD" ),
    HPBL( 18, "Planetary Boundary Layer Height", "m", "HPBL" ),
    _5WAVA( 19, "5-Wave Geopotential Height Anomaly", "gpm", "5WAVA" ),
    SDSGSO( 20, "Standard Deviation of Sub-Grid Scale Orography", "m", "SDSGSO" ),
    AOSGSO( 21, "Angle of Sub-Grid Scale Orography", "rad", "AOSGSO" ),
    SSGSO( 22, "Slope of Sub-Grid Scale Orography", "Numeric", "SSGSO" ),
    GWD( 23, "Gravity Wave Dissipation", "W m-2", "GWD" ),
    ASGSO( 24, "Anisotropy of Sub-Grid Scale Orography", "Numeric", "ASGSO" ),
    NLPRES( 25, "Natural Logarithm of Pressure in Pa", "Numeric", "NLPRES" ),
    EXPRES( 26, "Exner Pressure", "Numeric", "EXPRES" ),
    MSLET( 192, "MSLP (Eta model reduction)", "Pa", "MSLET" ),
    _5WAVH2( 193, "5-Wave Geopotential Height", "gpm", "5WAVH" ),
    U_GWD2( 194, "Zonal Flux of Gravity Wave Stress", "N m-2", "U-GWD" ),
    V_GWD2( 195, "Meridional Flux of Gravity Wave Stress", "N m-2", "V-GWD" ),
    HPBL2( 196, "Planetary Boundary Layer Height", "m", "HPBL" ),
    _5WAVA2( 197, "5-Wave Geopotential Height Anomaly", "gpm", "5WAVA" ),
    MSLMA( 198, "MSLP (MAPS System Reduction)", "Pa", "MSLMA" ),
    TSLSA( 199, "3-hr pressure tendency (Std. Atmos. Reduction)", "Pa s-1", "TSLSA" ),
    PLPL( 200, "Pressure of level from which parcel was lifted", "Pa", "PLPL" ),
    LPSX( 201, "X-gradient of Log Pressure", "m-1", "LPSX" ),
    LPSY( 202, "Y-gradient of Log Pressure", "m-1", "LPSY" ),
    HGTX( 203, "X-gradient of Height", "m-1", "HGTX" ),
    HGTY( 204, "Y-gradient of Height", "m-1", "HGTY" ),
    LAYTH( 205, "Layer Thickness", "m", "LAYTH" ),
    NLGSP( 206, "Natural Log of Surface Pressure", "ln (kPa)", "NLGSP" ),
    CNVUMF( 207, "Convective updraft mass flux", "kg m-2 s-1", "CNVUMF" ),
    CNVDMF( 208, "Convective downdraft mass flux", "kg m-2 s-1", "CNVDMF" ),
    CNVDEMF( 209, "Convective detrainment mass flux", "kg m-2 s-1", "CNVDEMF" ),
    LMH( 210, "Mass Point Model Surface", "", "LMH" ),
    HGTN( 211, "Geopotential Height (nearest grid point)", "gpm", "HGTN" ),
    PRESN( 212, "Pressure (nearest grid point)", "Pa", "PRESN" ),
    ORCONV( 213, "Orographic Convexity", "", "ORCONV" ),
    ORASW( 214, "Orographic Asymmetry, W Component", "", "ORASW" ),
    ORASS( 215, "Orographic Asymmetry, S Component", "", "ORASS" ),
    ORASSW( 216, "Orographic Asymmetry, SW Component", "", "ORASSW" ),
    ORASNW( 217, "Orographic Asymmetry, NW Component", "", "ORASNW" ),
    ORLSW( 218, "Orographic Length Scale, W Component", "", "ORLSW" ),
    ORLSS( 219, "Orographic Length Scale, S Component", "", "ORLSS" ),
    ORLSSW( 220, "Orographic Length Scale, SW Component", "", "ORLSSW" ),
    ORLSNW( 221, "Orographic Length Scale, NW Component", "", "ORLSNW" ),

    // Product Discipline 10 - Oceanographic Products
    MISSING( 255, "Missing", "", "" ),
    RESERVED( -1, "Reserved", "", "" );

    private final int code;
    private final String label;
    private final String unit;
    private final String abbrev;

    private static final Map<Integer, Mass> CODES;

    static {
        CODES = Stream.of( values() )
                .filter( d -> d != MISSING && d != RESERVED )
                .collect( Collectors.toMap( Mass::getCode, Function.identity() ) );
    }

    public static Mass lookup( int code )
    {
        if( code == 255 ) {
            return MISSING;
        }
        return CODES.getOrDefault( code, RESERVED );
    }

    private Mass( int code, String label, String unit, String abbrev )
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
