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
 *
 * @author peter
 */
public class BitMap
        extends Section
{

    private final int bitMapIndicator;

    public BitMap( GribInputStream gis )
            throws IOException
    {
        super( gis );

        bitMapIndicator = gis.readUnsignedByte();
        seekNextSection( gis );
    }

    /**
     * Bit map type.
     * <p>
     * 0 A bit map applies to this product and is specified in this section.
     * <p>
     * 1-253 A bit map pre-determined by the orginating/generating center applies to this product and is not specified in this section.
     * <p>
     * 254 A bit map previously defined in the same GRIB2 message applies to this product.
     * <p>
     * 255 A bit map does not apply to this product.
     * <p>
     * Note: If this returns a non-zero value then this section has no data from octets 7-nn, i.e. the section size is 6 bytes.
     * <p>
     * @return
     */
    public int getBitMapIndicator()
    {
        return bitMapIndicator;
    }

    /**
     * Convenience, true if a bit map applies to this product and is specified in this section. Same as {@code getBitMapIndicator()==0};
     * <p>
     * @return
     */
    public boolean isBitMapSpecified()
    {
        return bitMapIndicator == 0;
    }

    /**
     * Convenience, returns true if a bitMap applies to this product Same as {@code !isBitMapSpecified() && !isNoBitMap()};
     * <p>
     * @return
     */
    public boolean isBitMap()
    {
        return !isBitMapSpecified() && !isNoBitMap();
    }

    /**
     * Convenience, true if a bit map does not apply to this product. Same as {@code getBitMapIndicator()==255};
     * <p>
     * @return
     */
    public boolean isNoBitMap()
    {
        return bitMapIndicator == 255;
    }
}
