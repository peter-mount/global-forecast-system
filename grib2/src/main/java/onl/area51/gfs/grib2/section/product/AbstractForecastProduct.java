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
import java.math.BigDecimal;
import java.math.BigInteger;
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

    private final UnitOfTimeRange unitTimeRange;
    private final int forecastTime;

    private final FixedSurfaceType typeFirstFixedSurface;
    private final int scaleFactorFirstFixedSurface;
    private final int scaleValueFirstFixedSurface;
    private final BigDecimal firstFixedSurface;

    private final FixedSurfaceType typeSecondFixedSurface;
    private final int scaleFactorSecondFixedSurface;
    private final int scaleValueSecondFixedSurface;
    private final BigDecimal secondFixedSurface;

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

        unitTimeRange = UnitOfTimeRange.lookup( gis.readUnsignedByte() );
        forecastTime = gis.readInt();

        typeFirstFixedSurface = FixedSurfaceType.lookup( gis.readUnsignedByte() );
        scaleFactorFirstFixedSurface = gis.readUnsignedByte();
        scaleValueFirstFixedSurface = gis.readInt();
        firstFixedSurface = BigDecimal.valueOf( scaleValueFirstFixedSurface, scaleFactorFirstFixedSurface );

        typeSecondFixedSurface = FixedSurfaceType.lookup( gis.readUnsignedByte() );
        scaleFactorSecondFixedSurface = gis.readUnsignedByte();
        scaleValueSecondFixedSurface = gis.readInt();
        secondFixedSurface = BigDecimal.valueOf( scaleValueSecondFixedSurface, scaleFactorSecondFixedSurface );
    }

    @Override
    public String toString()
    {
        return String.format( getFirstFixedSurfaceType().getUnit().isEmpty() ? "%1$s (%2$s) %5$s %6$s" : "%1$s (%2$s) %3$.0f %4$s %5$s %6$s",
                              getProduct() == null ? "null" : getProduct().getAbbrev(),
                              getProduct() == null ? "" : getProduct().getLabel(),
                              fixValue( getFirstFixedSurface(), getFirstFixedSurfaceType().getUnit() ),
                              fixUnit( getFirstFixedSurface(), getFirstFixedSurfaceType().getUnit() ),
                              getForecastTime(), getUnitTimeRange().getLabel()
        );
    }

    public static boolean isSupported( ProductDefinition def )
    {
        if( def instanceof AbstractForecastProduct ) {
            AbstractForecastProduct prod = (AbstractForecastProduct) def;
            return prod.getProduct() != null;
        }
        return false;
    }

    /**
     * Fix unit to handle kX
     * <p>
     * @param d
     * @param unit
     *             <p>
     * @return
     */
    private String fixUnit( BigDecimal d, String unit )
    {
        return unit.isEmpty() || d.intValue() < 1000 ? unit : "k" + unit;
    }
    private static final BigDecimal THOUSAND = BigDecimal.valueOf( 1000 );

    /**
     * Fix unit to handle kX
     * <p>
     * @param d
     * @param unit
     *             <p>
     * @return
     */
    private BigDecimal fixValue( BigDecimal d, String unit )
    {
        return unit.isEmpty() || d.intValue() < 1000 ? d : d.divide( THOUSAND );
    }

    public ParameterCategory getParameterCategory()
    {
        return parameterCategory;
    }

    public T getProduct()
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

    public UnitOfTimeRange getUnitTimeRange()
    {
        return unitTimeRange;
    }

    public int getForecastTime()
    {
        return forecastTime;
    }

    public FixedSurfaceType getFirstFixedSurfaceType()
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

    public BigDecimal getFirstFixedSurface()
    {
        return firstFixedSurface;
    }

    public FixedSurfaceType getSecondFixedSurfaceType()
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

    public BigDecimal getSecondFixedSurface()
    {
        return secondFixedSurface;
    }

}
