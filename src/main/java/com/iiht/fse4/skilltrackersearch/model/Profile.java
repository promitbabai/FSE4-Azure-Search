package com.iiht.fse4.skilltrackersearch.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * The Associate is a model class which holds the data sent from the Angular UI,
 * to be persisted onto the Mongodb, also this Object is sent out to the Angular UI
 *
 * @author  Promit Majumder
 * @version 1.0
 * @since   2023-01-15
 */
@Getter
@Setter
@NoArgsConstructor
@Document("PROFILE")
public class Profile implements Serializable {

    @Id
    private String associateid;

    private String name;

    private String mobile;

    private String email;

    private String lastupdated;

    private String password;

    private String role;

    private List<SkillsFromUI> techskills;

    private List<SkillsFromUI> nontechskills;


    public Profile(String associateid, String name, String mobile, String email, String lastupdated, String password, String role, List<SkillsFromUI> techskills, List<SkillsFromUI> nontechskills) {
        this.associateid = associateid;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.lastupdated = lastupdated;
        this.password = password;
        this.role = role;
        this.techskills = techskills;
        this.nontechskills = nontechskills;
    }

    public String toString()
    {
        return "\n\n PROFILE DATA\n" + associateid + " | " + name + " | " + mobile + " | " + email + " | "
                + lastupdated + " | " + password + " | " + role;
    }

}
