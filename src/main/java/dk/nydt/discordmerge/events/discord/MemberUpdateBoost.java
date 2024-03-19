package dk.nydt.discordmerge.events.discord;

import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.commands.configs.Messages;
import dk.nydt.discordmerge.handlers.ObjectHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class MemberUpdateBoost extends ListenerAdapter {
    private final Messages messages = DiscordMerge.getMessages();
    private final ObjectHandler objectHandler = DiscordMerge.getObjectHandler();
    public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent event) {
        Member member = event.getMember();
        if(Integer.parseInt(String.valueOf(event.getOldValue())) > Integer.parseInt(String.valueOf(event.getNewValue()))) {
            return;
        }
        member.getUser().openPrivateChannel().queue(privateChannel -> {
            try {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle(messages.discordBoostEventTitle);
                embedBuilder.setColor(messages.discordBoostEventEmbedColor);
                embedBuilder.setDescription(messages.discordBoostEventDescription.replace("%code%", objectHandler.createBoostCode()));
                privateChannel.sendMessageEmbeds(embedBuilder.build()).queue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
