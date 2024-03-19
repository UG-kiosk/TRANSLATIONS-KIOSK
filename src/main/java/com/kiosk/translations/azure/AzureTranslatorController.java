package com.kiosk.translations.azure;

import com.kiosk.translations.azure.dto.TranslationData;
import com.kiosk.translations.azure.dto.TranslationResponseData;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/translations")
public class AzureTranslatorController {
    public final AzureTranslatorService azureTranslatorService;

    public AzureTranslatorController(AzureTranslatorService azureTranslatorService){
        this.azureTranslatorService=azureTranslatorService;
    }

    @PostMapping
    List<TranslationResponseData> translateTexts(@RequestBody List<TranslationData> azureTranslatorRequest, @RequestParam List<String> targetLanguages, @RequestParam String from){
        return azureTranslatorService.translateTexts(azureTranslatorRequest, targetLanguages, from);
    }
}
