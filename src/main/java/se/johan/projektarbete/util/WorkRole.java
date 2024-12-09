package se.johan.projektarbete.util;

import java.sql.Date;

public class WorkRole {
    private int roleId;
    private String title;
    private String workDescription;
    private double salary;
    private java.sql.Date creationDate;


    //Constructors
    public WorkRole(int roleId, String title, String workDescription, double salary, Date creationDate) {
        setRoleId(roleId);
        setTitle(title);
        setWorkDescription(workDescription);
        setSalary(salary);
        setCreationDate(creationDate);
    }

    public WorkRole(String title, String workDescription, double salary, Date creationDate) {
        setTitle(title);
        setWorkDescription(workDescription);
        setSalary(salary);
        setCreationDate(creationDate);
    }

    //Getters & Setters
    public int getRoleId() {
        return this.roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWorkDescription() {
        return this.workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    public double getSalary() {
        return this.salary;
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            salary = 0;
        }
        this.salary = salary;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
