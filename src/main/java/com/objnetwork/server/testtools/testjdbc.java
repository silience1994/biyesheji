package com.objnetwork.server.testtools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testjdbc {
    public static void Test() {
 
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        System.out.println("tag1");
 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.err.println("mydb(): " + e.getMessage());
        }
 
        System.out.println("tag2");

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC",
                    "root", "qiren886740");
        } catch (SQLException ex) {
            System.err.println("conn:" + ex.getMessage());
        }
        
        System.out.println("tag3");
        
        if (conn != null)
            System.out.println("connection successful");
        else
            System.out.println("connection failure");
 
        try {
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            System.err.println("createStatement();" + ex.getMessage());
        }
 
        try {
            rs = stmt.executeQuery("select count(*) from user");
        } catch (SQLException ex) {
            System.err.println("stmt.excuteQuery();" + ex.getMessage());
        }
 
        try {
            while (rs.next()) {
                System.out.println("Row number: " + rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.err.println("A ERROR is failure" + ex.getMessage());
        }
    }
 
    public static void printClassName(Object obj) {
        System.out.println("The class of " + obj + " is "
                + obj.getClass().getName());
    }
 
}