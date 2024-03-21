package dk.nydt.discordmerge.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.Collections;
import java.util.List;

public class Messages extends OkaeriConfig {
    public String discordAccountCommandTitle = "Account";
    public String discordAccountCommandDescriptionNoAccounts = "No linked Minecraft accounts.";
    public String discordAccountCommandDescriptionAccounts = "Here's the linked Minecraft accounts:";
    public int discordAccountCommandEmbedColor = 0x00ff00;
    @Comment(" ")
    public String discordCodeCommandTitle = "Code";
    public String discordCodeCommandDescription = "Here's your code: %code%";
    public int discordCodeCommandEmbedColor = 0x00ff00;
    @Comment(" ")
    public String discordBoostEventTitle = "Boost";
    public String discordBoostEventDescription = "Here's your code: %code%";
    public int discordBoostEventEmbedColor = 0x00ff00;
    @Comment(" ")
    public List<String> minecraftReloadCommandSuccess = Collections.singletonList("You have successfully reloaded the plugin! Took %time%ms");
    public List<String> minecraftReloadCommandNotReloaded = Collections.singletonList("The plugin wasn't reloaded properly.");
    @Comment(" ")
    public List<String> minecraftBoostCommandInvalidCode = Collections.singletonList("Invalid code!");
    public List<String> minecraftBoostCommandSuccess = Collections.singletonList("You have successfully claimed your boost reward!");
    @Comment(" ")
    public List<String> minecraftClaimCommandNotLinked = Collections.singletonList("You are not linked to a Discord account!");
    public List<String> minecraftClaimCommandNoGuild = Collections.singletonList("You are not in the correct guild!");
    public List<String> minecraftClaimCommandNoMember = Collections.singletonList("You are not in the guild!");
    public List<String> minecraftClaimCommandNoRole = Collections.singletonList("You do not have the required role to claim a reward!");
    public List<String> minecraftClaimCommandAlreadyHasRole = Collections.singletonList("You already have the role! %role%");
    public List<String> minecraftClaimCommandNoAvailableRoles = Collections.singletonList("There are no available roles to claim!");
    public List<String> minecraftClaimCommandSuccess = Collections.singletonList("You have successfully claimed your role! %role%");
    @Comment(" ")
    public List<String> minecraftLinkCommandSuccess = Collections.singletonList("You have successfully linked your account!");
    public List<String> minecraftLinkCommandInvalidCode = Collections.singletonList("Invalid code!");
    public List<String> minecraftLinkCommandAlreadyLinked = Collections.singletonList("You are already linked to the Discord account with id %discord%!");
    public List<String> minecraftLinkCommandTryAgain = Collections.singletonList("Try again!");
    @Comment(" ")
    public List<String> minecraftUnlinkCommandSuccess = Collections.singletonList("You have successfully unlinked your account!");
    public List<String> minecraftUnlinkCommandNotLinked = Collections.singletonList("You are not linked to a Discord account!");

}
