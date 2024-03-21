package dk.nydt.discordmerge.commands.minecraft;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.configs.Config;
import dk.nydt.discordmerge.configs.Messages;
import dk.nydt.discordmerge.events.minecraft.DiscordClaimRolesEvent;
import dk.nydt.discordmerge.handlers.SQLiteHandler;
import dk.nydt.discordmerge.objects.LinkedUser;
import dk.nydt.discordmerge.objects.MinecraftAccount;
import dk.nydt.discordmerge.utils.ColorUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.restaction.CacheRestAction;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@CommandAlias("claim")
public class ClaimCommand extends BaseCommand {
    private final Config configurations = DiscordMerge.getConfiguration();
    private final Messages messages = DiscordMerge.getMessages();
    @Default
    public void onDefault(CommandSender sender) {
        Player player = (Player) sender;
        try {
            List<MinecraftAccount> minecraftAccount = SQLiteHandler.getMinecraftAccountDao().queryForEq("uuid", player.getUniqueId());
            if(minecraftAccount.isEmpty()) {
                for(String message : messages.minecraftClaimCommandNotLinked) {
                    player.sendMessage(ColorUtils.getColor(message));
                }
            } else {
                LinkedUser linkedUser = minecraftAccount.get(0).getLinkedUser();
                if (linkedUser != null) {
                    Guild guild = DiscordMerge.getJda().getGuildById(configurations.guildId);
                    if(guild == null) {
                        for(String message : messages.minecraftClaimCommandNoGuild) {
                            player.sendMessage(ColorUtils.getColor(message));
                        }
                        return;
                    }
                    CacheRestAction<Member> cache = guild.retrieveMember(User.fromId(linkedUser.getDiscordId()));
                    Member member = cache.complete();
                    if(member == null) {
                        for(String message : messages.minecraftClaimCommandNoMember) {
                            player.sendMessage(ColorUtils.getColor(message));
                        }
                        return;
                    }
                    Map<Integer, Map<String, String>> rolePermissions = configurations.rolePermissions;
                    for(Map.Entry<Integer, Map<String, String>> entry : rolePermissions.entrySet()) {
                        if(player.hasPermission(entry.getValue().get("permission"))) {
                            Role role = guild.getRoleById(entry.getValue().get("roleId"));
                            if(role == null) {
                                for(String message : messages.minecraftClaimCommandNoRole) {
                                    player.sendMessage(ColorUtils.getColor(message));
                                }
                                continue;
                            }
                            if(member.getRoles().contains(role)) {
                                for(String message : messages.minecraftClaimCommandAlreadyHasRole) {
                                    player.sendMessage(ColorUtils.getColor(message.replace("%role%", role.getName())));
                                }
                                continue;
                            }
                            guild.addRoleToMember(member, role).queue();
                            for(String message : messages.minecraftClaimCommandSuccess) {
                                player.sendMessage(ColorUtils.getColor(message.replace("%role%", role.getName())));
                            }
                            DiscordClaimRolesEvent discordClaimRoles = new DiscordClaimRolesEvent(player);
                            Bukkit.getServer().getPluginManager().callEvent(discordClaimRoles);
                        } else {
                            for(String message : messages.minecraftClaimCommandNoAvailableRoles) {
                                player.sendMessage(ColorUtils.getColor(message));
                            }
                        }
                    }
                } else {
                    for(String message : messages.minecraftClaimCommandNotLinked) {
                        player.sendMessage(ColorUtils.getColor(message));
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
