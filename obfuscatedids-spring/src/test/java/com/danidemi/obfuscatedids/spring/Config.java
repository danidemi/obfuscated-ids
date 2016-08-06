package com.danidemi.obfuscatedids.spring;

import com.danidemi.obfuscatedids.spring.core.UnobfuscatingObfuscator;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.PropertyEditor;

@Configuration
public class Config {

/*    @Bean public Controller testController() {

        return new Controller();

    }*/

    @RestController
    public static class Controller {

        // no matching editors or conversion strategy found
        // http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-ann-typeconversion

        @RequestMapping("/test/{obfuscatedid}")
        public String getCode(@PathVariable("obfuscatedid") ObfuscatedId obfuscatedId) {
            String obfuscatedId1 = obfuscatedId.toString();
            long decode = new UnobfuscatingObfuscator().decode(obfuscatedId1);
            return String.valueOf(decode);
        }

        @InitBinder
        protected void initBinder(WebDataBinder binder) {

            PropertyEditor pe = new ObfuscatedIdPropertyEditor();

            binder.registerCustomEditor(
                    ObfuscatedId.class,
                    pe);
        }

    }

}
