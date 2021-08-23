import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

public class XLSParser {

    private final int TABLE_SIZE = 30;
    private String ter;
    private String date;
    private String format;
    private StringBuilder builder  = new StringBuilder();

    public void parse(File file) throws IOException, SQLException {

        parseFileName(file.getName());
        FileInputStream inputStream = new FileInputStream(file);

        Iterator<Row> rowIterator = getIterator(inputStream);

        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();

            if (checkIsCorrect(row)) {
                Iterator<Cell> cellIterator = row.cellIterator();
                builder.append("( ");

                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    if (cell.getCellType().equals(CellType.STRING)){
                        builder.append("\'" + cell.getStringCellValue() + "\'" + ", ");
                    }

                    if (cell.getCellType().equals(CellType.NUMERIC)){
                        builder.append(cell.getNumericCellValue() + ", ");
                    }
                }

                builder.append(ter + ", \'" + date + "\')");
                DBConnection.executeInsert(builder.toString());
                builder = new StringBuilder();
            }
        }
        inputStream.close();
    }

    private void parseFileName(String name)  {
        int terIndex = name.indexOf("_");
        int lastIndex = name.lastIndexOf(".");

        this.ter = name.substring(0, terIndex);
        this.date = name.substring(terIndex + 1, lastIndex);
        this.format = name.substring(lastIndex + 1);
    }

    private Iterator<Row> getIterator(FileInputStream inputStream) throws IOException {

        if (format.equals("xls")) {
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
            if (sheet.getLastRowNum() >= TABLE_SIZE) {
                return sheet.iterator();
            }
        }

        if (format.equals("xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
            if (sheet.getLastRowNum() >= TABLE_SIZE) {
                return sheet.iterator();
            }
        }

        return null;
    }

    private boolean checkIsCorrect(Row row ){


        if (row.getCell(1) == null && row.getCell(2) == null && row.getCell(3) == null){
           return false;
       }

        if (!(row.getCell(2).getCellType().equals(CellType.NUMERIC) && row.getCell(3).getCellType().equals(CellType.NUMERIC))){
            return false;
        }

        if (row.getCell(1).getStringCellValue().equals("")){
            return false;
        }

        return true;
    }

}

