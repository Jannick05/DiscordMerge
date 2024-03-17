package dk.nydt.discordmerge.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.nydt.discordmerge.events.minecraft.DiscordLink;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtDiscordLink extends SkriptEvent {

    static {
        Skript.registerEvent("discord link", EvtDiscordLink.class, DiscordLink.class, "[DiscordMerge ][on ]discord link");

        EventValues.registerEventValue(DiscordLink.class, Player.class, new Getter<Player, DiscordLink>() {
            public Player get(DiscordLink event) {
                return event.getPlayer();
            }
        }, 0);
    }
    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        return false;
    }

    @Override
    public boolean check(Event event) {
        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return null;
    }
}
