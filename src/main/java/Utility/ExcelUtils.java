package Utility;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static int logCounter = 0;
	public static XSSFWorkbook reportWorkbook = new XSSFWorkbook();
	public static XSSFSheet reportSheet;
	public static int statusCounter = 0;
	public static XSSFWorkbook statusWorkbook = new XSSFWorkbook();
	public static XSSFSheet statusSheet;
	public static Workbook rtmWorkbook;
	public static Sheet rtmSheet;
	public static int rowNum = 0;
	public static int cellNum = 0;

	public void createSheet(String sheetName) {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet(sheetName);
	}

	public void pushToSheet(List<String> data, XSSFSheet xLSheet, int rowNum) throws IOException {
		Row newRow = xLSheet.createRow(rowNum);
		for (int j = 0; j < data.size(); j++) {
			// Fill data in row
			Cell cell = newRow.createCell(j);
			cell.setCellValue((String) data.get(j));
		}
	}

	public void pushToSheet(String data, int rowNum) {
		Row row = reportSheet.createRow(rowNum);
		Cell cell = row.createCell(0);
		cell.setCellValue(data);
	}

	public void writeToFile(String fileName, XSSFWorkbook workbook) throws IOException {
		File file = new File(System.getProperty("user.dir") + "\\test-output\\Output Files\\" + fileName + ".xlsx");
		FileOutputStream writeFile = new FileOutputStream(file);
		workbook.write(writeFile);
		writeFile.close();
	}

	public void writeToExcel(String fileName, String sheetName, List<String> data) throws IOException {
		this.createSheet(sheetName);
		this.pushToSheet(data, sheet, 0);
		this.writeToFile(fileName, workbook);
	}

	public void updateToExcel(String fileName, List<String> data) throws IOException {
		int num = sheet.getLastRowNum();
		this.pushToSheet(data, sheet, num + 1);
		this.writeToFile(fileName, workbook);
	}

	public static Map<String, String> readExcel() throws Exception {
		File file = new File(System.getProperty("user.dir") + "/InputData.xlsx");
		FileInputStream fileStream = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		DataFormatter formatter = new DataFormatter(Locale.US);
		Map<String, String> hmap = new HashMap<String, String>();
		String key, value;
		for (int i = 0; i < 12; i++) {
			Row row = sheet.getRow(i);
			key = formatter.formatCellValue(row.getCell(0));
			value = formatter.formatCellValue(row.getCell(1));
			hmap.put(key, value);
		}
		workbook.close();
		return hmap;
	}

	public void reportToExcel(String message) throws IOException {
		if (logCounter == 0)
			reportSheet = reportWorkbook.createSheet("Logger Sheet");
		System.out.println(logCounter + ": " + message);
		this.pushToSheet(message, logCounter);
		logCounter++;
		this.writeToFile("ExecutionReport", reportWorkbook);
	}

	public void statusUpdate(String testCaseID, String testCaseName, String status) throws IOException {
		List<String> contents = new ArrayList<String>();
		if (statusCounter == 0)
			statusSheet = statusWorkbook.createSheet("Status Sheet");
		contents.add(testCaseID);
		contents.add(testCaseName);
		contents.add(status);
		this.pushToSheet(contents, statusSheet, statusCounter);
		statusCounter++;
		this.writeToFile("StatusReport", statusWorkbook);
	}

	public static void setUpRTMExcel() {
		try {
			File file = new File(System.getProperty("user.dir") + "/HotWheels.xlsx");
			InputStream fileStream = new FileInputStream(file);
			rtmWorkbook = WorkbookFactory.create(fileStream);
			rtmSheet = rtmWorkbook.getSheet(BeginnerClass.prop.getProperty("sheetName"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setRowCellValues(String testCaseId) {
		String[] pair = BeginnerClass.prop.getProperty(testCaseId).split(" ");
		rowNum = Integer.parseInt(pair[0].trim());
		cellNum = Integer.parseInt(pair[1].trim());
	}
	
	public void writeToFile() throws IOException {
		rowNum++;
		File file = new File(System.getProperty("user.dir") +  "/test-output/Output Files/HotWheels_Updated.xlsx");
		FileOutputStream writeFile = new FileOutputStream(file);
		rtmWorkbook.write(writeFile);
		writeFile.close();
	}

	@SuppressWarnings("deprecation")
	public void updateResultToRTM(String message) {
		Cell cell = rtmSheet.getRow(rowNum).getCell(cellNum);
		if (cell == null)
			cell = rtmSheet.getRow(rowNum).createCell(cellNum);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(message);
		BeginnerClass.rtmExecution++;
		updateStatusInExcel(message);
	}
	

	@SuppressWarnings("deprecation")
	public void updateStatusInExcel(String actual) {
		DataFormatter formatter = new DataFormatter(Locale.US);
		String expected = formatter.formatCellValue(rtmSheet.getRow(rowNum).getCell(cellNum-1));
		
		Cell cell = rtmSheet.getRow(rowNum).getCell(cellNum+1);
		if (cell == null)
			cell = rtmSheet.getRow(rowNum).createCell(cellNum+1);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		if(actual.equalsIgnoreCase(expected))
			cell.setCellValue("Pass");
		else
			cell.setCellValue("Fail");
		
		try {
			writeToFile();
		} catch (IOException e) {
			System.out.println("RTM Status Update is failed >>> " + e.getMessage());
		}
		
	}
	
	public static void resetRowValue(int number) {
		rowNum = rowNum-number;
	}

}