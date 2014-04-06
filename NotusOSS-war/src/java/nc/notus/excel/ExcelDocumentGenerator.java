package nc.notus.excel;

import java.io.FileOutputStream;
import java.io.IOException;
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
 * Class provides a methods to generate a formatted excel document
 * @author Andrey Ilin
 */
public class ExcelDocumentGenerator {

    private final int ROW_NUM = 6;
    private final int CELL_NUM = 3;
    private final int STYLES_NUM = 4;
    private HSSFWorkbook workBook = new HSSFWorkbook();
    private HSSFSheet sheet;
    //TODO: Change arrays to some proper data source.
    private String[] titles = {"Requirement", "Requirement description",
        "Priority"};
    private String[] reqDesc = {"Assumed system uses English language for all" +
        " text and graphical screens", "Any location management is out of " +
        "scope", "All routers are considered to be hosted at one place",
        "Process Orchestrator is out of scope", "Catalog editing is out of" +
        " scope", "Service Template editing if out of scope"};
    private String req = "NC.KYIV.2014.WIND.OSS.";
    private String priority = "M";

    private CellStyle[] initStyles() {
        int counter = 0;
        Font boldFont = workBook.createFont();
        Font defaultFont = workBook.createFont();
        defaultFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        CellStyle[] cs = new CellStyle[STYLES_NUM];

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

    /**
     * Creates a new excel sheet in work book. This sheet will have name given
     * by the user and cell styles specified in initStyles method.
     * @param name of a new sheet
     */
    public void createNewSheet(String name) {
        int styleIndex = 0;
        this.sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName(name));
        CellStyle[] cs = initStyles();
        //creating header row
        HSSFRow header = sheet.createRow(0);
        //creating cells in the header row
        HSSFCell cell = null;
        styleIndex = 1;
        for (int i = 0; i < CELL_NUM; i++) {
            cell = header.createCell(i, HSSFCell.CELL_TYPE_STRING);
            cell.setCellStyle(cs[0]);
            cell.setCellValue(titles[i]);
            if (i == 2) {
                cell.setCellStyle(cs[styleIndex]);
            }
        }
        //creating data rows, cell and setting cell style
        //for every cell in the row
        HSSFRow tempRow = null;
        HSSFCell tempCell = null;

        for (int i = 0; i < ROW_NUM; i++) {
            tempRow = sheet.createRow(i + 1);
            for (int j = 0; j < CELL_NUM; j++) {
                tempCell = tempRow.createCell(j);
                if (j == 0) {
                    styleIndex = 2;
                    tempCell.setCellStyle(cs[styleIndex]);
                    tempCell.setCellValue(req + Integer.toString(i + 1));
                } else {
                    if (j == 1) {
                        tempCell.setCellValue(reqDesc[i]);
                    }
                    if (j == 2) {
                        styleIndex = 3;
                        tempCell.setCellStyle(cs[styleIndex]);
                        tempCell.setCellValue(priority);
                    }
                }
            }
            //setting column width
            sheet.setColumnWidth(0, 40 * 256);
            sheet.setColumnWidth(1, 60 * 256);
            sheet.setColumnWidth(2, 10 * 256);

        }
    }

    /**
     * Creates a new excel document. Document will contain sheets created with
     * createNewSheet method.
     */
    void generateReport() {
        try {
            FileOutputStream fileOut = new FileOutputStream("workbook.xls");
            workBook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
    }
}
