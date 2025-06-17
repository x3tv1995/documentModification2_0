package ru.etna.documentmodification2_0.service;

import lombok.Data;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
@Data
@Service
public class DocumentCacheService {
    private XWPFDocument cachedDocTemplate;
    private  String  templatePath;
    public void  templateLoad(String path) throws IOException {
        if(cachedDocTemplate == null || !path.equals(templatePath)) {
                 try (FileInputStream fis = new FileInputStream(new File(path))) {
                      this.cachedDocTemplate = new XWPFDocument(fis);
                 }
                 this.templatePath = path;
        }

    }


}
