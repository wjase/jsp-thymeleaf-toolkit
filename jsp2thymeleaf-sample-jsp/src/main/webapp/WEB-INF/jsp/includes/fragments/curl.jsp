<h3>c:url</h3>
Escaped urls adjusted with the current application context.<br/>
<pre>
&lt;c:url value="/resources/text.txt" var="url"/&gt;
&lt;spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" /&gt;
JSTL URL: \${url}&lt;br/&gt;
</pre>
<c:url value="/resources/text.txt" var="url"/>
<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" />
Spring URL: ${springUrl} at ${time}<br/>
JSTL URL: ${url}<br/>
