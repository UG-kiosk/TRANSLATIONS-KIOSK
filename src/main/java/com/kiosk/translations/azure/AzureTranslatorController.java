package com.kiosk.translations.azure;

import com.azure.ai.translation.text.models.TranslatedTextItem;
import com.kiosk.translations.azure.dtos.AzureTranslatorRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/translations")
public class AzureTranslatorController {
    public final AzureTranslatorService azureTranslatorService;

    public AzureTranslatorController(AzureTranslatorService azureTranslatorService){
        this.azureTranslatorService=azureTranslatorService;
    }

    @PostMapping
    List<TranslatedTextItem> translateText(@RequestBody AzureTranslatorRequestDTO azureTranslatorRequestDTO){
        return azureTranslatorService.translateText(azureTranslatorRequestDTO);
    }
}
