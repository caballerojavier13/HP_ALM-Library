package com.caballero.hp_alm_client;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelReportGenerator {

    public void generateExcelReport(String destFileName) throws ParserConfigurationException, IOException, SAXException {

        File xmlFile = new File("/Users/javier.caballero/Downloads/target/surefire-reports/testng-results.xml");

        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder build = fact.newDocumentBuilder();
        Document doc = build.parse(xmlFile);
        doc.getDocumentElement().normalize();

        XSSFWorkbook book = new XSSFWorkbook();
        XSSFCellStyle fail = book.createCellStyle();
        XSSFCellStyle pass = book.createCellStyle();

        NodeList test_list = doc.getElementsByTagName("test");

        for (int i = 0; i < test_list.getLength(); i++) {
            Node test_node = test_list.item(i);
            String test_name = ((Element) test_node).getAttribute("name");
            System.out.println(test_name);
        }


        for (int i = 0; i < test_list.getLength(); i++) {
            int r = 0;
            Node test_node = test_list.item(i);
            String test_name = ((Element) test_node).getAttribute("name");

            //XSSFSheet sheet = book.createSheet(test_name);
            XSSFSheet sheet = book.createSheet("Test Set #" + i);
            NodeList class_list = ((Element) test_node).getElementsByTagName("class");

            for (int j = 0; j < class_list.getLength(); j++) {
                Node class_node = class_list.item(j);
                String class_name = ((Element) class_node).getAttribute("name");
                NodeList test_method_list = ((Element) class_node).getElementsByTagName("test-method");
                for (int k = 0; k < test_method_list.getLength(); k++) {

                    Node test_method_node = test_method_list.item(k);
                    String test_method_name = ((Element) test_method_node).getAttribute("name");
                    String test_method_status = ((Element) test_method_node).getAttribute("status");
                    String test_method_config_value = ((Element) test_method_node).getAttribute("is-config");

                    if (!test_method_config_value.equalsIgnoreCase("true")) {
                        fail.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
                        pass.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());

                        fail.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        pass.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                        String test_method_description = ((Element) test_method_node).getAttribute("description");
                        String[] values = test_method_description.split(",");

                        XSSFRow row = sheet.createRow(r++);
                        XSSFCell cel_id = row.createCell(0);

                        cel_id.setCellValue(values.length > 1? values[0] : "");

                        XSSFCell cel_description = row.createCell(1);

                        cel_description.setCellValue(values.length > 1? values[1] : "");

                        XSSFCell cel_name = row.createCell(2);
                        cel_name.setCellValue(class_name + "." + test_method_name);

                        XSSFCell cel_status = row.createCell(3);
                        cel_status.setCellValue(test_method_status);

                        if ("fail".equalsIgnoreCase(test_method_status))
                            cel_status.setCellStyle(fail);
                        else
                            cel_status.setCellStyle(pass);

                        XSSFCell cel_excep;
                        String excep_msg;


                        if ("fail".equalsIgnoreCase(test_method_status)) {
                            NodeList excep_list = ((Element) test_method_node).getElementsByTagName("exception");
                            Node excep_node = excep_list.item(0);
                            excep_msg = ((Element) test_method_node).getAttribute("class");

                            cel_excep = row.createCell(4);
                            cel_excep.setCellValue(excep_msg);
                        }
                    }
                }
            }
        }

        FileOutputStream fout = new FileOutputStream("./" + destFileName);
        book.write(fout);
        fout.close();
        System.out.println("Report Generated !!");
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        new ExcelReportGenerator().generateExcelReport("report.xlsx");

    }

}
