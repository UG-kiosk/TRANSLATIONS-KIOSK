package com.kiosk.translations.azure;

import com.azure.ai.translation.text.TextTranslationClient;
import com.azure.ai.translation.text.TextTranslationClientBuilder;
import com.azure.ai.translation.text.models.*;
import com.azure.core.credential.AzureKeyCredential;
import com.kiosk.translations.azure.dtos.AzureTranslatorRequestDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<TranslatedTextItem> translateText(AzureTranslatorRequestDTO azureTranslatorRequestDTO){
//        String from = "en";
//        List<String> targetLanguages = new ArrayList<>();
//        targetLanguages.add("pl");
//        List<InputTextItem> content = new ArrayList<>();
//        content.add(new InputTextItem("This is a test."));

        List<TranslatedTextItem> translations = client.translate(azureTranslatorRequestDTO.getTargetLanguages(), azureTranslatorRequestDTO.getContent(), null, azureTranslatorRequestDTO.getFrom(), TextType.PLAIN, null, ProfanityAction.NO_ACTION, ProfanityMarker.ASTERISK, false, false, null, null, null, false);

        for (TranslatedTextItem translation : translations) {
            for (Translation textTranslation : translation.getTranslations()) {
                System.out.println("Text was translated to: '" + textTranslation.getTo() + "' and the result is: '" + textTranslation.getText() + "'.");
            }
        }
        return translations;
    }
}
