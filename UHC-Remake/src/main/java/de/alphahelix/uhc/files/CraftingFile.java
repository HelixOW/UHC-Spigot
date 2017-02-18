package de.alphahelix.uhc.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.uhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.HashMap;

public class CraftingFile extends SimpleFile<UHC> {

    public CraftingFile(UHC uhc) {
        super("recipes.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("Golden Apple-0-1.Line.top", "g g g");
        setDefault("Golden Apple-0-1.Line.mid", "g S g");
        setDefault("Golden Apple-0-1.Line.bottom", "g g g");
        setArgumentList("Golden Apple-0-1.variables", "g = gold ingot:0", "S = skull item:3");
    }

    public void registerAllCrafting() {
        for (String str : getKeys(false)) {
            Bukkit.addRecipe(getRecipe(str));
        }
    }

    private ShapedRecipe getRecipe(String itemToCraft) {
        ArrayList<Character> variables = new ArrayList<>();
        HashMap<Character, ItemStack> vars = new HashMap<>();

        for (String ingreStr : getStringList(itemToCraft + ".variables")) {
            String[] data = ingreStr.split(":");
            String[] data2 = data[0].split("=");
            char var = data2[0].charAt(0);

            Material m = Material.getMaterial(data2[1].substring(1).replace(" ", "_").toUpperCase());
            short damage = Short.parseShort(data[1]);

            vars.put(var, new ItemStack(m, 1, damage));
            variables.add(var);
        }

        String[] data = itemToCraft.split("-");

        Material m = Material.getMaterial(data[0].replace(" ", "_").toUpperCase());
        int amount = Integer.parseInt(data[2]);
        short damage = Short.parseShort(data[1]);

        ItemStack toCraft = new ItemStack(m, amount, damage);

        ShapedRecipe recipe = new ShapedRecipe(toCraft);

        String top = getString(itemToCraft + ".Line.top").replace(" ", "");
        String mid = getString(itemToCraft + ".Line.mid").replace(" ", "");
        String bottom = getString(itemToCraft + ".Line.bottom").replace(" ", "");

        recipe.shape(top, mid, bottom);

        for (Character variable : variables) {
            recipe.setIngredient(variable, vars.get(variable).getData());
        }

        return recipe;
    }
}
