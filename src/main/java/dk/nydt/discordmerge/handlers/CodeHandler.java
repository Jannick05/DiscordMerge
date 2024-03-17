package dk.nydt.discordmerge.handlers;

import lombok.Getter;

import java.util.*;

public class CodeHandler {
    @Getter
    private Map<String, String> linkCodes = new HashMap<>();

    public String generateCode() {
        String CODE_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomIndex = (int) (Math.random() * CODE_POOL.length());
            codeBuilder.append(CODE_POOL.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public String createLinkCode(String discordId) {
        String code = generateCode();
        linkCodes.put(code, discordId);
        return code;
    }
}
