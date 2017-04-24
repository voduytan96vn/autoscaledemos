 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <h2>Welcome to EAP-JDG Demo</h2>
 <h3>List of Objects in the cache:</h3>
 <c:when test="${not empty playerList}">
<c:forEach var="p" items="playerList">
   <c:out value="${p.name}" />
   <c:out value="${p.surname}" />
   <c:out value="${p.teamName}" />
 
</c:forEach>
</c:when>
Add Data
<br />
<form action="test" >

Name <input type="text" name="name" > <br/>
Surname <input type="text" name="surname" > <br/>
Team <input type="text" name="teamname" > <br/>
<input type="submit">
</form>
