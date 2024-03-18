package dk.nydt.discordmerge.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.nydt.discordmerge.events.minecraft.DiscordUnlink;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtDiscordUnlink extends SkriptEvent {

    static {
        Skript.registerEvent("discord unlink", EvtDiscordUnlink.class, DiscordUnlink.class, "[DiscordMerge ][on ]discord unlink");

        EventValues.registerEventValue(DiscordUnlink.class, Player.class, new Getter<Player, DiscordUnlink>() {
            public Player get(DiscordUnlink event) {
                return event.getPlayer();
            }
        }, 0);
    }
    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "discord unlink";
    }
}
