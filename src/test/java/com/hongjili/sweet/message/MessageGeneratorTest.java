package com.hongjili.sweet.message;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageGeneratorTest {
    static final String MESSAGE_FILE_PATH = "./src/test/resources/sweet_words.txt";

    MessageGenerator messageGenerator;

    @Before
    public void setup() {
        messageGenerator = new MessageGenerator(MESSAGE_FILE_PATH);
    }

    /**
     * Test getting random line of file. After running 100 times, it should have at least 2 different lines.
     */
    @Test
    public void testGetRandomMessage() {
        //given
        Set<String> result = new HashSet<>();
        //when
        for (int i = 0; i < 100; i++) {
            result.add(messageGenerator.getRandomMessage());
        }
        //then
        Assert.assertTrue(result.size() > 1);
    }
}
