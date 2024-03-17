package dk.nydt.discordmerge.commands.minecraft;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import dk.nydt.discordmerge.events.minecraft.DiscordClaimBoost;
import dk.nydt.discordmerge.handlers.SQLiteHandler;
import dk.nydt.discordmerge.objects.BoostCode;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

@CommandAlias("boost")
public class BoostCommand extends BaseCommand {
    @Default
     @Syntax("<code>")
    public void onDefault(CommandSender sender, String code) {
        Player player = (Player) sender;
        try {
            List<BoostCode> codes = SQLiteHandler.getBoostCodeDao().queryForEq("code", code);
            if (codes.isEmpty()) {
                player.sendMessage("Invalid code!");
                return;
            }
            BoostCode boostCode = codes.get(0);
            boostCode.delete();
            player.sendMessage("You have successfully claimed your boost reward!");
            DiscordClaimBoost discordClaimBoost = new DiscordClaimBoost(player);
            Bukkit.getServer().getPluginManager().callEvent(discordClaimBoost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
