package com.caballero.hp_alm_client.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestInstance extends Entity {

    public static final String STATUS_BLOCKED = "Blocked";
    public static final String STATUS_FAILED = "Failed";
    public static final String STATUS_NA = "N/A";
    public static final String STATUS_NO_RUN = "No Run";
    public static final String STATUS_NOT_COMPLETED = "Not Completed";
    public static final String STATUS_PASSED = "Passed";

    public TestInstance(Entity entity) {
        super("test-instance", entity);
        fieldValue("subtype-id", "hp.qc.test-instance.MANUAL");
    }

    public TestInstance() {
        super("test-instance");
        fieldValue("subtype-id", "hp.qc.test-instance.MANUAL");
    }

    public String testSetId() {
        return fieldValue("cycle-id");
    }

    public void testSetId(String value) {
        fieldValue("cycle-id", value);
    }

    public String testId() {
        return fieldValue("test-id");
    }

    public void testId(String value) {
        fieldValue("test-id", value);
    }

    public String iterations() {
        return fieldValue("iterations");
    }

    public void iterations(String value) {
        fieldValue("iterations", value);
    }

    public String status() {
        return fieldValue("status");
    }

    public void status(String value) {
        fieldValue("status", value);
    }

    public static String getTestInstanceStatusFromReportStatus(String reportStatus){
        switch (reportStatus){
            case "FAIL":
                return TestInstance.STATUS_FAILED;
            case "PASS":
                return TestInstance.STATUS_PASSED;
            default:
                return TestInstance.STATUS_NA;
        }
    }

}
