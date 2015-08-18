/*
 * New BSD License (BSD-new)
 *
 * Copyright (c) 2015 Maxim Roncacé
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     - Neither the name of the copyright holder nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.caseif.rosetta;

import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Represents an object which has the potential to be localized in one of
 * multiple languages and returned as a string.
 *
 * <p>In the event that a {@link Localizable} cannot be localized with the given
 * parameters or in its parent {@link LocaleManager}'s default locale, it will
 * output its internal key instead.</p>
 *
 * @author Max Roncacé
 * @version 1.0.0
 * @since 1.0
 */
public class Localizable {

    private final LocaleManager parent;
    private final String key;

    private String[] replacements;

    Localizable(LocaleManager parent, String key, String... replacements) {
        this.parent = parent;
        this.key = key;
        this.replacements = replacements;
    }

    /**
     * Gets the parent {@link LocaleManager} for this {@link Localizable}.
     *
     * @return The parent {@link LocaleManager} for this {@link Localizable}.
     * @since 1.0
     */
    public LocaleManager getParent() {
        return parent;
    }

    /**
     * Gets the key associated with this {@link Localizable}'s message.
     *
     * @return The key associated with this {@link Localizable}'s message
     * @since 1.0
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the replacements for placeholder sequences defined for this
     * {@link Localizable}.
     *
     * <p>Placeholder sequences are defined as a percent symbol (%) followed by
     * a number greater than or equal to 1. The first element of the replacement
     * string array will replace any placeholder sequences matching {@code %1},
     * the second, sequences matching {@code %2}, and so on.</p>
     *
     * <p><strong>Note:</strong> Mutating the array returned from this method
     * will not impact this {@link Localizable}.</p>
     *
     * @return The replacements for placeholder sequences defined for this
     *     {@link Localizable}
     * @since 1.0
     */
    public String[] getReplacements() {
        return Arrays.copyOf(replacements, replacements.length);
    }

    /**
     * Sets the replacements for placeholder sequences in this
     * {@link Localizable}.
     *
     * <p>Placeholder sequences are defined as a percent symbol (%) followed by
     * a number greater than or equal to 1. The first element of the replacement
     * string array will replace any placeholder sequences matching {@code %1},
     * the second, sequences matching {@code %2}, and so on.</p>
     *
     * <p><strong>Note:</strong> Mutating the values of the array passed as a
     * parameter after calling this method will not impact this
     * {@link Localizable}.</p>
     *
     * @param replacements The replacement strings to set for this
     *     {@link Localizable}
     * @since 1.0
     */
    public void setReplacements(String[] replacements) {
        this.replacements = Arrays.copyOf(replacements, replacements.length);
    }

    /**
     * Localizes this {@link Localizable} in the given locale.
     *
     * <p>It is unnecessary to include alternate dialects of a locale as
     * fallbacks (e.g. {@code en_GB} as a fallback for {@code en_US}), as they
     * are included by default by the library.</p>
     *
     * @param locale The locale to localize this {@link Localizable} in
     * @param fallbacks Locales to fall back upon if this {@link Localizable}
     *     is not available in the player's locale (the parent
     *     {@link LocaleManager}'s default locale will be used if all fallbacks
     *     are exhausted, and if this is unavailable, the value of
     *     {@link Localizable#getKey()} will be used instead)
     * @return A string representing the localized message, or this
     *     {@link Localizable}'s internal key if no localizations are available
     * @since 1.0
     */
    public String localizeIn(String locale, String... fallbacks) {
        return localizeIn(locale, false, fallbacks);
    }

    private String localizeIn(String locale, boolean recursive, String... fallbacks) {
        if (getParent().configs.containsKey(locale)) { // check if the locale is defined
            Properties props = getParent().configs.get(locale);
            if (props.containsKey(getKey())) { // check if the message is defined in the locale
                String message = (String) props.get(getKey()); // yay, it worked
                for (int i = 0; i < fallbacks.length; i++) { // replace placeholder sequences
                    message = message.replaceAll("%" + (i + 1), replacements[i]);
                }
                return message;
            }
        }
        if (!recursive) { // only inject alternatives the method is not called recursively and the first choice fails
            List<String> fbList = Arrays.asList(fallbacks);
            for (int i = 0; i < fbList.size(); i++) {
                String fb = fbList.get(i);
                if (LocaleManager.ALTERNATIVES.containsKey(fb)) {
                    for (String alt : LocaleManager.ALTERNATIVES.get(fb)) {
                        if (!fbList.contains(alt)) { // check if the alternate dialect is already in the list
                            fbList.add(i + 1, alt); // inject alternate dialects after the current fallback entry
                            ++i; // increment the counter past the new entry
                        }
                    }
                }
            }
            if (LocaleManager.ALTERNATIVES.containsKey(locale)) {
                for (String alt : LocaleManager.ALTERNATIVES.get(locale)) {
                    if (!fbList.contains(alt)) { // check if the alternate dialect is already in the list
                        fbList.add(0, alt); // inject alternate dialects at the start of the list
                    }
                }
            }
        }
        if (fallbacks.length > 0) { // still some fallbacks to use
            String[] newFallbacks = new String[fallbacks.length - 1]; // reconstruct the fallback array
            System.arraycopy(fallbacks, 1, newFallbacks, 0, newFallbacks.length); // drop the first element
            return localizeIn(fallbacks[0], true, newFallbacks); // try the next fallback
        } else if (!locale.equals(getParent().getDefaultLocale())) {
            return localizeIn(getParent().getDefaultLocale(), true); // try the default locale
        } else {
            return getKey(); // last resort if no locale is available
        }
    }

    /**
     * Localizes this {@link Localizable} in the owning {@link LocaleManager}'s
     * default locale.
     *
     * @return The appropriate localization for this {@link Localizable}.
     * @since 1.0
     */
    public String localize() {
        return localizeIn(getParent().getDefaultLocale());
    }

    /**
     * Localizes this {@link Localizable} in the given {@link Player}'s locale.
     *
     * <p>It is unnecessary to include alternate dialects of a locale as
     * fallbacks (e.g. {@code en_GB} as a fallback for {@code en_US}), as they
     * are included by default by the library.</p>
     *
     * @param player The {@link Player} to localize this {@link Localizable} for
     * @param fallbacks Locales to fall back upon if this {@link Localizable}
     *     is not available in the player's locale (the parent
     *     {@link LocaleManager}'s default locale will be used if all fallbacks
     *     are exhausted, and if this is unavailable, the value of
     *     {@link Localizable#getKey()} will be used instead)
     * @return A string representing the localized message, or this
     *     {@link Localizable}'s internal key if no localizations are available
     * @since 1.0
     */
    public String localizeFor(Player player, String... fallbacks) {
        return localizeIn(getParent().getLocale(player), fallbacks);
    }

    /**
     * Sends this {@link Localizable} to the given {@link Player} in their
     * respective locale.
     *
     * <p>It is unnecessary to include alternate dialects of a locale as
     * fallbacks (e.g. {@code en_GB} as a fallback for {@code en_US}), as they
     * are included by default by the library.</p>
     *
     * @param player The {@link Player} to send this {@link Localizable}
     *     to
     * @param fallbacks Locales to fall back upon if this {@link Localizable}
     *     is not available in the player's locale (the parent
     *     {@link LocaleManager}'s default locale will be used if all fallbacks
     *     are exhausted, and if this is unavailable, the value of
     *     {@link Localizable#getKey()} will be used instead)
     * @since 1.0
     */
    public void sendTo(Player player, String... fallbacks) {
        player.sendMessage(localizeFor(player, fallbacks));
    }

}