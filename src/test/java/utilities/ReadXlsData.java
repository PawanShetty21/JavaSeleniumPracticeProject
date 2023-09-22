package utilities;

import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadXlsData {

public String exlSheetName="login";


    //Calling return type as Objects since Excel sheet can contain String, int, long values
    @DataProvider(name = "testData")
    public Object[][] getData() throws IOException {
        String root_path = System.getProperty("user.dir"); // To get root path

        File file = new File(root_path + "/src/test/testData/TestData.xlsx");
        FileInputStream fis = new FileInputStream(file);
        Workbook wb = WorkbookFactory.create(fis);
        Sheet sheetName = wb.getSheet(exlSheetName);

        int totalRows = sheetName.getLastRowNum();
        System.out.println("Total rows"+totalRows);

        Row rowCells = sheetName.getRow(0);
        int totalCols = rowCells.getLastCellNum();
        System.out.println("Total columns: "+ totalCols);
        DataFormatter format = new DataFormatter();

        String testData[][] = new String[totalRows][totalCols];
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                testData[i - 1][j] = format.formatCellValue(sheetName.getRow(i).getCell(j));
                System.out.println(testData[i - 1][j]);
            }
        }
        return testData;

    }
}
