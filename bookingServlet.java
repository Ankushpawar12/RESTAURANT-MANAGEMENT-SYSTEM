import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/bookingServlet")
public class bookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        int partySize = Integer.parseInt(request.getParameter("partySize"));

        String jdbcUrl = "jdbc:mysql://localhost:3306/signup";
        String dbUser = "root";
        String dbPassword = "Tushar389@satara";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            out.println(1);

            String query = "INSERT INTO booking (name, email, phone, date, time, size) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, date);
            preparedStatement.setString(5, time);
            preparedStatement.setInt(6, partySize);

            int rowsAffected = preparedStatement.executeUpdate();
            out.println(2);

            response.setContentType("text/html");
            out.println("<html><body>");

            if (rowsAffected > 0) {
                out.println("<p>Booking successful!</p>");
            } else {
                out.println("<p>Booking failed. Please try again.</p>");
            }

            out.println("</body></html>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<html><body><p>An error occurred. Please try again later.</p></body></html>");
        }
    }
}






