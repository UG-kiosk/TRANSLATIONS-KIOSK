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

    public Map<String, Map<String, String>> translateText(Map<String, String> azureTranslatorRequest, List<String> targetLanguages, String from){
        List<InputTextItem> content = new ArrayList<>();
        for (Map.Entry<String, String> request : azureTranslatorRequest.entrySet()) {
            content.add(new InputTextItem(request.getValue()));
        }
        List<TranslatedTextItem> translations = client.translate(targetLanguages, content, null, from, TextType.PLAIN, null, ProfanityAction.NO_ACTION, ProfanityMarker.ASTERISK, false, false, null, null, null, false);
        Map<String, Map<String, String>> azureTranslated = new HashMap<>();
        for (int i = 0; i < translations.size(); i++) {
            TranslatedTextItem translation = translations.get(i);
            String key = azureTranslatorRequest.keySet().toArray(new String[0])[i];
            for (Translation languagesTranslation : translation.getTranslations()) {
                String language = languagesTranslation.getTo();
                String text = languagesTranslation.getText();

                if (!azureTranslated.containsKey(language)) {
                    azureTranslated.put(language, new HashMap<>());
                }
                azureTranslated.get(language).put(key, text);
            }
        }
        return azureTranslated;
    }
}
