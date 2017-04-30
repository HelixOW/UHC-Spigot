package de.alphahelix.uhc.commands;

import de.alphahelix.alphalibary.command.SimpleCommand;
import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.register.UHCFileRegister;
import de.alphahelix.uhc.util.schematic.SchematicManagerUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SchematicCommand extends SimpleCommand {

    public SchematicCommand() {
        super("schematic", "Create your own uhc schematics", "schem");
    }

    @Override
    public boolean execute(CommandSender cs, String label, String[] args) {

        if (!(cs instanceof Player))
            return true;

        Player p = (Player) cs;

        if (!p.hasPermission("uhc.admin")) {
            p.sendMessage(UHC.getPrefix()
                    + UHCFileRegister.getMessageFile().getNoPermissions());
            return true;
        }

        if (args.length == 0) {
            TextComponent msg = new TextComponent(UHC.getPrefix() + "?7Please click ?chere ?7to see the wiki");
            msg.setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/AlphaHelixDev/UHC-Spigot/wiki"));
            p.spigot().sendMessage(msg);
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("paste")) {
                if (SchematicManagerUtil.paste(p)) {
                    p.sendMessage(UHC.getPrefix() + "You've pasted the schematic");
                } else {
                    p.sendMessage(UHC.getPrefix() + "Can't find any loaded schematic");
                }
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("load")) {
                if (SchematicManagerUtil.load(args[1], p))
                    p.sendMessage(UHC.getPrefix() + "You've successfully loaded the schematic " + args[1]);
                else
                    p.sendMessage(UHC.getPrefix() + "Can't find schematic :" + args[1]);
            } else if (args[0].equalsIgnoreCase("save")) {
                if (SchematicManagerUtil.getFirstLocation(p) != null && SchematicManagerUtil.getSecondLocation(p) != null) {
                    if (SchematicManagerUtil.save(args[1], SchematicManagerUtil.convertToStringlist(
                            SchematicManagerUtil.getBlocks(SchematicManagerUtil.getFirstLocation(p), SchematicManagerUtil.getSecondLocation(p)), p.getLocation())))
                        p.sendMessage(UHC.getPrefix() + "You've successfully saved the schematic " + args[1]);
                    else
                        p.sendMessage(UHC.getPrefix() + "Can't process save for schematic" + args[1]);
                } else {
                    p.sendMessage(UHC.getPrefix() + "You've to define two points first.");
                }
            } else if (args[0].equalsIgnoreCase("delete")) {
                if (SchematicManagerUtil.delete(args[1]))
                    p.sendMessage(UHC.getPrefix() + "You've deleted the schematic " + args[1]);
                else
                    p.sendMessage(UHC.getPrefix() + "Can't find schematic :" + args[1]);
            }
        } else {
            TextComponent msg = new TextComponent(UHC.getPrefix() + "?7Please click ?chere ?7to see the wiki");
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
