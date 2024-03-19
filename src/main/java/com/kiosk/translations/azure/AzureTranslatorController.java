package com.kiosk.translations.azure;

import com.kiosk.translations.azure.dto.TranslationRequestDTO;
import com.kiosk.translations.azure.dto.TranslationResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/translations")
public class AzureTranslatorController {
    public final AzureTranslatorService azureTranslatorService;

    public AzureTranslatorController(AzureTranslatorService azureTranslatorService){
        this.azureTranslatorService=azureTranslatorService;
    }

    @PostMapping
    List<TranslationResponseDTO> translateTexts(@RequestBody List<TranslationRequestDTO> azureTranslatorRequest, @RequestParam List<String> targetLanguages, @RequestParam String from){
        return azureTranslatorService.translateTexts(azureTranslatorRequest, targetLanguages, from);
    }
}
