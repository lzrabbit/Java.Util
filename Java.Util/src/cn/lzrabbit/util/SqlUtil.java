package cn.lzrabbit.util;

import java.sql.*;
import java.util.Iterator;
import java.util.List;

import org.springframework.cglib.beans.BulkBean;

public class SqlUtil {

	private String URL;
	private String USER;
	private String PASSWORD;

	public SqlUtil() throws ClassNotFoundException {
		String server = "192.168.99.109";
		String database = "test";
		String user = "sa";
		String password = "nopass.2";
		initialize(server, database, user, password);
	}

	public SqlUtil(String server, String database, String user, String password) throws ClassNotFoundException {
		initialize(server, database, user, password);
	}

	private void initialize(String server, String database, String user, String password) throws ClassNotFoundException {
		this.URL = jtds(server, database);
		this.USER = user;
		this.PASSWORD = password;
	}

	public String jtds(String server, String database) throws ClassNotFoundException {
		String driver = "net.sourceforge.jtds.jdbc.Driver";
		Class.forName(driver);
		return String.format("jdbc:jtds:sqlserver://%s:1433/%s", server, database);
	}

	public String mysql(String server, String database) throws ClassNotFoundException {
		String driver = "com.mysql.jdbc.Driver";
		Class.forName(driver);
		return String.format("jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true", server, database);

	}

	public String microsoft(String server, String database) throws ClassNotFoundException {
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		Class.forName(driver);
		return String.format("jdbc:sqlserver://%s:1433;database=%s;", server, database);
	}

	public void insertBatch(String sql, List<List<Object>> params, int batchSize) throws SQLException {
		long start = System.currentTimeMillis();
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				for (int j = 0; j < params.get(i).size(); j++) {
					pstmt.setObject(j + 1, params.get(i).get(j));
				}
				pstmt.addBatch();
				if (i > 0 && i % batchSize == 0) {
					pstmt.executeBatch();
					pstmt.clearParameters();
					pstmt.clearBatch();
				}
			}
			pstmt.executeBatch();
			pstmt.clearParameters();
			pstmt.clearBatch();
		}
		conn.commit();
		conn.close();
		long end = System.currentTimeMillis();
		System.out.println(String.format("批次:%s 耗时:%s", batchSize, (end - start)));
	}

	public void insertBatch1(List<String> sqls, int batchSize) throws SQLException {
		long start = System.currentTimeMillis();
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();

		if (sqls != null) {
			for (int i = 0; i < sqls.size(); i++) {
				stmt.addBatch(sqls.get(i));
				if (i > 0 && i % batchSize == 0) {
					stmt.executeBatch();
					stmt.clearBatch();
				}
			}
			stmt.executeBatch();
			stmt.clearBatch();
		}
		conn.commit();
		conn.close();
		long end = System.currentTimeMillis();
		System.out.println(String.format("批次:%s 耗时:%s", batchSize, (end - start)));
	}

	private Connection getConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
		conn.setAutoCommit(false);
		return conn;
	}

}
