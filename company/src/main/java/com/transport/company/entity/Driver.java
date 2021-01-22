package com.transport.company.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "driver")
public class Driver {
    private long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private List<DriverQualificationEnum> driverQualifications;
    private double yearlySalary;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email_address", nullable = false)
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Column(name = "qualifications", nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass= DriverQualificationEnum.class)
    @CollectionTable(name="driver_qualifications")
    public List<DriverQualificationEnum> getDriverQualifications() {
        return driverQualifications;
    }
    
    public void setDriverQualifications(List<DriverQualificationEnum> qualifications) {
        this.driverQualifications = qualifications;
    }

    @Column(name = "yearly_salary", nullable = true)
    public double getYearlySalary() {
        return yearlySalary;
    }

    public void setYearlySalary(double yearlySalary) {
        this.yearlySalary = yearlySalary;
    }
}

