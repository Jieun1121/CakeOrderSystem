package cakeapp.dao;

import cakeapp.dto.PaymentDTO;
import cakeapp.DatabaseUtil;

import java.sql.*;

public class PaymentDAO {

    // 결제 등록
    public void insertPayment(PaymentDTO payment) {
        String sql = "INSERT INTO Payment (order_id, payment_method, payment_date, amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, payment.getOrderId());
            pstmt.setString(2, payment.getPaymentMethod());
            pstmt.setString(3, payment.getPaymentDate());
            pstmt.setDouble(4, payment.getAmount());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
