package com.suraj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.suraj.Student;

public class StudentDbUtil {
	private DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
		
	}
		
		// Get List of all student
		public List<Student> getStudents(){
			
			List <Student> students = new ArrayList<>();
			
			Connection myConn = null;
			Statement myStmt = null;
			ResultSet myRs = null;
			
			
			try {
				myConn = dataSource.getConnection();
			//	Step 3 : create a SQL Statement
				String sql = "SELECT *FROM student";
				myStmt = myConn.createStatement();
						
			//	step 4 : Excute a SQL queary.
				myRs = myStmt.executeQuery(sql);
				
			//	Converting rows into student class object
				
				while(myRs.next()) {
			//		Retriving the data from the resultset
					int id = myRs.getInt("id");
					String firstName = myRs.getString("first_name");
					String lastName  = myRs.getString("last_name");
					String email = myRs.getString("email");
					
			//		Create new Student object
					
					Student tempStudent = new Student(id,firstName,lastName, email);
					students.add(tempStudent);
					
				}
				
			}catch(Exception exc) {
				exc.printStackTrace();
				
			}
			finally {
				// clean jdbc resource (myStmt, myRs, myConn)
				close(myConn, myRs, myStmt);
			}
			return students;
			
		}
		
		// Get A Single Student By Id
		public Student getStudent(String theStudentId) throws Exception{
			Student theStudent = null;
			
			Connection myConn = null;
			PreparedStatement myStmt = null;
			ResultSet myRs = null;
			
			int StudentId;
			
			
			try {
				//convert studentId to int
				StudentId = Integer.parseInt(theStudentId);
				
				// get connection
				myConn = dataSource.getConnection();
				
				// create sql to get selected student
				String sql = "select *from student where id=?";
				
				// create prepared statement
				myStmt = myConn.prepareStatement(sql);
				
				// parameter binding
				myStmt.setInt(1, StudentId);
			
				// excute query
				myRs = myStmt.executeQuery();
				
				// retrive data from result set row
				if(myRs.next()) {
					String firstName = myRs.getString("first_name");
					String lastName = myRs.getString("last_name");
					String email = myRs.getString("email");
					
					// using the theStudentId during object construction
					theStudent = new Student(StudentId, firstName, lastName, email);
					}else {
						throw new Exception("Could not find student id: "+StudentId);
					}
					
				return theStudent;
			}finally {
				// cleanup JDBC objects
				close(myConn, myRs, myStmt);
			}
			
		}
		
		// Add a New Student
		public void addStudent(Student theStudent)throws Exception {
			Connection myConn = null;
			PreparedStatement myStmt = null;
			try {
				// get connection
				myConn = dataSource.getConnection();
				
				// create sql query
				String sql = "insert into student (first_name, last_name, email) values (?,?,?)";
				
				myStmt = myConn.prepareStatement(sql);
				
				// set param values for he student
				
				myStmt.setString(1,theStudent.getFirstName());
				myStmt.setString(2,theStudent.getLastName());
				myStmt.setString(3,theStudent.getEmail());
				
				// excute query
				myStmt.execute();
				
			}finally {
				// cleanup JDBC resources
				close(myConn, null, myStmt);
			}
		}
		
		// Update A Student
		public void UpdateStudent(Student theStudent) throws Exception{
			Connection myConn = null;
			PreparedStatement myStmt = null;
			try {
				// get Connection
				myConn = dataSource.getConnection();
				
				// create SQL query
				String sql = "update student set first_name=?, last_name=?, email=? where id=?";
				
				myStmt = myConn.prepareStatement(sql);
				
				// set param values for he student
				
				myStmt.setString(1,theStudent.getFirstName());
				myStmt.setString(2,theStudent.getLastName());
				myStmt.setString(3,theStudent.getEmail());
				myStmt.setInt(4,theStudent.getId());
				
				// excute query
				
				myStmt.execute();
			}finally {
				// cleanup JDBC resources
				close(myConn, null, myStmt);
			}
		}
		
		// Delete A Student
		public void deleteStudent(String theStudentId)throws Exception {
			Connection myConn = null;
			PreparedStatement myStmt = null;
			try {
				// Convert student to int
				int StudentId = Integer.parseInt(theStudentId);
				
				// get Connection
				myConn = dataSource.getConnection();
				
				// create sql to get selected student
				String sql = "delete from student where id = ?";
				
				// create prepared statement
				myStmt = myConn.prepareStatement(sql);
				
				//parameter binding
				myStmt.setInt(1, StudentId);
				
				// excute query
				myStmt.execute();
			
			}finally {
				// cleanup JDBC object
				close(myConn, null, myStmt);
			}
			
			
		}
		
		// CleanUp code
		private void close(Connection myConn, ResultSet myRs, Statement myStmt) {
			try {
				if(myConn != null) {
					myConn.close();
				}
				if(myRs != null) {
					myRs.close();
				}
				if(myStmt != null) {
					myStmt.close();
				}
			} catch(Exception exe) {
				exe.printStackTrace();
			}
			
		}

		
		
	

}












