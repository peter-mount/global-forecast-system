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

/**
 *
 * @author peter
 */
public class Main
{

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
        java.awt.EventQueue.invokeLater( () -> new MapViewer().setVisible( true ) );
    }
}
