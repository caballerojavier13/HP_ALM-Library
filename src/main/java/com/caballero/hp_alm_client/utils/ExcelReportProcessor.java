package com.caballero.hp_alm_client.utils;

import com.caballero.hp_alm_client.model.Field;
import com.caballero.hp_alm_client.model.TestInstance;
import com.caballero.hp_alm_client.model.TestSet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class ExcelReportProcessor implements ReportProcessor {
    @Override
    public void processReport(String reportPath, String executionName, String testSetFolderId, ReportConfiguration reportConfiguration) throws IOException {

        SimpleDateFormat formatTestSetDate = new SimpleDateFormat("yyyy-MM-dd");

        File excelFile = new File(reportPath);
        FileInputStream fis = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        try {

            HpAlmClient hpAlmClient = new HpAlmClient(new Config("alm.properties"));

            TestSet testSet = new TestSet();

            testSet.name(executionName); // Must be unique
            testSet.parentId(testSetFolderId);
            testSet.setStatus(TestSet.STATUS_OPEN);
            testSet.fields().add(new Field("open-date", formatTestSetDate.format(Date.from(Instant.now()))));

            testSet = hpAlmClient.saveTestSet(testSet);

            System.out.println(String.format("Test Set created with id: %s", testSet.id()));

            // for every row in the report, it's mean for every test case executed
            for (Row row : workbook.getSheetAt(0)) {

                String testId = row.getCell(reportConfiguration.getTestIdIndexColumn()).getStringCellValue();
                String testStatus = row.getCell(reportConfiguration.getTestStatusIndexColumn()).getStringCellValue();

                TestInstance testInstance = new TestInstance();
                testInstance.testSetId(testSet.id());
                testInstance.testId(testId);
                testInstance.status(TestInstance.STATUS_NO_RUN);

                // Add the test cases to the test run as a test instance
                testInstance = hpAlmClient.createTestInstance(testInstance);

                System.out.println(String.format("Test instance created with id: %s", testInstance.id()));

                testInstance.status(TestInstance.getTestInstanceStatusFromReportStatus(testStatus));

                // Update the tests instance with the execution result
                hpAlmClient.updateTestInstance(testInstance);



            }

        } catch (Exception e){
            e.printStackTrace();
        }

        workbook.close();
        fis.close();
    }
}
