package dk.nydt.discordmerge.addons;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import dk.nydt.discordmerge.DiscordMerge;
import lombok.Getter;
import org.bukkit.Bukkit;

public class SkriptDependency {
    @Getter
    private static Skript skript;
    @Getter
    private static SkriptAddon addon;
    public static void initialize() {
        if(!Bukkit.getServer().getPluginManager().isPluginEnabled("Skript")) {
            System.out.println("Skript is not enabled, disabling Skript addon");
            return;
        }
        try {
            skript = (Skript) Bukkit.getPluginManager().getPlugin("Skript");
            System.out.println("Skript is enabled, enabling Skript addon");
            try {
                addon = Skript.registerAddon(DiscordMerge.getInstance());
                addon.loadClasses("dk.nydt.discordmerge", "skript");
                System.out.println("Skript addon has been loaded");
            } catch (Exception e) {
                System.out.println("Skript addon could not be loaded");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Skript is not enabled, disabling Skript addon");
            e.printStackTrace();
        }
    }
}
