package com.kiosk.translations.azure.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TranslationResponseData {
    private String uniqueKey;
    private Map<String, Map<String, String>> translations;

}
