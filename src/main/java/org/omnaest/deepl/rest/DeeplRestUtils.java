package org.omnaest.deepl.rest;

import java.util.List;

import org.omnaest.utils.rest.client.RestClient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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

    public static Usage getUsage(License license, String authorizationKey)
    {
        return RestClient.newJSONRestClient()
                         .request()
                         .toUrl(RestClient.urlBuilder()
                                          .setBaseUrl("https://" + license.getBaseUrl() + "/v2/usage")
                                          .addQueryParameter("auth_key", authorizationKey)
                                          .build())
                         .get(Usage.class);
    }

    public static Languages getAvailableLanguages(License license, String authorizationKey)
    {
        return RestClient.newJSONRestClient()
                         .request()
                         .toUrl(RestClient.urlBuilder()
                                          .setBaseUrl("https://" + license.getBaseUrl() + "/v2/languages")
                                          .addQueryParameter("auth_key", authorizationKey)

                                          .build())
                         .get(Languages.class);
    }

    public static class Usage
    {
        @JsonProperty("character_count")
        private long characterCount;

        @JsonProperty("character_limit")
        private long characterLimit;

        public long getCharacterCount()
        {
            return this.characterCount;
        }

        public long getCharacterLimit()
        {
            return this.characterLimit;
        }

        @Override
        public String toString()
        {
            return "Usage [characterCount=" + this.characterCount + ", characterLimit=" + this.characterLimit + "]";
        }

    }

    public static class Languages
    {
        private List<Language> languages;

        @JsonCreator
        public Languages(List<Language> languages)
        {
            super();
            this.languages = languages;
        }

        @JsonValue
        public List<Language> getLanguages()
        {
            return this.languages;
        }

    }

    public static class Language
    {
        @JsonProperty
        private String language;

        @JsonProperty
        private String name;

        public String getLanguage()
        {
            return this.language;
        }

        public String getName()
        {
            return this.name;
        }

        @Override
        public String toString()
        {
            return "Language [language=" + this.language + ", name=" + this.name + "]";
        }

    }
}
