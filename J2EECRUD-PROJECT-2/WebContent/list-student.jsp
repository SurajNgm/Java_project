<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Student Tracker App</title>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
    
    <body>
       <div id="wrapper">
              <div id="header">
                    <h2>Micronsol University</h2>
              </div>
       </div>
       <div id="container">
             <div id="content">
             
             <!-- put new button: Add Student -->
             
             <input class="add-student-button" type="button" value="Add Student" onclick="window.location.href='add-student-form.jsp'; return false;"/>
             
                   <table>
                         <tr>
                               <th>First Name</th>
                               <th>Last Name</th>
                               <th>Email</th>
                               <th>Action</th>
                         </tr>
                         
                        <c:forEach var="tempStudent"  items="${STUDENT_LIST}" >
                            
                            <!-- setup a link for eah student -->
                            
                            <c:url var="tempLink" value="StudentControllerServlet">
                                  <c:param name="command" value="LOAD"/>
                                  <c:param name="studentId" value="${tempStudent.id}"/>
                            </c:url>
                            
                            <!-- setup a link to delete a student  -->
                            
                             <c:url var="deleteLink" value="StudentControllerServlet">
                                  <c:param name="command" value="DELETE"/>
                                  <c:param name="studentId" value="${tempStudent.id}"/>
                            </c:url>
                             <tr>
                             
                                   <td>${tempStudent.firstName}</td>
                                   <td>${tempStudent.lastName}</td>
                                   <td>${tempStudent.email}</td>
                                   <td>
                                       <a href="${tempLink}">Update</a> | 
                                       <a href="${deleteLink}" onclick="if(!(confirm('Are you sure to delete this student?'))) return false">Delete</a>
                                   </td>
                             </tr>
                           
                         </c:forEach>
                   </table>
             </div>
       </div>
       
    </body>
</html>