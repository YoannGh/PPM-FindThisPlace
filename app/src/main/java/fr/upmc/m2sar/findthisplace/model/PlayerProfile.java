package fr.upmc.m2sar.findthisplace.model;

import java.io.Serializable;

public class PlayerProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;

    public PlayerProfile(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
