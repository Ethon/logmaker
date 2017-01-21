package cc.ethon.logmaker.reader.logmaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cc.ethon.logmaker.Exercise;
import cc.ethon.logmaker.Exercise.ExerciseType;
import cc.ethon.logmaker.Set;
import cc.ethon.logmaker.Workout;
import cc.ethon.logmaker.WorkoutLog;
import cc.ethon.logmaker.reader.LogReader;

public class LogmakerXmlReader implements LogReader {

	private final File file;
	private InputStream stream;

	private Document parseDocument(InputStream stream) throws ParserConfigurationException, SAXException, IOException {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(stream);
	}

	private Set parseSet(Element setNode, Exercise exercise, LocalDate date) {
		final LocalTime time = LocalTime.parse(setNode.getAttribute("time"), DateTimeFormatter.ISO_LOCAL_TIME);
		final int weight = Integer.valueOf(setNode.getAttribute("weight"));
		final int reps = Integer.valueOf(setNode.getAttribute("reps"));
		final int timeDone = Integer.valueOf(setNode.getAttribute("timeDone"));
		final int distance = Integer.valueOf(setNode.getAttribute("distance"));
		return new Set(date, time, exercise, reps, weight, timeDone, distance);
	}

	private void parseExercise(Workout workout, Element exerciseNode, LocalDate date) {
		final String name = exerciseNode.getAttribute("name");
		final ExerciseType type = ExerciseType.valueOf(exerciseNode.getAttribute("type"));
		final Exercise exercise = new Exercise(name, type);
		final NodeList sets = exerciseNode.getElementsByTagName("Set");
		for (int i = 0; i < sets.getLength(); ++i) {
			workout.addSet(parseSet((Element) sets.item(i), exercise, date));
		}
	}

	private Workout parseWorkout(Element workoutNode) {
		final String name = workoutNode.getAttribute("name");
		final LocalDate date = LocalDate.parse(workoutNode.getAttribute("date"), DateTimeFormatter.ISO_LOCAL_DATE);
		final Workout workout = new Workout(!name.isEmpty() ? Optional.of(name) : Optional.empty(), date);
		final NodeList exercises = workoutNode.getElementsByTagName("Exercise");
		for (int i = 0; i < exercises.getLength(); ++i) {
			parseExercise(workout, (Element) exercises.item(i), date);
		}
		return workout;
	}

	private WorkoutLog parseWorkoutLog(Element root) {
		final WorkoutLog workoutLog = new WorkoutLog();
		final NodeList workouts = root.getElementsByTagName("Workout");
		for (int i = 0; i < workouts.getLength(); ++i) {
			workoutLog.addWorkout(parseWorkout((Element) workouts.item(i)));
		}
		return workoutLog;
	}

	public LogmakerXmlReader(File file) {
		this.file = file;
		this.stream = null;
	}

	public LogmakerXmlReader(InputStream stream) {
		this.file = null;
		this.stream = stream;
	}

	@Override
	public WorkoutLog readLog() throws Exception {
		if (stream == null) {
			stream = new FileInputStream(file);
		}

		final Document doc = parseDocument(stream);
		final Element root = doc.getDocumentElement();
		if (!root.getTagName().equals("WorkoutLog")) {
			throw new IllegalArgumentException("Expected <WorkoutLog>");
		}
		return parseWorkoutLog(root);
	}
}
