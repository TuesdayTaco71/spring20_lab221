package GUI;

import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

/**
 * Status: Waiting
 * @author tanh2k2k
 */
public class FrameTextEditor extends javax.swing.JFrame
{
    /* ============================= VARIABLES ============================= */
    
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private UndoManager undoManager;            // Undo, Redo Controller
    private String findStr = "";                // "Find" text
    private String filePath = "";               // File path
    private String originalTxt = "";            // To compare saved text and txtArea
    private boolean rdbFindDown = true;         // Boolean for find and replace
    private boolean isNewFile = true;           // Check new file to open save Joption

    /* ============================ CONSTRUCTOR ============================ */
    
    public FrameTextEditor()
    {
        FlatIntelliJLaf.install();
        initComponents();
        undoManager = new UndoManager();
//        Document doc = this.txtArea.getDocument();
//        doc.addUndoableEditListener(new UndoableEditListener()
//        {
//            @Override
//            public void undoableEditHappened(UndoableEditEvent e)
//            {
//                undoManager.addEdit(e.getEdit());
//            }
//        });
        getEdit();
    }

    /* ========================== GETTER & SETTER ========================== */
    
    public void getEdit()
    {
        Document doc = this.txtArea.getDocument();
        doc.addUndoableEditListener(new UndoableEditListener()
        {
            @Override
            public void undoableEditHappened(UndoableEditEvent e)
            {
                undoManager.addEdit(e.getEdit());
            }
        });
    }
    
    public void setNewTitle(String title)
    {
        super.setTitle(title + " - Nathpad");
    }

    public String getFindStr()
    {
        return findStr;
    }

    public void setFindStr(String findStr)
    {
        this.findStr = findStr;
    }

    public void setRdbFindDown(boolean rdbFindDown)
    {
        this.rdbFindDown = rdbFindDown;
    }

    public JTextArea getTxtArea()
    {
        return txtArea;
    }
    
    /* ============================== FUNCTION ============================== */
    
