package org.omnaest.deepl.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.omnaest.deepl.rest.DeeplRestUtils.License;
import org.omnaest.deepl.rest.DeeplRestUtils.TranslationResponse;

/**
 * @see DeeplRestUtils
 * @author omnaest
 */
public class DeeplRestUtilsTest
{
    @Test
    @Ignore
    public void testTranslate() throws Exception
    {
        License license = License.FREE;
        String authorizationKey = "";
        String text = "I love you!";
        String sourceLanguage = "EN";
        String targetLanguage = "DE";
        TranslationResponse response = DeeplRestUtils.translate(license, authorizationKey, text, sourceLanguage, targetLanguage);
        assertNotNull(response);
        assertNotNull(response.getTranslations());
        assertEquals(1, response.getTranslations()
                                .size());
        assertEquals("Ich liebe dich!", response.getTranslations()
                                                .get(0)
                                                .getText());
    }

}
