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
package onl.area51.gfs.grib2.section;

import java.io.IOException;
import java.time.LocalDateTime;
import onl.area51.gfs.grib2.io.GribInputStream;

/**
 * the Identification section
 * <p>
 * @author peter
 */
public class Identification
        extends Section
{

    private final int center;
    private final int subcenter;
    private final ReferenceTimeSignificance referenceTimeSignificance;
    private final LocalDateTime dateTime;

    public Identification( GribInputStream gis )
            throws IOException
    {
        super( gis );

        // 6-7
        center = gis.readShort();
        // 8-9
        subcenter = gis.readShort();

        // 10
        if( gis.readUnsignedByte() != 2 ) {
            throw new IOException( "Unsupported GRIB master tables version" );
        }

        // 11
        gis.readUnsignedByte();

        // 12
        referenceTimeSignificance = ReferenceTimeSignificance.lookup( gis.readUnsignedByte() );

        // 13-19 multiple fields
        dateTime = LocalDateTime.of( gis.readShort(), gis.readUnsignedByte(), gis.readUnsignedByte(),
                                     gis.readUnsignedByte(), gis.readUnsignedByte(), gis.readUnsignedByte() );

        // 20
        gis.readUnsignedByte();
        // 21
        gis.readUnsignedByte();

        // Reserved entries
        seekNextSection( gis );
    }

    public int getCenter()
    {
        return center;
    }

    public int getSubcenter()
    {
        return subcenter;
    }

    public ReferenceTimeSignificance getReferenceTimeSignificance()
    {
        return referenceTimeSignificance;
    }

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    @Override
    public String toString()
    {
        return "Identification{ center=" + center + ", subcenter=" + subcenter + ", referenceTimeSignificance=" + referenceTimeSignificance + ", dateTime=" + dateTime + '}';
    }

}
