package io.github.davidmc971.modularmsmf.api;

/**
 * An Interface to represent the key requirements for a language to be used in
 * ModularMSMF.
 * 
 * @author David Alexander Pfeiffer (davidmc971)
 * @since 0.3.0
 */
public interface ILanguage {
    /**
     * Returns the Language ID. For example "en_US".
     * 
     * @return Language ID
     */
    public String ID();

    /**
     * Returns the written name of the language. For example "English".
     * 
     * @return Language name
     */
    public String Name();

    /**
     * Used to loalize Strings throughout ModularMSMF. For Example:
     * "general.toomanyarguments" in language "en_US" returns "Too many arguments."
     * 
     * @param localizationKey The key for the language, usually the YAML path or
     *                        JSON path depending on the language implementation.
     * @return The localized String.
     */
    public String Localize(String localizationKey);
}
