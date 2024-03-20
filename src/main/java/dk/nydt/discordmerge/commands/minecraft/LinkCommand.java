package dk.nydt.discordmerge.commands.minecraft;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.configs.Messages;
import dk.nydt.discordmerge.events.minecraft.DiscordLinkEvent;
import dk.nydt.discordmerge.handlers.CodeHandler;
import dk.nydt.discordmerge.handlers.ObjectHandler;
import dk.nydt.discordmerge.handlers.SQLiteHandler;
import dk.nydt.discordmerge.objects.LinkedUser;
import dk.nydt.discordmerge.objects.MinecraftAccount;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@CommandAlias("link")
public class LinkCommand extends BaseCommand {
    private final CodeHandler codeHandler = DiscordMerge.getCodeHandler();
    private final ObjectHandler objectHandler = DiscordMerge.getObjectHandler();
    private final Messages messages = DiscordMerge.getMessages();
    @Default
    @Syntax("<code>")
    public void onDefault(CommandSender sender, String code) {
        Player player = (Player) sender;
        try {
            List<MinecraftAccount> minecraftAccount = SQLiteHandler.getMinecraftAccountDao().queryForEq("uuid", player.getUniqueId());
            if (minecraftAccount.isEmpty()) {
                if(codeHandler.getLinkCodes().containsKey(code)) {
                    objectHandler.createMerge(codeHandler.getLinkCodes().get(code), player.getName(), player.getUniqueId());
                    codeHandler.getLinkCodes().remove(code);
                    for(String message : messages.minecraftLinkCommandSuccess) {
                        player.sendMessage(message);
                    }
                    DiscordLinkEvent discordLink = new DiscordLinkEvent(player);
                    Bukkit.getServer().getPluginManager().callEvent(discordLink);
                } else {
                    for(String message : messages.minecraftLinkCommandInvalidCode) {
                        player.sendMessage(message);
                    }
                }
            } else {
                LinkedUser linkedUser = minecraftAccount.get(0).getLinkedUser();
                if(linkedUser != null) {
                    for(String message : messages.minecraftLinkCommandAlreadyLinked) {
                        message = message.replace("%discord%", linkedUser.getDiscordId());
                        player.sendMessage(message);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            for(String message : messages.minecraftLinkCommandTryAgain) {
                player.sendMessage(message);
            }
        }
    }
}
