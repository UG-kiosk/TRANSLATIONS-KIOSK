package com.kiosk.translations.azure.dtos;

import com.azure.ai.translation.text.models.InputTextItem;
import lombok.Data;

import java.util.List;

@Data
public class AzureTranslatorRequestDTO {
    private String from;
    private List<String> targetLanguages;
    private List<InputTextItem> content;

}
