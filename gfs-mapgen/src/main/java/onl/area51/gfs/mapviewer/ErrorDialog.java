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
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.SwingUtilities;

/**
 *
 * @author peter
 */
public class ErrorDialog
        extends javax.swing.JDialog
{

    private static ErrorDialog dialog;

    public static void show( Exception e )
    {
        show( e.getMessage(), e );
    }

    public static void show( String message, Exception e )
    {
        SwingUtilities.invokeLater( () -> {
            if( dialog == null ) {
                dialog = new ErrorDialog( Main.getFrame() );
            }
            dialog.jLabel1.setText( message );
            try( StringWriter sw = new StringWriter() ) {
                try( PrintWriter w = new PrintWriter( sw ) ) {
                    e.printStackTrace( w );
                }
                dialog.jTextArea1.setText( sw.toString() );
            }
            catch( IOException ex ) {
                dialog.jTextArea1.setText( ex.getMessage() );

            }

            dialog.setVisible( true );
        } );
    }

    /**
     * Creates new form ErrorDialog
     * <p>
     * @param parent
     * @param modal
     */
    public ErrorDialog( java.awt.Frame parent )
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("jLabel1");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        setVisible( false );
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main( String args[] )
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
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
        catch( ClassNotFoundException ex ) {
            java.util.logging.Logger.getLogger( ErrorDialog.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }
        catch( InstantiationException ex ) {
            java.util.logging.Logger.getLogger( ErrorDialog.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }
        catch( IllegalAccessException ex ) {
            java.util.logging.Logger.getLogger( ErrorDialog.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }
        catch( javax.swing.UnsupportedLookAndFeelException ex ) {
            java.util.logging.Logger.getLogger( ErrorDialog.class.getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater( () -> {
            ErrorDialog dialog1 = new ErrorDialog( new javax.swing.JFrame() );
            dialog1.addWindowListener( new java.awt.event.WindowAdapter()
            {
                @Override
                public void windowClosing( java.awt.event.WindowEvent e )
                {
                    System.exit( 0 );
                }
            } );
            dialog1.setVisible( true );
        } );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
