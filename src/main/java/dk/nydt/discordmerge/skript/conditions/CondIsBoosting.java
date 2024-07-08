package dk.nydt.discordmerge.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.configs.Config;
import dk.nydt.discordmerge.configs.Messages;
import dk.nydt.discordmerge.handlers.ObjectHandler;
import dk.nydt.discordmerge.handlers.SQLiteHandler;
import dk.nydt.discordmerge.objects.LinkedUser;
import dk.nydt.discordmerge.objects.MinecraftAccount;
import dk.nydt.discordmerge.utils.ColorUtils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class CondIsBoosting extends Condition {
    private final Config config = DiscordMerge.getConfiguration();

    private Expression<Player> player;
    private Expression<String> id;

    static {
        Skript.registerCondition(CondIsBoosting.class, "[discordmerge ][if ]%player% is boosting");
    }
    @Override
    public boolean check(Event e) {
        Guild guild = Objects.requireNonNull(DiscordMerge.getJda().getGuildById(config.guildId));

        try {
            List<MinecraftAccount> minecraftAccount = SQLiteHandler.getMinecraftAccountDao().queryForEq("uuid", player.getSingle(e).getUniqueId());
            if(minecraftAccount.isEmpty()) {
                return false;
            }
            LinkedUser linkedUser = minecraftAccount.get(0).getLinkedUser();
            if (linkedUser == null) {
                return false;
            }
            Member member = DiscordMerge.getObjectHandler().getCachedUser(linkedUser.getDiscordId());
            return member.getRoles().contains(guild.getRoleById(Objects.requireNonNull(config.boosterRoleId)));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "[discordmerge ][if ]%player% is boosting";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        return true;
    }
}
