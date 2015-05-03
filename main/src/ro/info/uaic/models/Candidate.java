package ro.info.uaic.models;

import java.util.Date;

/**
 * Created by lotus on 03.05.2015.
 */
public class Candidate
{
    private int id;
    private String firstName;
    private String lastName;
    private String cnp;
    private String address;
    private Date birthDate;
    private TestType testType;
    private float testMark;
    private float avgBac;
    private TestType bacDomain;
    private float bacDomainMark;
    private Date applicationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public TestType getTestType() {
        return testType;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    public float getTestMark() {
        return testMark;
    }

    public void setTestMark(float testMark) {
        this.testMark = testMark;
    }

    public float getAvgBac() {
        return avgBac;
    }

    public void setAvgBac(float avgBac) {
        this.avgBac = avgBac;
    }

    public TestType getBacDomain() {
        return bacDomain;
    }

    public void setBacDomain(TestType bacDomain) {
        this.bacDomain = bacDomain;
    }

    public float getBacDomainMark() {
        return bacDomainMark;
    }

    public void setBacDomainMark(float bacDomainMark) {
        this.bacDomainMark = bacDomainMark;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }
}
