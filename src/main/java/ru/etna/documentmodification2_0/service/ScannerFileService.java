package ru.etna.documentmodification2_0.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Service
public class ScannerFileService {
    private static final Logger log = LoggerFactory.getLogger(ScannerFileService.class);

    public   List<String> arrayPathAbsolute(String folderPath) {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        List<String> listFilesPdfAbsolute = new ArrayList<String>();
        if (folder.isDirectory() && folder.exists()) {
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.getAbsolutePath().toLowerCase().endsWith(".pdf")) {
                        listFilesPdfAbsolute.add(file.getAbsolutePath());
                    }
                }
            }
        }else {
            log.info("Папки не существует");
           throw  new IllegalArgumentException("Папки не существует");
        }
        return listFilesPdfAbsolute;
    }
}
