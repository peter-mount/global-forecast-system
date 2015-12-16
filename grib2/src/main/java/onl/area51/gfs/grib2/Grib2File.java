/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onl.area51.gfs.grib2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        System.out.println( "File length " + gis.length() );

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

}
