package com.kiosk.translations.azure.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TranslationRequestDTO {
    private String uniqueKey;
    private Map<String, String> translationPayload;

}
