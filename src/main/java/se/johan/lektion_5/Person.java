package se.johan.lektion_5;

import java.sql.Date;
import java.util.Objects;

public class Person {
    private Integer personId;
    private String firstName;
    private String lastName;
    private String gender;
    private java.sql.Date dob;
    private double income;

    //Constructors
    Person(Integer personId, String firstName, String lastName, String gender, java.sql.Date dob, double income) {
        setPersonId(personId);
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setDob(dob);
        setIncome(income);
    }

    Person(String firstName, String lastName, String gender, java.sql.Date dob, double income) {
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setDob(dob);
        setIncome(income);
    }

    //Getters & Setters
    public Integer getPersonId() {
        return this.personId;
    }

    public void setPersonId(Integer personId) {
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

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Double.compare(income, person.income) == 0 && Objects.equals(personId, person.personId) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(gender, person.gender) && Objects.equals(dob, person.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, firstName, lastName, gender, dob, income);
    }
}
