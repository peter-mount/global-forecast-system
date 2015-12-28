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
package onl.area51.gfs.mapviewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import onl.area51.gfs.mapviewer.cache.Tile;
import onl.area51.gfs.mapviewer.cache.TileCache;

/**
 *
 * @author peter
 */
public class TilePanel
        extends JPanel
{

    private static final int TILE_SIZE = 256;

    private Rectangle rb = new Rectangle( 0, 0, 1, 1 );
    private Insets ri = new Insets( 0, 0, 0, 0 );

    /**
     * Called when something changes like the Zoom or Underlying map
     */
    public void repaintMap()
    {
        SwingUtilities.invokeLater( () -> {
            int x = TILE_SIZE * (1 << TileCache.INSTANCE.getZoom());
            Dimension d = new Dimension( x, x );
            setMaximumSize( d );
            setPreferredSize( d );
            invalidate();
            //repaint();
        } );
    }

    @Override
    protected void paintComponent( Graphics g )
    {
        super.paintComponent( g );

        Color bg = getBackground();
        getBounds( rb );
        Insets riv = getInsets( ri );
        rb.translate( riv.left, riv.top );
        rb.width -= (riv.left + riv.right);
        rb.height -= (riv.top + riv.bottom);

        int zoom = TileCache.INSTANCE.getZoom();
        // Max tile on each azis (exclusive) so range is 0 .. maxXY-1
        int maxXY = 1 << zoom;

        // Calculate top left tile
        Rectangle visible = getVisibleRect();
        int lx = visible.x / TILE_SIZE;
        int ty = visible.y / TILE_SIZE;
        int rx = Math.min( lx + (visible.width / TILE_SIZE) + 2, maxXY );
        int by = Math.min( ty + (visible.height / TILE_SIZE) + 2, maxXY );

        Main.setStatus( "zoom=%s lx=%d ty=%d lx=%d rx=%d by=%d vx=%d vy=%d", zoom, lx, ty, lx, rx, by, visible.x, visible.y );

        Font font = Font.decode( Font.MONOSPACED );

        Shape ccache = g.getClip();
        //g.clipRect( rb.x, rb.y, rb.width, rb.height );
        //for( int y = ly, yp = TILE_SIZE * ly; yp < rb.y + rb.height && y < maxXY; yp += TILE_SIZE, y++ ) {
        //    for( int x = lx, xp = TILE_SIZE * lx; xp < rb.x + rb.width && x < maxXY; xp += TILE_SIZE, x++ ) {
        for( int y = ty, yp = TILE_SIZE * ty; y < by; yp += TILE_SIZE, y++ ) {
            for( int x = lx, xp = TILE_SIZE * lx; x < rx; xp += TILE_SIZE, x++ ) {
                Tile tile = TileCache.INSTANCE.getTile( x, y, t -> repaint(), ( t, e ) -> repaint() );
                if( tile != null ) {
                    if( tile.isImagePresent() ) {
                        g.drawImage( tile.getImage(), xp, yp, TILE_SIZE, TILE_SIZE, bg, this );
                    }
                    else {
                        g.setColor( Color.red );
                        g.drawRect( xp, yp, TILE_SIZE, TILE_SIZE );
                        g.drawLine( xp, yp, xp + TILE_SIZE, yp + TILE_SIZE );
                        g.drawLine( xp + TILE_SIZE, yp + TILE_SIZE, xp, yp );
                        g.setFont( font );
                        g.drawString( String.format( "(%d,%d,%d)", zoom, x, y ), xp + (TILE_SIZE >>> 2), yp + (TILE_SIZE >>> 1) );
                    }
                }
            }
        }
        g.setClip( ccache );
    }

}
