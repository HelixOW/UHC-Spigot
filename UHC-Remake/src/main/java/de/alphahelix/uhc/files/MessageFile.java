package de.alphahelix.uhc.files;

import de.alphahelix.alphaapi.file.SimpleFile;
import de.alphahelix.uhc.enums.GState;
import de.alphahelix.uhc.instances.Crate;
import de.alphahelix.uhc.instances.Kit;
import de.alphahelix.uhc.instances.UHCTeam;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MessageFile extends SimpleFile {

    public MessageFile() {
        super("messages.uhc");
    }

    @Override
    public void addValues() {
        setDefault("Player has joined", "&7+[player]");
        setDefault("Player has left", "&7-[player]");
        setDefault("Full", "We are sorry but this round is full!");
        setDefault("Scenariomode", "&7This UHC server is running in &cScenario Mode&7. Due to that the server doesn't allow kits.");
        setDefault("Kitmode", "&7This UHC server is running in &cKits Mode.");
        setDefault("No permissions", "&7You don't have &cpermissions &7to execute that command!");
        setDefault("Command not found", "&7Can't find the command :C. Try again!");
        setDefault("Kit chosen", "&7You've &asuccessfully chosen &7the kit &8: &a[kit]&7!");
        setDefault("Not enough coins", "&7You don't have &cenough &7coins to buy &7the kit &8: &a[kit]&7!");
        setDefault("Lobby time left info", "&aGame starts in &7[time] [unit]&8.");
        setDefault("Period of Peace time left info", "&aDamage in &7[time] [unit]&8.");
        setDefault("Period of Peace ended", "&cDanger&7! The damage is now &4on&7!");
        setDefault("WarmUp time left info", "&aPvP is enabled in &7[time] [unit]&8.");
        setDefault("WarmUp ended", "&cAttention&7,&c please&7! PvP is &4now enabled&7!");
        setDefault("Deathmatch time left info", "&aDeathmatch is in &7[time] [unit]&8.");
        setDefault("Deathmatch ended", "&cAttention&7,&c please&7! It's time for the &4deathmatch&7!");
        setDefault("End time left info", "&aServer will stop in &7[time] [unit]&8.");
        setDefault("End ended", "&cRestart&7!");
        setDefault("Team FFA", "&cGood Luck. You'll need it!");
        setDefault("Winning message", "&7The player &a[player] &7has &awon UHC&7.");
        setDefault("Not enough players", "&7There are &cnot enough &7players online. &cRestarting timer&7.");
        setDefault("Border has moved", "&7The Border has moved by &c[blocks] blocks&7!");
        setDefault("Picked team", "&7You've picked the team &b[team]&7!");
        setDefault("Team is full", "&7Sorry, but you &ccan't join &7this team!");
        setDefault("First crafted item", "&7[player] has crafted a &a[item] &7for the first time.");
        setDefault("Crate dropped", "&7You just got a [crate] crate &7dropped!");
        setDefault("Coins added", "&7You just got &a[amount] Coins&7!");
        setDefault("Points added", "&7You just got &a[amount] Points&7!");
        setDefault("Kit added", "&7You just got the &a[kit]&7 Kit!");
        setDefault("Ranklist", "&7The ranks are: [ranks]");
        setDefault("Achievement unlocked", "&7You just unlocked the achievement &8: &a[achievement]");
        setDefault("No reward for rank", "&7Your current rank doesn't have any rewards.");
        setDefault("Reward cooldown", "&7You have to wait &c[time] &7more to get the &anext &7reward!");
    }

    public String getNoReward() {
        return getColorString("No reward for rank");
    }

    public String getRewardCooldown(String time) {
        return getColorString("Reward cooldown").replace("[time]", time);
    }

    public String getKitAdded(Kit k) {
        return getColorString("Kit added").replace("[kit]", k.getName());
    }

    public String getCoinsAdded(String added) {
        return getColorString("Coins added").replace("[amount]", added);
    }

    public String getPointsAdded(String added) {
        return getColorString("Points added").replace("[amount]", added);
    }

    public String getJoin() {
        return getColorString("Player has joined");
    }

    public String getLeave() {
        return getColorString("Player has left");
    }

    public String getFull() {
        return getColorString("Full");
    }

    public String getScenariomode() {
        return getColorString("Scenariomode");
    }

    public String getNoPermissions() {
        return getColorString("No permissions");
    }

    public String getCommandNotFound() {
        return getColorString("Command not found");
    }

    public String getKitChosen(Kit kit) {
        return getColorString("Kit chosen").replace("[kit]", kit.getName());
    }

    public String getNotEnoughCoins(Kit kit) {
        return getColorString("Not enough coins").replace("[kit]", kit.getName());
    }

    public String getTimeLeftInfo(double time, String unit) {
        switch (GState.getCurrentState()) {
            case LOBBY:
                return getColorString("Lobby time left info").replace("[time]", Double.toString(time)).replace("[unit]", unit);
            case PERIOD_OF_PEACE:
                return getColorString("Period of Peace time left info").replace("[time]", Double.toString(time)).replace("[unit]", unit);
            case WARMUP:
                return getColorString("WarmUp time left info").replace("[time]", Double.toString(time)).replace("[unit]", unit);
            case IN_GAME:
                return getColorString("Deathmatch time left info").replace("[time]", Double.toString(time)).replace("[unit]", unit);
            case DEATHMATCH_WARMUP:
                return getColorString("Deathmatch time left info").replace("[time]", Double.toString(time)).replace("[unit]", unit);
            case END:
                return getColorString("End time left info").replace("[time]", Double.toString(time)).replace("[unit]", unit);
            default:
                return "";
        }
    }

    public String getEnd() {
        switch (GState.getCurrentState()) {
            case PERIOD_OF_PEACE:
                return getColorString("Period of Peace ended");
            case WARMUP:
                return getColorString("WarmUp ended");
            case DEATHMATCH:
                return getColorString("Deathmatch ended");
            case END:
                return getColorString("End ended");
            default:
                return "";
        }
    }

    public String getTeamFFA() {
        return getColorString("Team FFA");
    }

    public String getWin(Player p) {
        return getColorString("Winning message").replace("[player]", p.getDisplayName());
    }

    public String getNotEnoughPlayers() {
        return getColorString("Not enough players");
    }

    public String getBorderHasMoved() {
        return getColorString("Border has moved");
    }

    public String getPickedTeam(UHCTeam team) {
        return getColorString("Picked team").replace("[team]", team.getPrefix() + team.getName());
    }

    public String getTeamIsFull() {
        return getColorString("Team is full");
    }

    public String getFirstCraftedItem(Player p, ItemStack item) {
        return getColorString("First crafted item").replace("[]", p.getDisplayName()).replace("[item]", item.getType().name().toLowerCase().replace("_", " "));
    }

    public String getCrateDropped(Crate crate) {
        return getColorString("Crate dropped").replace("[crate]", crate.getName());
    }

    public String getRanklist(String ranks) {
        return getColorString("Ranklist").replace("[ranks]", ranks);
    }

    public String getAchievementUnlocked() {
        return getColorString("Achievement unlocked");
    }
}
