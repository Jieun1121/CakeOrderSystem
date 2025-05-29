package cakeapp.dao;

import cakeapp.dto.CustomerDTO;
import cakeapp.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // 고객 등록
    public void insertCustomer(CustomerDTO customer) {
        String sql = "INSERT INTO Customer (name, address, phone_number, email, registration_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPhoneNumber());
            pstmt.setString(4, customer.getEmail());
            pstmt.setString(5, customer.getRegistrationDate());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 고객 주소 수정
    public void updateCustomerAddress(int customerId, String newAddress) {
        String sql = "UPDATE Customer SET address = ? WHERE customer_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newAddress);
            pstmt.setInt(2, customerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 고객 탈퇴
    public void deleteCustomer(int customerId) {
        String sql = "DELETE FROM Customer WHERE customer_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 단골 고객 조회
    public List<String> selectLoyalCustomers(double minAmount) {
        String sql = "SELECT customer_id, name, SUM(amount) as total, COUNT(*) as orders FROM Orders JOIN Payment USING(order_id) " +
                     "GROUP BY customer_id HAVING total >= ?";
        List<String> result = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, minAmount);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getInt("customer_id") + " | " +
                           rs.getString("name") + " | " +
                           rs.getDouble("total") + " | " +
                           rs.getInt("orders"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
