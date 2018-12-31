package com.caballero.hp_alm_client.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RunStep extends Entity {

    public static final String STATUS_BLOCKED = "Blocked";
    public static final String STATUS_FAILED = "Failed";
    public static final String STATUS_NA = "N/A";
    public static final String STATUS_NO_RUN = "No Run";
    public static final String STATUS_NOT_COMPLETED = "Not Completed";
    public static final String STATUS_PASSED = "Passed";

    public RunStep(Entity entity) {
        super("run-step", entity);
    }

    public RunStep() {
        type("run-step");
    }


    @Override
    public void clearBeforeUpdate() {
        removeField("parent-id");
        super.clearBeforeUpdate();
    }

    public String runId() {
        return fieldValue("parent-id");
    }

    public void runId(String value) {
        fieldValue("parent-id", value);
    }

    public String desStepId() {
        return fieldValue("desstep-id");
    }

    public void desStepId(String value) {
        fieldValue("desstep-id", value);
    }

    public String description() {
        return fieldValue("description");
    }

    public void description(String value) {
        fieldValue("description", value);
    }

    public String stepOrder() {
        return fieldValue("step-order");
    }

    public void stepOrder(String value) {
        fieldValue("step-order", value);
    }

    public String hasPicture() {
        return fieldValue("has-picture");
    }

    public void hasPicture(String value) {
        fieldValue("has-picture", value);
    }

    public String attachment() {
        return fieldValue("attachment");
    }

    public void attachment(String value) {
        fieldValue("attachment", value);
    }

    public String status() {
        return fieldValue("status");
    }

    public void status(String value) {
        fieldValue("status", value);
    }

    public String path() {
        return fieldValue("path");
    }

    public void path(String value) {
        fieldValue("path", value);
    }

    public String comStatus() {
        return fieldValue("comp-status");
    }

    public void comStatus(String value) {
        fieldValue("comp-status", value);
    }

    public String testId() {
        return fieldValue("test-id");
    }

    public void testId(String value) {
        fieldValue("test-id", value);
    }

    public String actual() {
        return fieldValue("actual");
    }

    public void actual(String value) {
        fieldValue("actual", value);
    }

    public String expected() {
        return fieldValue("expected");
    }

    public void expected(String value) {
        fieldValue("expected", value);
    }

    public String executionTime() {
        return fieldValue("execution-time");
    }

    public void executionTime(String value) {
        fieldValue("execution-time", value);
    }

    public String executionDate() {
        return fieldValue("execution-date");
    }

    public void executionDate(String value) {
        fieldValue("execution-date", value);
    }

    public String hasLinkage() {
        return fieldValue("has-linkage");
    }

    public void hasLinkage(String value) {
        fieldValue("has-linkage", value);
    }
}
