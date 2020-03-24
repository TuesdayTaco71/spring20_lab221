package GUI;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author tanh2k2k
 */
public class DialogFont extends javax.swing.JDialog
{
    /**
     * Creates new form DialogFont
     */
    public DialogFont(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();
        addListFont();
        addListSize();
        setPreviewText();
    }
    
    private void addListSize()
    {
        DefaultListModel model = new DefaultListModel();
        for (int i = 8; i < 49; i += 2)
            model.addElement(i);
        listSize.setModel(model);
    }
    
    private void addListFont()
    {
        GraphicsEnvironment graphic = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = graphic.getAvailableFontFamilyNames();
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < fontNames.length; i++)
            model.addElement(fontNames[i]);
        listFont.setModel(model);
    }
    
    private void setPreviewText()
    {
        Font font = ((FrameTextEditor)this.getParent()).getTxtArea().getFont();
        lblPreview.setFont(font);
        txtFont.setText(font.getFontName());
        listFont.setSelectedValue(font.getFontName(), true);
        listStyle.setSelectedIndex(font.getStyle());
        txtStyle.setText(listStyle.getSelectedValue());
        txtSize.setText(Integer.toString(font.getSize()));
        listSize.setSelectedValue(font.getSize(), true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane2 = new javax.swing.JScrollPane();
        listStyle = new javax.swing.JList<>();
        lblSize = new javax.swing.JLabel();
        txtSize = new javax.swing.JTextField();
        btnOk = new javax.swing.JButton();
        lblFont = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        txtFont = new javax.swing.JTextField();
        pnlSample = new javax.swing.JPanel();
        lblPreview = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listFont = new javax.swing.JList<>();
        jScrollPaneSize = new javax.swing.JScrollPane();
        listSize = new javax.swing.JList();
        lblFontStyle = new javax.swing.JLabel();
        txtStyle = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Font");
        setResizable(false);

        listStyle.setModel(new javax.swing.AbstractListModel<String>()
        {
            String[] strings = { "Normal", "Bold", "Italic", "Bold Italic" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listStyle.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                listStyleValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listStyle);

        lblSize.setDisplayedMnemonic('s');
        lblSize.setText("Size:");

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnOkActionPerformed(evt);
            }
        });

        lblFont.setDisplayedMnemonic('f');
        lblFont.setText("Font:");
        lblFont.setToolTipText("");

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCancelActionPerformed(evt);
            }
        });

        txtFont.setEditable(false);

        pnlSample.setBorder(javax.swing.BorderFactory.createTitledBorder("Sample"));

        lblPreview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPreview.setText("AaBbYyZz");

        javax.swing.GroupLayout pnlSampleLayout = new javax.swing.GroupLayout(pnlSample);
        pnlSample.setLayout(pnlSampleLayout);
        pnlSampleLayout.setHorizontalGroup(
            pnlSampleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        pnlSampleLayout.setVerticalGroup(
            pnlSampleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPreview, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
        );

        listFont.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                listFontValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listFont);

        listSize.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                listSizeValueChanged(evt);
            }
        });
        jScrollPaneSize.setViewportView(listSize);

        lblFontStyle.setDisplayedMnemonic('y');
        lblFontStyle.setText("Font Style:");

        txtStyle.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtFont, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFont, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPaneSize, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtStyle, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(lblFontStyle, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(lblSize, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(txtSize))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnlSample, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFont)
                    .addComponent(lblFontStyle)
                    .addComponent(lblSize))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStyle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPaneSize))
                .addGap(18, 18, 18)
                .addComponent(pnlSample, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listFontValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_listFontValueChanged
    {//GEN-HEADEREND:event_listFontValueChanged
        Font current = lblPreview.getFont();
        String name = listFont.getSelectedValue().toString();
        int style = current.getStyle();
        int size = current.getSize();
        txtFont.setText(name);
        Font f = new Font(name, style, size);
        lblPreview.setFont(f);
    }//GEN-LAST:event_listFontValueChanged

    private void listStyleValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_listStyleValueChanged
    {//GEN-HEADEREND:event_listStyleValueChanged
        Font current = lblPreview.getFont();
        String name = current.getFontName();
        int style = listStyle.getSelectedIndex();
        int size = current.getSize();
        txtStyle.setText(listStyle.getSelectedValue().toString());
        Font f = new Font(name, style, size);
        lblPreview.setFont(f);
    }//GEN-LAST:event_listStyleValueChanged

    private void listSizeValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_listSizeValueChanged
    {//GEN-HEADEREND:event_listSizeValueChanged
        Font current = lblPreview.getFont();
        String name = current.getFontName();
        int style = current.getStyle();
        int size = (int) listSize.getSelectedValue();
        txtSize.setText(listSize.getSelectedValue().toString());
        Font f = new Font(name, style, size);
        lblPreview.setFont(f);
    }//GEN-LAST:event_listSizeValueChanged

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnOkActionPerformed
    {//GEN-HEADEREND:event_btnOkActionPerformed
        try
        {
            if(Integer.parseInt(txtSize.getText()) <= 0)
                throw new Exception();
            ((FrameTextEditor) this.getParent()).getTxtArea().setFont(new Font(txtFont.getName(), listStyle.getSelectedIndex(), Integer.parseInt(txtSize.getText())));
            this.dispose();
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Please enter size correctly...");
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
    {//GEN-HEADEREND:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(DialogFont.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(DialogFont.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(DialogFont.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(DialogFont.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                DialogFont dialog = new DialogFont(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter()
                {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e)
                    {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneSize;
    private javax.swing.JLabel lblFont;
    private javax.swing.JLabel lblFontStyle;
    private javax.swing.JLabel lblPreview;
    private javax.swing.JLabel lblSize;
    private javax.swing.JList<String> listFont;
    private javax.swing.JList listSize;
    private javax.swing.JList<String> listStyle;
    private javax.swing.JPanel pnlSample;
    private javax.swing.JTextField txtFont;
    private javax.swing.JTextField txtSize;
    private javax.swing.JTextField txtStyle;
    // End of variables declaration//GEN-END:variables
}
