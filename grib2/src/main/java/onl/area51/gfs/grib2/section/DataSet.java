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
import java.util.HashMap;
import java.util.Map;
import onl.area51.gfs.grib2.io.GribInputStream;

/**
 * A dataset within a grib file
 * <p>
 * @author peter
 */
public class DataSet
        extends Block
{

    private static final int END_MARKER = 0x37373737;

    private final Header header;
    private final Map<SectionType, ? super Section> sections = new HashMap<>();

    public DataSet( GribInputStream gis )
            throws IOException
    {
        super( gis );
        header = new Header( gis );

        int length;
        do {
            final long pos = gis.position();
            length = gis.readInt();
            if( length != END_MARKER ) {
                SectionType type = SectionType.lookup( gis.readUnsignedByte() );
                if( type != null ) {
                    gis.seek( pos );
                    Section section = type.read( gis );
                    if( section != null ) {
                        sections.put( type, section );
                    }
                }
                gis.seek( pos + length );
            }
        }
        while( length != END_MARKER );

        seekNextSection( gis );
    }

    public <T extends Section> T get( SectionType type )
    {
        return (T) sections.get( type );
    }

    @Override
    public final int getLength()
    {
        return header.getLength();
    }

    public Header getHeader()
    {
        return header;
    }

}
