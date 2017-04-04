/*
 * Created by JFormDesigner on Sat Apr 01 17:55:09 CEST 2017
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
import java.util.ArrayList;

/**
 * @author Sebasian Meier
 */
public class FlagsFrame extends JFrame {

    private ItemFrame base;

    public FlagsFrame(ItemFrame base) {
        initComponents();
        this.base = base;
    }

    private void btnConfirmActionPerformed(ActionEvent e) {
        ArrayList<String> laterToArray = new ArrayList<>();
        for (int i = 0; i < table1.getModel().getRowCount(); i++) {
            laterToArray.add(table1.getModel().getValueAt(i, 0).toString());
        }
        base.setFlags(laterToArray.toArray(new String[laterToArray.size()]));
    }

    private void btnFlagsActionPerformed(ActionEvent e) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/ItemFlag.html"));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Sebasian Meier
        btnConfirm = new AlphaButton();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        btnFlags = new AlphaButton();

        //======== this ========
        setTitle("Flags");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- btnConfirm ----
        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(e -> btnConfirmActionPerformed(e));
        contentPane.add(btnConfirm);
        btnConfirm.setBounds(240, 305, 250, 32);

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
                },
                new String[] {
                    "Flags"
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
        scrollPane1.setBounds(0, 0, 499, 300);

        //---- btnFlags ----
        btnFlags.setText("All valid Flags");
        btnFlags.addActionListener(e -> btnFlagsActionPerformed(e));
        contentPane.add(btnFlags);
        btnFlags.setBounds(10, 305, 215, 32);

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
    private AlphaButton btnConfirm;
    private JScrollPane scrollPane1;
    private JTable table1;
    private AlphaButton btnFlags;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
