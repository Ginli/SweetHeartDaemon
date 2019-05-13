package com.hongjili.sweet.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hongjili.sweet.SweetHeartDaemon;
import com.hongjili.sweet.guice.AppModule;

public class LambdaHandler implements RequestHandler<String, String> {
    private static final int MAX_RETRY = 4;
    private static final long INTERVAL_MILLIS = 100;
    private static final String SUCCESS = "SUCCESS";

    @Override
    public String handleRequest(String phoneNumber, Context context) {
        String region = Preconditions.checkNotNull(System.getProperty("region"));
        String messageFilePath = Preconditions.checkNotNull(System.getProperty("messagefilepath"));
        Injector injector = Guice.createInjector(AppModule.builder()
                .targetPhoneNumber(phoneNumber)
                .messageFilePath(messageFilePath)
                .region(region)
                .build());
        LambdaLogger logger = context.getLogger();

        int i = 0;
        Exception exception;
        do {
            i++;
            SweetHeartDaemon job = injector.getInstance(SweetHeartDaemon.class);

            try {
                job.go();
                logger.log("Successfully sent message to " + phoneNumber);
                return SUCCESS;
            } catch (Exception e) {
                logger.log("Error encountered when running job. Error message: " + e.getMessage() + " Retry...");
                exception = e;
            }

            sleep(INTERVAL_MILLIS);
        } while (i < MAX_RETRY);

        throw new RuntimeException(String.format("Unable to successfully run the job after %s attempts.", MAX_RETRY), exception);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
