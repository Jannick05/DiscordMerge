package dk.nydt.discordmerge.events.discord;

import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.configs.Messages;
import dk.nydt.discordmerge.handlers.ObjectHandler;
import dk.nydt.discordmerge.objects.LinkedUser;
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
            try {
                LinkedUser linkedUser = objectHandler.getLinkedUser(member.getId());
                linkedUser.setBooster(false);
                linkedUser.update();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                LinkedUser linkedUser = objectHandler.getLinkedUser(member.getId());
                linkedUser.setBooster(true);
                linkedUser.update();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
