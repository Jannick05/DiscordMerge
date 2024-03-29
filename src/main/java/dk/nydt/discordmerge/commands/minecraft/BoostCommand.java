package dk.nydt.discordmerge.commands.minecraft;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.configs.Messages;
import dk.nydt.discordmerge.events.minecraft.DiscordClaimBoostEvent;
import dk.nydt.discordmerge.handlers.SQLiteHandler;
import dk.nydt.discordmerge.objects.BoostCode;
import dk.nydt.discordmerge.utils.ColorUtils;
import lombok.experimental.ExtensionMethod;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

@ExtensionMethod({ColorUtils.class})
@CommandAlias("boost")
public class BoostCommand extends BaseCommand {
    private final Messages messages = DiscordMerge.getMessages();
    @Default
    @Syntax("<code>")
    public void onDefault(CommandSender sender, String code) {
        Player player = (Player) sender;
        try {
            List<BoostCode> codes = SQLiteHandler.getBoostCodeDao().queryForEq("code", code);
            if (codes.isEmpty()) {
                for(String message : messages.minecraftBoostCommandInvalidCode) {
                    player.sendMessage(ColorUtils.getColor(message));
                }
                return;
            }

            BoostCode boostCode = codes.get(0);
            boostCode.delete();
            for(String message : messages.minecraftBoostCommandSuccess) {
                player.sendMessage(ColorUtils.getColor(message));
            }
            DiscordClaimBoostEvent discordClaimBoost = new DiscordClaimBoostEvent(player);
            Bukkit.getServer().getPluginManager().callEvent(discordClaimBoost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
