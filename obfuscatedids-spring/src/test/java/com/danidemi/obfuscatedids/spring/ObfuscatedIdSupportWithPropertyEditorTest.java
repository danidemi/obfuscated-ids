package com.danidemi.obfuscatedids.spring;

import com.danidemi.obfuscatedids.core.UnobfuscatingObfuscator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.PropertyEditor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
@ContextConfiguration(classes = ObfuscatedIdSupportWithPropertyEditorTest.Config.class)
public class ObfuscatedIdSupportWithPropertyEditorTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void shouldWorkWithInitBinder() throws Exception {

        this.mvc.perform(get("/test/504938"))
                .andExpect(status().isOk())
                .andExpect(content().string("504938"));

    }

    @Configuration
    public static class Config {

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
                PropertyEditor pe = new ObfuscatedIdSupport();
                binder.registerCustomEditor(
                        ObfuscatedId.class,
                        pe);
            }

        }

    }

}