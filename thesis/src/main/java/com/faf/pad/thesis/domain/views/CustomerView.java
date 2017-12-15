package com.faf.pad.thesis.domain.views;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerView implements Serializable {
    private Long id;
    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @Min(value = 6)
    private String password;
    @NotNull
    private String email;

    @NotNull
    @DecimalMin(value = "18")
    @DecimalMax(value = "100")
    private Integer age;

    @NotNull
    private Long companyId;

    private CompanyView companyView;


    public CustomerView() {
    }

    public CustomerView(Long id, String name, String surname, Integer age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public CompanyView getCompanyView() {
        return companyView;
    }

    public void setCompanyView(CompanyView companyView) {
        this.companyView = companyView;
    }
}
