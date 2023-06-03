package project.project.file;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
@Getter
public class UploadFile {
    private static final String PATH="/intellij/project_img/";
    private String fileName;
    private String storeName;

    public UploadFile(String fileName) {

        this.fileName = fileName;
        String[] split = fileName.split("\\.");
        this.storeName = UUID.randomUUID().toString()+"."+split[split.length-1];
    }

    public void fileUpload(){
            try {
                URL url = new URL(fileName);
                Path path = Path.of(PATH + storeName);
                Files.copy(url.openStream(),path, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e){
                e.printStackTrace();
            }
    }

    public void fileUpload(MultipartFile file) {
        try {
            file.transferTo(new File(getFullPath()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getFullPath(){
        return PATH+storeName;
    }
    
}
