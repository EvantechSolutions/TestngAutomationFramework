package Utility;

public class DataMangerSetup 
{
	
	public static DataManager getDataManager(String type, String source, String user, String password) {
    switch (type.toLowerCase()) {
    case "excel":
        return new ExcelReader(source); 
    case "csv":
        return new CSVReader(source); 
    case "db":
        return new DBReader(source, user, password); 
    default:
        throw new IllegalArgumentException("Invalid data source type: " + type);
}
}
}
