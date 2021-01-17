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
    private Collection<DriverQualificationEnum> driverQualifications;
    private Company company;

//    public Driver() {}
//
//    public Driver(String firstName, String lastName, String emailId, List<DriverQualificationEnum> qualifications) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.emailId = emailId;
//        this.driverQualifications = qualifications;
//    }

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
    public Collection<DriverQualificationEnum> getDriverQualifications() {
        return driverQualifications;
    }
    
    public void setDriverQualifications(List<DriverQualificationEnum> qualifications) {
        this.driverQualifications = qualifications;
    }

    @ManyToOne(targetEntity=Company.class)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        StringBuilder qualificationsString = new StringBuilder();
        for (DriverQualificationEnum q : driverQualifications) {
            qualificationsString.append(q.toString());
        }
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
                + "qualifications: " + qualificationsString.toString() + "]";
    }
}

