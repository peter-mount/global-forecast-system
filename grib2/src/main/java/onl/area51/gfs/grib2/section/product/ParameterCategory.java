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

import onl.area51.gfs.grib2.section.product.meteo.Temperature;
import onl.area51.gfs.grib2.section.product.meteo.Moisture;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import onl.area51.gfs.grib2.section.product.meteo.Cloud;
import onl.area51.gfs.grib2.section.product.meteo.LongWaveRadiation;
import onl.area51.gfs.grib2.section.product.meteo.Mass;
import onl.area51.gfs.grib2.section.product.meteo.Momentum;
import onl.area51.gfs.grib2.section.product.meteo.ShortWaveRadiation;

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
public enum ParameterCategory
{

    // Product group 0 - Meterological Products
    TEMPERATURE( 0, 0, Temperature::lookup ),
    MOISTURE( 0, 1, Moisture::lookup ),
    MOMENTUM( 0, 2, Momentum::lookup ),
    MASS( 0, 3, Mass::lookup ),
    SHORT_WAVE_RADIATION( 0, 4, ShortWaveRadiation::lookup ),
    LONG_WAVE_RADIATION( 0, 5, LongWaveRadiation::lookup ),
    CLOUD( 0, 6, Cloud::lookup ),
    THERMODYNAMIC_STABILITY_INDICES( 0, 7 ),
    KINEMATIC_STABILITY_INDICES( 0, 8 ),
    TEMPERATURE_PROBABILITIES_DEPRECATED( 0, 9 ),
    MOISTURE_PROBABILITIES_DEPRECATED( 0, 10 ),
    MOMENTUM_PROBABILITIES_DEPRECATED( 0, 11 ),
    MASS_PROBABILITIES_DEPRECATED( 0, 12 ),
    AEROSOLS( 0, 13 ),
    TRACE_GASSES( 0, 14 ),
    RADAR( 0, 15 ),
    FORECAST_RADAR_IMAGERY( 0, 16 ),
    ELECTRODYNAMICS( 0, 17 ),
    NUCLEAR_RADIOLOGY( 0, 18 ),
    PHYSICAL_ATMOSPHERIC_PROPERTIES( 0, 19 ),
    ATMISPHERIC_CHEMICAL_CONSTITUENTS( 0, 20 ),

    CCITT_IA5_STRING( 0, 190 ),
    MISCELLANEOUS( 0, 191 ),
    COVARIANCE( 0, 192 ),

    // Product Discipline 1 - Hydrological products
    HYDROLOGY_BASIC( 1, 0 ),
    HYDROLOGY_PROBABILITIES( 1, 1 ),
    INLAND_WATER_SEDIMENT( 1, 2 ),

    // Product Discipline 2 - Land Surface Products
    VEGETATION_BIOMASS( 2, 0 ),
    AGRICULTURAL_AQUACULTURAL( 2, 1 ),
    TRANSPORTATION( 2, 2 ),
    SOIL( 2, 3 ),
    FIRE_WEATHER( 2, 4 ),
    LAND_SURFACE( 2, 5 ),

    // Product Discipline 3 - Space Products
    IMAFE_FORMAT( 3, 0 ),
    QUANTITIVE( 3, 2 ),
    FORECAST_SATELLITE_IMAGERY( 3, 192 ),

    // Product Discipline 4 - Space Weatrher Products
    TEMPERATURE_SPACE( 4, 0 ),
    MOMENTUM_SPACE( 4, 1 ),
    CHARGED_PARTICLE_MASS_NUMBER_SPACE( 4, 2 ),
    ELECTRIC_MAGNETIC_FIELDS_SPACE( 4, 3 ),
    ENERGETIC_PARTICLES_SPACE( 4, 4 ),
    WAVES_SPACE( 4, 5 ),
    SOLAR_ELECTROMAGNETIC_EMMISSIONS_SPACE( 4, 6 ),
    TERRESTRIAL_ELECTROMAGNETIC_EMMISSIONS_SPACE( 4, 7 ),
    IMAGERY_SPACE( 4, 8 ),
    ION_NEUTRAL_COUPLING_SPACE( 4, 9 ),

    // Product Discipline 10 - Oceanographic Products
    MISSING( -1, 255 ),
    RESERVED( -1, -1 );
    private final Function<Integer, ? super Product> productLookup;
    private final int productGroup;
    private final int code;

    private static final Map<Integer, Map<Integer, ParameterCategory>> CODES = new ConcurrentHashMap<>();

    static {
        for( ParameterCategory d: values() ) {
            if( d != MISSING && d != RESERVED ) {
                CODES.computeIfAbsent( d.productGroup, pg -> new ConcurrentHashMap<>() )
                        .put( d.code, d );
            }
        }
    }

    public static ParameterCategory lookup( int productGroup, int code )
    {
        if( code == 255 ) {
            return MISSING;
        }
        Map<Integer, ParameterCategory> group = CODES.get( productGroup );
        return group == null ? RESERVED : group.getOrDefault( code, RESERVED );
    }

    private ParameterCategory( int productGroup, int code )
    {
        this( productGroup, code, null );
    }

    private ParameterCategory( int productGroup, int code, Function<Integer, ? super Product> productLookup )
    {
        this.productLookup = productLookup;
        this.productGroup = productGroup;
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

    public int getProductGroup()
    {
        return productGroup;
    }

    public <P extends Product> P lookupProduct( int code )
    {
        return productLookup == null ? null : (P) productLookup.apply( code );
    }
}
