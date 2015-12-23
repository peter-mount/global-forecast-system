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
import onl.area51.gfs.grib2.section.Section;

/**
 *
 * @author peter
 */
public class DataRepresentation
        extends Section
{

    private final int noDataPoints;
    private final TemplateType templateType;
    private final Packing packing;

    public DataRepresentation( GribInputStream gis )
            throws IOException
    {
        super( gis );

        noDataPoints = gis.readInt();

        final int type = gis.readShort();
        templateType = TemplateType.lookup( type );
        if( templateType == TemplateType.RESERVED || templateType == TemplateType.MISSING ) {
            gis.seek( getPos() );
            gis.printHeader( 16 );
            throw new UnsupportedOperationException( "Unsupported data type " + type );
        }

        // Now decode the template specific data
        packing = templateType.getConstructor().apply( gis );
        seekNextSection( gis );
    }

    public int getNoDataPoints()
    {
        return noDataPoints;
    }

    public TemplateType getTemplateType()
    {
        return templateType;
    }

    public <P extends Packing> P getPacking()
    {
        return (P) packing;
    }

}
