package dk.nydt.discordmerge.commands.minecraft;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.handlers.CodeHandler;
import dk.nydt.discordmerge.handlers.ObjectHandler;
import dk.nydt.discordmerge.handlers.SQLiteHandler;
import dk.nydt.discordmerge.objects.LinkedUser;
import dk.nydt.discordmerge.objects.MinecraftAccount;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@CommandAlias("link")
public class LinkCommand extends BaseCommand {
    CodeHandler codeHandler = DiscordMerge.getCodeHandler();
    ObjectHandler objectHandler = DiscordMerge.getObjectHandler();
    @Default
    @Syntax("<code>")
    public void onDefault(CommandSender sender, String code) throws SQLException {
        Player player = (Player) sender;
        try {
            MinecraftAccount minecraftAccount = SQLiteHandler.getMinecraftAccountDao().queryForEq("uuid", player.getUniqueId()).get(0);
            LinkedUser linkedUser = minecraftAccount.getLinkedUser();
            if(linkedUser != null) {
                player.sendMessage("You are already linked to a Discord account!");
                return;
            }
            if(codeHandler.getCodes().containsKey(code)) {
                objectHandler.createMerge(codeHandler.getCodes().get(code), player.getName(), player.getUniqueId());
                codeHandler.getCodes().remove(code);
                player.sendMessage("You have successfully linked your account!");
            }
        } catch (Exception e) {
            player.sendMessage("Invalid code!");
        }
    }
}
