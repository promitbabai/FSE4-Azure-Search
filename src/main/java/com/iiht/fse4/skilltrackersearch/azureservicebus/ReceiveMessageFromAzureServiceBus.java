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
    //@Scheduled(fixedDelay = 60000)
    public void receiveMessage() throws InterruptedException {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
            String strDate = dateFormat.format(new Date());
            System.out.println("\n\n\n ###### Scheduler Reading Azure-Service-Bus Queue at - " + strDate);


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

        if(StringUtils.isNotBlank(messageRecievedFromAzure)){
            service.saveProfileFromCQRSAzureServiceBus(messageRecievedFromAzure);
        }else{
            System.out.println("###### NO MESSAGE TO CONSUME - " + strDate);
        }
    }

    private static void processError(ServiceBusErrorContext serviceBusErrorContext) {
        System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n");
    }


    private static void processMessage(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        System.out.println("\n\n\n ########### Message Recieved From AzureServiceBus - ");
        messageRecievedFromAzure = String.valueOf(message.getBody());
        System.out.println(messageRecievedFromAzure);
    }

}
