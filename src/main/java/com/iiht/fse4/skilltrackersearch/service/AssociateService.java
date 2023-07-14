package com.iiht.fse4.skilltrackersearch.service;


import com.google.gson.Gson;
//import com.iiht.fse4.skilltrackersearch.entity.Associate;
//import com.iiht.fse4.skilltrackersearch.entity.Skills;
import com.iiht.fse4.skilltrackersearch.exception.AssociateNotfoundException;
import com.iiht.fse4.skilltrackersearch.exception.AssociateNotfoundForGivenSkillException;
import com.iiht.fse4.skilltrackersearch.exception.MongoDBRepoSaveException;
//import com.iiht.fse4.skilltrackersearch.kafkaconfig.KafkaMessage;
import com.iiht.fse4.skilltrackersearch.model.Profile;
import com.iiht.fse4.skilltrackersearch.model.SkillsFromUI;
//import com.iiht.fse4.skilltrackersearch.repo.AssociateRepository;
import com.iiht.fse4.skilltrackersearch.repo.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AssociateService {

    @Autowired
    private ProfileRepository repo;
    //private AssociateRepository repo;

    private static final String RATING_SEARCH_VALUE = "10";


    public Profile getAssociateById(final String associateId){
        Profile data = null;
        data = repo.findByAssociateid(associateId);
        if(null == data){
            log.warn("Associate data not found with given ID");
            throw new AssociateNotfoundException();
        }else{
            log.info("Associate data found");
        }
        return data;
    }


    public List<Profile> getAllAssociates (){
        List<Profile> associateList =  repo.findAll();
        if(null == associateList){
            log.warn("Associate data not loaded");
            throw new AssociateNotfoundException();
        }else{
            log.info("Associate data found");
        }
        return sortAsPerExpertiseDescending(associateList);
    }


    private List<Profile> sortAsPerExpertiseDescending(List<Profile> associateList){

        for(Profile data : associateList){

            System.out.println("\n\n\n\n ##################");
            System.out.println("New Row Data = " + data.getName());
            List<SkillsFromUI> unSortedSkillsList = data.getTechskills();
            unSortedSkillsList.stream().forEach(skillObj -> System.out.println(skillObj.getTopic() + " - " + skillObj.getRating()));

            Comparator<SkillsFromUI> skillsComparatorLEx = (s1, s2) -> s1.getRating().compareTo(s2.getRating());

            List<SkillsFromUI> sortedSkillList = unSortedSkillsList.stream().sorted(skillsComparatorLEx).collect(Collectors.toList());

            System.out.println("---------- AFTER SORT OPERATION -------------------");
//            // print new list to console using forEach()
            sortedSkillList.stream().forEach(skillObj -> System.out.println(skillObj.getTopic() + " - " + skillObj.getRating()));

            System.out.println("\n\n\n----------END OF DATA----------------- \n\n\n");
            data.setTechskills(sortedSkillList);
        }
        return associateList;



    }




    public List<Profile> getAllAssociatesOrderBySort (final String orderby, final String sort){


        List<Profile> associateList = new ArrayList<Profile>();
        if(orderby.equals("name")&& sort.equals("asc")){
           associateList = repo.findByOrderByNameAsc();
        }
        if(orderby.equals("name")&& sort.equals("desc")){
            associateList = repo.findByOrderByNameDesc();
        }
        if(orderby.equals("associateid")&& sort.equals("asc")){
            associateList = repo.findByOrderByAssociateidAsc();
        }
        if(orderby.equals("associateid")&& sort.equals("desc")){
            associateList = repo.findByOrderByAssociateidDesc();
        }
        if(null == associateList){
            log.error("Associate data not loaded");
            throw new AssociateNotfoundException();
        }else{
            log.info("Associate data found with given ID");
        }
        return associateList;
    }

    public Profile getAssociateByID (final String associateId){
        Profile data =  repo.findByAssociateid(associateId);
        if(null == data){
            log.error("Associate data not found with given ID");
            throw new AssociateNotfoundException();
        }else{
            log.info("Associate data found with given ID");
        }
        return data;
    }

    public List<Profile> getAssociatesByName(final String nameFromUI){
        log.info("######### - Service Layer - getAssociatesByName");
        log.info("######### - Searching For  - " + nameFromUI);
        List<Profile> filteredAssociateList = new ArrayList<Profile>();
        StringBuilder nameInitials = new StringBuilder();
        if(nameFromUI.length()>4){
            filteredAssociateList =  repo.getAssociatesByName(nameFromUI);
            if(null == filteredAssociateList){
                log.warn("Associate data not loaded");
                throw new AssociateNotfoundException();
            }
        }else{
            List<Profile> allAssociateList =  repo.findAll();
            if(null == allAssociateList){
                log.error("Associate not found with given name");
                throw new AssociateNotfoundException();
            }else{
                log.info("Associate found with given name");
            }

            for(Profile associate : allAssociateList){
                String associateName = associate.getName();
                String[] nameInitialsArray = associateName.split(" ");


                //get the initails of each name
                for(String initials : nameInitialsArray){
                    nameInitials.append(initials.charAt(0));
                }
                System.out.println("Initails - " + nameInitials.toString());
                if(nameFromUI.equals(nameInitials.toString())){
                    filteredAssociateList.add(associate);
                }else{
                    nameInitials.delete(0,nameInitials.length());
                }
            }
        }
        log.info("######### - Data Recieved  - " + filteredAssociateList);
        return filteredAssociateList;
    }


    public List<Profile> getAssociatesBySkill(final String topic){
        System.out.println("\n\n\n getAssociatesBySkill - " + topic);
        //List<Associate> associateList = repo.getAssociateBySkill(topic);
        List<Profile> associateList =  repo.findAll();
        if(null == associateList){
            log.warn("Associate not found with given Skillset");
            throw new AssociateNotfoundForGivenSkillException();
        }else{
            log.info("Associate found with given Skillset");
        }
        List<Profile> filteredAssociateList = new ArrayList<Profile>();
        for(Profile data : associateList){
            for(SkillsFromUI skill : data.getTechskills()){
                if(topic.equals(skill.getTopic())){
                    int rating = Integer.parseInt(skill.getRating());
                    if(rating > 10){
                        filteredAssociateList.add(data);
                    }
                }
            }
        }
        return filteredAssociateList;
    }



    /**
     * <p>This method saves the Associate Object recieved from the Command part of CQRS Pattern and saves into the
     * Mongo DB
     * </p>
     * @param azureQueueMessage -  JSON Entity object recieved from Azure Service bus Queue
     */
    public void saveProfileFromCQRSAzureServiceBus(final String azureQueueMessage){
        System.out.println("\n\n\n saveProfileFromCQRSAzureServiceBus - ");
        Gson gson = new Gson();
        Profile profile = gson.fromJson(azureQueueMessage, Profile.class);

        //Associate associate = gson.fromJson(azureQueueMessage, Associate.class);
        try{
            repo.save(profile);
            log.info("AssociateRepository - SAVE from AZURE SERVICE BUS to MongoDB");
        }catch(Exception e){
            log.error("Associate data could not be saved to MongoDB");
            throw new MongoDBRepoSaveException();
        }

    }



    /**
     * <p>This method saves the Associate Object recieved from the Command part of CQRS Pattern and saves into the
     * Mongo DB
     * </p>
     * @param kafkaMessage -  JSON Entity object send from Angular UI to Maintain to here
     */
   /* public void saveProfileFromCQRSKafka(final KafkaMessage kafkaMessage){
        Profile associate = performModelTransformation(kafkaMessage.getProfile());
        if(kafkaMessage.getMongoOpsCode().equals("INSERT") || kafkaMessage.getMongoOpsCode().equals("UPDATE")){
            try{
                repo.save(associate);
                log.info("AssociateRepository - SAVE from KAKFA to MongoDB");
            }catch(Exception e){
                log.error("Associate data could not be saved to MongoDB");
                throw new MongoDBRepoSaveException();
            }

        }

    }*/


    /**
     * <p>This method transforms the incoming JSON Entity object to the MongoDB Entity Object that would
     * have to be persisted. . .
     * </p>
     * @param profile -  JSON Entity object send from Angular UI to Maintain to here
     * @return associate - the Entity object to be peristed into MongoDB
     */
    /*private Associate performModelTransformation (final Profile profile){
        Associate associate = new Associate();
        associate.setAssociateid(profile.getAssociateid());
        associate.setName(profile.getName());
        associate.setMobile(profile.getMobile());
        associate.setEmail(profile.getEmail());
        associate.setTechnical_skills(profile.getTechskills());
        associate.setNon_technical_skills(profile.getNontechskills());
        return associate;

    }*/

}
