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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import onl.area51.gfs.grib2.io.GribFileInputStream;
import onl.area51.gfs.grib2.io.GribInputStream;
import onl.area51.gfs.grib2.section.DataSet;

/**
 *
 * @author peter
 */
public class Grib2File
{

    private final File file;
    private final GribInputStream gis;

    private final Map<Integer, DataSet> index = new HashMap<>();
    private final List<DataSet> entries = new ArrayList<>();

    public Grib2File( File file )
            throws IOException
    {
        this.file = file;
        gis = new GribFileInputStream( file );
        
        while( gis.position() < gis.length() ) {
            DataSet dataSet = new DataSet( gis );
            entries.add( dataSet );
            index.put( entries.size(), dataSet );
        }
    }

    public File getFile()
    {
        return file;
    }

    public int size()
    {
        return entries.size();
    }

    public DataSet get( int id )
    {
        return entries.get( id );
    }

    public Map<Integer, DataSet> getIndex()
    {
        return index;
    }

    public void forEach( Consumer<DataSet> action )
    {
        entries.forEach( action );
    }

}
