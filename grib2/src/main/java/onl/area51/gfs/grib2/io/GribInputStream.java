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

    public int read()
            throws IOException;

    public int read( byte[] b, int off, int len )
            throws IOException;

    public int read( byte[] b )
            throws IOException;

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
}
