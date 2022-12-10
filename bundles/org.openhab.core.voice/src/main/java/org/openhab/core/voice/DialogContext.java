/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.core.voice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.core.audio.AudioSink;
import org.openhab.core.audio.AudioSource;
import org.openhab.core.voice.text.HumanLanguageInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describes dialog configured services and options.
 *
 * @author Miguel Álvarez - Initial contribution
 */
@NonNullByDefault
public class DialogContext {
    private final @Nullable KSService ks;
    private final @Nullable String keyword;
    private final STTService stt;
    private final TTSService tts;
    private final @Nullable Voice voice;
    private final List<HumanLanguageInterpreter> hlis;
    private final AudioSource source;
    private final AudioSink sink;
    private final Locale locale;
    private final @Nullable String listeningItem;

    public DialogContext(@Nullable KSService ks, @Nullable String keyword, STTService stt, TTSService tts,
            @Nullable Voice voice, List<HumanLanguageInterpreter> hlis, AudioSource source, AudioSink sink,
            Locale locale, @Nullable String listeningItem) {
        this.ks = ks;
        this.keyword = keyword;
        this.stt = stt;
        this.tts = tts;
        this.voice = voice;
        this.hlis = hlis;
        this.source = source;
        this.sink = sink;
        this.locale = locale;
        this.listeningItem = listeningItem;
    }

    public @Nullable KSService ks() {
        return ks;
    }

    public @Nullable String keyword() {
        return keyword;
    }

    public STTService stt() {
        return stt;
    }

    public TTSService tts() {
        return tts;
    }

    public @Nullable Voice voice() {
        return voice;
    }

    public List<HumanLanguageInterpreter> hlis() {
        return hlis;
    }

    public AudioSource source() {
        return source;
    }

    public AudioSink sink() {
        return sink;
    }

    public Locale locale() {
        return locale;
    }

    public @Nullable String listeningItem() {
        return listeningItem;
    }

    /**
     * Builder for {@link DialogContext}
     * Allows to describe a dialog context without requiring the involved services to be loaded
     */
    public static class Builder {
        private final Logger logger = LoggerFactory.getLogger(Builder.class);
        // services
        private @Nullable AudioSource source = null;
        private @Nullable AudioSink sink = null;
        private @Nullable KSService ks = null;
        private @Nullable STTService stt = null;
        private @Nullable TTSService tts = null;
        private @Nullable Voice voice = null;
        private List<HumanLanguageInterpreter> hlis = List.of();
        // options
        private @Nullable String listeningItem = null;
        private String keyword;
        private Locale locale;

        public Builder(String keyword, Locale locale) {
            this.keyword = keyword;
            this.locale = locale;
        }

        public Builder withSource(@Nullable AudioSource source) {
            this.source = source;
            return this;
        }

        public Builder withSink(@Nullable AudioSink sink) {
            this.sink = sink;
            return this;
        }

        public Builder withKS(@Nullable KSService service) {
            this.ks = service;
            return this;
        }

        public Builder withSTT(@Nullable STTService service) {
            this.stt = service;
            return this;
        }

        public Builder withTTS(@Nullable TTSService service) {
            this.tts = service;
            return this;
        }

        public Builder withHLIs(Collection<HumanLanguageInterpreter> services) {
            return withHLIs(new ArrayList<>(services));
        }

        public Builder withHLIs(List<HumanLanguageInterpreter> services) {
            this.hlis = services;
            return this;
        }

        public Builder withKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public Builder withVoice(@Nullable Voice voice) {
            this.voice = voice;
            return this;
        }

        public Builder withListeningItem(@Nullable String listeningItem) {
            this.listeningItem = listeningItem;
            return this;
        }

        public Builder withLocale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public DialogContext build() throws IllegalStateException {
            KSService ksService = ks;
            STTService sttService = stt;
            TTSService ttsService = tts;
            List<HumanLanguageInterpreter> hliServices = hlis;
            AudioSource audioSource = source;
            AudioSink audioSink = sink;
            List<String> errors = new ArrayList<>();
            if (sttService == null) {
                errors.add("Missing stt service");
            }
            if (ttsService == null) {
                errors.add("Missing tts service");
            }
            if (hliServices.isEmpty()) {
                errors.add("Missing interpreters");
            }
            if (audioSource == null) {
                errors.add("Missing audio source");
            }
            if (audioSink == null) {
                errors.add("Missing audio sink");
            }
            if (!errors.isEmpty()) {
                errors.forEach(logger::warn);
                throw new IllegalStateException("Cannot build dialog context, services are missing");
            }
            return new DialogContext(ksService, keyword, sttService, ttsService, voice, hliServices, audioSource,
                    audioSink, locale, listeningItem);
        }
    }
}
