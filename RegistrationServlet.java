import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import java.io.*;

public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Connection connection;

    public void init() {
        
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
	PrintWriter out = response.getWriter();
	response.setContentType("text/html");
	
try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/signup";
            String username = "root";
            String password = "Tushar389@satara";
	    connection = DriverManager.getConnection(dbURL, username, password);
            } 
	    catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
	
		
	 try {
		
		out.println("Hello Tushar");
		out.println(connection);
            if (connection != null) {
		
                String username = request.getParameter("name");
                String password = request.getParameter("password");
                String email = request.getParameter("email");
                String address = request.getParameter("address");
                String city = request.getParameter("city");
                String state = request.getParameter("state");
                int postcode = Integer.parseInt(request.getParameter("postcode"));
		out.println("Hello IMCA");
                String query = "INSERT INTO newuser (name, password, email, address, city, state, postcode) VALUES (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
		out.println("By Tushar");

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, city);
                preparedStatement.setString(6, state);
                preparedStatement.setInt(7, postcode);
                preparedStatement.executeUpdate();
		
                response.sendRedirect("success.jsp"); // Redirect to success page
            } else {
                throw new SQLException("Failed to establish a database connection.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage()); // Pass the error message as a request attribute
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response); 
            // response.sendRedirect("error.jsp"); // Redirect to error page
        }
    }

    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

