import java.io.*;
import java.sql.*;

public class Insert100Records {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root1234!!";

    public static void main(String[] args) {
        String dir = "C:\\SpringBoot\\ch9\\src\\main\\resources";
        String filePath = dir + "\\address.txt";

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split(",");

                    if (parts.length == 6) {

                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String country = parts[2];
                        String phone = parts[3];
                        int yearBirth = Integer.parseInt(parts[4]);
                        String address = parts[5];

                        String sql = "INSERT INTO members (id, name, country, contact, birth_year, address) VALUES (?, ?, ?, ?, ?, ?)";

                        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            pstmt.setInt(1, id);
                            pstmt.setString(2, name);
                            pstmt.setString(3, country);
                            pstmt.setString(4, phone);
                            pstmt.setInt(5, yearBirth);
                            pstmt.setString(6, address);
                            pstmt.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}