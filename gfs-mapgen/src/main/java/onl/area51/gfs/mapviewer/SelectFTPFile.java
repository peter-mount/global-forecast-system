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

import java.util.Collection;
import java.util.function.Predicate;
import javax.swing.SwingUtilities;
import onl.area51.gfs.mapviewer.action.DataSetModel;
import org.apache.commons.net.ftp.FTPFile;
import uk.trainwatch.io.ftp.FTPClient;

/**
 * Simple dialog to allow us to select files from a remote FTP server
 * <p>
 * @author peter
 */
public class SelectFTPFile
        extends javax.swing.JDialog
{

    private static SelectFTPFile SELECTOR;

    private FTPClient client;
    private SelectAction task;

    public static void select( FTPClient client, SelectAction task, String message, Collection<FTPFile> files )
    {
        select( client, task, message, files, a -> true );
    }

    public static void select( FTPClient client, SelectAction task, String message, Collection<FTPFile> files, Predicate<FTPFile> filter )
    {
        SwingUtilities.invokeLater( () -> {
            if( SELECTOR == null ) {
                SELECTOR = new SelectFTPFile( Main.getFrame() );
            }

            SELECTOR.client = client;
            SELECTOR.task = task;
            SELECTOR.setTitle( message );

            SELECTOR.jList1.setModel(
                    files.stream()
                    .filter( filter )
                    .reduce( new DataSetModel<FTPFile>(), DataSetModel::add, DataSetModel::combine )
            );

            SELECTOR.jList1.clearSelection();
            SELECTOR.selectButton.setEnabled( false );

            SELECTOR.setVisible( true );
        } );
    }

    public SelectFTPFile( java.awt.Frame parent )
    {
        super( parent, true );
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

        selectButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        selectButton.setMnemonic('S');
        selectButton.setText("Select");
        selectButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                selectButtonActionPerformed(evt);
            }
        });

        cancelButton.setMnemonic('C');
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cancelButtonActionPerformed(evt);
            }
        });

        jList1.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        jList1.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 617, Short.MAX_VALUE)
                        .addComponent(selectButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_selectButtonActionPerformed
    {//GEN-HEADEREND:event_selectButtonActionPerformed
        SwingUtilities.invokeLater( () -> {
            FTPFile file = (FTPFile) jList1.getSelectedValue();
            if( file != null ) {
                setVisible( false );
                Main.executeTask( () -> task.select( client, file ) );
            }
        } );
    }//GEN-LAST:event_selectButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
    {//GEN-HEADEREND:event_cancelButtonActionPerformed
        SwingUtilities.invokeLater( () -> {
            Main.executeTask( () -> client.close() );
            setVisible( false );
            Main.setStatus( "Aborted. Closing FTP Connection" );
        } );
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jList1MouseClicked
    {//GEN-HEADEREND:event_jList1MouseClicked
        SwingUtilities.invokeLater( () -> {
            selectButton.setEnabled( jList1.getSelectedIndex() != -1 );
        } );
    }//GEN-LAST:event_jList1MouseClicked

    @FunctionalInterface
    public static interface SelectAction
    {

        void select( FTPClient client, FTPFile file )
                throws Exception;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton selectButton;
    // End of variables declaration//GEN-END:variables
}