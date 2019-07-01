<h3>fortokens</h3>
<pre>
&lt;c:fortokens var="item" delims="," items="fat,cat,sat,mat"&gt;
This is item \${item}&lt;br/&gt;
&lt;/c:fortokens&gt;
</pre>
<c:forTokens var="item" delims="," items="fat,cat,sat,mat">
    This is item ${item}<br/>
</c:forTokens>
