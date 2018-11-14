package dao.res;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import dbc.DatabaseConnection;

public class baseDAO {

	protected DatabaseConnection dbc = null;
	protected Connection conn = null;

	public baseDAO() {
		try {
			dbc = new DatabaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn = dbc.getConnection();
	}
	
	public void close() {
		try {
			if(dbc != null) {
				dbc.close();
				dbc = null;
			}
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() throws Throwable{
		this.close();
		super.finalize();
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
