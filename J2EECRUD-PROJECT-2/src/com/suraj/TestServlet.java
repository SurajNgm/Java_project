 package com.suraj;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/student_tracker_db")
	private DataSource dataSource;
	

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	//	Step 1: set up the PrintWriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
	
	//	Step 2: Get a connection on database
	
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();

		//	Step 3 : create a SQL Stateent
			String sql = "SELECT *FROM student";
			myStmt = myConn.createStatement();
					
		//	step 4 : Excute a SQL queary.
			myRs = myStmt.executeQuery(sql);
					
		//	Step 5 : Process the result set.
			while(myRs.next()) {
				String email = myRs.getString("email");
				
				out.println(email);
			}
			
		}catch(Exception exc) {
			exc.printStackTrace();
			}
		}
		
		
		
	}


