package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sqlite.SQLiteConfig;

public class MutterDAO {

	private String dbPath;

	public MutterDAO(String dbPath) {
		this.dbPath = dbPath;
	}

	public List<Mutter> findAll() throws SQLException {

		List<Mutter> list = new ArrayList<Mutter>();

		Connection conn = null;

		try {
			conn = connect();

			String sql = "select id, name, text from Mutter order by id desc";

			PreparedStatement pStm = conn.prepareStatement(sql);

			ResultSet rs = pStm.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String text= rs.getString("text");
				list.add(new Mutter(id, name, text));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				conn.rollback();
				System.out.println("rollback");
			}
			return null;
		}finally {
			if(conn != null) {
				conn.close();
			}
		}

		return list;
	}

	public boolean create(Mutter mutter) throws SQLException {

		Connection conn = null;

		try {
			conn = connect();

			String sql = "insert into Mutter(name, text) values(?, ?)";
			PreparedStatement pStm = conn.prepareStatement(sql);
			pStm.setString(1, mutter.getUserName());
			pStm.setString(2, mutter.getText());

			int result = pStm.executeUpdate();

			return result == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				conn.rollback();
				System.out.println("rollback");
			}
			return false;
		}finally {
			if(conn != null) {
				conn.close();
			}
		}
	}

	private Connection connect() throws SQLException {
        var config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:sqlite:" + dbPath, config.toProperties());
	}
}
