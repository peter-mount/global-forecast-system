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

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 *
 * @author peter
 */
public abstract class AbstractGribInputStream
        implements GribInputStream
{

    protected void ensure( int len )
            throws IOException
    {
    }

    @Override
    public final boolean readBoolean()
            throws IOException
    {
        ensure( 1 );
        int ch = this.read();
        if( ch < 0 ) {
            throw new EOFException();
        }
        return ch != 0;
    }

    @Override
    public final byte readByte()
            throws IOException
    {
        ensure( 1 );
        int ch = this.read();
        if( ch < 0 ) {
            throw new EOFException();
        }
        return (byte) (ch);
    }

    @Override
    public final int readUnsignedByte()
            throws IOException
    {
        ensure( 1 );
        int ch = this.read();
        if( ch < 0 ) {
            throw new EOFException();
        }
        return ch;
    }

    @Override
    public final short readShort()
            throws IOException
    {
        ensure( 2 );
        int ch1 = this.read();
        int ch2 = this.read();
        if( (ch1 | ch2) < 0 ) {
            throw new EOFException();
        }
        return (short) ((ch1 << 8) + ch2);
    }

    @Override
    public final int readUnsignedShort()
            throws IOException
    {
        ensure( 2 );
        int ch1 = this.read();
        int ch2 = this.read();
        if( (ch1 | ch2) < 0 ) {
            throw new EOFException();
        }
        return (ch1 << 8) + ch2;
    }

    @Override
    public final char readChar()
            throws IOException
    {
        ensure( 2 );
        int ch1 = this.read();
        int ch2 = this.read();
        if( (ch1 | ch2) < 0 ) {
            throw new EOFException();
        }
        return (char) ((ch1 << 8) + ch2);
    }

    @Override
    public final int readInt()
            throws IOException
    {
        ensure( 4 );
        int ch1 = this.read();
        int ch2 = this.read();
        int ch3 = this.read();
        int ch4 = this.read();
        if( (ch1 | ch2 | ch3 | ch4) < 0 ) {
            throw new EOFException();
        }
        return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4;
    }

    @Override
    public final long readLong()
            throws IOException
    {
        ensure( 8 );
        return ((long) (readInt()) << 32) + (readInt() & 0xFFFFFFFFL);
    }

    @Override
    public final float readFloat()
            throws IOException
    {
        ensure( 4 );
        return Float.intBitsToFloat( readInt() );
    }

    @Override
    public final double readDouble()
            throws IOException
    {
        ensure( 8 );
        return Double.longBitsToDouble( readLong() );
    }

    @Override
    public final String readLine()
            throws IOException
    {
        final StringBuilder input = new StringBuilder();
        int c = -1;
        boolean eol = false;
        while( !eol ) {
            switch( c = read() ) {
                case -1:
                case '\n':
                    eol = true;
                    break;
                case '\r':
                    eol = true;
                    final long cur = position();
                    if( (read()) != '\n' ) {
                        seek( cur );
                    }
                    break;
                default:
                    input.append( (char) c );
                    break;
            }
        }
        if( (c == -1) && (input.length() == 0) ) {
            return null;
        }
        return input.toString();
    }

    @Override
    public final String readUTF()
            throws IOException
    {
        return DataInputStream.readUTF( this );
    }
    //</editor-fold>

}
