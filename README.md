
![Build status on https://travis-ci.com/](https://api.travis-ci.org/danidemi/obfuscated-ids.svg)

Obfuscated-IDs
==============
Never expose your database IDs again.

_Obfuscated-IDs_ is a java open-source library that allows you to easily
avoid to expose the internal IDs of your database.

_Obfuscated-IDs_ is based on: 
* [hashids](http://hashids.org/)
* [hashids.java](https://github.com/jiecao-fm/hashids-java)

## Overview

With a _Spring Framework_ and _Obfuscated-IDs_ i's possible to write a `@Controller`
that automatically allows the code to use the real numeric `ID` while it is always 
shown in its disguised form at the outside.
For instance, look at this `@RestController`.
 
        @RestController
        public class EchoController {

            @RequestMapping("/user/{obfuscatedUserId}/{message}")
            public String getCode(@PathVariable AutoObfuscatedId obfuscatedUserId, @PathVariable String message) {
                
                long id = obfuscatedId.id();
                
                // ...access the database with the numeric id
                User user = userRepo.findOne(id);
                
                return "User " + obfuscatedId + " says '" + message + "'";
            }

        }

It exposes a service that can be invoked like that...

        /user/2BHah7n8ziI/HelloWorld
        
But `obfuscatedId.id()` will return the corresponding numeric `ID`.

## Installation

Add the following Maven dependency

    <dependency>
        <groupId>com.danidemi.obfuscatedids</groupId>
        <artifactId>obfuscatedids-spring</artifactId>
        <version>0.0.1</version>
    </dependency>
    
Configure an `IdObfuscator`. 

    @Bean
    public static IdObfuscator idObfuscator() {
        return new HashIdObfuscator();
    }
    
Add a `@RestControllerAdvice` that enables the conversion of parameters
 of type `AutoObfuscatedId`.

    @RestControllerAdvice
    public static class Advice {

        @Autowired IdObfuscator obfuscator;

        @InitBinder
        public void addSupportForObfuscatedId(WebDataBinder binder) {
            binder.registerCustomEditor(AutoObfuscatedId.class, new AutoObfuscatedIdSupport(obfuscator));
        }

    }
    
Write your controller.

    @Autowired @Autowired IdObfuscator obfuscator;

    @RequestMapping("/users")
    public List<String> getUsers() {
        return asList(
            obfuscator.disguise( user1.getId() ),
            obfuscator.disguise( user2.getId() )
        );
    }

    @RequestMapping("/user/{obfuscatedDbId}")
    public String getUser(@PathVariable AutoObfuscatedId obfuscatedDbId) {
        ...
    }
