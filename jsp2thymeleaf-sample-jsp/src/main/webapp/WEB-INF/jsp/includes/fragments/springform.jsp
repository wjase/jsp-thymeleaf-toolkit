<html>
    <head>
        <title>Registration Page (Spring Form)</title>
    </head>
    <body>
        <h3>Spring Form</h3>
        <c:url var="post_url" value='/somevalue'/>
        <form:form class="form" method="POST" commandName="user" action="<c:url value='${post_url}'/>">
            <table class="form-group">
                <tr>
                    <td>User Name :</td>
                    <td><form:input path="name" class="form-control"/></td>
                </tr>
                <tr>
                    <td>Password :</td>
                    <td><form:input path="password" class="form-control"/></td>
                </tr>
                <tr>
                    <td>Confirm Password :</td>
                    <td><form:password path="confirmPassword" class="form-control"/></td>
                </tr>
                <tr>
                    <td>Gender :</td>
                    <td>
                        <form:radiobutton path="gender" value="M" class="form-check-input"/> 
                        <form:label path="gender" class="form-check-label">M</form:label>
                        <form:radiobutton  path="gender" value="F" class="form-check-input"/>
                        <form:label path="gender" class="form-check-label">F</form:label>
                    </td>
                </tr>
                    <tr>
                        <td>Country :</td>
                        <td>
                        <form:select path="country"  class="form-control">
                                <form:option value="0" label="Select" />
                                <c:forEach items="${countryList}" var="item">
                                  <form:option value="${item.countryId}" label="${item.countryName}" />
                                </c:forEach>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td>About you :</td>
                        <td><form:textarea path="aboutYou"  class="form-control"/></td>
                    </tr>
                    <tr>
                        <td>Community :</td>
                    <td class="form-check">
                        <form:checkboxes path="communities" items="${communityList}" delimiter="<br/>"  class="form-check-input"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="form-check">
                            <form:label for="mailingList" path="mailingList" class="form-check-label">
                                <form:checkbox path="mailingList"  class="form-check-input"/>
                                Would you like to join our mailinglist?
                            </form:label>
                        </div>
                        
                        </td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Register" class="btn btn-pill btn-primary form-control-lg"/></td>
                </tr>
            </table>

        </form:form>
    </body>
</html>
