<h3>c:out</h3>
Escaped params in body text and tag attributes.<br/>

<pre>
In body text: &lt;c:out value="${outParam}"/&gt;&lt;br/&gt;
In tags: &lt;input value="&lt;c:out value="${outParam}"/&gt;"/&gt;&lt;br/&gt;
Message: \${message}

</pre>
In body text: <c:out value="${message}"/><br/>
In tags: <input value="<c:out value="${message}"/>"/><br/>
Message: ${message}

