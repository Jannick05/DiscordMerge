package dk.nydt.discordmerge.events.discord;

import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.handlers.ObjectHandler;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class MemberUpdateBoost extends ListenerAdapter {
    private final ObjectHandler objectHandler = DiscordMerge.getObjectHandler();
    public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent event) {
        Member member = event.getMember();
        if(Integer.parseInt(String.valueOf(event.getOldValue())) > Integer.parseInt(String.valueOf(event.getNewValue()))) {
            return;
        }
        member.getUser().openPrivateChannel().queue(privateChannel -> {
            try {
                privateChannel.sendMessage("You just boosted the server! Here's your code: " + objectHandler.createBoostCode()).queue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
