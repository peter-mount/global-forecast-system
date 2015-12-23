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
public class GridPointComplexPacking
        extends AbstractPacking
{

    private final int gridSplittingMethod;
    private final int missingValueManagementUsed;
    private final int primaryMissingValueSubstitute;
    private final int secondaryMissingValueSubstitute;
    private final int numberGroups;
    private final int referenceGroupWidths;
    private final int numberBitsGroupWidths;
    private final int referenceGroupLengths;
    private final int incrementGroupLengths;
    private final int trueLengthLastGroup;
    private final int numberBitsScaledGroupLengths;

    public GridPointComplexPacking( GribInputStream gis )
            throws IOException
    {
        super( gis );

        gridSplittingMethod = gis.readUnsignedByte();

        missingValueManagementUsed = gis.readUnsignedByte();
        primaryMissingValueSubstitute = gis.readInt();
        secondaryMissingValueSubstitute = gis.readInt();

        numberGroups = gis.readInt();
        referenceGroupWidths = gis.readUnsignedByte();
        numberBitsGroupWidths = gis.readUnsignedByte();

        referenceGroupLengths = gis.readInt();
        incrementGroupLengths = gis.readUnsignedByte();

        trueLengthLastGroup = gis.readInt();
        numberBitsScaledGroupLengths = gis.readUnsignedByte();
    }

    public int getGridSplittingMethod()
    {
        return gridSplittingMethod;
    }

    public int getMissingValueManagementUsed()
    {
        return missingValueManagementUsed;
    }

    public int getPrimaryMissingValueSubstitute()
    {
        return primaryMissingValueSubstitute;
    }

    public int getSecondaryMissingValueSubstitute()
    {
        return secondaryMissingValueSubstitute;
    }

    public int getNumberGroups()
    {
        return numberGroups;
    }

    public int getReferenceGroupWidths()
    {
        return referenceGroupWidths;
    }

    public int getNumberBitsGroupWidths()
    {
        return numberBitsGroupWidths;
    }

    public int getReferenceGroupLengths()
    {
        return referenceGroupLengths;
    }

    public int getIncrementGroupLengths()
    {
        return incrementGroupLengths;
    }

    public int getTrueLengthLastGroup()
    {
        return trueLengthLastGroup;
    }

    public int getNumberBitsScaledGroupLengths()
    {
        return numberBitsScaledGroupLengths;
    }

}
