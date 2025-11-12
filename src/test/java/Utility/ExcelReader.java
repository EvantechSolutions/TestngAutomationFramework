package Utility;

import java.io.FileInputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader implements DataManager {
	private String filePath;

	public ExcelReader(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public List<Map<String, String>> getAllTestData(String sheetName) {
		List<Map<String, String>> allData = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			Row headerRow = sheet.getRow(0);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				Map<String, String> dataMap = new HashMap<>();
				for (int j = 0; j < row.getLastCellNum(); j++) {
					String key = headerRow.getCell(j).getStringCellValue();
					Cell cell = row.getCell(j);
					String value = "";
					if (cell != null) {
						switch (cell.getCellType()) {
						case STRING:
							value = cell.getStringCellValue();
							break;
						case NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								value = cell.getDateCellValue().toString();
							} else {
								value = String.valueOf((long) cell.getNumericCellValue());
							}
							break;
						case BOOLEAN:
							value = String.valueOf(cell.getBooleanCellValue());
							break;
						case FORMULA:
							value = cell.getCellFormula();
							break;
						case BLANK:
							value = "";
							break;
						default:
							value = "";
						}
					}
					dataMap.put(key, value);
				}
				allData.add(dataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allData;
	}
}