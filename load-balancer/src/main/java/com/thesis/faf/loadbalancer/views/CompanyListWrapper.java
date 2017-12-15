package com.thesis.faf.loadbalancer.views;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "companies")
public class CompanyListWrapper implements Serializable{
    private List<CompanyView> list;

    public CompanyListWrapper() {
    }

    public CompanyListWrapper(List<CompanyView> list) {
        this.list = list;
    }

    @XmlElement(name = "company")
    public List<CompanyView> getList() {
        return list;
    }
}
