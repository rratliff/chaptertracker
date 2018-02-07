# Goal

The goal of this project is to learn about Maven and Spring Boot.

# Dependencies

* Dependencies are loaded using Maven. This is a very basic setup using the Spring Boot Starter maven parent.
* What is Maven? Maven is a dependency management tool and a build tool.

# Spring

* Spring encompasses several projects. We are using Spring Boot.
* Spring Boot is a sort of "quick start" set of Spring projects intended to minimize the amount of configuration to get a project running.
* Spring Boot includes Spring MVC
    * Spring MVC, a project that provides various types of support for server-side HTML generation.
    * In server-side HTML generation, the view would be, for example, a JSP page
    * Server-side HTML generation has fallen out of favor, to be replaced by REST resources and a Javascript front end. This shifts responsibility for rendering the page to the browser.
    * You use Spring MVC to create REST resources by creating one or more controllers with an `@RequestMapping` annotation.

# Entities

* There are two entities: Book and ReadingRecord. They integrate with the system using JPA, the Java persistence annotations.
* I'm attempting to follow a pattern where there is a repository tier.
* JPA allows you to create an entity relationship, such as a one to many.
