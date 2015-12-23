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
package onl.area51.gfs.grib2.section.data;

import java.io.IOException;
import onl.area51.gfs.grib2.io.GribInputStream;

/**
 *
 * @author peter
 */
public class AbstractPacking
        implements Packing
{

    private final float referenceValue;
    private final int binaryScaleFactor;
    private final int decimalScaleFactor;
    private final int noBits;
    private final OriginalFieldValue typeOriginalFieldValues;

    protected AbstractPacking( GribInputStream gis )
            throws IOException
    {
        referenceValue = gis.readFloat();
        binaryScaleFactor = gis.readShort();
        decimalScaleFactor = gis.readShort();
        noBits = gis.readUnsignedByte();
        typeOriginalFieldValues = OriginalFieldValue.lookup( gis.readUnsignedByte() );
    }

    public float getReferenceValue()
    {
        return referenceValue;
    }

    public int getBinaryScaleFactor()
    {
        return binaryScaleFactor;
    }

    public int getDecimalScaleFactor()
    {
        return decimalScaleFactor;
    }

    public int getNoBits()
    {
        return noBits;
    }

    public OriginalFieldValue getTypeOriginalFieldValues()
    {
        return typeOriginalFieldValues;
    }

}
