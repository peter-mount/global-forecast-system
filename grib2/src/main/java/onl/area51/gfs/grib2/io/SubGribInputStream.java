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

import java.io.EOFException;
import java.io.IOException;

/**
 *
 * @author peter
 */
public class SubGribInputStream
        extends AbstractGribInputStream
        implements GribInputStream
{

    private static final int BUFFER_SIZE = 1024;

    private final GribInputStream gis;
    private final long dataOffset;
    private final long length;

    private byte[] buffer = null;
    private int cpos = -1;
    private int size = -1;
    // Position of the GribInputStream implementationj
    private int pos = 0;

    public SubGribInputStream()
    {
        this.gis = null;
        this.dataOffset = 0;
        this.length = 0;
    }

    public SubGribInputStream( GribInputStream gis, long dataOffset, long length )
    {
        this.gis = gis;
        this.dataOffset = dataOffset;
        this.length = length;
    }

    @Override
    public void close()
            throws IOException
    {
        buffer = null;
        cpos = -1;
        size = -1;
        pos = (int) length;
    }

    /**
     * Is the byte at position pos in the buffer
     * <p>
     * @param pos position within the dataset
     * <p>
     * @return true if we have it in the buffer
     */
    public boolean isBuffered( int pos )
    {
        return isBuffered( pos, 1 );
    }

    /**
     * Is the block of bytes is buffered or not
     * <p>
     * @param pos position within the dataset
     * @param len length of the block
     * <p>
     * @return true if buffered, false if not
     * <p>
     * @throws IllegalArgumentException if pos is negative, length is &lt; 1 or &gt; the buffer size or if (pos+length) is larger than the dataset
     */
    public boolean isBuffered( int pos, int len )
    {
        if( pos < 0 || (pos + len) > this.length ) {
            throw new IllegalArgumentException( "Invalid position " + pos + "," + len + " valid 0," + this.length );
        }
        if( len < 1 || len > BUFFER_SIZE ) {
            throw new IllegalArgumentException( "Invalid length " + len );
        }
        return buffer != null && cpos > -1 && size > -1 && pos >= cpos && (pos + len) <= (cpos + size);
    }

    /**
     * Ensures we have the required bytes in the buffer
     * <p>
     * @param pos
     * @param len
     *            <p>
     * @throws IOException
     */
    @Override
    protected void ensure( int len )
            throws IOException
    {
        if( pos + len >= length ) {
            throw new EOFException();
        }

        if( buffer == null || pos < cpos || (pos - cpos + len) >= size ) {
            if( buffer == null ) {
                buffer = new byte[BUFFER_SIZE];
            }
            cpos = pos;
            size = Math.min( BUFFER_SIZE, (int) (this.length - pos - len) );
            gis.seek( dataOffset + cpos );
            gis.readFully( buffer, 0, size );
        }
    }

    @Override
    public long position()
            throws IOException
    {
        return pos;
    }

    @Override
    public long length()
            throws IOException
    {
        return length;
    }

    @Override
    public int read()
            throws IOException
    {
        ensure( 1 );

        if( pos + dataOffset >= length ) {
            throw new EOFException();
        }

        int v = (int) buffer[pos - cpos];
        pos++;
        return v;
    }

    @Override
    public int read( byte[] b, int off, int len )
            throws IOException
    {
        if( pos + dataOffset + len >= length ) {
            throw new EOFException();
        }

        if( len <= BUFFER_SIZE ) {
            ensure( len );
            System.arraycopy( buffer, pos - cpos, b, off, len );
        }
        else {
            // Do a raw read as its too big for the buffer
            gis.readFully( b, off, len );
        }
        pos += len;
        return len;
    }

    @Override
    public void seek( long position )
            throws IOException
    {
        if( position < 0 || position > length ) {
            throw new IllegalArgumentException( "Invalid position " + position + " not in 0<=position<" + length );
        }

        pos = (int) position;
    }

    @Override
    public void readFully( byte[] b, int off, int len )
            throws IOException
    {
        read( b, off, len );
    }

    @Override
    public int skipBytes( int n )
            throws IOException
    {
        seek( pos + n );
        return n;
    }

}
