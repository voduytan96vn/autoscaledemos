 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <h2>Welcome to EAP-JDG Demo</h2>
 <h3>List of Objects in the cache:</h3>
 
 <c:choose>
    <c:when test="${empty playerList}">
        Cache is empty
    </c:when>
    <c:otherwise>
    
    <c:forEach var="entry" items="${playerList}">
  Key: <c:out value="${entry.key}"/>
  Value: <c:out value="${entry.value}"/>
  <br />
</c:forEach>
        
 
 
    </c:otherwise>
</c:choose>

<br /> 
Add Data
<br />

<table>
    <form method="post" action="test">
        <tr>
        <td>Name</td>
        <td>
            <input type="text" name="name">
        </td>
        </tr>
       
               <tr>
        <td>Surname</td>
        <td>
            <input type="text" name="surname">
        </td>
        </tr>
        
                <tr>
        <td>Team</td>
        <td>
            <input type="text" name="teamname">
        </td>
        </tr>
        
                <tr>
       
        <td>
           <input type="submit">
        </td>
        </tr>          
    
    </form>
</table>


