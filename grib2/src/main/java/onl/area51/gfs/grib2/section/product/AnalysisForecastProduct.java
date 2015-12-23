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

import java.io.IOException;
import onl.area51.gfs.grib2.io.GribInputStream;
import onl.area51.gfs.grib2.section.Header;

/**
 * GRIB2 - PRODUCT DEFINITION TEMPLATE 4.0
 * <p>
 * Analysis or forecast at a horizontal level or in a horizontal layer at a point in time
 * <p>
 * @param <T> Type of final Product
 * <p>
 * @author peter
 */
public class AnalysisForecastProduct<T extends Product>
        extends AbstractForecastProduct<T>
{

    public AnalysisForecastProduct( Header header, GribInputStream gis )
            throws IOException
    {
        super( header, gis );

    }

}
