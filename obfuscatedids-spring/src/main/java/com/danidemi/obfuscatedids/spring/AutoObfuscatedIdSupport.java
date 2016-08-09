/*******************************************************************************
 * MIT License
 *
 * Copyright (c) 2016-2016  Daniele Demichelis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

package com.danidemi.obfuscatedids.spring;

import com.danidemi.obfuscatedids.core.IdObfuscator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;
import org.springframework.web.bind.WebDataBinder;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Locale;

public class AutoObfuscatedIdSupport extends PropertyEditorSupport implements Formatter<AutoObfuscatedId> {

    private final IdObfuscator obfuscator;
    private static final Logger log = LoggerFactory.getLogger(AutoObfuscatedIdSupport.class);

    public AutoObfuscatedIdSupport(final IdObfuscator obfuscator) {
        if(obfuscator == null) throw new IllegalArgumentException();
        this.obfuscator = obfuscator;
    }

    // PropertyEditorSupport =============================
    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(
                new AutoObfuscatedId(text, obfuscator.decode( text ))
        );
    }

    public String getAsText() {
        AutoObfuscatedId value = (AutoObfuscatedId) this.getValue();
        return value != null ? value.toString() : "";
    }

    // Formatter =============================
    @Override
    public AutoObfuscatedId parse(String s, Locale locale) throws ParseException {
        return new AutoObfuscatedId(s, obfuscator.decode(s));
    }

    @Override
    public String print(AutoObfuscatedId obfuscatedId, Locale locale) {
        return obfuscatedId.toString();
    }

    public static final void registerCustomEditor(final WebDataBinder binder, final IdObfuscator obfuscator) {
        if(binder == null) throw new IllegalArgumentException();
        if(obfuscator == null) throw new IllegalArgumentException();

        Class<AutoObfuscatedId> theClazz = AutoObfuscatedId.class;
        AutoObfuscatedIdSupport propertyEditor = new AutoObfuscatedIdSupport(obfuscator);

        binder.registerCustomEditor(theClazz, propertyEditor);
        log.info("Registered {} to support parameters of type {}.", propertyEditor, theClazz);
    }
}
