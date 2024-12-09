package se.johan.v48project.bös;

import java.sql.Date;
import java.util.Objects;

public class Person {
    //Attributes
    private int personId;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private java.sql.Date dob;
    private double income;


    //Constructors
    Person(int personID, String firstName, String lastName, String email, String gender, Date dob, double income) {
        setPersonId(personId);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setGender(gender);
        setDob(dob);
        setIncome(income);
    }

    Person(String firstName, String lastName, String email, String gender, Date dob, double income) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setGender(gender);
        setDob(dob);
        setIncome(income);
    }


    //Getters & Setters
    public int getPersonId() {
        return this.personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return this.dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public double getIncome() {
        return this.income;
    }

    public void setIncome(double income) {
        this.income = income;
    }


    //Equals & hashcode
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personId == person.personId && Double.compare(income, person.income) == 0 && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(gender, person.gender) && Objects.equals(dob, person.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, firstName, lastName, gender, dob, income);
    }
}
