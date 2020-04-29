package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetMutterLogic {

	private String dbPath;

	public GetMutterLogic(String dbPath) {
		this.dbPath = dbPath;
	}

	public List<Mutter> execute(){
		MutterDAO dao = new MutterDAO(dbPath);
		try {
			return dao.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Mutter>();
	}
}
