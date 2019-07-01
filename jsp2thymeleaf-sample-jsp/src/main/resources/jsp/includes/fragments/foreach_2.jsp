<h3>foreach - Form 2 (Range iterator)</h3>
<pre>
&lt;c:foreach var="item" begin="2" end="10" step="2" varStatus="status"&gt;
This is item \${item}. Status is \${status.index}&lt;br/&gt;
&lt;/c:foreach&gt;
</pre>
<c:forEach var="item" begin="2" end="10" step="2" varStatus="status">
    This is item ${item}. Status is ${status.index}<br/>
</c:forEach>
