

#jsp2thymeleaf-tldgen - creates a skeleton converter for a TLD
====================================================================

The jsp2thymeleaf-tldgen creates a new project in the current directory which
contains a skeleton converter for a JSP taglib.

If you have a custom TLD you want to convert then use this utility to get started.

It will create an element converter for each type of tag in your taglib.

There are two kinds: function tags and element tags which have different kinds of converters.

## Function Tags:
The function converters allow you to change the method name and reorder or combine arguments.

eg:

 mytaglib:someFunctionName(arg1,arg2,arg3)  ---> th:newFunctionName(arg3,arg1,arg2)

## Element Tags:

The element converters map the name and attributes of your jsp tag element to  
a new thymeleaf equivalent.

eg
<c:out value="Hello there"/>   ------->   <span th:text="Hello there"/>

Once generated you will need to edit your converter to tell it how to transform each
element or function.

Then you build it using maven  (eg mvn clean install)

Finally, tell the jsp2tl-maven plugin ts available

#SYNTAX:
==========

    java -jar jsp2thymeleaf-tldgen [options] [tld path]

#OPTIONS:
==========
