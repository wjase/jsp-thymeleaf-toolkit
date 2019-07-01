<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
        <!--The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags--> 
        <meta name="description" content=""/>
        <meta name="author" content=""/>
        <link rel="icon" href="favicon.ico"/>

        <title>JSP2Thymeleaf Migration Sample Project</title>

        <!-- Bootstrap core CSS -->
        <link href="/webjars/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet"/>

        <!-- Custom styles for this template -->
        <link href="starter-template.css" rel="stylesheet"/>
    </head>

    <body>
        <%@ include file="includes/header.jsp" %>

        <div class="container">
            <h2>Common JSTL Tags</h2>
            <%@ include file="includes/fragments/cout.jsp" %>
            <%@ include file="includes/fragments/curl.jsp" %>
            <%@ include file="includes/fragments/cif.jsp" %>
            <%@ include file="includes/fragments/foreach_1.jsp" %>
            <%@ include file="includes/fragments/foreach_2.jsp" %>
            <%@ include file="includes/fragments/fortokens.jsp" %>
            <%@ include file="includes/fragments/choose.jsp" %>
            <%@ include file="includes/fragments/springform.jsp" %>
        </div>

        <hr/>

        <%@include file="includes/footer.jsp" %>

        <!-- /container -->

        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="/webjars/jquery/3.1.1/jquery.min.js" integrity="sha384-THPy051/pYDQGanwU6poAc/hOdQxjnOEXzbT+OuUAFqNqFjL+4IGLBgCJC3ZOShY" crossorigin="anonymous"></script>
        <script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>