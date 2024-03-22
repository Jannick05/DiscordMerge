package dk.nydt.discordmerge.commands.minecraft;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.configs.Messages;
import dk.nydt.discordmerge.events.minecraft.DiscordUnlinkEvent;
import dk.nydt.discordmerge.handlers.SQLiteHandler;
import dk.nydt.discordmerge.objects.LinkedUser;
import dk.nydt.discordmerge.objects.MinecraftAccount;
import dk.nydt.discordmerge.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@CommandAlias("unlink|unverify")
public class UnlinkCommand extends BaseCommand {
    private final Messages messages = DiscordMerge.getMessages();
    @Default
    public void onDefault(CommandSender sender) {
        Player player = (Player) sender;
        try {
            List<MinecraftAccount> minecraftAccount = SQLiteHandler.getMinecraftAccountDao().queryForEq("uuid", player.getUniqueId());
            if(minecraftAccount.isEmpty()) {
                for(String message : messages.minecraftUnlinkCommandNotLinked) {
                    player.sendMessage(ColorUtils.getColor(message));
                }
            } else {
                LinkedUser linkedUser = minecraftAccount.get(0).getLinkedUser();
                if (linkedUser != null) {
                    linkedUser.removeMinecraftAccount(minecraftAccount.get(0));
                    for(String message : messages.minecraftUnlinkCommandSuccess) {
                        player.sendMessage(ColorUtils.getColor(message));
                    }

                    DiscordUnlinkEvent discordUnlink = new DiscordUnlinkEvent(player);
                    Bukkit.getServer().getPluginManager().callEvent(discordUnlink);
                }
            }
        } catch (Exception e) {
            for(String message : messages.minecraftUnlinkCommandNotLinked) {
                player.sendMessage(ColorUtils.getColor(message));
            }
        }
    }
}
