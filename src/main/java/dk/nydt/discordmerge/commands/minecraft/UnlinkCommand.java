package dk.nydt.discordmerge.commands.minecraft;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dk.nydt.discordmerge.handlers.SQLiteHandler;
import dk.nydt.discordmerge.objects.LinkedUser;
import dk.nydt.discordmerge.objects.MinecraftAccount;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@CommandAlias("unlink")
public class UnlinkCommand extends BaseCommand {
    @Default
    public void onDefault(CommandSender sender) {
        Player player = (Player) sender;
        try {
            List<MinecraftAccount> minecraftAccount = SQLiteHandler.getMinecraftAccountDao().queryForEq("uuid", player.getUniqueId());
            if(minecraftAccount.isEmpty()) {
                player.sendMessage("You are not linked to a Discord account!");
            } else {
                LinkedUser linkedUser = minecraftAccount.get(0).getLinkedUser();
                if (linkedUser != null) {
                    linkedUser.removeMinecraftAccount(minecraftAccount.get(0));
                    player.sendMessage("You have successfully unlinked your account!");
                }
            }
        } catch (Exception e) {
            player.sendMessage("You are not linked to a Discord account!");
        }
    }
}
