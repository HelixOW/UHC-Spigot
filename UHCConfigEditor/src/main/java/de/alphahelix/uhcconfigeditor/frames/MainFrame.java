/*
 * Created by JFormDesigner on Wed Mar 29 16:34:31 CEST 2017
 */

package de.alphahelix.uhcconfigeditor.frames;

import de.alphahelix.uhcconfigeditor.AlphaButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Sebasian Meier
 */
public class MainFrame extends JFrame {
    public MainFrame() {
        initComponents();
    }

    private void btnAchievementsActionPerformed(ActionEvent e) {
        new AchievementsFrame();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Sebasian Meier
        btnBorder = new AlphaButton();
        btnConfirm = new AlphaButton();
        btnCrates = new AlphaButton();
        btnDeathmsg = new AlphaButton();
        btnDrops = new AlphaButton();
        btnKits = new AlphaButton();
        btnLobby = new AlphaButton();
        btnMessages = new AlphaButton();
        btnMOTD = new AlphaButton();
        btnGeneral = new AlphaButton();
        btnRanks = new AlphaButton();
        btnRecipes = new AlphaButton();
        btnScenarios = new AlphaButton();
        btnScoreboard = new AlphaButton();
        btnSpectator = new AlphaButton();
        btnStats = new AlphaButton();
        btnStatus = new AlphaButton();
        btnTab = new AlphaButton();
        btnTeams = new AlphaButton();
        btnTimers = new AlphaButton();
        btnUnits = new AlphaButton();
        btnAchievements = new AlphaButton();

        //======== this ========
        setTitle("UHC Config Editor");
        setIconImage(new ImageIcon("C:\\Code\\icon.png").getImage());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- btnBorder ----
        btnBorder.setText("Border");
        btnBorder.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnBorder);
        btnBorder.setBounds(195, 20, 130, 75);

        //---- btnConfirm ----
        btnConfirm.setText("Confirm");
        btnConfirm.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnConfirm);
        btnConfirm.setBounds(350, 20, 130, 75);

        //---- btnCrates ----
        btnCrates.setText("Crates");
        btnCrates.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnCrates);
        btnCrates.setBounds(510, 20, 130, 75);

        //---- btnDeathmsg ----
        btnDeathmsg.setText("Deathmessages");
        btnDeathmsg.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnDeathmsg);
        btnDeathmsg.setBounds(670, 20, 140, 75);

        //---- btnDrops ----
        btnDrops.setText("Drops");
        btnDrops.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnDrops);
        btnDrops.setBounds(840, 20, 130, 75);

        //---- btnKits ----
        btnKits.setText("Kits");
        btnKits.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnKits);
        btnKits.setBounds(35, 125, 130, 75);

        //---- btnLobby ----
        btnLobby.setText("Lobby");
        btnLobby.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnLobby);
        btnLobby.setBounds(195, 125, 130, 75);

        //---- btnMessages ----
        btnMessages.setText("Messages");
        btnMessages.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnMessages);
        btnMessages.setBounds(350, 125, 130, 75);

        //---- btnMOTD ----
        btnMOTD.setText("MOTD");
        btnMOTD.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnMOTD);
        btnMOTD.setBounds(510, 125, 130, 75);

        //---- btnGeneral ----
        btnGeneral.setText("General");
        btnGeneral.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnGeneral);
        btnGeneral.setBounds(675, 130, 130, 75);

        //---- btnRanks ----
        btnRanks.setText("Ranks");
        btnRanks.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnRanks);
        btnRanks.setBounds(840, 130, 130, 75);

        //---- btnRecipes ----
        btnRecipes.setText("Recipes");
        btnRecipes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnRecipes);
        btnRecipes.setBounds(40, 230, 130, 75);

        //---- btnScenarios ----
        btnScenarios.setText("Scenarios");
        btnScenarios.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnScenarios);
        btnScenarios.setBounds(195, 230, 130, 75);

        //---- btnScoreboard ----
        btnScoreboard.setText("Scoreboard");
        btnScoreboard.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnScoreboard);
        btnScoreboard.setBounds(350, 230, 130, 75);

        //---- btnSpectator ----
        btnSpectator.setText("Spectator");
        btnSpectator.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnSpectator);
        btnSpectator.setBounds(510, 230, 130, 75);

        //---- btnStats ----
        btnStats.setText("Statsmessages");
        btnStats.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnStats);
        btnStats.setBounds(675, 230, 135, 75);

        //---- btnStatus ----
        btnStatus.setText("Status");
        btnStatus.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        contentPane.add(btnStatus);
        btnStatus.setBounds(840, 230, 130, 75);

        //---- btnTab ----
        btnTab.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnTab.setText("Tablist");
        contentPane.add(btnTab);
        btnTab.setBounds(195, 330, 130, 75);

        //---- btnTeams ----
        btnTeams.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnTeams.setText("Teams");
        contentPane.add(btnTeams);
        btnTeams.setBounds(350, 330, 130, 75);

        //---- btnTimers ----
        btnTimers.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnTimers.setText("Timers");
        contentPane.add(btnTimers);
        btnTimers.setBounds(510, 330, 130, 75);

        //---- btnUnits ----
        btnUnits.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnUnits.setText("Units");
        contentPane.add(btnUnits);
        btnUnits.setBounds(680, 330, 130, 75);

        //---- btnAchievements ----
        btnAchievements.setText("Achievements");
        btnAchievements.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnAchievements.addActionListener(e -> btnAchievementsActionPerformed(e));
        contentPane.add(btnAchievements);
        btnAchievements.setBounds(35, 20, 130, 75);

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
        setResizable(false);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Sebasian Meier
    private AlphaButton btnBorder;
    private AlphaButton btnConfirm;
    private AlphaButton btnCrates;
    private AlphaButton btnDeathmsg;
    private AlphaButton btnDrops;
    private AlphaButton btnKits;
    private AlphaButton btnLobby;
    private AlphaButton btnMessages;
    private AlphaButton btnMOTD;
    private AlphaButton btnGeneral;
    private AlphaButton btnRanks;
    private AlphaButton btnRecipes;
    private AlphaButton btnScenarios;
    private AlphaButton btnScoreboard;
    private AlphaButton btnSpectator;
    private AlphaButton btnStats;
    private AlphaButton btnStatus;
    private AlphaButton btnTab;
    private AlphaButton btnTeams;
    private AlphaButton btnTimers;
    private AlphaButton btnUnits;
    private AlphaButton btnAchievements;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
