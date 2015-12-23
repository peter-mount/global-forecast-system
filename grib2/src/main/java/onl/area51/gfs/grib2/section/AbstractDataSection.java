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

import onl.area51.gfs.grib2.io.SubGribInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import onl.area51.gfs.grib2.io.GribInputStream;
import static onl.area51.gfs.grib2.section.Section.BASE_SIZE;

/**
 * Adds a GribInputStream implementation that can be used to access the data within this section.
 * <p>
 * <p>
 * @author peter
 */
public abstract class AbstractDataSection
        extends Section
{

    private final GribInputStream gis;
    private int dataOffset = BASE_SIZE;

    public AbstractDataSection( GribInputStream gis )
            throws IOException
    {
        super( gis );
        this.gis = gis;
    }

    protected final void setBaseOffset( GribInputStream gis )
            throws IOException
    {
        dataOffset = (int) (gis.position() - getPos());
    }

    public final GribInputStream subStream()
    {
        return new SubGribInputStream( gis, getPos() + dataOffset, getLength() - dataOffset );
    }

}
