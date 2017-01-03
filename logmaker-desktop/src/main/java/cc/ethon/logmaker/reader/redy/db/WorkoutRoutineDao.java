package cc.ethon.logmaker.reader.redy.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class WorkoutRoutineDao implements AutoCloseable {

	private final Connection connection;
	private PreparedStatement getWorkoutRoutineByLastTrainedDateStatement;

	private void prepareStatements() throws SQLException {
		getWorkoutRoutineByLastTrainedDateStatement = connection.prepareStatement("SELECT * FROM `workoutroutine` WHERE lastTrained LIKE ?");
	}

	private WorkoutRoutineDomainObject readOne(ResultSet rs) throws SQLException {
		final Integer id = rs.getInt("id");
		final String lastTrained = rs.getString("lastTrained");
		final String name = rs.getString("name");
		Integer pos = rs.getInt("pos");
		if (rs.wasNull()) {
			pos = null;
		}
		return new WorkoutRoutineDomainObject(id, lastTrained, name, pos);
	}

	public WorkoutRoutineDao(Connection connection) throws SQLException {
		this.connection = connection;
		prepareStatements();
	}

	public WorkoutRoutineDomainObject getWorkoutRoutineByLastTrainedDate(LocalDate date) throws SQLException {
		final String dateString = String.format("%d-%2d-%2d", date.getYear(), date.getMonthValue(), date.getDayOfMonth());
		getWorkoutRoutineByLastTrainedDateStatement.setString(1, dateString + "%");
		try (ResultSet rs = getWorkoutRoutineByLastTrainedDateStatement.executeQuery()) {
			if (rs.next()) {
				readOne(rs);
			}
		}
		return null;
	}

	@Override
	public void close() throws SQLException {
		getWorkoutRoutineByLastTrainedDateStatement.close();
	}
}
