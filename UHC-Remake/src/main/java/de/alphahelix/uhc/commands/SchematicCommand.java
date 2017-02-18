package de.alphahelix.uhc.commands;

import de.alphahelix.alphalibary.command.SimpleCommand;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.register.UHCRegister;
import de.alphahelix.uhc.util.schematic.SchematicManagerUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SchematicCommand extends SimpleCommand<UHC> {

    private final SchematicManagerUtil SM;

    public SchematicCommand(UHC plugin) {
        super(plugin, "schematic", "Create your own uhc schematics",  "schem");
        SM = UHCRegister.getSchematicManagerUtil();
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {

        if (!(cs instanceof Player))
            return true;

        Player p = (Player) cs;

        if (!p.hasPermission("uhc.admin")) {
            p.sendMessage(getPlugin().getPrefix()
                    + UHCFileRegister.getMessageFile().getNoPermissions());
            return true;
        }

        if (args.length == 0) {
            TextComponent msg = new TextComponent(getPlugin().getPrefix() + "?7Please click ?chere ?7to see the wiki");
            msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
            p.spigot().sendMessage(msg);
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("paste")) {
                if (SM.paste(p)) {
                    p.sendMessage(getPlugin().getPrefix() + "You've pasted the schematic");
                } else {
                    p.sendMessage(getPlugin().getPrefix() + "Can't find any loaded schematic");
                }
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("load")) {
                if (SM.load(args[1], p))
                    p.sendMessage(getPlugin().getPrefix() + "You've successfully loaded the schematic " + args[1]);
                else
                    p.sendMessage(getPlugin().getPrefix() + "Can't find schematic :" + args[1]);
            } else if (args[0].equalsIgnoreCase("save")) {
                if (SM.getFirstLocation(p) != null && SM.getSecondLocation(p) != null) {
                    if (SM.save(args[1], SM.convertToStringlist(
                            SM.getBlocks(SM.getFirstLocation(p), SM.getSecondLocation(p)), p.getLocation())))
                        p.sendMessage(getPlugin().getPrefix() + "You've successfully saved the schematic " + args[1]);
                    else
                        p.sendMessage(getPlugin().getPrefix() + "Can't process save for schematic" + args[1]);
                } else {
                    p.sendMessage(getPlugin().getPrefix() + "You've to define two points first.");
                }
            } else if (args[0].equalsIgnoreCase("delete")) {
                if (SM.delete(args[1]))
                    p.sendMessage(getPlugin().getPrefix() + "You've deleted the schematic " + args[1]);
                else
                    p.sendMessage(getPlugin().getPrefix() + "Can't find schematic :" + args[1]);
            }
        } else {
            TextComponent msg = new TextComponent(getPlugin().getPrefix() + "?7Please click ?chere ?7to see the wiki");
            msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
            p.spigot().sendMessage(msg);
            return true;
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender cs, String label, String[] args) {
        return null;
    }

}
