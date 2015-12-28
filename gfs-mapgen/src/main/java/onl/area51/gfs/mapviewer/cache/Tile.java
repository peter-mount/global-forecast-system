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
package onl.area51.gfs.mapviewer.cache;

import java.awt.Image;

/**
 *
 * @author peter
 */
public class Tile
{

    private final int z, x, y;
    private Image image;
    private boolean error;
    private boolean pending;

    public Tile( int z, int x, int y )
    {
        this.z = z;
        this.x = x;
        this.y = y;
    }

    public int getZ()
    {
        return z;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public synchronized boolean isImagePresent()
    {
        return image != null;
    }

    public synchronized Image getImage()
    {
        return image;
    }

    public synchronized void setImage( Image image )
    {
        this.image = image;
    }

    public synchronized boolean isError()
    {
        return error;
    }

    public synchronized void setError( boolean error )
    {
        this.error = error;
    }

    public synchronized void setPending( boolean pending )
    {
        this.pending = pending;
    }

    public synchronized boolean isPending()
    {
        return pending;
    }

    @Override
    public String toString()
    {
        return "Tile[x=" + x + ", y=" + y + ", imagePresent=" + isImagePresent() + ']';
    }

}
