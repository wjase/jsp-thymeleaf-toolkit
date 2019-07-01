<!DOCTYPE html SYSTEM "http://thymeleaf.org/dtd/xhtml-strict-thymeleaf.dtd">
<html>
    <head>
        <title>Fragment ${current.title}</title>
        <style>
            .text{
                display:<c:out value="normal"/>;
                color: ${current.color}    
            }
        </style>
    </head>
    <body id="somevalue" class="avalue" data-test="${attExpr}" >
        <input disabled value="" type="text"/>
        This is the body
        ${some.value}
        More text
        <c:out value="boo with spaces"/>
        Yet still more text
    </body>
</html>
