package se.johan.projektarbete.util;

public class Employee {
    private int employeeId;
    private String fullName;
    private String email;
    private String employeePassword;
    private int roleId;


    public Employee(int employeeId, String fullName, String email, String employeePassword, int roleId) {
        setEmployeeId(employeeId);
        setFullName(fullName);
        setEmail(email);
        setEmployeePassword(employeePassword);
        setRoleId(roleId);
    }

    public Employee(String fullName, String email, String employeePassword, int roleId) {
        setFullName(fullName);
        setEmail(email);
        setEmployeePassword(employeePassword);
        setRoleId(roleId);
    }

    //Getters & Setters
    public int getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeePassword() {
        return this.employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public int getRoleId() {
        return this.roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
