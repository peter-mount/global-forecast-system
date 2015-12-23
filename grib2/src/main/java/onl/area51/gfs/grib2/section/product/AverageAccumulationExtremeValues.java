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
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
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
public class AverageAccumulationExtremeValues<T extends Product>
        extends AbstractForecastProduct<T>
{

    private final LocalDateTime endOfOverallTimeInterval;
    private final int numTimeRangeSpecs;
    private final int totalDataValuesMissing;

    private final TimeRange outermostTimeRange;
    private final Collection<TimeRange> timeRanges;

    public AverageAccumulationExtremeValues( Header header, GribInputStream gis )
            throws IOException
    {
        super( header, gis );

        try {
            endOfOverallTimeInterval = LocalDateTime.of( gis.readUnsignedShort(), gis.readUnsignedByte(), gis.readUnsignedByte(),
                                                         gis.readUnsignedByte(), gis.readUnsignedByte(), gis.readUnsignedByte() );

            numTimeRangeSpecs = gis.readUnsignedByte();
            totalDataValuesMissing = gis.readInt();
            outermostTimeRange = new TimeRange( gis );
            timeRanges = IntStream.range( 0, numTimeRangeSpecs )
                    .mapToObj( i -> new TimeRange( gis ) )
                    .collect( Collectors.toList() );
        }
        catch( UncheckedIOException ex ) {
            throw ex.getCause();
        }
    }

    public LocalDateTime getEndOfOverallTimeInterval()
    {
        return endOfOverallTimeInterval;
    }

    public static class TimeRange
    {

        private final int statProcessUsed;
        private final int typeTimeIncrement;
        private final int indicatorUnitOfTimeRange;
        private final int lengthTimeRangeStatProcessingDone;
        private final int indUnitOfTime;
        private final int timeIncrementSuccessiveFields;

        private TimeRange( GribInputStream gis )
        {
            try {
                statProcessUsed = gis.readUnsignedByte();
                typeTimeIncrement = gis.readUnsignedByte();
                indicatorUnitOfTimeRange = gis.readUnsignedByte();
                lengthTimeRangeStatProcessingDone = gis.readInt();
                indUnitOfTime = gis.readUnsignedByte();
                timeIncrementSuccessiveFields = gis.readInt();
            }
            catch( IOException ex ) {
                throw new UncheckedIOException( ex );
            }
        }

        public int getStatProcessUsed()
        {
            return statProcessUsed;
        }

        public int getTypeTimeIncrement()
        {
            return typeTimeIncrement;
        }

        public int getIndicatorUnitOfTimeRange()
        {
            return indicatorUnitOfTimeRange;
        }

        public int getLengthTimeRangeStatProcessingDone()
        {
            return lengthTimeRangeStatProcessingDone;
        }

        public int getIndUnitOfTime()
        {
            return indUnitOfTime;
        }

        public int getTimeIncrementSuccessiveFields()
        {
            return timeIncrementSuccessiveFields;
        }

    }
}
