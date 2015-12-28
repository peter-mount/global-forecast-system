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

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author peter
 */
public enum MapTileServer
{

    OPEN_STREET_MAP( "OpenStreetMap", "http://c.tile.openstreetmap.org/%z/%x/%y.png", MapTileLayout.OPEN_STREET_MAP, 0, 6 ),
    OPEN_CYCLE_MAP( "OpenCycleMap", "http://a.tile.opencyclemap.org/cycle/%z/%x/%y.png", MapTileLayout.OPEN_STREET_MAP, 0, 10 ),
    MAP_QUEST( "MapQuest", "http://otile1.mqcdn.com/tiles/1.0.0/osm/%z/%x/%y.jpg", MapTileLayout.OPEN_STREET_MAP, 0, 10 );

    private final String title;
    private final String uri;
    private final MapTileLayout layout;
    private final int minZoom;
    private final int maxZoom;

    public static ComboBoxModel newComboBoxModel()
    {
        return new DefaultComboBoxModel( MapTileServer.values() );
    }

    private MapTileServer( String title, String uri, MapTileLayout layout, int minZoom, int maxZoom )
    {
        this.title = title;
        this.uri = uri;
        this.layout = layout;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
    }

    @Override
    public String toString()
    {
        return title;
    }

    public MapTileLayout getLayout()
    {
        return layout;
    }

    public int getMinZoom()
    {
        return minZoom;
    }

    public int getMaxZoom()
    {
        return maxZoom;
    }

    public String getTileUrl( int z, int x, int y )
    {
        return uri.replace( "%z", String.valueOf( z ) )
                .replace( "%x", String.valueOf( x ) )
                .replace( "%y", String.valueOf( y ) );
    }
}
