package model;

import java.sql.SQLException;

public class PostMutterLogic {

	private String dbPath;

	public PostMutterLogic(String dbPath) {
		this.dbPath = dbPath;
	}

	public void execute(Mutter mutter) {
		MutterDAO dao = new MutterDAO(dbPath);
		try {
			dao.create(mutter);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
