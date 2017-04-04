/*
 * Created by JFormDesigner on Sat Apr 01 17:48:33 CEST 2017
 */

package de.alphahelix.uhcconfigeditor.frames.items;

import de.alphahelix.uhcconfigeditor.AlphaButton;
import de.alphahelix.uhcconfigeditor.frames.ItemFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * @author Sebasian Meier
 */
public class LoreFrame extends JFrame {

    private ItemFrame base;

    public LoreFrame(ItemFrame base) {
        initComponents();
        this.base = base;
    }

    private void btnConfirmActionPerformed(ActionEvent e) {
        ArrayList<String> laterToArray = new ArrayList<>();
        for (int i = 0; i < table1.getModel().getRowCount(); i++) {
            laterToArray.add(table1.getModel().getValueAt(i, 0).toString());
        }
        base.setLore(laterToArray.toArray(new String[laterToArray.size()]));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Sebasian Meier
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        btnConfirm = new AlphaButton();

        //======== this ========
        setTitle("Lore");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                new Object[][] {
                    {null},
                    {null},
                    {null},
                    {null},
                    {null},
                    {null},
                    {null},
                    {null},
                    {null},
                    {null},
                },
                new String[] {
                    "Lines"
                }
            ) {
                Class<?>[] columnTypes = new Class<?>[] {
                    String.class
                };
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }
            });
            {
                TableColumnModel cm = table1.getColumnModel();
                cm.getColumn(0).setResizable(false);
            }
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 0, 500, 300);

        //---- btnConfirm ----
        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(e -> btnConfirmActionPerformed(e));
        contentPane.add(btnConfirm);
        btnConfirm.setBounds(240, 305, 250, 32);

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
    private JScrollPane scrollPane1;
    private JTable table1;
    private AlphaButton btnConfirm;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
