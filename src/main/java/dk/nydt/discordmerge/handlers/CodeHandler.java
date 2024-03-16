package dk.nydt.discordmerge.handlers;

import lombok.Getter;

import java.util.*;

public class CodeHandler {
    @Getter
    public Map<String, String> codes = new HashMap<>();
    public String generateCode(String discordId) {
        String CODE_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomIndex = (int) (Math.random() * CODE_POOL.length());
            codeBuilder.append(CODE_POOL.charAt(randomIndex));
        }
        String code = codeBuilder.toString();
        codes.put(code, discordId);
        return code;
    }
}
