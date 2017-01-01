package cc.ethon.logmaker.reader.redy.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SetDao implements AutoCloseable {

	private final Connection connection;
	private PreparedStatement getAllSetsStatement;

	private void prepareStatements() throws SQLException {
		getAllSetsStatement = connection.prepareStatement("SELECT * FROM `set` ORDER BY date ASC");
	}

	private SetDomainObject readOne(ResultSet rs) throws SQLException {
		final String date = rs.getString("date");
		Integer distance = rs.getInt("distance");
		if (rs.wasNull()) {
			distance = null;
		}
		final Integer exerciseId = rs.getInt("exercise_id");
		final Integer id = rs.getInt("id");
		final String note = rs.getString("note");
		final Integer repeats = rs.getInt("repeats");
		final Integer strain = rs.getInt("strain");
		Integer time = rs.getInt("time");
		if (rs.wasNull()) {
			time = null;
		}
		final Integer weightGrams = rs.getInt("weightGrams");
		return new SetDomainObject(date, distance, exerciseId, id, note, repeats, strain, time, weightGrams);
	}

	public SetDao(Connection connection) throws SQLException {
		this.connection = connection;
		prepareStatements();
	}

	public List<SetDomainObject> getAllSets() throws SQLException {
		final List<SetDomainObject> result = new ArrayList<SetDomainObject>();
		try (final ResultSet rs = getAllSetsStatement.executeQuery()) {
			while (rs.next()) {
				result.add(readOne(rs));
			}
		}
		return result;
	}

	@Override
	public void close() throws SQLException {
		getAllSetsStatement.close();
	}

}
