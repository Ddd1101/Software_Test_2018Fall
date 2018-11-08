package dbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Shanghai";
	private static final String DBuser = "root";
	private static final String DBPASSWORD = "qwer1234";
	private Connection conn = null;
	
	public DatabaseConnection() throws Exception {
		try {
			Class.forName(DBDRIVER);
			conn = DriverManager.getConnection(DBURL, DBuser, DBPASSWORD);
		} catch(ClassNotFoundException e) {
			System.out.println("jdbc Driver cannot found.");
			e.printStackTrace();
			throw e;
		} catch(SQLException e) {
			System.out.println("DB connection failed.");
			e.printStackTrace();
			throw e;
		}
	}

    public Connection getConnection() {
        return this.conn;
    }

    public void close() throws Exception {
        if (this.conn != null) {
            this.conn.close();
        }
    }
    
    public boolean runSQL(String sqlFileName) {
		boolean flag = true;
		File file = new File(sqlFileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String sql = null;
			while((sql = reader.readLine()) != null) {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				int rows_count = pstmt.executeUpdate();
				System.out.println(rows_count + ":\t" + sql);
			}
			reader.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			flag = false;
		}
		return flag;
	}
}
