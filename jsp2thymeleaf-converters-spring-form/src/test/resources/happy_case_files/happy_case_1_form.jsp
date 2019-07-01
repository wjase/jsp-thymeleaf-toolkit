<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %> 
<html><%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %> 
    <head>
        <title>Registration Page</title>
    </head>
    <body>
        <c:url var="post_url" value='/somevalue'/>
        <form:form method="POST" commandName="user" action="<c:url value='${post_url}'/>">
            <form:errors/>
            <table>
                <tr>
                    <td>User Name :</td>
                    <td><form:input path="name" cssStyle="display:inherited;"/></td>
                </tr>
                <tr>
                    <td>Password :</td>
                    <td><form:input type="password" path="password" /></td>
                </tr>
                <tr>
                    <td>Gender :</td>
                    <td>
                        <form:input type="radio" path="gender" value="M" /> 
                        <form:label for="gender" >M</form:label>
                        <form:input type="radio" path="gender" value="F" />
                        <form:label for="gender" >F</form:label>
                        </td>
                    </tr>
                    <tr>
                        <td>Country :</td>
                        <td>
                        <form:select path="country">
                            <form:option value="0" label="Select" />
                            <c:forEach items="countryList" var="item">
                                <form:option value="${item.countryId}" label="${item.countryName}" />
                            </c:forEach>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td>About you :</td>
                    <td><form:textarea path="aboutYou" /></td>
                </tr>
                <tr>
                    <td>Community :</td>
                    <td>
                        <form:checkboxes type="checkbox" items="communityList" itemValue="${item}" />
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <form:input path="mailingList"  />
                        <form:label for="mailingList">Would you like to join our mailing list?</form:label>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"><form:input type="submit" value="Register"></td>
                    </tr>
                </table>
        </form:form>
    </body>
</html>
