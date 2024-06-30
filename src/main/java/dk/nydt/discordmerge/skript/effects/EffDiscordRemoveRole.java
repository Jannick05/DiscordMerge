package dk.nydt.discordmerge.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.configs.Config;
import dk.nydt.discordmerge.handlers.SQLiteHandler;
import dk.nydt.discordmerge.objects.LinkedUser;
import dk.nydt.discordmerge.objects.MinecraftAccount;
import net.dv8tion.jda.api.entities.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.List;

public class EffDiscordRemoveRole extends Effect {

    private final Config configurations = DiscordMerge.getConfiguration();
    private Expression<Player> player;
    private Expression<String> id;

    static {
        Skript.registerEffect(EffDiscordRemoveRole.class, "[DiscordMerge ]remove role with id %string% from %player%");
    }

    @Override
    protected void execute(@NotNull Event event) {
        final Player player = this.player.getSingle(event);
        final String id = this.id.getSingle(event);

        if (player == null || id == null)
            return;

        try {
            List<MinecraftAccount> minecraftAccount = SQLiteHandler.getMinecraftAccountDao().queryForEq("uuid", player.getUniqueId());
            if (minecraftAccount.isEmpty()) {
                System.out.println("Minecraft account is empty");
                return;
            }
            LinkedUser linkedUser = minecraftAccount.get(0).getLinkedUser();
            UserSnowflake user = User.fromId(linkedUser.getDiscordId());
            Guild guild = DiscordMerge.getJda().getGuildById(configurations.guildId);
            if (guild == null) {
                System.out.println("Guild is null");
                return;
            }
            Member member = guild.retrieveMember(user).complete();
            if (member == null) {
                System.out.println("Member is null");
                return;
            }
            Role role = guild.getRoleById(id);
            if (role == null) {
                System.out.println("Role is null");
                return;
            }
            if(member.getRoles().contains(role)) {
                guild.removeRoleFromMember(member, role).queue();
            } else {
                System.out.println("Member does not have role");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "[DiscordMerge ]remove role with id %string% from %player%";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[1];
        this.id = (Expression<String>) expressions[0];
        return true;
    }
}
