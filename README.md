
![Build status on https://travis-ci.com/](https://api.travis-ci.org/danidemi/obfuscated-ids.svg)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c7d97eb70d0e4a3eb9dad3c9b5ff67ec)](https://www.codacy.com/app/demichelis/obfuscated-ids?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=danidemi/obfuscated-ids&amp;utm_campaign=Badge_Grade)

Obfuscated-IDs
==============

<p align="center">
    <table>
        <tr>
            <td align="center">
                <img src="https://raw.githubusercontent.com/danidemi/obfuscated-ids/master/logo.png" />
            </td>
        </tr>
        <tr>
            <td align="center">
                Undisclose IDs with <i>Obfuscated-IDs</i>
            </td>
        </tr>    
    </table>
</p>

_Obfuscated-IDs_ is a java open-source library that allows you to easily
avoid to expose the internal IDs of your database at web level.

_Obfuscated-IDs_ is based on: 
* [hashids](http://hashids.org/)
* [hashids.java](https://github.com/jiecao-fm/hashids-java)



Overview
--------

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



Quick Start
-----------

Add the following Maven dependency

    <dependency>
        <groupId>com.danidemi.obfuscatedids</groupId>
        <artifactId>obfuscatedids-spring</artifactId>
        <version>0.0.1</version>
    </dependency>
    
> [!] Please, [check on Maven Central the latest version](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.danidemi.obfuscatedids%22).
    
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


Maven
-----

### maven-gpg-plugin

Check if GPG is correctly set up executing the following commnad.

    mvn gpg:sign
    
If that returns this error...

    gpg: no default secret key: unusable secret key
    gpg: signing failed: unusable secret key

...then A GPG key is needed. 
If one is available you can import it with...
    
    gpg --import ~/mygpgkey_pub.gpg
    gpg --allow-secret-key-import --import ~/mygpgkey_sec.gpg
    
Rerun again `mvn gpg:sign` to check all is in place.
    
    
    

    

References
----------

__Projects__
* [hashids](http://hashids.org/)
* [hashids.java](https://github.com/jiecao-fm/hashids-java)
* [Optimus](https://github.com/jenssegers/optimus)

__Manuals__
* [Maven GPG Plugin](https://maven.apache.org/plugins/maven-gpg-plugin/plugin-info.html)

__Posts__
* [Import GPG Key Pair](https://www.debuntu.org/how-to-importexport-gpg-key-pair/)