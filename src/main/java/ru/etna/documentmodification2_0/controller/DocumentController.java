package ru.etna.documentmodification2_0.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.etna.documentmodification2_0.dto.DocumentFormDTO;
import ru.etna.documentmodification2_0.service.DocumentService;

@Controller
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("form", new DocumentFormDTO());
        return "index";
    }

    @PostMapping("/process")
    public String process(DocumentFormDTO documentFormDTO, Model model) {

        try {
            documentService.process(documentFormDTO);

            model.addAttribute("result", "PDF создан по пути: " + documentFormDTO.getPathDirectory() );
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка: " + e.getMessage());
        }
        model.addAttribute("form", documentFormDTO);
        return "index";
    }

    @PostMapping("/merge")
    public String mergePdfs(@RequestParam String pdfInputFolder, Model model) {
        try {
            documentService.mergePdf(pdfInputFolder);
            model.addAttribute("mergeResult", "Все PDF объединены ");
        } catch (Exception e) {
            model.addAttribute("mergeError", "Ошибка слияния: " + e.getMessage());
        }
        model.addAttribute("form", new DocumentFormDTO());
        return "index";
    }
}
