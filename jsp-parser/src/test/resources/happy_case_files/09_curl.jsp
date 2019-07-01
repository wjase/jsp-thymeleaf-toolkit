<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="someVar" value="/root/path/here"/>

<a href="<c:url value="/embedded/path"><c:param value="george"/></c:url>">Some Link</a>