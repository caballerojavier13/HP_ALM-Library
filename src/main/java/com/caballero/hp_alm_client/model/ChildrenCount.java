package com.caballero.hp_alm_client.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Field")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChildrenCount {
    @XmlElement(name = "Value", required = true)
    private String value;

    public ChildrenCount() {
    }

    public ChildrenCount(String value) {
        value(value);
    }

    public String value() {
        return value;
    }

    public void value(String value) {
        this.value = value;
    }

}
