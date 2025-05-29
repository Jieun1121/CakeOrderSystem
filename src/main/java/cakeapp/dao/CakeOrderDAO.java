package cakeapp.dao;

import cakeapp.dto.CakeOrderDTO;
import cakeapp.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CakeOrderDAO {

    // 케이크 주문 등록
    public void insertCakeOrder(CakeOrderDTO order) {
        String sql = "INSERT INTO CakeOrder (cake_name, customization_options, order_date, order_status, customer_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, order.getCakeName());
            pstmt.setString(2, order.getCustomizationOptions());
            pstmt.setString(3, order.getOrderDate());
            pstmt.setString(4, order.getOrderStatus());
            pstmt.setInt(5, order.getCustomerId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 주문 상태 수정
    public void updateOrderState(int orderId, String newState) {
        String sql = "UPDATE CakeOrder SET order_status = ? WHERE order_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newState);
            pstmt.setInt(2, orderId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 주문 취소 삭제
    public void deleteCancelledOrder(int orderId) {
        String sql = "DELETE FROM CakeOrder WHERE order_id = ? AND order_status = 'CANCELLED'";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 고객 최근 주문 조회
    public List<String> selectCustomerOrder(int customerId) {
        String sql = "SELECT order_id, cake_name, order_date, order_status FROM CakeOrder WHERE customer_id = ? ORDER BY order_date DESC LIMIT 5";
        List<String> orders = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                orders.add(rs.getInt("order_id") + " | " +
                           rs.getString("cake_name") + " | " +
                           rs.getString("order_date") + " | " +
                           rs.getString("order_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
