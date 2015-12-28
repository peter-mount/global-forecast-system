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
package onl.area51.gfs.grib2;

import java.io.File;
import java.io.IOException;
import onl.area51.gfs.grib2.section.BitMap;
import onl.area51.gfs.grib2.section.DataSet;
import onl.area51.gfs.grib2.section.Header;
import onl.area51.gfs.grib2.section.Identification;
import onl.area51.gfs.grib2.section.Section;
import onl.area51.gfs.grib2.section.SectionType;
import onl.area51.gfs.grib2.section.data.DataRepresentation;
import onl.area51.gfs.grib2.section.product.AbstractForecastProduct;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peter
 */
public class Grib2FileTest
{

    @Test
    public void open()
            throws IOException
    {
        Grib2File file = new Grib2File( new File( "/home/peter/Downloads/gfs.t06z.pgrb2.0p25.f012" ) );

        System.out.println( "Entry count " + file.size() );

        for( int i = 0; i < file.size(); i++ ) {
            DataSet dataSet = file.get( i );
            Header h = dataSet.getHeader();
            Identification id = dataSet.get( SectionType.IDENTIFICATION );

            System.out.printf( "%d:%d:d=%s:%s\n", i + 1, dataSet.getPos(), id.getDateTime(), dataSet );
            System.out.println( id );

            // Analysis & Average
            Section sect = dataSet.get( SectionType.PRODUCT_DEFINITION );
            if( sect instanceof AbstractForecastProduct ) {
                AbstractForecastProduct prod = (AbstractForecastProduct) sect;
                System.out.println( prod.getParameterCategory() + " " + prod.getProduct() );
            }

            DataRepresentation rep = dataSet.get( SectionType.DATA_REPRESENTATION );
            if( rep != null ) {
                System.out.printf( "DataRep type=%s noPoints=%d\n", rep.getTemplateType(), rep.getNoDataPoints() );
            }

            BitMap bitMap = dataSet.get( SectionType.BIT_MAP );
            if( bitMap != null ) {
                System.out.printf( "BitMap type %s\n", bitMap.isNoBitMap() );
            }

            System.out.println();

//            if( i > 10 ) {
//                fail( "Break" );
//            }
        }

//        fail( "The test case is a prototype." );
    }

}
