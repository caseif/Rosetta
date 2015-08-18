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

import org.bukkit.plugin.Plugin;

/**
 * Provides localization support for a particular {@link Plugin}.
 *
 * <p>Locales are loaded as <code>.properties</code> files from the
 * <code>/locales</code> directory of the archive of the plugin owning this
 * {@link LocaleManager}.</p>
 *
 * @author Max Roncacé
 * @version 1.0.0
 * @since 1.0
 */
public interface LocaleManager {

    /**
     * Gets the plugin owning this {@link LocaleManager}.
     *
     * @return The plugin owning this {@link LocaleManager}
     * @since 1.0
     */
    Plugin getOwningPlugin();

    /**
     * Gets the default locale of this {@link LocaleManager}.
     *
     * @return A string representing the default locale. This should
     *     follow the {@code ISO 639-1} and {@code ISO 3166-1} standards,
     *     respectively (e.g. {@code en_US} or {@code enUS}) and defaults to
     *     {@code en_US}.
     * @since 1.0
     */
    String getDefaultLocale();

    /**
     * Sets the default locale of this {@link LocaleManager}.
     *
     * @param locale A string representing the default locale. This should
     *     follow the {@code ISO 639-1} and {@code ISO 3166-1} standards,
     *     respectively (e.g. {@code en_US} or {@code enUS}) and defaults to
     *     {@code en_US}.
     * @since 1.0
     */
    void setDefaultLocale(String locale);

    /**
     * Gets the {@link Localizable} associated with the given key, using the
     * given {@link CharSequence}s as replacements for placeholder sequences.
     *
     * @param key The key of the message to retrieve
     * @param replacements A var-args parameter representing replacements for
     *     placeholder sequences
     * @return The retrieved message as a {@link Localizable}
     * @since 1.0
     */
    Localizable getLocalizable(String key, String... replacements);

}
