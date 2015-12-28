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
package onl.area51.gfs.mapviewer.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An input stream which can issue a notification when progress passes specific points
 * <p>
 * @author peter
 */
public class ProgressOutputStream
        extends OutputStream
{

    private final OutputStream stream;
    private final ProgressListener listener;
    private final long length;
    private final long step;
    private long pos;
    private long next;

    public ProgressOutputStream( OutputStream stream, long length, ProgressListener listener )
    {
        this.stream = stream;
        this.length = length;
        this.listener = listener;

        long s = length / 100L;
        if( s < 1 ) {
            step = 1L;
            next = length;
        }
        else {
            step = s;
            next = step;
        }

        pos = 0;
    }

    @Override
    public void write( int b )
            throws IOException
    {
        if( pos == 0 ) {
            fire();
        }
        stream.write( b );
        step( 1 );
    }

    @Override
    public void write( byte[] b )
            throws IOException
    {
        write( b, 0, b.length );
    }

    @Override
    public void write( byte[] b, int off, int len )
            throws IOException
    {
        if( pos == 0 ) {
            fire();
        }
        stream.write( b, off, len );
        step( len );
    }

    private int step( int r )
    {
        pos += r;
        while( pos > next ) {
            fire();
            next += step;
        }
        return r;
    }

    private void fire()
    {
        listener.progressUpdate( (int) (pos * 100L / length), pos, length );
    }

}
