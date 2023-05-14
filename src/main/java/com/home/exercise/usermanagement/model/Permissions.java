package com.home.exercise.usermanagement.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "permission")
public class Permissions implements Serializable {
    
    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "permission")
    @JsonIgnore
    Set<GrantedPermissions> grantedTo = new HashSet<GrantedPermissions>();
        
    public Set<GrantedPermissions> getGrantedTo() {
        return grantedTo;
    }

    public void setGrantedTo(Set<GrantedPermissions> grantedTo) {
        this.grantedTo = grantedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
