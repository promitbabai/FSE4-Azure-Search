package com.iiht.fse4.skilltrackersearch.controller;

import com.iiht.fse4.skilltrackersearch.entity.Associate;
import com.iiht.fse4.skilltrackersearch.exception.AssociateNotfoundException;
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
    public List<Associate> getAllAssociates(){
        List<Associate> associateList = service.getAllAssociates();
        log.info("Sending data back to the Browser");
        return associateList;


    }


    /**
     *
     * @return - Return Value
     */
    @GetMapping("/getAllAssociatesOrderBySort")
    public List<Associate> getAllAssociatesOrderBySort(@RequestParam String orderby, @RequestParam String sort){
        System.out.println("getAllAssociatesOrderBySort");
        System.out.println("OrderBy="+orderby + " | Sort=" + sort);
        List<Associate> associateList = service.getAllAssociatesOrderBySort(orderby, sort);
        return associateList;

    }


    /**
     *
     * @return - Return Value
     */
    @GetMapping("/getAssociatesByName")
    public List<Associate> getAssociatesByName(@RequestParam String name){
        List<Associate> associateList = service.getAssociatesByName(name);
        return associateList;
    }

    @GetMapping("/getAssociateByID")
    //@CircuitBreaker(name = "skilltrackermongo", fallbackMethod = "getAssociateByIDFallback")
    @CircuitBreaker(name = "skilltrackermongo")
    public Associate getAssociateByID(@RequestParam String associateID){
        Associate associateData = service.getAssociateByID(associateID);
        return associateData;
    }

    public Associate getAssociateByIDFallback(Exception e){
        //log.error("getAPIFallBack::{}", e);
        System.out.println("\n\n Normal Get returned Null- Triggering getAssociateByIDFallback");
        Associate associate = new Associate();
        return associate;
    }


    @GetMapping("/getAssociatesBySkill")
    public List<Associate> getAssociatesBySkill(@RequestParam String topic){
        return service.getAssociatesBySkill(topic);
    }

}
