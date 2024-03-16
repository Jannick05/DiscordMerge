package dk.nydt.discordmerge.commands.discord;

import dk.nydt.discordmerge.DiscordMerge;
import dk.nydt.discordmerge.handlers.CodeHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CodeCommand extends ListenerAdapter {
    CodeHandler codeHandler = DiscordMerge.getCodeHandler();
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getMember() == null) return;
        if (event.getName().equals("code")) {
            event.reply("Here's your code: " + codeHandler.generateCode(event.getMember().getId())).setEphemeral(true).queue();
        }
    }
}
