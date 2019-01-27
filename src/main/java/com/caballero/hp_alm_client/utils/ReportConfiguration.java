package com.caballero.hp_alm_client.utils;

public class ReportConfiguration {

    private int testIdIndexColumn;
    private int testStatusIndexColumn;

    public ReportConfiguration() {}

    public ReportConfiguration(int testIdIndexColumn, int testStatusIndexColumn) {
        this.testIdIndexColumn = testIdIndexColumn;
        this.testStatusIndexColumn = testStatusIndexColumn;
    }

    public int getTestIdIndexColumn() {
        return testIdIndexColumn;
    }

    public void setTestIdIndexColumn(int testIdIndexColumn) {
        this.testIdIndexColumn = testIdIndexColumn;
    }

    public int getTestStatusIndexColumn() {
        return testStatusIndexColumn;
    }

    public void setTestStatusIndexColumn(int testStatusIndexColumn) {
        this.testStatusIndexColumn = testStatusIndexColumn;
    }
}
