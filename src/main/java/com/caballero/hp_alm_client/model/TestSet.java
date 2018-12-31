package com.caballero.hp_alm_client.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestSet extends Entity {

    public static final String STATUS_OPEN = "Open";

    public TestSet(Entity entity){
        super("test-set", entity);
    }

    public TestSet() {
        super("test-set");
        fieldValue("subtype-id","hp.qc.test-set.default");
    }

    public String status() {
        return fieldValue("status");
    }

    public void setStatus(String value) {
        fieldValue("status", value);
    }

    public String subtypeId() {
        return fieldValue("subtype-id");
    }

    public void SetSubtypeId(String value) {
        fieldValue("subtype-id", value);
    }

    public String comment() {
        return fieldValue("comment");
    }

    public void setComment(String value) {
        fieldValue("comment", value);
    }
}
