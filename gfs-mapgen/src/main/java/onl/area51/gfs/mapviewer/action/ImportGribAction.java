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
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import onl.area51.gfs.grib2.io.Grib2FileFilter;
import onl.area51.gfs.mapviewer.Main;
import onl.area51.gfs.mapviewer.SelectFTPFile;
import onl.area51.mapgen.swing.SwingUtils;
import onl.area51.mapgen.swing.io.ProgressDialog;
import org.apache.commons.net.ftp.FTPFile;
import uk.trainwatch.io.ftp.FTPClient;
import uk.trainwatch.io.ftp.FTPClientBuilder;

/**
 *
 * @author peter
 */
public class ImportGribAction
        extends AbstractAction
{

    private static final String SERVER = "ftp.ncep.noaa.gov";
    private static final String DIR = "/pub/data/nccf/com/gfs/prod";

    private static final ImportGribAction INSTANCE = new ImportGribAction();

    private final JFileChooser fileChooser;

    public static ImportGribAction getInstance()
    {
        return INSTANCE;
    }

    public ImportGribAction()
    {
        super( "Import Grib File" );
        setEnabled( enabled );
        putValue( SHORT_DESCRIPTION, "Import Grib File" );
        putValue( ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke( KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK ) );

        fileChooser = new JFileChooser( System.getProperty( "user.home" ) );
        fileChooser.setMultiSelectionEnabled( false );

        Grib2FileFilter filter = new Grib2FileFilter();
        fileChooser.addChoosableFileFilter( filter );
        fileChooser.setFileFilter( filter );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        SwingUtilities.invokeLater( () -> {
            if( JOptionPane.showConfirmDialog( Main.getFrame(), "Retrieve latest GRIB file from NOAA?", "Import GRIB File", JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION ) {
                SwingUtils.executeTask( this::locateGribFile );
            }
        } );
    }

    /**
     * Locate the current Grib file
     */
    private void locateGribFile()
            throws Exception
    {
        // Note: Don't put this in try-resources as it lives longer than the try block
        FTPClient client = new FTPClientBuilder()
                .logger( Main::setStatus )
                .build();

        try {
            Main.setStatus( "Connecting to NOAA..." );
            client.connect( SERVER );

            Main.setStatus( "Logging in..." );
            client.login( "anonymous", "gribuser@" );

            client.changeWorkingDirectory( DIR );

            Main.setStatus( "Retrieving directory listing" );

            SelectFTPFile.select( client, this::viewDirectory, "Select GFS Run",
                                  // limit to 20 files, most recent at the top
                                  client.listDirectories()
                                  .stream()
                                  .sorted( ( a, b ) -> b.getName().compareToIgnoreCase( a.getName() ) )
                                  .limit( 20 )
                                  .collect( Collectors.toList() ),
                                  f -> f.getName().startsWith( "gfs." )
            );
        }
        catch( Exception ex ) {
            // Make certain the client is closed correctly
            client.close();
            throw ex;
        }
    }

    /**
     * View the GFS run files
     * <p>
     * @param client
     * @param dir
     *               <p>
     * @throws Exception
     */
    private void viewDirectory( FTPClient client, FTPFile dir )
            throws Exception
    {
        Main.setStatus( "Retrieving %s directory listing...", dir.getName() );
        client.changeWorkingDirectory( DIR + "/" + dir.getName() );

        SelectFTPFile.select( client, this::selectDestName, "Select GFS Grib File",
                              client.listFiles(),
                              f -> f.isFile() && f.getName().matches( "^gfs\\.t[0-9]{2}z\\.pgrb2\\.[0-2]p[0-9]{2}\\.f[0-9]{3}$" )
        );
    }

    private void selectDestName( FTPClient client, FTPFile remoteFile )
            throws Exception
    {
        SwingUtilities.invokeLater( () -> {
            // Default to the remote file name
            fileChooser.setSelectedFile( new File( remoteFile.getName() ) );

            if( fileChooser.showSaveDialog( Main.getFrame() ) == JFileChooser.APPROVE_OPTION ) {
                File localFile = fileChooser.getSelectedFile();

                ProgressDialog.copy( remoteFile.getName(),
                                     remoteFile.getSize(),
                                     () -> client.retrieveFileStream( remoteFile.getName() ),
                                     () -> {
                                         Main.setStatus( "Disconnecting, transfer complete." );
                                         SwingUtils.executeTask( client::close );
                                         OpenGribAction.getInstance().open( localFile );
                                     },
                                     () -> {
                                         Main.setStatus( "Disconnecting, transfer failed" );
                                         SwingUtils.executeTask( client::close );
                                     },
                                     localFile.toPath(),
                                     StandardCopyOption.REPLACE_EXISTING );
            }
            else {
                Main.setStatus( "Disconnecting, no transfer performed." );
                SwingUtils.executeTask( client::close );
            }

        }
        );
    }
}
