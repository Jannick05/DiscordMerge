package dk.nydt.discordmerge.commands.discord;

import com.j256.ormlite.dao.ForeignCollection;
import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.configs.Messages;
import dk.nydt.discordmerge.handlers.ObjectHandler;
import dk.nydt.discordmerge.objects.MinecraftAccount;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.sql.SQLException;

public class AccountCommand extends ListenerAdapter {
    private final ObjectHandler objectHandler = DiscordMerge.getObjectHandler();
    private final Messages messages = DiscordMerge.getMessages();
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getMember() == null) return;
        if (event.getName().equals("account")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            Member member = event.getOption("player", event.getMember(), OptionMapping::getAsMember);
            embedBuilder.setTitle(messages.discordAccountCommandTitle);
            embedBuilder.setColor(messages.discordAccountCommandEmbedColor);
            try {
                ForeignCollection<MinecraftAccount> minecraftAccounts;
                if(member == null) {
                    minecraftAccounts = objectHandler.getMinecraftAccounts(event.getMember().getId());
                } else {
                    minecraftAccounts = objectHandler.getMinecraftAccounts(member.getId());
                }
                if(minecraftAccounts.isEmpty()) {
                    embedBuilder.setDescription(messages.discordAccountCommandDescriptionNoAccounts);
                    event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
                    return;
                }
                embedBuilder.setDescription(messages.discordAccountCommandDescriptionAccounts);
                for (MinecraftAccount minecraftAccount : minecraftAccounts) {
                    embedBuilder.addField(minecraftAccount.getName(), minecraftAccount.getUuid().toString(), true);
                }

                event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
