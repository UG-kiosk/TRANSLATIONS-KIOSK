package com.kiosk.translations.azure;

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
    Map<String, List<Map<String, String>>> translateTexts(@RequestBody List<Map<String, String>> azureTranslatorRequest, @RequestParam List<String> targetLanguages, @RequestParam String from){
        return azureTranslatorService.translateTexts(azureTranslatorRequest, targetLanguages, from);
    }
}
