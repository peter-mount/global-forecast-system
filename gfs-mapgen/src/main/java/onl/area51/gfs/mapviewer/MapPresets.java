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
import onl.area51.mapgen.tilecache.MapPreset;

/**
 *
 * @author peter
 */
public enum MapPresets
        implements MapPreset
{

    UNSET( "Select preset", -1, -1, -1 ),
    WORLD( "World", 2, 81, 36 ),
    WEST_EUROPE_ATLANTIC( "Western Europe + Atlantic", 4, 1524, 965 ),
    UK5( "United Kingdom Zoom 5", 5, 3531, 2225 ),
    UK6_SOUTH( "United Kingdom Zoom 6 - South", 6, 7583, 5028 ),
    UK6_NORTH( "United Kingdom Zoom 6 - North", 6, 7583, 4653 ),
    ENGLAND_SOUTH( "England South", 7, 15762, 10535 );

    public static ComboBoxModel<MapPresets> getComboBoxModel()
    {
        return new DefaultComboBoxModel<>( values() );
    }

    private final String label;
    private final int zoom;
    private final int x, y;

    private MapPresets( String label, int zoom, int x, int y )
    {
        this.label = label;
        this.zoom = zoom;
        this.x = x;
        this.y = y;
    }

    @Override
    public String getLabel()
    {
        return label;
    }

    @Override
    public int getX()
    {
        return x;
    }

    @Override
    public int getY()
    {
        return y;
    }

    public int getZoom()
    {
        return zoom;
    }

    @Override
    public String toString()
    {
        return getLabel();
    }

}
