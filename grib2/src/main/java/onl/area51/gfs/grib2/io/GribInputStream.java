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

import java.io.Closeable;
import java.io.DataInput;
import java.io.IOException;

/**
 *
 * @author peter
 */
public interface GribInputStream
        extends DataInput,
                Closeable
{

    public long position()
            throws IOException;

    long length()
            throws IOException;

    int read()
            throws IOException;

    int read( byte[] b, int off, int len )
            throws IOException;

    default int read( byte[] b )
            throws IOException
    {
        return read( b, 0, b.length );
    }

    @Override
    default void readFully( byte b[] )
            throws IOException
    {
        readFully( b, 0, b.length );
    }

    void seek( long position )
            throws IOException;

    /**
     * Reads a char array from a series of bytes
     * <p>
     * @param c destination array
     * <p>
     * @throws IOException
     */
    default void readByteChar( char c[] )
            throws IOException
    {
        readByteChar( c, 0, c.length );
    }

    /**
     * Reads a char array from a series of bytes
     * <p>
     * @param c      destination array
     * @param offset offset in c
     * @param len    number of bytes to read
     * <p>
     * @throws IOException
     */
    default void readByteChar( char c[], int offset, int len )
            throws IOException
    {
        for( int i = 0; i < len; i++ ) {
            c[offset + i] = (char) readByte();
        }
    }

    /**
     * Reads a String from a series of bytes
     * <p>
     * @param len Number of characters to read
     * <p>
     * @return String
     * <p>
     * @throws IOException
     */
    default String readByteString( int len )
            throws IOException
    {
        char c[] = new char[len];
        readByteChar( c );
        return String.valueOf( c );
    }

    /**
     * Debugging utility, write 2 lines to {@link System#out}, the first the index from the current position, the second the byte value in hex
     * <p>
     * @param gis
     *            <p>
     * @throws IOException
     */
    default void printHeader()
            throws IOException
    {
        printHeader( 5 );
    }

    /**
     * Debugging utility, write 2 lines to {@link System#out}, the first the index from the current position, the second the byte value in hex
     * <p>
     * @param size number of bytes to debug
     * <p>
     * @throws IOException
     */
    default void printHeader( int size )
            throws IOException
    {
        final long pos = position();
        try {
            for( int i = 0; i < size; i++ ) {
                System.out.printf( "%02x ", i );
            }
            System.out.println();
            for( int i = 0; i < size; i++ ) {
                System.out.printf( "%02x ", readUnsignedByte() );
            }
            System.out.println();
        }
        finally {
            seek( pos );
        }
    }

}
