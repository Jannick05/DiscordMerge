package dk.nydt.discordmerge.commands.minecraft;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.configs.Config;
import dk.nydt.discordmerge.configs.Messages;
import dk.nydt.discordmerge.utils.ColorUtils;
import org.bukkit.command.CommandSender;

@CommandAlias("discordmerge")
@CommandPermission("discordmerge.admin")
public class DiscordMergeCommand extends BaseCommand {
    private final Messages messages = DiscordMerge.getMessages();
    private final Config config = DiscordMerge.getConfiguration();
    @Subcommand("reload")
    public void onReload(CommandSender sender) {
        long start = System.currentTimeMillis();

        try {
            messages.load();
            config.load();
            for(String message : messages.minecraftReloadCommandSuccess) {
                message = message.replace("%time%", String.valueOf(System.currentTimeMillis() - start));
                sender.sendMessage(ColorUtils.getColor(message));
            }
        } catch (Exception e) {
            e.printStackTrace();
            for(String message : messages.minecraftReloadCommandNotReloaded) {
                sender.sendMessage(ColorUtils.getColor(message));
            }
        }
    }
}
