package com.home.exercise.usermanagement.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "granted_permissions")
public class GrantedPermissions implements Serializable{
    
    @EmbeddedId
    GrantedPermissionKey id;

    public GrantedPermissionKey getId() {
        return id;
    }

    public void setId(GrantedPermissionKey id) {
        this.id = id;
    }

    @ManyToOne()
    @MapsId("personID")
    @JoinColumn(name = "person_id")
    @JsonIgnore
    Person person;

    @ManyToOne()
    @MapsId("permissionID")
    @JoinColumn(name = "permission_id")
    Permissions permission;

    @Transient
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Transient
    public Permissions getPermission() {
        return permission;
    }

    public void setPermission(Permissions permission) {
        this.permission = permission;
    }

    @Column(name = "granted")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate grantedDate;
    
    public LocalDate getGrantedDate() {
        return grantedDate;
    }

    public void setGrantedDate(LocalDate grantedDate) {
        this.grantedDate = grantedDate;
    }

}
