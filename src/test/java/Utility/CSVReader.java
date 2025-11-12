package Utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class CSVReader implements DataManager {
	private String filePath;

	public CSVReader(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public List<Map<String, String>> getAllTestData(String paramter) {
		List<Map<String, String>> allData = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String[] headers = br.readLine().split(",");
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				Map<String, String> dataMap = new HashMap<>();
				for (int i = 0; i < headers.length; i++) {
					dataMap.put(headers[i], values[i]);
				}
				allData.add(dataMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allData;
	}
}