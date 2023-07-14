package com.iiht.fse4.skilltrackersearch.controller;

import com.iiht.fse4.skilltrackersearch.exception.AssociateNotfoundException;
import com.iiht.fse4.skilltrackersearch.model.Profile;
import com.iiht.fse4.skilltrackersearch.service.AssociateService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/skill-tracker/api/v1/admin")
@Slf4j
public class SearchController {



    @Autowired
    private AssociateService service;

    /**
     *
     * @return - Return Value
     */
    @GetMapping("/getAllAssociates")
    public List<Profile> getAllAssociates(){
        List<Profile> associateList = service.getAllAssociates();
        log.info("Sending data back to the Browser");
        return associateList;
    }


    /**
     *
     * @return - Return Value
     */
    @GetMapping("/getAllAssociatesOrderBySort")
    public List<Profile> getAllAssociatesOrderBySort(@RequestParam String orderby, @RequestParam String sort){
        System.out.println("getAllAssociatesOrderBySort");
        System.out.println("OrderBy="+orderby + " | Sort=" + sort);
        List<Profile> associateList = service.getAllAssociatesOrderBySort(orderby, sort);
        return associateList;

    }


    /**
     *
     * @return - Return Value
     */
    @GetMapping("/getAssociatesByName")
    public List<Profile> getAssociatesByName(@RequestParam String name){
        log.info("######### - Controller - getAssociatesByName");
        List<Profile> associateList = service.getAssociatesByName(name);
        return associateList;
    }

    @GetMapping("/getAssociateByID")
    //@CircuitBreaker(name = "skilltrackermongo", fallbackMethod = "getAssociateByIDFallback")
    //@CircuitBreaker(name = "skilltrackermongo")
    public Profile getAssociateByID(@RequestParam String associateID){
        log.info("######### - Controller - getAssociateByID");
        Profile data = service.getAssociateByID(associateID);
        return data;
    }

    public Profile getAssociateByIDFallback(Exception e){
        //log.error("getAPIFallBack::{}", e);
        System.out.println("\n\n Normal Get returned Null- Triggering getAssociateByIDFallback");
        Profile profile = new Profile();
        return profile;
    }


    @GetMapping("/getAssociatesBySkill")
    public List<Profile> getAssociatesBySkill(@RequestParam String topic){
        log.info("######### - Controller - getAssociatesBySkill");
        return service.getAssociatesBySkill(topic);
    }

}
