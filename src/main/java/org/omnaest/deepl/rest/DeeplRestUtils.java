package org.omnaest.deepl.rest;

import java.util.List;

import org.omnaest.utils.rest.client.RestClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeeplRestUtils
{
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TranslationResponse
    {
        @JsonProperty
        private List<Translation> translations;

        public List<Translation> getTranslations()
        {
            return this.translations;
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Translation
    {
        @JsonProperty("detected_source_language")
        private String detectedSourceLanguage;

        @JsonProperty
        private String text;

        public String getDetectedSourceLanguage()
        {
            return this.detectedSourceLanguage;
        }

        public String getText()
        {
            return this.text;
        }

    }

    public static enum License
    {
        FREE("api-free.deepl.com"), PROFESSIONAL("api.deepl.com");
        private String baseUrl;

        private License(String baseUrl)
        {
            this.baseUrl = baseUrl;
        }

        public String getBaseUrl()
        {
            return this.baseUrl;
        }

    }

    public static TranslationResponse translate(License license, String authorizationKey, String text, String sourceLanguage, String targetLanguage)
    {
        return RestClient.newJSONRestClient()
                         .request()
                         .toUrl(RestClient.urlBuilder()
                                          .setBaseUrl("https://" + license.getBaseUrl() + "/v2/translate")
                                          .addQueryParameter("auth_key", authorizationKey)
                                          .addQueryParameter("source_lang", sourceLanguage)
                                          .addQueryParameter("target_lang", targetLanguage)
                                          .addQueryParameter("text", text)
                                          .build())
                         .get(TranslationResponse.class);
    }
}
