package com.iiht.fse4.skilltrackersearch.kafkaconfig;


import com.iiht.fse4.skilltrackersearch.entity.Associate;
import com.iiht.fse4.skilltrackersearch.model.Profile;
import com.iiht.fse4.skilltrackersearch.repo.AssociateRepository;
import com.iiht.fse4.skilltrackersearch.service.AssociateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



/**
 * This is the Kafka Consumer Class that that will listen to the Kafka Queue for incoming messages.
 * The Incoming messages will be the JSON entity Object
 *
 * Please see the {@link com.iiht.fse4.skilltrackersearch.entity.Associate} class for true identity
 * @author Promit Majumder
 *
 */

@Service
public class KafkaConsumer {

//    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
//
//    @Autowired
//    private AssociateRepository repo;
//
//    @Autowired
//    private AssociateService service;
//
//    @KafkaListener(
//            topics = "${spring.kafka.topic.name}"
//            ,groupId = "${spring.kafka.consumer.group-id}"
//    )
//
//
//    public void consume(KafkaMessage kafkaMessage){
//        LOGGER.info(String.format("Course event received in course-query service => %s", kafkaMessage.toString()));
//        System.out.println("event++++++++++++++++++");
//        System.out.println(kafkaMessage.getProfile().getName());
//        service.saveProfileFromCQRS(kafkaMessage);
//    }


}