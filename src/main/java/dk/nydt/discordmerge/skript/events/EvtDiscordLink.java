package dk.nydt.discordmerge.skript.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.nydt.discordmerge.events.minecraft.DiscordLink;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtDiscordLink extends SkriptEvent {

    static {
        Skript.registerEvent("discord link", EvtDiscordLink.class, DiscordLink.class, "[DiscordMerge ][on ]discord link");

        EventValues.registerEventValue(DiscordLink.class, OfflinePlayer.class, new Getter<OfflinePlayer, DiscordLink>() {
            public OfflinePlayer get(DiscordLink event) {
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
        return "discord link";
    }
}
