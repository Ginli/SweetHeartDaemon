package com.hongjili.sweet.message;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageGenerator {
    @NonNull
    String messageFilePath;
    @NonNull
    List<String> availableMessages;

    public MessageGenerator(String messageFilePath) {
        this.messageFilePath = messageFilePath;
        availableMessages = createAllMessages();
    }

    public String getRandomMessage() {
        int i = new Random().nextInt(availableMessages.size());
        return availableMessages.get(i);
    }

    private List<String> createAllMessages() {
        List<String> res = new ArrayList<>();

        try(Stream<String> stream = Files.lines(Paths.get(messageFilePath))) {
            stream.forEach(res::add);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Cannot read message file: %s", messageFilePath), e);
        }

        return res;
    }
}
