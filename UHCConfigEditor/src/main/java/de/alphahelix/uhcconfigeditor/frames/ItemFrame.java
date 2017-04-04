/*
 * Created by JFormDesigner on Wed Mar 29 17:50:42 CEST 2017
 */

package de.alphahelix.uhcconfigeditor.frames;

import de.alphahelix.alphaapi.file.SimpleFile;
import de.alphahelix.uhcconfigeditor.AlphaButton;
import de.alphahelix.uhcconfigeditor.frames.items.EnchantmentsFrame;
import de.alphahelix.uhcconfigeditor.frames.items.FlagsFrame;
import de.alphahelix.uhcconfigeditor.frames.items.LoreFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;
import java.util.HashMap;

/**
 * @author Sebasian Meier
 */
public class ItemFrame extends JFrame {

    private HashMap<String, Integer> enchantments = new HashMap<>();
    private String[] lore = {""};
    private String[] flags = {""};
    private SimpleFile toEdit;
    private String filePath;

    public ItemFrame(SimpleFile toEdit, String filePath) {
        this.toEdit = toEdit;
        this.filePath = filePath;
        initComponents();
    }

    private void btnConfirmActionPerformed(ActionEvent e) {

        if (txtfldName.getText().isEmpty())
            toEdit.override(filePath + ".name", txtfldName.getText());
        if (txtfldIType.getText().isEmpty())
            toEdit.override(filePath + ".type", txtfldIType.getText());
        toEdit.override(filePath + ".amount", sldrAmount.getValue());
        toEdit.override(filePath + ".damage", sldrDMG.getValue());
        toEdit.override(filePath + ".slot", sldrSlot.getValue() - 1);
        toEdit.setMap(filePath + ".enchantments", enchantments);
        toEdit.addArgumentsToList(filePath + ".lore", lore);
        toEdit.addArgumentsToList(filePath + ".flags", flags);

        this.dispose();
    }

    private void btnEnchantmentsActionPerformed(ActionEvent e) {
        new EnchantmentsFrame(this);
    }

    private void btnLoreActionPerformed(ActionEvent e) {
        new LoreFrame(this);
    }

    private void btnFlagsActionPerformed(ActionEvent e) {
        new FlagsFrame(this);
    }

    private void btnTypesActionPerformed(ActionEvent e) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html"));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Sebasian Meier
        lblName = new JLabel();
        txtfldName = new JTextField();
        label1 = new JLabel();
        lblType = new JLabel();
        txtfldIType = new JTextField();
        sldrAmount = new JSlider();
        labelDMG = new JLabel();
        sldrDMG = new JSlider();
        labelSLot = new JLabel();
        sldrSlot = new JSlider();
        btnConfirm = new AlphaButton();
        lblSpacer = new JLabel();
        btnEnchantments = new AlphaButton();
        btnLore = new AlphaButton();
        btnFlags = new AlphaButton();
        btnTypes = new AlphaButton();

        //======== this ========
        setIconImage(new ImageIcon("C:\\Code\\icon.png").getImage());
        setTitle("Item");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- lblName ----
        lblName.setText("Itemname");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(lblName);
        lblName.setBounds(new Rectangle(new Point(35, 30), lblName.getPreferredSize()));

        //---- txtfldName ----
        txtfldName.setHorizontalAlignment(SwingConstants.CENTER);
        txtfldName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(txtfldName);
        txtfldName.setBounds(160, 30, 565, txtfldName.getPreferredSize().height);