    /**
     * Write all txtArea's data to the .txt file
     * @param path Location of the file
     */
    private void writeFile(String path)
    {
        try
        {
            File file = new File(path);
            if (!file.exists())
                file.createNewFile();
            FileWriter fWriter = new FileWriter(file);
            BufferedWriter bWriter = new BufferedWriter(fWriter);
            txtArea.write(bWriter);
            bWriter.close();
            fWriter.close();
        } catch (IOException ex)
        {
            Logger.getLogger(FrameTextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Read all .txt file data and transfer to txtArea
     * @param path Location of the file
     */
    private void readFile(String path)
    {
        try
        {
            FileReader fReader = new FileReader(path);
            BufferedReader bReader = new BufferedReader(fReader);
            txtArea.read(bReader, null);
            bReader.close();
            fReader.close();
        } catch (IOException ex)
        {
            Logger.getLogger(FrameTextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Show Option Dialog to save file or not
     * @return true when "Save" or "Don't Save" are pressed, false when Cancel is pressed
     */
    private boolean verifySave()
    {
        if (!originalTxt.equals(txtArea.getText()))
        {
            int result;
            Object[] options = { "Save", "Don't save", "Cancel" };
            if (!isNewFile)
                result = JOptionPane.showOptionDialog(this,
                        "Do you want to save change to " + filePath,
                        "Nathpad",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
            else
            {
                String file = this.getTitle().replaceAll("- Nathpad", "");
                result = JOptionPane.showOptionDialog(this,
                        "Do you want to save change to " + file,
                        "Nathpad",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
            }
            if (result == JOptionPane.YES_OPTION)
                if (!isNewFile)
                    writeFile(filePath);
                else
                {
                    JFileChooser save = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
                    save.setFileFilter(filter);
                    int option = save.showSaveDialog(this);
                    if (option == JFileChooser.APPROVE_OPTION)
                        writeFile(save.getSelectedFile().getPath() + ".txt");
                    if (option == JFileChooser.CANCEL_OPTION)
                        return false;
                }
            else if (result == JOptionPane.CANCEL_OPTION)
                return false;
        }
        return true;
    }

    /**
     * Replace user input findStr which has special characters in regex ($, ^, *, (, ), +, |, \, ., ?)
     * @param s findStr
     * @param speialChar "\\"
     * @return findStr
     */
    public static String createNewFindStr(String s, String speialChar)
    {
        String newString = new String();
        for (int i = 0; i < s.length(); i++)
        {
            if (("" + s.charAt(i)).matches("(\\$|\\^|\\*|\\(|\\)|\\+|\\||\\\\|\\.|\\/|\\?)"))
                newString += speialChar;
            newString += s.charAt(i);
        }
        return newString;
    }

    public void replaceAll(String text)
    {
        String s = txtArea.getText();
        findStr = createNewFindStr(findStr, "\\");
        s = s.replaceAll(findStr, text);
        txtArea.setText(s);
    }
    
    public void findText()
    {
        String content = txtArea.getText();
        int positionStart = txtArea.getCaretPosition(); // Get position of text pointer
        int position = 0;
        if (rdbFindDown)
        {
            position = content.indexOf(findStr, positionStart);
            if (position == -1)
            {
                JOptionPane.showMessageDialog(this, "Cannot find \"" + findStr + "\"");
                txtArea.setCaretPosition(positionStart);
            } else
                txtArea.select(position, position + findStr.length());
        } else
        {
            if (txtArea.getSelectedText() != null)
                positionStart -= txtArea.getSelectedText().length();
            content = content.substring(0, positionStart);
            position = content.lastIndexOf(findStr);
            if (position == -1)
            {
                JOptionPane.showMessageDialog(this, "Cannot find \"" + findStr + "\"");
                txtArea.setCaretPosition(positionStart);
            } else
                txtArea.select(position, position + findStr.length());
        }
    }

    public void replaceText(String text)
    {
        if (txtArea.getSelectedText() != null)
            txtArea.replaceSelection(text);
        String content = txtArea.getText();
        int positionStart = txtArea.getCaretPosition();
        int position = content.indexOf(findStr, positionStart);
        if (position == -1)
        {
            positionStart = 0;
            position = content.indexOf(findStr, positionStart);
            if (position == -1)
            {
                JOptionPane.showMessageDialog(this, "Cannot find \"" + findStr + "\"");
                return;
            }
        }
        txtArea.select(position, position + findStr.length());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        jMenuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuNew = new javax.swing.JMenuItem();
        menuOpen = new javax.swing.JMenuItem();
        menuSave = new javax.swing.JMenuItem();
        menuSaveAs = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuExit = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();
        menuUndo = new javax.swing.JMenuItem();
        menuRedo = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuCut = new javax.swing.JMenuItem();
        menuCopy = new javax.swing.JMenuItem();
        menuPaste = new javax.swing.JMenuItem();
        menuDel = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuFind = new javax.swing.JMenuItem();
        menuReplace = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        menuSelectAll = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        menuFont = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Nathpad");

        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtArea.setRows(5);
        txtArea.setBorder(null);
        txtArea.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(txtArea);

        menuFile.setMnemonic('F');
        menuFile.setText("File");

        menuNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuNew.setMnemonic('n');
        menuNew.setText("New");
        menuNew.setPreferredSize(new java.awt.Dimension(180, 22));
        menuNew.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuNewActionPerformed(evt);
            }
        });
        menuFile.add(menuNew);

        menuOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuOpen.setMnemonic('o');
        menuOpen.setText("Open...");
        menuOpen.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuOpenActionPerformed(evt);
            }
        });
        menuFile.add(menuOpen);

        menuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuSave.setMnemonic('s');
        menuSave.setText("Save");
        menuSave.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuSaveActionPerformed(evt);
            }
        });
        menuFile.add(menuSave);

        menuSaveAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuSaveAs.setMnemonic('s');
        menuSaveAs.setText("Save As...");
        menuSaveAs.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuSaveAsActionPerformed(evt);
            }
        });
        menuFile.add(menuSaveAs);
        menuFile.add(jSeparator1);

        menuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        menuExit.setMnemonic('e');
        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuExitActionPerformed(evt);
            }
        });
        menuFile.add(menuExit);

        jMenuBar.add(menuFile);

        menuEdit.setMnemonic('E');
        menuEdit.setText("Edit");
        menuEdit.addMenuListener(new javax.swing.event.MenuListener()
        {
            public void menuCanceled(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt)
            {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt)
            {
                menuEditMenuSelected(evt);
            }
        });

        menuUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        menuUndo.setMnemonic('u');
        menuUndo.setText("Undo");
        menuUndo.setPreferredSize(new java.awt.Dimension(160, 22));
        menuUndo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuUndoActionPerformed(evt);
            }
        });
        menuEdit.add(menuUndo);

        menuRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        menuRedo.setMnemonic('r');
        menuRedo.setText("Redo");
        menuRedo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuRedoActionPerformed(evt);
            }
        });
        menuEdit.add(menuRedo);
        menuEdit.add(jSeparator2);

        menuCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        menuCut.setMnemonic('c');
        menuCut.setText("Cut");
        menuCut.setEnabled(false);
        menuCut.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuCutActionPerformed(evt);
            }
        });
        menuEdit.add(menuCut);

        menuCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        menuCopy.setMnemonic('c');
        menuCopy.setText("Copy");
        menuCopy.setEnabled(false);
        menuCopy.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuCopyActionPerformed(evt);
            }
        });
        menuEdit.add(menuCopy);

        menuPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        menuPaste.setMnemonic('p');
        menuPaste.setText("Paste");
        menuPaste.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuPasteActionPerformed(evt);
            }
        });
        menuEdit.add(menuPaste);

        menuDel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        menuDel.setMnemonic('d');
        menuDel.setText("Delete");
        menuDel.setEnabled(false);
        menuDel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuDelActionPerformed(evt);
            }
        });
        menuEdit.add(menuDel);
        menuEdit.add(jSeparator3);

        menuFind.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        menuFind.setMnemonic('f');
        menuFind.setText("Find...");
        menuFind.setEnabled(false);
        menuFind.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuFindActionPerformed(evt);
            }
        });
        menuEdit.add(menuFind);

        menuReplace.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        menuReplace.setMnemonic('r');
        menuReplace.setText("Replace...");
        menuReplace.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuReplaceActionPerformed(evt);
            }
        });
        menuEdit.add(menuReplace);
        menuEdit.add(jSeparator5);

        menuSelectAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        menuSelectAll.setMnemonic('A');
        menuSelectAll.setText("Select All");
        menuSelectAll.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuSelectAllActionPerformed(evt);
            }
        });
        menuEdit.add(menuSelectAll);
        menuEdit.add(jSeparator6);

        menuFont.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        menuFont.setMnemonic('F');
        menuFont.setText("Font...");
        menuFont.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                menuFontActionPerformed(evt);
            }
        });
        menuEdit.add(menuFont);

        jMenuBar.add(menuEdit);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // ============== FILE ==============
    // New
    private void menuNewActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuNewActionPerformed
    {//GEN-HEADEREND:event_menuNewActionPerformed
        if (!verifySave())
            return;
        isNewFile = true;
        originalTxt = "";
        txtArea.setText("");
        setTitle("Nathpad");
        undoManager = new UndoManager();
    }//GEN-LAST:event_menuNewActionPerformed

    // Open
    private void menuOpenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuOpenActionPerformed
    {//GEN-HEADEREND:event_menuOpenActionPerformed
        if (!verifySave())
            return;
        JFileChooser open = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
        open.setFileFilter(filter);
        int option = open.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION)
        {
            this.txtArea.setText("");
            try
            {
                filePath = open.getSelectedFile().getPath();
                readFile(filePath);
                isNewFile = false;
                originalTxt = txtArea.getText();
                getEdit();
                setNewTitle(open.getSelectedFile().getName().substring(0, open.getSelectedFile().getName().length() - 4));
            } catch (Exception e)
            {
                JOptionPane.showMessageDialog(this, "Can't open file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_menuOpenActionPerformed

    // Save
    private void menuSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuSaveActionPerformed
    {//GEN-HEADEREND:event_menuSaveActionPerformed
        if (!originalTxt.equals(txtArea.getText()))
            if (!isNewFile)
            {
                originalTxt = txtArea.getText();
                writeFile(filePath);
            } else
            {
                JFileChooser save = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
                save.setFileFilter(filter);
                int option = save.showSaveDialog(this);
                if (option == JFileChooser.APPROVE_OPTION)
                {
                    filePath = save.getSelectedFile().getPath() + ".txt";
                    writeFile(filePath);
                    isNewFile = false;
                    setNewTitle(save.getSelectedFile().getName());
                    System.out.println(save.getSelectedFile().getName());
                }
            }
    }//GEN-LAST:event_menuSaveActionPerformed

    // Save As
    private void menuSaveAsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuSaveAsActionPerformed
    {//GEN-HEADEREND:event_menuSaveAsActionPerformed
        JFileChooser save = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
        save.setFileFilter(filter);
        int option = save.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION)
        {
            originalTxt = txtArea.getText();
            filePath = save.getSelectedFile().getPath() + ".txt";
            writeFile(filePath);
            isNewFile = false;
            setNewTitle(save.getSelectedFile().getName());
        }
    }//GEN-LAST:event_menuSaveAsActionPerformed

    // Exit
    private void menuExitActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuExitActionPerformed
    {//GEN-HEADEREND:event_menuExitActionPerformed
        if (!verifySave())
            return;
        System.exit(0);
    }//GEN-LAST:event_menuExitActionPerformed

    
    /* ============================= EDIT MENU ============================= */
    // Undo
    private void menuUndoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuUndoActionPerformed
    {//GEN-HEADEREND:event_menuUndoActionPerformed
        if (undoManager.canUndo())
            undoManager.undo();
    }//GEN-LAST:event_menuUndoActionPerformed
    // Redo
    private void menuRedoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuRedoActionPerformed
    {//GEN-HEADEREND:event_menuRedoActionPerformed
        if (undoManager.canRedo())
            undoManager.redo();
    }//GEN-LAST:event_menuRedoActionPerformed
    // Cut
    private void menuCutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuCutActionPerformed
    {//GEN-HEADEREND:event_menuCutActionPerformed
        String cutString = txtArea.getSelectedText();
        StringSelection cutSelection = new StringSelection(cutString);
        clipboard.setContents(cutSelection, cutSelection);
        txtArea.replaceRange("", txtArea.getSelectionStart(), txtArea.getSelectionEnd());
    }//GEN-LAST:event_menuCutActionPerformed
    // Copy
    private void menuCopyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuCopyActionPerformed
    {//GEN-HEADEREND:event_menuCopyActionPerformed
        String cutString = txtArea.getSelectedText();
        StringSelection cutSelection = new StringSelection(cutString);
        clipboard.setContents(cutSelection, cutSelection);
    }//GEN-LAST:event_menuCopyActionPerformed
    // Paste
    private void menuPasteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuPasteActionPerformed
    {//GEN-HEADEREND:event_menuPasteActionPerformed
        try
        {
            Transferable pasteText = clipboard.getContents(this);
            String sel = (String) pasteText.getTransferData(DataFlavor.stringFlavor);
            txtArea.replaceRange(sel, txtArea.getSelectionStart(), txtArea.getSelectionEnd());
        } catch (UnsupportedFlavorException | IOException ex)
        {
            Logger.getLogger(FrameTextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuPasteActionPerformed
    // Delete
    private void menuDelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuDelActionPerformed
    {//GEN-HEADEREND:event_menuDelActionPerformed
        txtArea.replaceSelection("");
    }//GEN-LAST:event_menuDelActionPerformed
    // Find
    private void menuFindActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuFindActionPerformed
    {//GEN-HEADEREND:event_menuFindActionPerformed
        DialogFind find = new DialogFind(this, true);
        find.setVisible(true);
        this.setEnabled(false);
    }//GEN-LAST:event_menuFindActionPerformed
    // Replace
    private void menuReplaceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuReplaceActionPerformed
    {//GEN-HEADEREND:event_menuReplaceActionPerformed
        DialogReplace replace = new DialogReplace(this, true);
        replace.setVisible(true);
    }//GEN-LAST:event_menuReplaceActionPerformed
    // ============== ENABLE MENU ==============
    private void menuEditMenuSelected(javax.swing.event.MenuEvent evt)//GEN-FIRST:event_menuEditMenuSelected
    {//GEN-HEADEREND:event_menuEditMenuSelected
        boolean setFind = !txtArea.getText().equals("");
        menuFind.setEnabled(setFind);
        boolean setEdit = txtArea.getSelectedText() != null;
        menuCut.setEnabled(setEdit);
        menuCopy.setEnabled(setEdit);
        menuPaste.setEnabled(clipboard.getContents(null).isDataFlavorSupported(DataFlavor.stringFlavor));
        menuDel.setEnabled(setEdit);
        menuUndo.setEnabled(undoManager.canUndo());
        menuRedo.setEnabled(undoManager.canRedo()); 
    }//GEN-LAST:event_menuEditMenuSelected

    private void menuSelectAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuSelectAllActionPerformed
    {//GEN-HEADEREND:event_menuSelectAllActionPerformed
        txtArea.selectAll();
    }//GEN-LAST:event_menuSelectAllActionPerformed

    private void menuFontActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_menuFontActionPerformed
    {//GEN-HEADEREND:event_menuFontActionPerformed
        DialogFont font = new DialogFont(this, true);
        font.setVisible(true);
    }//GEN-LAST:event_menuFontActionPerformed

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
                if ("Windows".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(FrameTextEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(FrameTextEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(FrameTextEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(FrameTextEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new FrameTextEditor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JMenuItem menuCopy;
    private javax.swing.JMenuItem menuCut;
    private javax.swing.JMenuItem menuDel;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuFind;
    private javax.swing.JMenuItem menuFont;
    private javax.swing.JMenuItem menuNew;
    private javax.swing.JMenuItem menuOpen;
    private javax.swing.JMenuItem menuPaste;
    private javax.swing.JMenuItem menuRedo;
    private javax.swing.JMenuItem menuReplace;
    private javax.swing.JMenuItem menuSave;
    private javax.swing.JMenuItem menuSaveAs;
    private javax.swing.JMenuItem menuSelectAll;
    private javax.swing.JMenuItem menuUndo;
    private javax.swing.JTextArea txtArea;
    // End of variables declaration//GEN-END:variables
}