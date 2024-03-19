package dk.nydt.discordmerge.commands.discord;

import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.commands.configs.Messages;
import dk.nydt.discordmerge.handlers.CodeHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CodeCommand extends ListenerAdapter {
    private final CodeHandler codeHandler = DiscordMerge.getCodeHandler();
    private final Messages messages = DiscordMerge.getMessages();
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getMember() == null) return;
        if (event.getName().equals("code")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(messages.discordCodeCommandTitle);
            embedBuilder.setColor(messages.discordCodeCommandEmbedColor);
            embedBuilder.setDescription(messages.discordCodeCommandDescription.replace("%code%", codeHandler.createLinkCode(event.getMember().getId())));
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        }
    }
}
