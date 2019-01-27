package com.caballero.hp_alm_client;

import com.caballero.hp_alm_client.utils.ExcelReportProcessor;
import com.caballero.hp_alm_client.utils.ReportConfiguration;
import com.caballero.hp_alm_client.utils.ReportProcessor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Main2 {

    public static void main(String[] args) throws IOException {
        SimpleDateFormat formatTestSetName = new SimpleDateFormat("yyyyMMdd_HHmmSS");

        String report = "/users/javier.caballero/Downloads/report.xlsx";

        String buildName = "1.7.0";

        String os = "iOS";

        String executionName = String.format(
                "Automation %s - %s - #%s",
                os, buildName, formatTestSetName.format(Date.from(Instant.now())));

        String testSetFolderId = "16";

        ReportProcessor reportProcessor = new ExcelReportProcessor();

        ReportConfiguration reportConfiguration = new ReportConfiguration(0, 3);

        reportProcessor.processReport(report, executionName, testSetFolderId, reportConfiguration);

    }

}
