package com.hongjili.sweet.guice;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.retry.RetryPolicy;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.hongjili.sweet.SweetHeartDaemon;
import com.hongjili.sweet.message.MessageGenerator;
import lombok.Builder;

@Builder
public class AppModule extends AbstractModule {
    private static final int MAX_ERROR_RETRY = 5;

    private final String targetPhoneNumber;
    private final String region;
    private final String messageFilePath;

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("PhoneNumber")).toInstance(targetPhoneNumber);
        bind(String.class).annotatedWith(Names.named("Region")).toInstance(region);
        bind(String.class).annotatedWith(Names.named("MessageFilePath")).toInstance(messageFilePath);
    }

    @Provides
    SweetHeartDaemon provideSweetHeartDaemon(AmazonSNS snsClient, @Named("PhoneNumber") String phoneNumber,
                                             MessageGenerator messageGenerator) {
        return new SweetHeartDaemon(snsClient, phoneNumber, messageGenerator);
    }

    @Provides
    MessageGenerator provideMessageGenerator(@Named("MessageFilePath") String messageFilePath) {
        return new MessageGenerator(messageFilePath);
    }

    @Provides
    AmazonSNS provideSnsClient(@Named("Region") String region) {
        return AmazonSNSClientBuilder.standard()
                .withRegion(region)
                .withClientConfiguration(new ClientConfiguration().withMaxErrorRetry(MAX_ERROR_RETRY))
                .build();
    }
}
