package com.bitbakery.plugin.translator;

import static com.google.api.translate.Language.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/**
 * Encapsulates details about what Google Translate supports, and what it doesn't
 */
public class Languages {
    private static HashMap<String, HashMap<String, String>> TRANSLATIONS;

    private static final HashMap<String, String> ARABIC_TARGETS = new HashMap<String, String>();
    private static final HashMap<String, String> CHINESE_TARGETS = new HashMap<String, String>();
    private static final HashMap<String, String> ENGLISH_TARGETS = new HashMap<String, String>();
    private static final HashMap<String, String> FRENCH_TARGETS = new HashMap<String, String>();
    private static final HashMap<String, String> GERMAN_TARGETS = new HashMap<String, String>();
    private static final HashMap<String, String> ITALIAN_TARGETS = new HashMap<String, String>();
    private static final HashMap<String, String> JAPANESE_TARGETS = new HashMap<String, String>();
    private static final HashMap<String, String> KOREAN_TARGETS = new HashMap<String, String>();
    private static final HashMap<String, String> PORTUGESE_TARGETS = new HashMap<String, String>();
    private static final HashMap<String, String> RUSSIAN_TARGETS = new HashMap<String, String>();
    private static final HashMap<String, String> SPANISH_TARGETS = new HashMap<String, String>();

    static {
        TRANSLATIONS = new HashMap<String, HashMap<String, String>>();

        TRANSLATIONS.put(display(ENGLISH), ENGLISH_TARGETS);
        ENGLISH_TARGETS.put(display(ARABIC), ARABIC);
        ENGLISH_TARGETS.put(displayDialect("zh", "CN"), "zh-CN"); // TODO - Gracefully handle dialects...
        ENGLISH_TARGETS.put(display(FRENCH), FRENCH);
        ENGLISH_TARGETS.put(display(GERMAN), GERMAN);
        ENGLISH_TARGETS.put(display(ITALIAN), ITALIAN);
        ENGLISH_TARGETS.put(display(JAPANESE), JAPANESE);
        ENGLISH_TARGETS.put(display(KOREAN), KOREAN);
        ENGLISH_TARGETS.put(display(PORTUGESE), PORTUGESE);
        ENGLISH_TARGETS.put(display(RUSSIAN), RUSSIAN);
        ENGLISH_TARGETS.put(display(SPANISH), SPANISH);

        TRANSLATIONS.put(display(ARABIC), ARABIC_TARGETS);
        ARABIC_TARGETS.put(display(ENGLISH), ENGLISH);

        TRANSLATIONS.put(display(CHINESE), CHINESE_TARGETS);
        CHINESE_TARGETS.put(display(ENGLISH), ENGLISH);

        TRANSLATIONS.put(display(FRENCH), FRENCH_TARGETS);
        FRENCH_TARGETS.put(display(ENGLISH), ENGLISH);
        FRENCH_TARGETS.put(display(GERMAN), GERMAN);

        TRANSLATIONS.put(display(GERMAN), GERMAN_TARGETS);
        GERMAN_TARGETS.put(display(ENGLISH), ENGLISH);
        GERMAN_TARGETS.put(display(FRENCH), FRENCH);

        TRANSLATIONS.put(display(ITALIAN), ITALIAN_TARGETS);
        ITALIAN_TARGETS.put(display(ENGLISH), ENGLISH);

        TRANSLATIONS.put(display(JAPANESE), JAPANESE_TARGETS);
        JAPANESE_TARGETS.put(display(ENGLISH), ENGLISH);

        TRANSLATIONS.put(display(KOREAN), KOREAN_TARGETS);
        KOREAN_TARGETS.put(display(ENGLISH), ENGLISH);

        TRANSLATIONS.put(display(PORTUGESE), PORTUGESE_TARGETS);
        PORTUGESE_TARGETS.put(display(ENGLISH), ENGLISH);

        TRANSLATIONS.put(display(RUSSIAN), RUSSIAN_TARGETS);
        RUSSIAN_TARGETS.put(display(ENGLISH), ENGLISH);

        TRANSLATIONS.put(display(SPANISH), SPANISH_TARGETS);
        SPANISH_TARGETS.put(display(ENGLISH), ENGLISH);
    }


    public static String display(final String code) {
        return new Locale(code).getDisplayLanguage();
    }

    public static String displayDialect(final String lang, final String dialect) {
        return new Locale(lang, dialect).getDisplayLanguage();
    }

    public static String[] getLanguageNames(String source) {
        String[] languages = TRANSLATIONS.get(source).keySet().toArray(new String[]{});
        Arrays.sort(languages);
        return languages;
    }

    public static String getLanguageCode(String source, String name) {
        return TRANSLATIONS.get(source).get(name);
    }
}
