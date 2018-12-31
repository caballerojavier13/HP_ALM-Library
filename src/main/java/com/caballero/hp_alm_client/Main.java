package com.caballero.hp_alm_client;

import com.caballero.hp_alm_client.model.*;
import disney.hp_alm_client.model.*;
import com.caballero.hp_alm_client.utils.Config;
import com.caballero.hp_alm_client.utils.HpAlmClient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args){

        SimpleDateFormat formatTestSetDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatTestSetName = new SimpleDateFormat("yyyyMMdd_HHmmSS");

        String buildName = "1.7.0";

        String os = "iOS";

        String[] testIds = {"2488"}; // Given by parameters
        String executionName = String.format(
                "Automation %s - %s - #%s",
                os, buildName, formatTestSetName.format(Date.from(Instant.now())));

        String testSetFolderId = "16";

        try {

            HpAlmClient hpAlmClient = new HpAlmClient(new Config("alm.properties"));

            TestSet testSet = new TestSet();

            testSet.name(executionName);
            testSet.parentId(testSetFolderId);
            testSet.setStatus(TestSet.STATUS_OPEN);
            testSet.fields().add(new Field("open-date", formatTestSetDate.format(Date.from(Instant.now()))));

            testSet = hpAlmClient.saveTestSet(testSet);

            System.out.println(String.format("Test Set created with id: %s", testSet.id()));

            for(String testId : testIds) {

                Test test = hpAlmClient.loadTest(testId);

                TestInstance testInstance = new TestInstance();
                testInstance.testSetId(testSet.id());
                testInstance.testId(test.id());
                testInstance.name();
                testInstance.status(TestInstance.STATUS_NO_RUN);

                testInstance = hpAlmClient.createTestInstance(testInstance);

                System.out.println(String.format("Test instance created with id: %s", testInstance.id()));

                List<Run> runList = hpAlmClient.loadRunByTestInstance(testInstance.id());

                for(Run r: runList){

                    List<RunStep> runSteps = hpAlmClient.loadRunStepsByRun(r.id());

                    for(RunStep rs : runSteps){

                        rs.status(RunStep.STATUS_PASSED);

                        hpAlmClient.updateRunStep(rs);
                    }

                }

                testInstance.status(TestInstance.STATUS_PASSED);

                hpAlmClient.updateTestInstance(testInstance);

                // Uploading evidence

                File evidence = new File("file_path");

                if (evidence.exists()) {
                    hpAlmClient.uploadAttachmentToTestInstance(testInstance.id(), evidence);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
