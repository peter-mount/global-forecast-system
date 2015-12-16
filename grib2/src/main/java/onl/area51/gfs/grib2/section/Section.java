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
import onl.area51.gfs.grib2.io.GribInputStream;

/**
 * Common code for all sections (other than the Header)
 * <p>
 * @author peter
 */
public abstract class Section
        extends Block
{

    private final int length;
    private final int id;

    public Section( GribInputStream gis )
            throws IOException
    {
        super( gis );

        this.length = gis.readInt();
        id = gis.readUnsignedByte();
    }

    public final int getId()
    {
        return id;
    }

    @Override
    public final int getLength()
    {
        return length;
    }

}
