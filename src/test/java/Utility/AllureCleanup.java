package Utility;

import org.testng.annotations.BeforeSuite;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class AllureCleanup {

    @BeforeSuite(alwaysRun=true)
    public void cleanAllureFolders() {
        deleteFolder("allure-results");
//        deleteFolder("allure-report");
    }

    private void deleteFolder(String folderName) {
        Path folderPath = Paths.get(System.getProperty("user.dir"), folderName);
        try {
            if (Files.exists(folderPath)) {
                Files.walk(folderPath)
                        .map(Path::toFile)
                        .forEach(File::delete);
                System.out.println("Cleaned folder: " + folderName);
            } else {
                System.out.println("Folder not found: " + folderName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
