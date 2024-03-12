package com.kiosk.translations.azure;

import com.azure.ai.translation.text.TextTranslationClient;
import com.azure.ai.translation.text.TextTranslationClientBuilder;
import com.azure.ai.translation.text.models.*;
import com.azure.core.credential.AzureKeyCredential;
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

    public Map<String, List<Map<String, String>>> translateTexts(List<Map<String, String>> azureTranslatorRequest, List<String> targetLanguages, String from){
        Map<String, List<Map<String, String>>> azureTranslated = new HashMap<>();
        int j=0;
        for (Map<String, String> object : azureTranslatorRequest) {
            List<TranslatedTextItem> translations = translateAzure(targetLanguages, from, object);

            for (int i = 0; i < translations.size(); i++) {
                TranslatedTextItem translation = translations.get(i);
                String key = object.keySet().toArray(new String[0])[i];
                for (Translation languagesTranslation : translation.getTranslations()) {

                    String language = languagesTranslation.getTo();
                    String text = languagesTranslation.getText();
                    azureTranslated.computeIfAbsent(language, k -> new ArrayList<>());

                    while (azureTranslated.get(language).size() <= j) {
                        azureTranslated.get(language).add(new HashMap<>());
                    }

                    azureTranslated.get(language).get(j).put(key, text);
                }
            }
            j+=1;
        }

        return azureTranslated;
    }

    private List<TranslatedTextItem> translateAzure(List<String> targetLanguages, String from, Map<String, String> object) {
        List<InputTextItem> content = object.values().stream().map(InputTextItem::new).collect(Collectors.toList());
        List<TranslatedTextItem> translations = client.translate(targetLanguages, content, null, from, TextType.PLAIN, null, ProfanityAction.NO_ACTION, ProfanityMarker.ASTERISK, false, false, null, null, null, false);
        return translations;
    }
}
