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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import onl.area51.gfs.grib2.Grib2File;
import onl.area51.mapgen.swing.SwingUtils;

/**
 *
 * @author peter
 */
public class Main
{

    private static MapViewer frame;
    private static Grib2File gribFile;

    public static void main( String args[] )
    {
        /* Set the Nimbus look and feel
         *
         *If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for( javax.swing.UIManager.LookAndFeelInfo info: javax.swing.UIManager.getInstalledLookAndFeels() ) {
                if( "Nimbus".equals( info.getName() ) ) {
                    javax.swing.UIManager.setLookAndFeel( info.getClassName() );
                    break;
                }
            }
        }
        catch( ClassNotFoundException |
               InstantiationException |
               IllegalAccessException |
               javax.swing.UnsupportedLookAndFeelException ex ) {
            java.util.logging.Logger.getLogger( MapViewer.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }

        /* Create and display the form */
        SwingUtilities.invokeLater( () -> {
            frame = new MapViewer();
            frame.setVisible( true );
        } );
    }

    public static MapViewer getFrame()
    {
        return (MapViewer)SwingUtils.getFrame();
    }

    public static Grib2File getGribFile()
    {
        return gribFile;
    }

    public static void setGribFile( Grib2File gribFile )
    {
        if( Main.gribFile != null ) {
            try {
                Main.gribFile.close();
            }
            catch( IOException ex ) {
                Logger.getLogger( Main.class.getName() ).log( Level.SEVERE, null, ex );
            }
        }
        Main.gribFile = gribFile;
    }

    public static void setStatus( String s )
    {
        frame.setStatus( s );
    }

    public static void setStatus( String s, Object... args )
    {
        setStatus( String.format( s, args ) );
    }

}
