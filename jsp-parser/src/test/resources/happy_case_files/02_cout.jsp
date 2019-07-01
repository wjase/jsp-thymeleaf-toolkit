<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %> 
<div <c:out value="style=&quot;display:none;&quot;"/> >div content</div>
<c:out value='${param.name}'/> 
In body text: <c:out value="${inBodyMessage}"/><br/>
In tags: <input value='<c:out value="${inAttributeMessage}"/>'/><br/>
