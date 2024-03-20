package dk.nydt.discordmerge.events.minecraft;

import lombok.Getter;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DiscordClaimRolesEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    private final Player player;
    private Boolean cancelled = Boolean.FALSE;

    public DiscordClaimRolesEvent(Player player) {
        this.player = player;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
