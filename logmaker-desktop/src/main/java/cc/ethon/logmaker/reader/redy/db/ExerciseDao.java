package cc.ethon.logmaker.reader.redy.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDao implements AutoCloseable {

	private final Connection connection;
	private PreparedStatement getAllExercisesStatement;
	private PreparedStatement getExerciseByIdStatement;

	private void prepareStatements() throws SQLException {
		getAllExercisesStatement = connection.prepareStatement("select * from `exercise`");
		getExerciseByIdStatement = connection.prepareStatement("select * from `exercise` where id = ?");
	}

	private ExerciseDomainObject readOne(ResultSet rs) throws SQLException {
		final Boolean deleted = rs.getBoolean("deleted");
		final Integer id = rs.getInt("id");
		final String name = rs.getString("name");
		final String note = rs.getString("note");
		Integer pos = rs.getInt("pos");
		if (rs.wasNull()) {
			pos = null;
		}
		Integer statParameter = rs.getInt("statParameter");
		if (rs.wasNull()) {
			statParameter = null;
		}
		Integer statPeriod = rs.getInt("statPeriod");
		if (rs.wasNull()) {
			statPeriod = null;
		}
		final Boolean timerEnabled = rs.getBoolean("timerEnabled");
		Integer timerSecs = rs.getInt("timerSecs");
		if (rs.wasNull()) {
			timerSecs = null;
		}
		Integer type = rs.getInt("type");
		if (rs.wasNull()) {
			type = null;
		}
		return new ExerciseDomainObject(deleted, id, name, note, pos, statParameter, statPeriod, timerEnabled, timerSecs, type);
	}

	public ExerciseDao(Connection connection) throws SQLException {
		super();
		this.connection = connection;
		prepareStatements();
	}

	public List<ExerciseDomainObject> getAllExercises() throws SQLException {
		final List<ExerciseDomainObject> result = new ArrayList<ExerciseDomainObject>();
		try (ResultSet rs = getAllExercisesStatement.executeQuery()) {
			while (rs.next()) {
				result.add(readOne(rs));
			}
		}
		return result;
	}

	public ExerciseDomainObject getExerciseById(Integer id) throws SQLException {
		getExerciseByIdStatement.setInt(1, id);
		try (ResultSet rs = getExerciseByIdStatement.executeQuery()) {
			if (!rs.next()) {
				throw new IllegalArgumentException("No exercise with id " + id);
			}
			return readOne(rs);
		}
	}

	@Override
	public void close() throws SQLException {
		getAllExercisesStatement.close();
		getExerciseByIdStatement.close();
	}

}
