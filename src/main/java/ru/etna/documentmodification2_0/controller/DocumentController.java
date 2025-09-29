package ru.etna.documentmodification2_0.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.etna.documentmodification2_0.dto.DocumentFormDTO;
import ru.etna.documentmodification2_0.dto.PsiFormDto;
import ru.etna.documentmodification2_0.entity.StatisticsDocHandler;
import ru.etna.documentmodification2_0.repository.StatsRepository;
import ru.etna.documentmodification2_0.service.DocumentService;
import ru.etna.documentmodification2_0.service.StatsService;

import java.util.List;


/***
 * Автор: Антон Долгов
 * Дата создания 16.06.2025
 * телеграмм @x3tv1995
 */
@Controller
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    private final   StatsService  statsService;
    private final StatsRepository statsRepository;

    @GetMapping("/statsTop5")
    public String statsTop5(Model model) {
        List<Object[]> templates = statsService.top5SlowTemplate();
        model.addAttribute("templates", templates);
        return "stats";
    }

    @GetMapping("/stats")
    public String stats(Model model) {
        List<StatisticsDocHandler> all = statsRepository.findAll();
        model.addAttribute("stats", all);
        return "statsAll";
    }


    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("formPassports", new DocumentFormDTO());
        model.addAttribute("formPsi", new PsiFormDto());
        return "index";
    }

    @PostMapping("/processPassports")
    public String process(DocumentFormDTO documentFormDTO, Model model) {

        try {
            documentService.process(documentFormDTO);

            model.addAttribute("resultPassports", "PDF создан по пути: " + documentFormDTO.getPathDirectory());
        } catch (Exception e) {
            model.addAttribute("errorPassports", "Ошибка: " + e.getMessage());
        }
        model.addAttribute("formPassports", new DocumentFormDTO());
        model.addAttribute("formPsi", new PsiFormDto());
        return "index";
    }
    @GetMapping("/process")
    public String showFormPassports(Model model) {
        model.addAttribute("formPassports", new DocumentFormDTO());
        model.addAttribute("formPsi", new PsiFormDto());
        return "index";
    }

    @PostMapping("/processPsi")
    public String processPsi(PsiFormDto psiFormDto, Model model) {

        try {
            documentService.processForDocx(psiFormDto);

            model.addAttribute("resultPsi", "Документы созданы по пути: " + psiFormDto.getPathDirectory());
        } catch (Exception e) {
            model.addAttribute("errorPsi", "Ошибка: " + e.getMessage());
        }
        model.addAttribute("formPsi", new PsiFormDto());
        model.addAttribute("formPassports", new DocumentFormDTO());

        return "index";
    }

    @PostMapping("/mergePsi")
    public String mergeDocx(@RequestParam String docxInputFolder, Model model) {

        try {

            documentService.mergePdfForPsi(docxInputFolder);
            model.addAttribute("mergeResultPsi", "Все psi.pdf объединены ");
        } catch (Exception e) {
            model.addAttribute("mergeErrorPsi", "Ошибка слияния: " + e.getMessage());
        }
        model.addAttribute("formPsi", new PsiFormDto());
        model.addAttribute("formPassports", new DocumentFormDTO());

        return "index";
    }


    @PostMapping("/mergePassports")
    public String mergePdfs(@RequestParam String pdfInputFolder, Model model) {
        try {
            documentService.mergePdf(pdfInputFolder);
            model.addAttribute("mergeResultPassports", "Все PDF объединены ");
        } catch (Exception e) {
            model.addAttribute("mergeErrorPassports", "Ошибка слияния: " + e.getMessage());
        }
        model.addAttribute("formPassports", new DocumentFormDTO());
        model.addAttribute("formPsi", new PsiFormDto());
        return "index";
    }

    @GetMapping("/processPassports")
    public String handleGetProcess() {
        return "redirect:/";
    }
}
