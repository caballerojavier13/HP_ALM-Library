package com.caballero.hp_alm_client.utils;

import java.io.IOException;

public interface ReportProcessor {
    void processReport(String reportPath, String executionName, String testSetFolderId) throws IOException;
}
