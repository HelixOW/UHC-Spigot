package de.alphahelix.uhcconfigeditor;

import de.alphahelix.uhcconfigeditor.ui.ButtonUI;

import javax.swing.*;
import java.awt.*;

public class AlphaButton extends JButton {
    public AlphaButton() {
        setFont(new Font("Segoe UI", Font.PLAIN, 16));
        setBackground(Color.GRAY);
        setForeground(Color.WHITE);
        setUI(new ButtonUI());
    }
}
