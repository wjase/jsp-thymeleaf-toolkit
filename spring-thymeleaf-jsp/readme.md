
spring-thymeleaf-jsp
====================

Mixing JSP and Thymeleaf content in your spring MVC project.

This project allows you to:
  1. Have views rendered with either Thymeleaf templates and JSP pages
  2. Have JSP pages insert Thymeleaf fragments
  3. Include Thymeleaf fragments like headers footers in a JSP page

The intention is to make it possible to migrate a project from JSP to Thymeleaf
by first converting common page elements and then page by page. 

Usage
=====

For spring MVC projects, import the JspThymeleafInteropAutoConfiguration class.
