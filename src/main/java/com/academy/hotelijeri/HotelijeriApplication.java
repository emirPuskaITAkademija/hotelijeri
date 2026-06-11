package com.academy.hotelijeri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ova anotacija je najvažnija anotacija u našoj klasi koja starta aplikaciju i kombinacija je tri različite druge anotacije:
 * <p>
 * 1. @Configuration - kaže Springu da ova klasa sadrži konfiguraciju aplikacije
 * </p>
 * <p>
 * 2. @EnableAutoConfiguration - ova anotacije je "magijaW" Spring Boot-a..pogleda depdendenvy i ako vidi
 * <li>spring-boot-starter-web - ona automatski diže Tomcat, DispatcherServlet, Jackson, Spring MVC
 * <li>spring-boot-starter-data-jpa - EntityManager, Hibernate, DataSource, TransactionManager
 * </p>
 * <p>
 * 3. @ComponentScan - kaže springu da skenira sve podpakete od npr. com.academy.hotelijeri i pronađe
 * sve Spring Bean (controller, service, repository, config...) i kreira objekte po šablonu klasa koje su
 * anotirane specifičnim anotacijama svojstvenim Spring Bean..@Controller, @Service, @Repository, @Component..
 *
 *
 * Dva načina pakovanja:
 * <li>1. hotelijeri.war</li>
 * <li>2. hotelijeri.jar
 *      embeda u našu aplikaciju Tomcat i automatski instalira našu aplikaciju na tomcat
 *      i starta tomcat sa našom aplikacijom na njemu
 * </li>
 */
@SpringBootApplication
public class HotelijeriApplication {

    public static void main(String[] args) {
        /**
         * Šta radi linija koda ispod:
         * 1. Kreira Spring Contejner
         * 2. Pronalazi sve ne EJB nego Spring Beanobe
         * 3. Učitava sve konfiguracije
         * 4. Pokreće ugrađeni Tomcat
         * 5. Registracije kontrolera
         * 6. App je up and running i spremna za test
         */
        SpringApplication.run(HotelijeriApplication.class, args);
    }
}
