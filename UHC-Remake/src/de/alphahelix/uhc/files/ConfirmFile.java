package de.alphahelix.uhc.files;

import de.alphahelix.uhc.UHC;
import de.alphahelix.uhc.instances.EasyFile;

public class ConfirmFile extends EasyFile {

    public ConfirmFile(UHC uhc) {
        super("confirm.uhc", uhc);
    }

    @Override
    public void addValues() {
        setDefault("Name", "&cConfirm!");
        setDefault("Accept.Material", "stained glass pane:5");
        setDefault("Accept.name", "&aConfirm?");

        setDefault("Denied.Material", "STAINED_GLASS_PANE:14");
        setDefault("Denied.name", "&cDeny!");
    }

}
