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
package onl.area51.gfs.grib2.section.grid;

import java.io.IOException;
import onl.area51.gfs.grib2.io.GribInputStream;
import onl.area51.gfs.grib2.section.Section;

/**
 *
 * @author peter
 */
public abstract class GridDefinition
        extends Section
{

    private final int source;
    private final int noDataPoints;
    private final int noOptionalList;
    private final InterpretationListNumbers interpretationListNumbers;

    public static <T extends GridDefinition> T create( GribInputStream gis )
            throws IOException
    {
        final long pos = gis.position();

        // Get the template from octets 13 & 14
        gis.seek( pos + 12 );
        final int templateId = gis.readUnsignedShort();
        final GridDefinitionTemplate template = GridDefinitionTemplate.lookup( templateId );
        if( template == GridDefinitionTemplate.RESERVED ) {
            throw new UnsupportedOperationException( "Reserved template " + templateId + " requested" );
        }

        gis.seek( pos );
        final T grid = (T) template.getConstructor().apply( gis );
        grid.seekNextSection( gis );
        return grid;
    }

    protected GridDefinition( GribInputStream gis )
            throws IOException
    {
        super( gis );

        //6
        source = gis.readUnsignedByte();

        //7-10
        noDataPoints = gis.readInt();

        // 11
        noOptionalList = gis.readUnsignedByte();

        // 12
        interpretationListNumbers = InterpretationListNumbers.lookup( gis.readUnsignedByte() );

        // 13-14
        gis.readUnsignedShort();

        // 15-xx belongs to the subsclass
    }

    public int getNoDataPoints()
    {
        return noDataPoints;
    }

    public int getNoOptionalList()
    {
        return noOptionalList;
    }

    public int getSource()
    {
        return source;
    }

    public InterpretationListNumbers getInterpretationListNumbers()
    {
        return interpretationListNumbers;
    }

}
