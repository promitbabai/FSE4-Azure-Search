package com.iiht.fse4.skilltrackersearch.azureservicebus;

import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import com.iiht.fse4.skilltrackersearch.service.AssociateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.azure.messaging.servicebus.*;

@Component
public class ReceiveMessageFromAzureServiceBus {

    @Autowired
    private AssociateService service;

    private static String messageRecievedFromAzure = StringUtils.EMPTY;

    public String CONN_STR = "Endpoint=sb://skill-tracker-service-bus.servicebus.windows.net/;SharedAccessKeyName=queue-policy;SharedAccessKey=qGTrNdBJeXrW08C27eninGOb2Hjq4pp9M+ASbDG6VVs=;EntityPath=update-record-in-search-queue";

    public String QUEUE_NAME = "update-record-in-search-queue";

    // Method To trigger the scheduler every one minute
    @Scheduled(fixedDelay = 60000)
    public void receiveMessage() throws InterruptedException {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
            String strDate = dateFormat.format(new Date());
            System.out.println("Reading Azure-Service-Bus Queue at - " + strDate);


        CountDownLatch countDownLatch = new CountDownLatch(1);

        // create the processor client via the builder and its sub-builder
        ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
                .connectionString(CONN_STR)
                .processor()
                .queueName(QUEUE_NAME)
                .receiveMode(ServiceBusReceiveMode.RECEIVE_AND_DELETE)
                .processMessage(ReceiveMessageFromAzureServiceBus::processMessage)
                .processError(ReceiveMessageFromAzureServiceBus::processError)
                .disableAutoComplete()
                .buildProcessorClient();

        // Starts the processor in the background and returns immediately
        processorClient.start();

        service.saveProfileFromCQRSAzureServiceBus(messageRecievedFromAzure);

    }

    private static void processError(ServiceBusErrorContext serviceBusErrorContext) {
        System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n");
    }


    private static void processMessage(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        System.out.println("\n\n\n Message - ");
        messageRecievedFromAzure = String.valueOf(message.getBody());
        System.out.println(messageRecievedFromAzure);


        //System.out.printf("Processing message. Session: %s, Sequence #: %s. Contents: %s%n", message.getMessageId(),
          //      message.getSequenceNumber(), message.getBody());
    }

    /*private static void processError(ServiceBusErrorContext context, CountDownLatch countdownLatch) {
        System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
                context.getFullyQualifiedNamespace(), context.getEntityPath());

        if (!(context.getException() instanceof ServiceBusException)) {
            System.out.printf("Non-ServiceBusException occurred: %s%n", context.getException());
            return;
        }

        ServiceBusException exception = (ServiceBusException) context.getException();
        ServiceBusFailureReason reason = exception.getReason();

        if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_DISABLED
                || reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND
                || reason == ServiceBusFailureReason.UNAUTHORIZED) {
            System.out.printf("An unrecoverable error occurred. Stopping processing with reason %s: %s%n",
                    reason, exception.getMessage());

            countdownLatch.countDown();
        } else if (reason == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
            System.out.printf("Message lock lost for message: %s%n", context.getException());
        } else if (reason == ServiceBusFailureReason.SERVICE_BUSY) {
            try {
                // Choosing an arbitrary amount of time to wait until trying again.
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.err.println("Unable to sleep for period of time");
            }
        } else {
            System.out.printf("Error source %s, reason %s, message: %s%n", context.getErrorSource(),
                    reason, context.getException());
        }
    }*/
}
