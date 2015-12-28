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
package onl.area51.gfs.mapviewer.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import onl.area51.gfs.grib2.Grib2File;
import onl.area51.gfs.grib2.io.Grib2FileFilter;
import onl.area51.gfs.grib2.section.DataSet;
import onl.area51.gfs.grib2.section.SectionType;
import onl.area51.gfs.grib2.section.product.AbstractForecastProduct;
import onl.area51.gfs.mapviewer.Main;
import onl.area51.gfs.mapviewer.MapViewer;

/**
 *
 * @author peter
 */
public class OpenGribAction
        extends AbstractAction
{

    private static final OpenGribAction INSTANCE = new OpenGribAction();

    public static OpenGribAction getInstance()
    {
        return INSTANCE;
    }

    private final JFileChooser fileChooser;

    private OpenGribAction()
    {
        super( "Open Grib File" );
        setEnabled( enabled );
        putValue( SHORT_DESCRIPTION, "Open Grib File" );
        putValue( ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke( KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK ) );

        fileChooser = new JFileChooser( System.getProperty( "user.home" ) );
        fileChooser.setMultiSelectionEnabled( false );

        Grib2FileFilter filter = new Grib2FileFilter();
        fileChooser.addChoosableFileFilter( filter );
        fileChooser.setFileFilter( filter );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if( fileChooser.showOpenDialog( Main.getFrame() ) == JFileChooser.OPEN_DIALOG ) {
            File file = fileChooser.getSelectedFile();

            Main.executeTask( () -> {
                MapViewer viewer = Main.getFrame();

                Grib2File grib = new Grib2File( file );
                Main.setGribFile( grib );

                // Replace the list model
                viewer.getDataSets().setModel(
                        grib.stream()
                        .filter( ds -> AbstractForecastProduct.isSupported( ds.get( SectionType.PRODUCT_DEFINITION ) ) )
                        .reduce( new DataSetModel(), DataSetModel::add, DataSetModel::combine )
                );
            } );
        }
        else if( e == null ) {
            // No Action then from Main so close
            System.exit( 0 );
        }
    }

}
