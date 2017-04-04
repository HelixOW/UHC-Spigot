/*
 * Created by JFormDesigner on Sat Apr 01 13:33:32 CEST 2017
 */

package de.alphahelix.uhcconfigeditor.frames;

import de.alphahelix.alphaapi.file.SimpleFile;
import de.alphahelix.uhcconfigeditor.AlphaButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Sebasian Meier
 */
public class AchievementsFrame extends JFrame {

    SimpleFile edit;

    public AchievementsFrame() {
        initComponents();
        edit = new SimpleFile("plugins/UHC-Remake", "achievements.uhc");
    }

    private void alphaButton1ActionPerformed(ActionEvent e) {
        new ItemFrame(edit, "Item");
    }

    private void btnConfirmActionPerformed(ActionEvent e) {

        if(!txtFldGUIName.getText().isEmpty())
            edit.override("GUI name", txtFldGUIName.getText());

        if(!txtFldAchievedYes.getText().isEmpty())
            edit.override("hasAchievement.true", txtFldAchievedYes.getText());

        if(!txtFldAchievedFalse.getText().isEmpty())
            edit.override("hasAchievement.false", txtFldAchievedFalse.getText());

        for (int rows = 0; rows < tblAchievements.getModel().getRowCount(); rows++)
            if (tblAchievements.getModel().getValueAt(rows, 1) != null)
                if (tblAchievements.getModel().getValueAt(rows, 2) != null)
                    if (tblAchievements.getModel().getValueAt(rows, 3) != null) {
                        edit.override("Achievements." + tblAchievements.getModel().getValueAt(rows, 0).toString().toLowerCase() + ".name",
                                tblAchievements.getModel().getValueAt(rows, 1));
                        edit.override("Achievements." + tblAchievements.getModel().getValueAt(rows, 0).toString().toLowerCase() + ".icon",
                                tblAchievements.getModel().getValueAt(rows, 2));
                        edit.addArgumentsToList("Achievements." + tblAchievements.getModel().getValueAt(rows, 0).toString().toLowerCase() + ".description",
                                tblAchievements.getModel().getValueAt(rows, 3).toString());
                    }

        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Sebasian Meier
        btnItem = new AlphaButton();
        lblSpacer = new JLabel();
        lblGUIName = new JLabel();
        txtFldGUIName = new JTextField();
        lblGUIName2 = new JLabel();
        txtFldAchievedYes = new JTextField();
        txtFldAchievedFalse = new JTextField();
        lblGUIName3 = new JLabel();
        scrollPane1 = new JScrollPane();
        tblAchievements = new JTable();
        btnConfirm = new AlphaButton();

        //======== this ========
        setIconImage(new ImageIcon("C:\\Code\\icon.png").getImage());
        setTitle("Achievements");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- btnItem ----
        btnItem.setText("Item");
        btnItem.addActionListener(e -> alphaButton1ActionPerformed(e));
        contentPane.add(btnItem);
        btnItem.setBounds(25, 25, 710, btnItem.getPreferredSize().height);
        contentPane.add(lblSpacer);
        lblSpacer.setBounds(5, 400, 760, 5);

        //---- lblGUIName ----
        lblGUIName.setText("GUI name");
        lblGUIName.setHorizontalAlignment(SwingConstants.CENTER);
        lblGUIName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(lblGUIName);
        lblGUIName.setBounds(25, 75, 120, 35);

        //---- txtFldGUIName ----
        txtFldGUIName.setHorizontalAlignment(SwingConstants.CENTER);
        txtFldGUIName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(txtFldGUIName);
        txtFldGUIName.setBounds(165, 75, 570, txtFldGUIName.getPreferredSize().height);

        //---- lblGUIName2 ----
        lblGUIName2.setText("Achieved? True");
        lblGUIName2.setHorizontalAlignment(SwingConstants.CENTER);
        lblGUIName2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(lblGUIName2);
        lblGUIName2.setBounds(25, 125, 120, 35);

        //---- txtFldAchievedYes ----
        txtFldAchievedYes.setHorizontalAlignment(SwingConstants.CENTER);
        txtFldAchievedYes.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(txtFldAchievedYes);
        txtFldAchievedYes.setBounds(165, 125, 570, 33);

        //---- txtFldAchievedFalse ----
        txtFldAchievedFalse.setHorizontalAlignment(SwingConstants.CENTER);
        txtFldAchievedFalse.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(txtFldAchievedFalse);
        txtFldAchievedFalse.setBounds(165, 165, 570, 33);

        //---- lblGUIName3 ----
        lblGUIName3.setText("Achieved? False");
        lblGUIName3.setHorizontalAlignment(SwingConstants.CENTER);
        lblGUIName3.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(lblGUIName3);
        lblGUIName3.setBounds(25, 165, 130, 35);

        //======== scrollPane1 ========
        {

            //---- tblAchievements ----
            tblAchievements.setModel(new DefaultTableModel(
                new Object[][] {
                    {"Slayer", null, null, null},
                    {"Winner", null, null, null},
                    {"Dragon Slayer", null, null, null},
                    {"OMG Diamonds", null, null, null},
                    {"Burn Baby Burn", null, null, null},
                    {"Highway to Hell", null, null, null},
                },
                new String[] {
                    "Config name", "Name", "Icon (Type)", "Description"
                }
            ) {
                Class<?>[] columnTypes = new Class<?>[] {
                    String.class, Object.class, String.class, String.class
                };
                boolean[] columnEditable = new boolean[] {
                    false, true, true, true
                };
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            {
                TableColumnModel cm = tblAchievements.getColumnModel();
                cm.getColumn(0).setResizable(false);
                cm.getColumn(0).setPreferredWidth(100);
                cm.getColumn(1).setResizable(false);
                cm.getColumn(1).setPreferredWidth(120);
                cm.getColumn(2).setResizable(false);
                cm.getColumn(2).setPreferredWidth(120);
                cm.getColumn(3).setResizable(false);
                cm.getColumn(3).setPreferredWidth(280);
            }
            tblAchievements.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            scrollPane1.setViewportView(tblAchievements);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(20, 215, 720, 120);

        //---- btnConfirm ----
        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(e -> btnConfirmActionPerformed(e));
        contentPane.add(btnConfirm);
        btnConfirm.setBounds(20, 350, 720, 37);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

        setVisible(true);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Sebasian Meier
    private AlphaButton btnItem;
    private JLabel lblSpacer;
    private JLabel lblGUIName;
    private JTextField txtFldGUIName;
    private JLabel lblGUIName2;
    private JTextField txtFldAchievedYes;
    private JTextField txtFldAchievedFalse;
    private JLabel lblGUIName3;
    private JScrollPane scrollPane1;
    private JTable tblAchievements;
    private AlphaButton btnConfirm;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
