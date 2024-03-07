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
    Map<String, Map<String, String>> translateText(@RequestBody Map<String, String> azureTranslatorRequest, @RequestParam List<String> targetLanguages, @RequestParam String from){
        return azureTranslatorService.translateText(azureTranslatorRequest, targetLanguages, from);
    }
}
