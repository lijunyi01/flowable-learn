package com.allcom.entity;


import java.io.Serializable;
import java.sql.Date;

public class Person implements Serializable {

  private static final long serialVersionUID = -5809782578272943999L;

  private Integer personid;
  private String username;
  private String firstname;
  private String lastname;
  private Date birthdate;

  public Person(String username, String firstname, String lastname, Date birthdate) {
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthdate = birthdate;
  }

  public Person(Integer personid, String username, String firstname, String lastname, Date birthdate) {
    this.personid = personid;
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthdate = birthdate;
  }

  public Integer getPersonid() {
    return personid;
  }

  public void setPersonid(Integer personid) {
    this.personid = personid;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }


  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }


  public Date getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(java.sql.Date birthdate) {
    this.birthdate = birthdate;
  }

}
