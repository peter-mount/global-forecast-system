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
public class GridDefinition
        extends Section
{

    private final int source;
    private final int noDataPoints;
    private final int noOptionalList;

    public GridDefinition( GribInputStream gis )
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
        gis.readUnsignedByte();

        // 13-14
        gis.readUnsignedShort();

        // 15-xx
        seekNextSection( gis );
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

}
