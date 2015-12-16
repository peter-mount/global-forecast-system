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

    @Override
    public final boolean readBoolean()
            throws IOException
    {
        return file.readBoolean();
    }

    @Override
    public final byte readByte()
            throws IOException
    {
        return file.readByte();
    }

    @Override
    public final int readUnsignedByte()
            throws IOException
    {
        return file.readUnsignedByte();
    }

    @Override
    public final short readShort()
            throws IOException
    {
        return file.readShort();
    }

    @Override
    public final int readUnsignedShort()
            throws IOException
    {
        return file.readUnsignedShort();
    }

    @Override
    public final char readChar()
            throws IOException
    {
        return file.readChar();
    }

    @Override
    public final int readInt()
            throws IOException
    {
        return file.readInt();
    }

    @Override
    public final long readLong()
            throws IOException
    {
        return file.readLong();
    }

    @Override
    public final float readFloat()
            throws IOException
    {
        return file.readFloat();
    }

    @Override
    public final double readDouble()
            throws IOException
    {
        return file.readDouble();
    }

    @Override
    public final String readLine()
            throws IOException
    {
        return file.readLine();
    }

    @Override
    public final String readUTF()
            throws IOException
    {
        return file.readUTF();
    }

}
