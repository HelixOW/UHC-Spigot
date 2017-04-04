/*
 * Created by JFormDesigner on Sat Apr 01 17:31:29 CEST 2017
 */

package de.alphahelix.uhcconfigeditor.frames.items;

import de.alphahelix.uhcconfigeditor.AlphaButton;
import de.alphahelix.uhcconfigeditor.frames.ItemFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;

/**
 * @author Sebasian Meier
 */
public class EnchantmentsFrame extends JFrame {

    private ItemFrame base;

    public EnchantmentsFrame(ItemFrame base) {
        initComponents();
        this.base = base;
    }

    private void btnConfirmActionPerformed(ActionEvent e) {
        for (int rows = 0; rows < table1.getModel().getRowCount(); rows++)
            if (table1.getModel().getValueAt(rows, 0) != null)
                if (table1.getModel().getValueAt(rows, 1) != null)
                    base.getEnchantments().put(table1.getModel().getValueAt(rows, 0).toString(), (Integer) table1.getModel().getValueAt(rows, 1));

        this.dispose();
    }

    private void btnEnchActionPerformed(ActionEvent e) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://hub.spigotmc.org/javadocs/spigot/org/bukkit/enchantments/Enchantment.html"));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Sebasian Meier
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        btnConfirm = new AlphaButton();
        btnEnch = new AlphaButton();

        //======== this ========
        setTitle("Enchantments");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                new Object[][] {
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                    {null, null},
                },
                new String[] {
                    "Enchantment", "Level"
                }
            ) {
                Class<?>[] columnTypes = new Class<?>[] {
                    String.class, Integer.class
                };
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return columnTypes[columnIndex];
                }
            });
            {
                TableColumnModel cm = table1.getColumnModel();
                cm.getColumn(0).setResizable(false);
                cm.getColumn(0).setPreferredWidth(235);
                cm.getColumn(1).setResizable(false);
                cm.getColumn(1).setPreferredWidth(265);
            }
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 0, 495, 300);

        //---- btnConfirm ----
        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(e -> btnConfirmActionPerformed(e));
        contentPane.add(btnConfirm);
        btnConfirm.setBounds(240, 305, 250, btnConfirm.getPreferredSize().height);

        //---- btnEnch ----
        btnEnch.setText("All valid Enchantments");
        btnEnch.addActionListener(e -> btnEnchActionPerformed(e));
        contentPane.add(btnEnch);
        btnEnch.setBounds(10, 305, 215, 32);

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
    private AlphaButton btnEnch;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