        //---- label1 ----
        label1.setText("Amount");
        label1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(35, 140), label1.getPreferredSize()));

        //---- lblType ----
        lblType.setText("Itemtype");
        lblType.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(lblType);
        lblType.setBounds(35, 85, 80, 25);

        //---- txtfldIType ----
        txtfldIType.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtfldIType.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(txtfldIType);
        txtfldIType.setBounds(160, 85, 565, 28);

        //---- sldrAmount ----
        sldrAmount.setFont(new Font("Dialog", Font.PLAIN, 12));
        sldrAmount.setMaximum(64);
        sldrAmount.setMinimum(1);
        sldrAmount.setMajorTickSpacing(3);
        sldrAmount.setMinorTickSpacing(1);
        sldrAmount.setValue(1);
        sldrAmount.setPaintLabels(true);
        sldrAmount.setPaintTicks(true);
        contentPane.add(sldrAmount);
        sldrAmount.setBounds(160, 145, 575, 35);

        //---- labelDMG ----
        labelDMG.setText("Damage");
        labelDMG.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(labelDMG);
        labelDMG.setBounds(35, 195, 70, 25);

        //---- sldrDMG ----
        sldrDMG.setFont(new Font("Dialog", Font.PLAIN, 12));
        sldrDMG.setMaximum(15);
        sldrDMG.setMajorTickSpacing(1);
        sldrDMG.setMinorTickSpacing(1);
        sldrDMG.setValue(0);
        sldrDMG.setPaintLabels(true);
        sldrDMG.setPaintTicks(true);
        contentPane.add(sldrDMG);
        sldrDMG.setBounds(155, 200, 580, 35);

        //---- labelSLot ----
        labelSLot.setText("Slot");
        labelSLot.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPane.add(labelSLot);
        labelSLot.setBounds(35, 260, 70, 25);

        //---- sldrSlot ----
        sldrSlot.setFont(new Font("Dialog", Font.PLAIN, 12));
        sldrSlot.setMaximum(9);
        sldrSlot.setMinimum(1);
        sldrSlot.setMajorTickSpacing(1);
        sldrSlot.setMinorTickSpacing(1);
        sldrSlot.setValue(1);
        sldrSlot.setPaintLabels(true);
        sldrSlot.setPaintTicks(true);
        contentPane.add(sldrSlot);
        sldrSlot.setBounds(155, 265, 580, 35);

        //---- btnConfirm ----
        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(e -> btnConfirmActionPerformed(e));
        contentPane.add(btnConfirm);
        btnConfirm.setBounds(290, 465, 445, 65);
        contentPane.add(lblSpacer);
        lblSpacer.setBounds(0, 525, 760, 35);

        //---- btnEnchantments ----
        btnEnchantments.setText("Enchantments");
        btnEnchantments.addActionListener(e -> btnEnchantmentsActionPerformed(e));
        contentPane.add(btnEnchantments);
        btnEnchantments.setBounds(35, 320, 700, btnEnchantments.getPreferredSize().height);

        //---- btnLore ----
        btnLore.setText("Lore");
        btnLore.addActionListener(e -> btnLoreActionPerformed(e));
        contentPane.add(btnLore);
        btnLore.setBounds(35, 360, 700, 32);

        //---- btnFlags ----
        btnFlags.setText("Flags");
        btnFlags.addActionListener(e -> btnFlagsActionPerformed(e));
        contentPane.add(btnFlags);
        btnFlags.setBounds(35, 400, 700, btnFlags.getPreferredSize().height);

        //---- btnTypes ----
        btnTypes.setText("All valid Types");
        btnTypes.addActionListener(e -> btnTypesActionPerformed(e));
        contentPane.add(btnTypes);
        btnTypes.setBounds(40, 465, 220, 60);

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
    private JLabel lblName;
    private JTextField txtfldName;
    private JLabel label1;
    private JLabel lblType;
    private JTextField txtfldIType;
    private JSlider sldrAmount;
    private JLabel labelDMG;
    private JSlider sldrDMG;
    private JLabel labelSLot;
    private JSlider sldrSlot;
    private AlphaButton btnConfirm;
    private JLabel lblSpacer;
    private AlphaButton btnEnchantments;
    private AlphaButton btnLore;
    private AlphaButton btnFlags;
    private AlphaButton btnTypes;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public HashMap<String, Integer> getEnchantments() {
        return enchantments;
    }

    public void setLore(String[] lore) {
        this.lore = lore;
    }

    public void setFlags(String[] flags) {
        this.flags = flags;
    }
}
