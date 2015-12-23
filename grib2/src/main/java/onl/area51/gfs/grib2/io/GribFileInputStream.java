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
package onl.area51.gfs.grib2.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * A {@link GribInputStream} of a {@link File}
 * <p>
 * @author peter
 */
public class GribFileInputStream
        extends AbstractGribInputStream
        implements GribInputStream
{

    private final RandomAccessFile file;

    public GribFileInputStream( File file )
            throws FileNotFoundException
    {
        this( new RandomAccessFile( file, "r" ) );
    }

    public GribFileInputStream( RandomAccessFile file )
    {
        this.file = file;
    }

    @Override
    public long position()
            throws IOException
    {
        return file.getFilePointer();
    }

    @Override
    public int read()
            throws IOException
    {
        return file.read();
    }

    @Override
    public int read( byte[] b, int off, int len )
            throws IOException
    {
        return file.read( b, off, len );
    }

    @Override
    public int read( byte[] b )
            throws IOException
    {
        return file.read( b );
    }

    @Override
    public final void readFully( byte[] b )
            throws IOException
    {
        file.readFully( b );
    }

    @Override
    public final void readFully( byte[] b, int off, int len )
            throws IOException
    {
        file.readFully( b, off, len );
    }

    @Override
    public int skipBytes( int n )
            throws IOException
    {
        return file.skipBytes( n );
    }

    @Override
    public void seek( long pos )
            throws IOException
    {
        file.seek( pos );
    }

    @Override
    public long length()
            throws IOException
    {
        return file.length();
    }

    @Override
    public void close()
            throws IOException
    {
        file.close();
    }

}
