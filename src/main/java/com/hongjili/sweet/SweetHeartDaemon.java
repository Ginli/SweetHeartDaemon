package com.hongjili.sweet;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.hongjili.sweet.message.MessageGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SweetHeartDaemon {
    @NonNull
    AmazonSNS snsClient;
    @NonNull
    String phoneNumber;
    @NonNull
    MessageGenerator msgGenerator;

    public void go() {
        String message = msgGenerator.getRandomMessage();
        snsClient.publish(new PublishRequest()
                .withPhoneNumber(phoneNumber)
                .withMessage(message));
        log.info("Successfully sent message {} to phone number {}.", message, phoneNumber);
    }
}
