package com.kiosk.translations.azure;

import com.azure.ai.translation.text.TextTranslationClient;
import com.azure.ai.translation.text.TextTranslationClientBuilder;
import com.azure.ai.translation.text.models.*;
import com.azure.core.credential.AzureKeyCredential;
import com.kiosk.translations.azure.dto.TranslationRequestDTO;
import com.kiosk.translations.azure.dto.TranslationResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AzureTranslatorService {
    static final String AZURE_REGION = System.getenv("TEXT_TRANSLATOR_API_REGION");

    static final String AZURE_TRANSLATOR_KEY = System.getenv("TEXT_TRANSLATOR_API_KEY");

    static final AzureKeyCredential CREDENTIAL = new AzureKeyCredential(AZURE_TRANSLATOR_KEY);
    private final TextTranslationClient client;

    public AzureTranslatorService() {
        client = new TextTranslationClientBuilder()
                .credential(CREDENTIAL)
                .region(AZURE_REGION)
                .buildClient();
    }

    public List<TranslationResponseDTO> translateTexts(List<TranslationRequestDTO> azureTranslatorRequest, List<String> targetLanguages, String from){
        List<TranslationResponseDTO> azureTranslatedResult = new ArrayList<>();
        for (TranslationRequestDTO objectToTranslate : azureTranslatorRequest) {
            List<TranslatedTextItem> translations = translateAzure(targetLanguages, from, objectToTranslate.getTranslationPayload());
            Map<String, String> innerMap = objectToTranslate.getTranslationPayload();

            TranslationResponseDTO translationObject = new TranslationResponseDTO();
            translationObject.setUniqueKey(objectToTranslate.getUniqueKey());
            translationObject.setTranslations(new HashMap<>());

            for (int i = 0; i < translations.size(); i++) {
                TranslatedTextItem translation = translations.get(i);
                String key = innerMap.keySet().toArray(new String[0])[i];
                for (Translation languagesTranslation : translation.getTranslations()) {
                    String language = languagesTranslation.getTo();
                    String text = languagesTranslation.getText();

                    translationObject.getTranslations().computeIfAbsent(language, k -> new HashMap<>());

                    translationObject.getTranslations().get(language).put(key, text);
                }
            }
            azureTranslatedResult.add(translationObject);
        }

        return azureTranslatedResult;
    }

    private List<TranslatedTextItem> translateAzure(List<String> targetLanguages, String from, Map<String, String> object) {
        List<InputTextItem> content = object.values().stream().map(InputTextItem::new).collect(Collectors.toList());
        List<TranslatedTextItem> translations = client.translate(targetLanguages, content, null, from, TextType.PLAIN, null, ProfanityAction.NO_ACTION, ProfanityMarker.ASTERISK, false, false, null, null, null, false);
        return translations;
    }
}
