# JSP2Thmyeleaf Migration Sample Project - stage 0

## Stage 0 - Introduction

This guide shows the process for converting a JSP project to Thymeleaf using the JSP2Thymeleaf
tool set and libraries. JSP2Thymeleaf provides both tools to convert JSP templates into Thymeleaf ones,
and libraries to ease the mismatch in features between the two worlds.

## Preparation 

So, you've been using JSP's since, forever, and they've served you well (pun intended).

However, lately, you've been wondering if it might not be, er,  Thyme for a change?

Spring boot is fantastic at getting config under control and for getting projects up
and running, but it doesn't exactly welcome JSP views with open arms. Instead, you
end up feeling like the creepy older third cousin at the Christmas meal that everyone
tolerates, but no one would miss. Don't worry - there's a new kid on the block: Thymeleaf.

Why? Thymeleaf.

It's shiny. It's new(er). It doesn't involve compiling code at runtime in a production environment.

If done right, designers can view templates that will render without needing a server.
Plus, the folks at Spring have blessed it with simple setup and auto configuration in Spring applications.

So, how do we move our pages from JSP's to Thymeleaf? To answer this question, lets ask and answer some more: 

* Do we have to migrate all the pages at once in a big bang conversion?

  No. With the spring-thymeleaf-jsp project you can have both operating at the same time. 
  It allows you to convert a single page to Thymeleaf and the rest of the JSPs will work just fine. 
 
* Are JSP's and Thymeleaf functionally equivalent? 

  Not quite. They are very similar but slightly different. JSP's allows tags within tags and 
  injection of java directly into templates. Thymeleaf does not.

  Fortunately, they are both extensible, which allows us to make almost all 
  automated migrations possible and approach the changes in a staged manner. 

  These extensions for the JSP and Thymeleaf allow you to:

   # include Thymeleaf fragments like headers and footers into existing JSP pages, by using 
     a custom taglib. 

   # convert JSP cout tags inside other tags and choose tags into functionally equivalent
     Thymeleaf blocks using a new ThyeJSPCompatability dialect.

* I have java embedded in my JSP's. Can you help?
  
  It's ok. We're in a safe space. There's no need to feel bad.
  After all, I'm sure all of us have been tempted to insert java directly into
  a template because we could... but most of us didn't.
 
  WHAT WERE YOU THINKING?

  Sorry, but if you have java inserted into your templates it will be detected
  by the JSP2Thymeleaf converter and commented out for you to... attend to.
  
  Seriously, though - consider using Thymeleaf's custom processors and dialects 
  to help you out of your current pit, just as you should have used JSP taglibs 
  and custom tags in the JSP world. Also, consider using the Spring Expression
  Language (SPEL) to achieve your nefarious ends instead of resorting to naked 
  Java.

  I'll paraphrase Yoda, when I say that inserting java directly into JSPs is 
  "...faster, more seductive, but once you head down that pathway forever will 
  it dominate you" - ok he wasn't talking about inserting java into JSP's - he 
  was talking about a Dark Force ... actually maybe he was talking about 
  inserting java into JSPs.  

* How do we stage a migration?

  Great question! OK - here are the steps. (We'll go through them in detail in 
  each GIT stage.)
 
  # Add the spring-thymeleaf-jsp library to your pom. This sets up configuration
    which will allow thymeleaf to co-exist with existing JSP pages, and also allows
    JSP pages to include converted Thymeleaf fragments, so you only need one set of 
    things like headers and footers.

  # Start work on a fragment, like a header or footer. They're smaller and can 
    be shared in both JSPs and Thymeleaf.
 
    You can either do it manually or by running JSP2Thymeleaf on a file:

    java -jar [path...to]jsp2thymeleaf.jar -f [path...to]input.jsp -o [path for thymeleaf templates] -u

    The -u tells J2T to hunt for any includes of the file you changed and update them.

    Spring Boot expects to find templates in src/main/resources/templates.

    If you get error messages then you may need to add your own taglib converters
    or massage tricky bits which defeat the JSP2Thymeleaf parser.

    The reason I suggest starting with fragments, is that the compatability layer
    in spring-thymeleaf-jsp allows JSP's to include TL templates, but not the reverse.

  # If you use any custom taglibs, you can create custom converters (which might need
    custom dialects at runtime too). See the Custom Taglibs Howto.md in the Jsp2Thymeleaf docs folder.

  # Rinse and repeat. Keep converting fragments and then running your automated
    web tests (you do have automated web tests, right?) as you go to make sure nothing
    is broken.

  # Finally remove the JSP related dependencies from your POM, and make yourself
    a nice hot cup of tea. Welcome to the future.


