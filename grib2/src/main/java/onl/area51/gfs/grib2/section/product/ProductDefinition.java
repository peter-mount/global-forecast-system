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
package onl.area51.gfs.grib2.section.product;

import java.io.IOException;
import onl.area51.gfs.grib2.io.GribInputStream;
import onl.area51.gfs.grib2.section.Header;
import onl.area51.gfs.grib2.section.Section;

/**
 *
 * @author peter
 */
public abstract class ProductDefinition
        extends Section
{

    private final int noCoordAfterTemplate;
    private final ProductDefinitionTemplate template;

    public static <T extends ProductDefinition> T create( Header header, GribInputStream gis )
            throws IOException
    {
        final long pos = gis.position();

        // Get the template from octets 8 & 9
        gis.seek( pos + 7 );
        final int templateId = gis.readUnsignedShort();
        final ProductDefinitionTemplate template = ProductDefinitionTemplate.lookup( templateId );
        if( template == ProductDefinitionTemplate.RESERVED ) {
            gis.seek( pos );
            gis.printHeader( 16 );
            throw new UnsupportedOperationException( "Reserved template " + templateId + " requested " + header );
        }

        gis.seek( pos );
        final T grid = (T) template.getConstructor().apply( header, gis );
        grid.seekNextSection( gis );
        return grid;
    }

    protected ProductDefinition( GribInputStream gis )
            throws IOException
    {
        super( gis );
        noCoordAfterTemplate = gis.readUnsignedShort();
        template = ProductDefinitionTemplate.lookup( gis.readUnsignedShort() );
        // 10-xx definition Template
        // xx+1-nn optional list of coordinate values
    }

    public ProductDefinitionTemplate getTemplate()
    {
        return template;
    }

    public int getNoCoordAfterTemplate()
    {
        return noCoordAfterTemplate;
    }

}
