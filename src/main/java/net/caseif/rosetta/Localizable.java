/*
 * New BSD License (BSD-new)
 *
 * Copyright (c) 2015 Maxim Roncac�
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

//TODO: class documentation probably needs some work
/**
 * Represents an object which has the potential to be localized in one of
 * multiple languages and returned as a string.
 *
 * <p>This may represent an ordered collection of strings and/or other
 * {@link Localizable}s.</p>
 *
 * <p>In the event that a {@link Localizable} cannot be localized in the given
 * locale, it will output its internal key instead.</p>
 *
 * @author Max Roncac�
 * @version 1.0.0
 * @since 1.0
 */
public interface Localizable {

    /**
     * Gets the parent {@link LocaleManager} for this {@link Localizable}.
     *
     * @return The parent {@link LocaleManager} for this {@link Localizable}.
     * @since 1.0
     */
    LocaleManager getParent();

    /**
     * Gets the key associated with this {@link Localizable}'s message.
     *
     * @return The key associated with this {@link Localizable}'s message
     * @since 1.0
     */
    String getKey();

    /**
     * Localizes this {@link Localizable} in the server's default locale.
     *
     * @return The appropriate localization for this {@link Localizable}.
     * @since 1.0
     */
    String localize();

    /**
     * Localizes this {@link Localizable} in the given locale.
     *
     * @param locale The locale to localize this {@link Localizable} in
     * @return A string representing the localized message. This should follow
     *     the ISO 639-1 and ISO 3166-1 standards, respectively (e.g. en_US
     *     or enUS).
     * @since 1.0
     */
    String localizeIn(String locale);

    /**
     * Localizes this {@link Localizable} in the given locale.
     *
     * @param locale The locale to localize this {@link Localizable} in
     * @param fallbacks A varargs parameter signifying in order locales to fall
     *     back to if the given locale is not available for this
     *     {@link Localizable}
     * @return A string representing the localized message. This should follow
     *     the ISO 639-1 and ISO 3166-1 standards, respectively (e.g. en_US
     *     or enUS).
     * @since 1.0
     */
    String localizeIn(String locale, String... fallbacks);

    /**
     * Localizes this {@link Localizable} in the given {@link Player}'s locale.
     *
     * @param player The {@link Player} to localize this {@link Localizable} for
     * @return A string representing the localized message
     * @since 1.0
     */
    String localizeFor(Player player);

    /**
     * Localizes this {@link Localizable} in the given {@link Player}'s locale.
     *
     * @param player The {@link Player} to localize this {@link Localizable} for
     * @param fallbacks A varargs parameter signifying in order locales to fall
     *     back to if the {@link Player}'s locale is not available for this
     *     {@link Localizable}
     * @return A string representing the localized message
     * @since 1.0
     */
    String localizeFor(Player player, String... fallbacks);

    /**
     * Sends this {@link Localizable} to the given {@link Player} in their
     * respective locale.
     *
     * @param player The {@link Player} to send this {@link Localizable}
     *     to
     * @since 1.0
     */
    void sendTo(Player player);

    /**
     * Sends this {@link Localizable} to the given {@link Player} in their
     * respective locale.
     *
     * @param player The {@link Player} to send this {@link Localizable} to
     * @param fallbacks A varargs parameter signifying in order locales to fall
     *     back to if the {@link Player}'s locale is not available for this
     *     {@link Localizable}
     * @since 1.0
     */
    void sendTo(Player player, String... fallbacks);

}