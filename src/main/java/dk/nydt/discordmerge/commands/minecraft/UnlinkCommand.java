package dk.nydt.discordmerge.commands.minecraft;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.handlers.ObjectHandler;
import dk.nydt.discordmerge.handlers.SQLiteHandler;
import dk.nydt.discordmerge.objects.LinkedUser;
import dk.nydt.discordmerge.objects.MinecraftAccount;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("unlink")
public class UnlinkCommand extends BaseCommand {
    ObjectHandler objectHandler = DiscordMerge.getObjectHandler();
    @Default
    public void onDefault(CommandSender sender) {
        Player player = (Player) sender;
        try {
            MinecraftAccount minecraftAccount = SQLiteHandler.getMinecraftAccountDao().queryForEq("uuid", player.getUniqueId()).get(0);
            LinkedUser linkedUser = minecraftAccount.getLinkedUser();
            if (linkedUser != null) {
                linkedUser.removeMinecraftAccount(minecraftAccount);
                player.sendMessage("You have successfully unlinked your account!");
            }
        } catch (Exception e) {
            player.sendMessage("You are not linked to a Discord account!");
        }
    }
}
