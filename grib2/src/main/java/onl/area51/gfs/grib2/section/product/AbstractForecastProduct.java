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
public abstract class AbstractForecastProduct<T extends Product>
        extends ProductDefinition
{

    private final ParameterCategory parameterCategory;
    private final T parameterNumber;
    private final int typeGeneratingProcess;
    private final int backgroundProcessIdentifier;
    private final int analysisForecastProcessId;
    private final int hoursObservationDataCutoff;
    private final int minutesObservationDataCutoff;

    private final int unitTimeRange;
    private final int forecastTime;

    private final int typeFirstFixedSurface;
    private final int scaleFactorFirstFixedSurface;
    private final int scaleValueFirstFixedSurface;

    private final int typeSecondFixedSurface;
    private final int scaleFactorSecondFixedSurface;
    private final int scaleValueSecondFixedSurface;

    public AbstractForecastProduct( Header header, GribInputStream gis )
            throws IOException
    {
        super( gis );

        // Octet 10 is the parameter category, 11 the actual product
        parameterCategory = ParameterCategory.lookup( header.getDiscipline().getCode(), gis.readUnsignedByte() );
        parameterNumber = parameterCategory.lookupProduct( gis.readUnsignedByte() );

        typeGeneratingProcess = gis.readUnsignedByte();
        backgroundProcessIdentifier = gis.readUnsignedByte();
        analysisForecastProcessId = gis.readUnsignedByte();
        hoursObservationDataCutoff = gis.readUnsignedShort();
        minutesObservationDataCutoff = gis.readUnsignedByte();

        unitTimeRange = gis.readUnsignedByte();
        forecastTime = gis.readInt();

        typeFirstFixedSurface = gis.readUnsignedByte();
        scaleFactorFirstFixedSurface = gis.readUnsignedByte();
        scaleValueFirstFixedSurface = gis.readInt();

        typeSecondFixedSurface = gis.readUnsignedByte();
        scaleFactorSecondFixedSurface = gis.readUnsignedByte();
        scaleValueSecondFixedSurface = gis.readInt();

    }

    public ParameterCategory getParameterCategory()
    {
        return parameterCategory;
    }

    public T getParameterNumber()
    {
        return parameterNumber;
    }

    public int getTypeGeneratingProcess()
    {
        return typeGeneratingProcess;
    }

    public int getBackgroundProcessIdentifier()
    {
        return backgroundProcessIdentifier;
    }

    public int getAnalysisForecastProcessId()
    {
        return analysisForecastProcessId;
    }

    public int getHoursObservationDataCutoff()
    {
        return hoursObservationDataCutoff;
    }

    public int getMinutesObservationDataCutoff()
    {
        return minutesObservationDataCutoff;
    }

    public int getUnitTimeRange()
    {
        return unitTimeRange;
    }

    public int getForecastTime()
    {
        return forecastTime;
    }

    public int getTypeFirstFixedSurface()
    {
        return typeFirstFixedSurface;
    }

    public int getScaleFactorFirstFixedSurface()
    {
        return scaleFactorFirstFixedSurface;
    }

    public int getScaleValueFirstFixedSurface()
    {
        return scaleValueFirstFixedSurface;
    }

    public int getTypeSecondFixedSurface()
    {
        return typeSecondFixedSurface;
    }

    public int getScaleFactorSecondFixedSurface()
    {
        return scaleFactorSecondFixedSurface;
    }

    public int getScaleValueSecondFixedSurface()
    {
        return scaleValueSecondFixedSurface;
    }

}
