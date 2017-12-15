package com.thesis.faf.loadbalancer.views;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
public class CustomerListWrapper implements Serializable {
    private List<CustomerView> list;

    public CustomerListWrapper() {
    }

    public CustomerListWrapper(List<CustomerView> list) {
        this.list = list;
    }

    @XmlElement(name = "customer")
    public List<CustomerView> getList() {
        return list;
    }
}
