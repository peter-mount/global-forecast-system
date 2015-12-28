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
package onl.area51.gfs.mapviewer.action;

import java.util.Enumeration;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import onl.area51.gfs.grib2.Grib2File;
import onl.area51.gfs.grib2.section.DataSet;

/**
 *
 * @author peter
 */
public class DataSetModel
        extends DefaultListModel<DataSet>
{

    public DataSetModel()
    {
    }

    public DataSetModel add( DataSet ds )
    {
        addElement( ds );
        return this;
    }

    public static DataSetModel combine( DataSetModel a, DataSetModel b )
    {
        Enumeration<DataSet> en = b.elements();
        while( en.hasMoreElements() ) {
            a.addElement( en.nextElement() );
        }
        return a;
    }

    public void reset( Grib2File grib )
    {
        SwingUtilities.invokeLater( () -> {
            clear();
            grib.forEach( this::addElement );
        } );
    }
}
