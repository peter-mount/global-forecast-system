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

import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.swing.SwingUtilities;
import onl.area51.gfs.mapviewer.Main;
import uk.trainwatch.io.IOSupplier;

/**
 *
 * @author peter
 */
public class ProgressDialog
        extends javax.swing.JDialog
        implements ProgressListener
{

    private static ProgressDialog dialog = null;

    public static void show( String src, String dest, long length, Main.Task task )
    {
        SwingUtilities.invokeLater( () -> {
            if( dialog == null ) {
                dialog = new ProgressDialog( Main.getFrame() );
            }
            dialog.srcFile.setText( src );
            dialog.destFile.setText( dest );
            dialog.expectedSize.setText( String.valueOf( length ) );
            dialog.bytesRead.setText( "0" );

            dialog.start = LocalDateTime.now();
            dialog.remaining.setText( "N/A" );

            dialog.setVisible( true );

            Main.executeTask( task );
        } );
    }

    public static void copy( String src, long length, IOSupplier<InputStream> supplier,
                             Path path, CopyOption... copyOptions )
    {
        copy( src, length, supplier, null, null, path, copyOptions );
    }

    public static void copy( String src, long length, IOSupplier<InputStream> supplier,
                             Main.Task postTask,
                             Path path, CopyOption... copyOptions )
    {
        copy( src, length, supplier, postTask, null, path, copyOptions );
    }

    public static void copy( String src, long length, IOSupplier<InputStream> supplier,
                             Main.Task postTask, Main.Task failTask,
                             Path path, CopyOption... copyOptions )
    {
        show( src,
              path.getName( path.getNameCount() - 1 ).toString(),
              length,
              () -> {
                  SwingUtilities.invokeLater( () -> dialog.setTitle( "Transfering " + src ) );
                  try {
                      try( InputStream is = new ProgressInputStream( supplier.get(), length, dialog ) ) {
                          Files.copy( is, path, copyOptions );
                      }
                      postTask.run();
                  }
                  catch( Exception ex ) {
                      Main.executeTask( failTask );
                      throw ex;
                  }
                  finally {
                      close();
                  }
              } );
    }

    public static void close()
    {
        SwingUtilities.invokeLater( () -> {
            if( dialog != null ) {
                dialog.setVisible( false );
            }
        } );
    }

    private LocalDateTime start;

    public ProgressDialog( java.awt.Frame parent )
    {
        super( parent, false );
        initComponents();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        srcFile = new javax.swing.JLabel();
        destFile = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jLabel5 = new javax.swing.JLabel();
        bytesRead = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        expectedSize = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        remaining = new javax.swing.JLabel();

        setResizable(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Source File:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Destination File:");

        srcFile.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        srcFile.setText("jLabel3");

        destFile.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        destFile.setText("jLabel3");

        progressBar.setStringPainted(true);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("Bytes Read:");

        bytesRead.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        bytesRead.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bytesRead.setText("jLabel3");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Expected Size:");

        expectedSize.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        expectedSize.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        expectedSize.setText("jLabel3");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel9.setText("Progress:");

        remaining.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        remaining.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(srcFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(destFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bytesRead, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expectedSize, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(remaining, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel5, jLabel7, jLabel9});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bytesRead, expectedSize, remaining});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(srcFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(destFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(bytesRead))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(expectedSize))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(remaining))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    @Override
    public void progressUpdate( int percent, long position, long length )
    {
        SwingUtilities.invokeLater( () -> {
            progressBar.setValue( Math.max( 0, Math.min( 100, percent ) ) );
        } );

        SwingUtilities.invokeLater( () -> {
            bytesRead.setText( String.valueOf( position ) );
            expectedSize.setText( String.valueOf( length ) );
        } );

        SwingUtilities.invokeLater( () -> {
            StringBuilder buf = new StringBuilder( 24 );

            if( position > 0L ) {
                long duration = Duration.between( start, LocalDateTime.now() ).getSeconds();
                long totalTime = duration * length / position;
                long seconds = totalTime - duration;

                long hours = seconds / SECONDS_PER_HOUR;
                int minutes = (int) ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
                int secs = (int) (seconds % SECONDS_PER_MINUTE);
                if( hours != 0 ) {
                    buf.append( hours ).append( "h " );
                }
                if( minutes != 0 ) {
                    buf.append( minutes ).append( "m " );
                }
                if( secs != 0 || (hours == 0 && minutes == 0) ) {
                    buf.append( secs ).append( "s " );
                }
            }

            if( buf.length() > 1 ) {
                buf.setLength( buf.length() - 1 );
            }
            else {
                buf.append( "N/A" );
            }
            remaining.setText( buf.toString() );
        } );
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bytesRead;
    private javax.swing.JLabel destFile;
    private javax.swing.JLabel expectedSize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel remaining;
    private javax.swing.JLabel srcFile;
    // End of variables declaration//GEN-END:variables
}