package com.bitbakery.plugin.translator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/**
 * Encapsulates details about what Google Translate supports, and what it doesn't
 */
public class Language implements Comparable<Language> {

    public static final Language ARABIC = new Language("ar");
    public static final Language CHINESE = new Language("zh", "CN");
    public static final Language DUTCH = new Language("nl");
    public static final Language ENGLISH = new Language("en");
    public static final Language FRENCH = new Language("fr");
    public static final Language GERMAN = new Language("de");
    public static final Language GREEK = new Language("el");
    public static final Language ITALIAN = new Language("it");
    public static final Language JAPANESE = new Language("ja");
    public static final Language KOREAN = new Language("ko");
    public static final Language PORTUGESE = new Language("pt");
    public static final Language RUSSIAN = new Language("ru");
    public static final Language SPANISH = new Language("es");


    private static HashMap<Language, Language[]> TRANSLATIONS;
    private static HashMap<String, Language> CODE_MAPPING;

    public static Language[] SOURCE_LANGUAGES;


    static {
        TRANSLATIONS = new HashMap<Language, Language[]>();
        TRANSLATIONS.put(ARABIC, sort(ENGLISH));
        TRANSLATIONS.put(CHINESE, sort(ENGLISH));
        TRANSLATIONS.put(ENGLISH, sort(ARABIC, CHINESE, FRENCH, GERMAN, ITALIAN, JAPANESE, KOREAN, PORTUGESE, RUSSIAN, SPANISH));
        TRANSLATIONS.put(FRENCH, sort(ENGLISH, GERMAN));
        TRANSLATIONS.put(GERMAN, sort(ENGLISH, FRENCH));
        TRANSLATIONS.put(ITALIAN, sort(ENGLISH));
        TRANSLATIONS.put(JAPANESE, sort(ENGLISH));
        TRANSLATIONS.put(KOREAN, sort(ENGLISH));
        TRANSLATIONS.put(PORTUGESE, sort(ENGLISH));
        TRANSLATIONS.put(RUSSIAN, sort(ENGLISH));
        TRANSLATIONS.put(SPANISH, sort(ENGLISH));

        SOURCE_LANGUAGES = sort(TRANSLATIONS.keySet().toArray(new Language[TRANSLATIONS.keySet().size()]));

        CODE_MAPPING = new HashMap<String, Language>();
        CODE_MAPPING.put(ARABIC.code, ARABIC);
        CODE_MAPPING.put(CHINESE.code, CHINESE);
        CODE_MAPPING.put(ENGLISH.code, ENGLISH);
        CODE_MAPPING.put(FRENCH.code, FRENCH);
        CODE_MAPPING.put(GERMAN.code, GERMAN);
        CODE_MAPPING.put(ITALIAN.code, ITALIAN);
        CODE_MAPPING.put(JAPANESE.code, JAPANESE);
        CODE_MAPPING.put(KOREAN.code, KOREAN);
        CODE_MAPPING.put(PORTUGESE.code, PORTUGESE);
        CODE_MAPPING.put(RUSSIAN.code, RUSSIAN);
        CODE_MAPPING.put(SPANISH.code, SPANISH);
    }

    private static Language[] sort(Language... l) {
        Arrays.sort(l);
        return l;
    }


    public static String getDisplayName(final String code) {
        return get(code).display;
    }

    public static Language[] getTargetLanguages(Language source) {
        return TRANSLATIONS.get(source);
    }

    public static Language getDefaultLanguage() {
        // TODO - Note that this does NOT work for Chinese - we need the code + dialect!
        return CODE_MAPPING.get(Locale.getDefault().getLanguage());
    }

    public static Language get(String code) {
        return CODE_MAPPING.get(code);
    }

    public static boolean isSupported(String code) {
        return CODE_MAPPING.containsKey(code);
    }


    public String code;
    public String display;

    private Language(String code) {
        this.code = code;
        display = new Locale(code).getDisplayLanguage();
    }

    private Language(String code, String dialect) {
        this.code = code + "-" + dialect;
        display = new Locale(code, dialect).getDisplayLanguage();
    }

    public String toString() {
        return display;
    }

    public int compareTo(Language that) {
        return display.compareTo(that.display);
    }
}
