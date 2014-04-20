package nc.notus.reports;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.WorkbookUtil;

/**
 * This class is an abstract representation of system                           
 * @author Andrey Ilin
 */
public class ReportGenerator {

    /* Separates columns in reportData row strings */
    private final String COLUMN_SEPARATOR = "#";

    /*
     * Report data stored here                                                  
     * Data is stored as strings that represents table rows
     * Columns in this rows are separated with COLUMN_SEPARATOR
     * First element is a row of column names
     */
    private String[] reportData = null;
    private String reportName = null;
    private HSSFWorkbook workBook = new HSSFWorkbook();

    /**
     * Creates a new instance of ReportGenerator for specific report 
     * @param type report's type
     * @param startDateString  date report term starts with
     * @param finishDateString date report term ends with
     */
    public ReportGenerator(Report report) {
        this.reportData = report.getReportData();
        this.reportName = report.getReportName();
    }

    /**
     * Returns name of the report to be generated
     * @return name of the report to be generated
     */
    public String getReportName() {
        return this.reportName;
    }

    /**
     * Returns a report as html format string
     * @return html format string
     */
    public String getReportHTML() {
        StringBuilder HTMLReportBuilder = new StringBuilder();
        HTMLReportBuilder.append("<table border='1' width='50%' cellpadding='10'>");
        if (reportData != null) {
            for (String row : reportData) {
                String[] columns = row.split(COLUMN_SEPARATOR);
                HTMLReportBuilder.append("<tr>");
                for (int i = 0; i < columns.length; i++) {
                    HTMLReportBuilder.append("<td>");
                    HTMLReportBuilder.append(columns[i]);
                    HTMLReportBuilder.append("</td>");
                }
                HTMLReportBuilder.append("</tr>");
            }
            HTMLReportBuilder.append("</table>");
        }
        return HTMLReportBuilder.toString();
    }

    public void getReportXLS(OutputStream streamToWrite) throws IOException {
        createNewSheet();
        workBook.write(streamToWrite);
    }

    /**
     * Creates a new excel sheet in work book. This sheet will have name given
     * by the user and cell styles specified in initStyles method.
     */
    public void createNewSheet() {
        int styleIndex = 0;
        HSSFSheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName(
                reportName));
        /*
         * 0 - bold, left-aligned
         * 1 - bold, center-aligned
         * 2 - normal, left-aligned
         * 3 - normal, center-aligned
         * Use styleIndex to specify style
         */
        CellStyle[] cellStyle = initStyles();
        HSSFRow tempRow = sheet.createRow(0);
        HSSFCell tempCell = null;
        String[] dataRow = null;
        String[] columnNames = reportData[0].split(COLUMN_SEPARATOR);
        int columnNumber = columnNames.length;
        int rowNumber = reportData.length;
        styleIndex = 1; //Style for headers

        /* Creating data rows, cells with specific cell style */
        int columnWidthInChars = 50 * 256;

        /* Column headers */
        for (int i = 0; i < columnNumber; i++) {
            tempCell = tempRow.createCell(i, HSSFCell.CELL_TYPE_STRING);
            tempCell.setCellStyle(cellStyle[styleIndex]);
            tempCell.setCellValue(columnNames[i]);

        }
        for (int i = 1; i < rowNumber; i++) {
            tempRow = sheet.createRow(i);
            dataRow = reportData[i].split(COLUMN_SEPARATOR);
            for (int j = 0; j < columnNumber; j++) {
                tempCell = tempRow.createCell(j);
                if (j == 0) {
                    styleIndex = 3;
                    tempCell.setCellStyle(cellStyle[styleIndex]);
                    tempCell.setCellValue(dataRow[j]);
                } else {
                    styleIndex = 2;
                    tempCell.setCellStyle(cellStyle[styleIndex]);
                    tempCell.setCellValue(dataRow[j]);
                }
            }
            for (int k = 0; k < columnNumber; k++) {
                sheet.setColumnWidth(i, columnWidthInChars);
            }
        }

    }

    private CellStyle[] initStyles() {
        int counter = 0;
        Font boldFont = workBook.createFont();
        Font defaultFont = workBook.createFont();
        defaultFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        CellStyle[] cs = new CellStyle[4]; //number of styles in table

        //creating header left-aligned cell style
        CellStyle headerLeftCS = workBook.createCellStyle();
        headerLeftCS.setAlignment(CellStyle.ALIGN_LEFT);
        headerLeftCS.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerLeftCS.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        headerLeftCS.setFont(boldFont);
        headerLeftCS.setWrapText(false);
        cs[counter] = headerLeftCS;
        counter++;
        //creating header center cell style
        CellStyle headerCenterCS = workBook.createCellStyle();
        headerCenterCS.setAlignment(CellStyle.ALIGN_CENTER);
        headerCenterCS.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerCenterCS.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        headerCenterCS.setFont(boldFont);
        headerCenterCS.setWrapText(false);
        cs[counter] = headerCenterCS;
        counter++;
        //creating left-aligned cell style
        CellStyle leftAlignedCS = workBook.createCellStyle();
        leftAlignedCS.setAlignment(CellStyle.ALIGN_LEFT);
        leftAlignedCS.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        leftAlignedCS.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        leftAlignedCS.setFont(boldFont);
        leftAlignedCS.setWrapText(true);
        cs[counter] = leftAlignedCS;
        counter++;
        //creating center-aligned cell style
        CellStyle centerAlignedCS = workBook.createCellStyle();
        centerAlignedCS.setAlignment(CellStyle.ALIGN_CENTER);
        centerAlignedCS.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        centerAlignedCS.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
        centerAlignedCS.setFont(defaultFont);
        centerAlignedCS.setWrapText(true);
        cs[counter] = centerAlignedCS;
        counter++;
        return cs;
    }
}
