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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import onl.area51.gfs.mapviewer.MapTileServer;

/**
 * A simple in memory/disk based cache used for map tiles
 * <p>
 * @author peter
 */
public enum TileCache
{

    INSTANCE;

    private static final Logger LOG = Logger.getLogger( TileCache.class.getName() );
    private final Map<Long, Tile> tiles = new ConcurrentHashMap<>();

    private MapTileServer server = MapTileServer.OPEN_STREET_MAP;
    private int zoom = 2;

    private final Executor executor;

    private TileCache()
    {
        executor = Executors.newSingleThreadExecutor();
    }

    public void setServer( MapTileServer server )
    {
        this.server = server;
        tiles.clear();
    }

    public MapTileServer getServer()
    {
        return server;
    }

    public int getZoom()
    {
        return zoom;
    }

    public void setZoom( int zoom )
    {
        if( this.zoom != zoom ) {
            this.zoom = zoom;
            tiles.clear();
        }
    }

    public void clear()
    {
        tiles.clear();
    }

    /**
     * Retrieve a tile from the cache
     * <p>
     * @param x       X
     * @param y       Y
     * @param success Consumer to be called when a tile has been retrieved from an external source
     * @param fail    Consumer to be called when a tile retrieval has failed
     * <p>
     * @return Tile or null if x,y is invalid for this zoom
     */
    public Tile getTile( int x, int y, Consumer<Tile> success, BiConsumer<Tile, Exception> fail )
    {
        final int zoom = this.zoom;
        final long max = 1L << zoom;
        final long lx = (long) x, ly = (long) y;
        if( lx < 0 || lx >= max || ly < 0 || ly >= max ) {
            return null;
        }

        final Tile tile = tiles.computeIfAbsent( lx * max + ly, index -> new Tile( zoom, x, y ) );

        // If no image but not pending (retrieving) or in error then background thread to retrieve it
        if( !tile.isImagePresent() && !tile.isError() && !tile.isPending() ) {
            executor.execute( () -> {

                if( !tile.isImagePresent() && !tile.isError() ) {
                    loadTile( tile );
                }

                if( !tile.isImagePresent() && !tile.isError() && !tile.isPending() ) {
                    try {
                        retrieveTile( tile );
                        loadTile( tile );
                    }
                    catch( IOException ex ) {
                        LOG.log( Level.SEVERE, ex, () -> tile.toString() );
                        SwingUtilities.invokeLater( () -> fail.accept( tile, ex ) );
                    }
                }

                if( tile.isImagePresent() ) {
                    SwingUtilities.invokeLater( () -> success.accept( tile ) );
                }
                else {
                    SwingUtilities.invokeLater( () -> fail.accept( tile, null ) );
                }
            } );
        }

        return tile;
    }

    /**
     * Get the MD5 of a string
     * <p>
     * @param s
     *          <p>
     * @return
     */
    public byte[] md5( String s )
    {
        try {
            return MessageDigest.getInstance( "MD5" ).digest( s.getBytes() );
        }
        catch( NoSuchAlgorithmException ex ) {
            LOG.log( Level.SEVERE, null, ex );
            throw new IllegalStateException( ex );
        }
    }

    private String hex( byte b )
    {
        String p = Integer.toHexString( Byte.toUnsignedInt( b ) );
        if( p.length() == 1 ) {
            p = '0' + p;
        }
        return p;
    }

    private Path getPath( final Tile tile )
    {
        String name = String.join( "_",
                                   server.toString(),
                                   String.valueOf( tile.getZ() ),
                                   String.valueOf( tile.getX() ),
                                   String.valueOf( tile.getY() ) ) + ".png";

        // The MediaWiki way of limiting cache directory size
        byte b[] = md5( name );
        String p1 = hex( b[0] ), p2 = hex( b[1] ).substring( 0, 1 );

        Path path = Paths.get( System.getProperty( "user.home" ), ".area51", "tileCache", p1.substring( 0, 1 ), p1, p1 + p2, name );

        return path;
    }

    private void loadTile( final Tile tile )
    {
        System.out.println( "Load tile " + getPath( tile ) );
        try {
            try( InputStream is = Files.newInputStream( getPath( tile ), StandardOpenOption.READ ) ) {
                tile.setImage( ImageIO.read( is ) );
            }
        }
        catch( Exception ex ) {
            tile.setImage( null );
        }
    }

    private void retrieveTile( final Tile tile )
            throws IOException
    {
        System.out.println( "Load tile " + getPath( tile ) );
        try {
            tile.setPending( true );
            Path path = getPath( tile );

            URL url = new URL( server.getTileUrl( tile.getZ(), tile.getX(), tile.getY() ) );
            LOG.log( Level.INFO, () -> "Retrieving " + url );

            URLConnection con = url.openConnection();
            con.connect();
            try( InputStream is = con.getInputStream() ) {
                path.getParent().toFile().mkdirs();
                Files.copy( is, path, StandardCopyOption.REPLACE_EXISTING );
            }
        }
        catch( IOException ex ) {
            LOG.log( Level.SEVERE, null, ex );
            tile.setError( true );
            throw ex;
        }
        finally {
            LOG.log( Level.SEVERE, "Pending false" );
            tile.setPending( false );
        }
    }
}
