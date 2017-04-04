package de.alphahelix.uhcconfigeditor;

import de.alphahelix.uhcconfigeditor.ui.ButtonUI;

import javax.swing.*;
import java.awt.*;

public class AlphaRadioButton extends JRadioButton {

    public AlphaRadioButton() {
        setBackground(Color.GRAY);
        setForeground(Color.WHITE);
        setUI(new ButtonUI());
    }
}
