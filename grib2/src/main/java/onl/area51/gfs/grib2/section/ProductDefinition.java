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
public class ProductDefinition
        extends Section
{

    private final int noCoordAfterTemplate;
    private final int templateNo;

    public ProductDefinition( GribInputStream gis )
            throws IOException
    {
        super( gis );
        noCoordAfterTemplate = gis.readUnsignedShort();
        templateNo = gis.readUnsignedShort();
        // 10-xx definition Template
        // xx+1-nn optional list of coordinate values
        seekNextSection( gis );
    }

    public int getTemplateNo()
    {
        return templateNo;
    }

    public int getNoCoordAfterTemplate()
    {
        return noCoordAfterTemplate;
    }

}
