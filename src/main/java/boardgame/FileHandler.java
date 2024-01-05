package boardgame;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler {
    
    /**
     * @param save,filename, filePath
     * gets file to save and gets  contents using Saveable interface
     */
    public static void saveToFile(Saveable save, String filename, String filePath){
        Path path = FileSystems.getDefault().getPath(filePath);
        try{
            Files.writeString(path, save.getStringToSave());
        } catch (IOException e) {
            throw(new RuntimeException(e.getMessage()));        
        }
    }

    /**
     * @param load,filename, filePath
     * gets file to load and gets  contents using Saveable interface
     */
    public static void loadFromFile(Saveable load, String filename, String filePath){
        Path path = FileSystems.getDefault().getPath(filePath);
        try{
            String fileLines = Files.readString(path);

            load.loadSavedString(fileLines);
        
        } catch (IOException e) {
            throw(new RuntimeException(e.getMessage()));        
        }
    }
    

    /**
     * @return string, toString 
     */
    @Override 
    public String toString(){
        return "Processing. Handling File.";
    }
}
