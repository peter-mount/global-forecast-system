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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import onl.area51.gfs.grib2.io.GribInputStream;
import onl.area51.gfs.grib2.section.Header;
import uk.trainwatch.io.IOBiFunction;

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
public enum ProductDefinitionTemplate
{

    /**
     * Analysis or forecast at a horizontal level or in a horizontal layer at a point in time. (see Template 4.0)
     */
    ANALYSIS_FORECAST( 0, AnalysisForecastProduct::new ),
    AVERAGE_ACCUMULATION_EXTREME_VALUES( 8, AverageAccumulationExtremeValues::new ),

    MISSING( 65535 ),
    /**
     * Dummy entry used to represent RESERVED disciplines not defined/supported. This value will NOT be present in the GRIB file
     */
    RESERVED( -1 );
    private final int code;
    private final IOBiFunction<Header, GribInputStream, ? super ProductDefinition> constructor;

    private static final Map<Integer, ProductDefinitionTemplate> CODES = new ConcurrentHashMap<>();

    static {
        for( ProductDefinitionTemplate d: values() ) {
            if( d.code > -1 ) {
                CODES.put( d.code, d );
            }
        }
    }

    public static ProductDefinitionTemplate lookup( int code )
    {
        return CODES.getOrDefault( code, RESERVED );
    }

    private ProductDefinitionTemplate( int code )
    {
        this( code, ( header, gis ) -> {
            throw new UnsupportedOperationException( code + " not implemented" );
        } );
    }

    private ProductDefinitionTemplate( int code, IOBiFunction<Header, GribInputStream, ? super ProductDefinition> constructor )
    {
        this.code = code;
        this.constructor = constructor;
    }

    public int getCode()
    {
        return code;
    }

    public IOBiFunction<Header, GribInputStream, ? super ProductDefinition> getConstructor()
    {
        return constructor;
    }

}
