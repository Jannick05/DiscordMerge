package dk.nydt.discordmerge.addons;

import ch.njol.skript.Skript;
import dk.nydt.discordmerge.DiscordMerge;
import lombok.Getter;
import org.bukkit.Bukkit;

public class SkriptAddon {
    @Getter
    private static Skript skript;
    @Getter
    private static ch.njol.skript.SkriptAddon addon;
    public SkriptAddon() {
        if(!Bukkit.getServer().getPluginManager().isPluginEnabled("Skript")) {
            return;
        }
        try {
            skript = (Skript) Bukkit.getPluginManager().getPlugin("Skript");
            try {
                addon = Skript.registerAddon(DiscordMerge.getInstance());
                addon.loadClasses("dk.nydt.discordmerge", "skript");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
