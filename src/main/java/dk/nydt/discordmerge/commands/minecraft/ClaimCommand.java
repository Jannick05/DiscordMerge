package dk.nydt.discordmerge.commands.minecraft;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.commands.configs.Config;
import dk.nydt.discordmerge.handlers.SQLiteHandler;
import dk.nydt.discordmerge.objects.LinkedUser;
import dk.nydt.discordmerge.objects.MinecraftAccount;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.restaction.CacheRestAction;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@CommandAlias("claim")
public class ClaimCommand extends BaseCommand {
    private final Config configurations = DiscordMerge.getConfiguration();
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
                    Guild guild = DiscordMerge.getJda().getGuildById(configurations.guildId);
                    if(guild == null) {
                        player.sendMessage("The guild is not in the discord, report to server admin!");
                        return;
                    }
                    CacheRestAction<Member> cache = guild.retrieveMember(User.fromId(linkedUser.getDiscordId()));
                    Member member = cache.complete();
                    if(member == null) {
                        player.sendMessage("The member is not in the discord, report to server admin!");
                        return;
                    }
                    Map<Integer, Map<String, String>> rolePermissions = configurations.rolePermissions;
                    for(Map.Entry<Integer, Map<String, String>> entry : rolePermissions.entrySet()) {
                        if(player.hasPermission(entry.getValue().get("permission"))) {
                            Role role = guild.getRoleById(entry.getValue().get("roleId"));
                            if(role == null) {
                                player.sendMessage("The role is not in the discord, report to server admin!");
                                continue;
                            }
                            if(member.getRoles().contains(role)) {
                                player.sendMessage("You already have the role!");
                                continue;
                            }
                            guild.addRoleToMember(member, role).queue();
                            player.sendMessage("You have successfully claimed the role!");
                        } else {
                            player.sendMessage("No available roles to claim!");
                        }
                    }
                } else {
                    player.sendMessage("You are not linked to a Discord account!");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
