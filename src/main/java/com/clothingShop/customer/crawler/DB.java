package com.clothingShop.customer.crawler;

import java.sql.*;

public class DB {
    public Connection conn = null;
    public DB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/clothingShop";
            String user = "root";
            String password = "12345678";
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("conn built");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet runSql(String sql) throws SQLException{
        Statement sta = conn.createStatement();
        return sta.executeQuery(sql);
    }

    public Boolean runSql2(String sql) throws SQLException{
        Statement sta = conn.createStatement();
        return sta.execute(sql);
    }

    protected void finalize() throws Throwable{
        if(conn != null || !conn.isClosed()){
            conn.close();
        }
    }
}
