package com.iiht.fse4.skilltrackersearch.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.iiht.fse4.skilltrackersearch.entity.Skills;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Profile implements Serializable {

    private String associateid;

    private String name;

    private String mobile;

    private String email;

    private List<Skills> techskills;

    private List<Skills> nontechskills;

    public Profile(String associateid, String name, String mobile, String email, List<Skills> techskills, List<Skills> nontechskills) {
        this.associateid = associateid;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.techskills = techskills;
        this.nontechskills = nontechskills;
    }
}
