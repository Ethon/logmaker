package cc.ethon.logmaker.reader.redy.db;

public class SetDomainObject {

	// CREATE TABLE `set` (
	// `date` VARCHAR NOT NULL ,
	// `distance` INTEGER ,
	// `exercise_id` INTEGER NOT NULL ,
	// `id` INTEGER PRIMARY KEY AUTOINCREMENT ,
	// `note` VARCHAR ,
	// `repeats` INTEGER NOT NULL ,
	// `strain` INTEGER NOT NULL ,
	// `time` INTEGER ,
	// `weightGrams` INTEGER NOT NULL )

	private String date;
	private Integer distance;
	private Integer exerciseId;
	private Integer id;
	private String note;
	private Integer repeats;
	private Integer strain;
	private Integer time;
	private Integer weightGrams;

	public SetDomainObject() {
	}

	public SetDomainObject(String date, Integer distance, Integer exerciseId, Integer id, String note, Integer repeats, Integer strain, Integer time,
			Integer weightGrams) {
		super();
		this.date = date;
		this.distance = distance;
		this.exerciseId = exerciseId;
		this.id = id;
		this.note = note;
		this.repeats = repeats;
		this.strain = strain;
		this.time = time;
		this.weightGrams = weightGrams;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Integer getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(Integer exerciseId) {
		this.exerciseId = exerciseId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getRepeats() {
		return repeats;
	}

	public void setRepeats(Integer repeats) {
		this.repeats = repeats;
	}

	public Integer getStrain() {
		return strain;
	}

	public void setStrain(Integer strain) {
		this.strain = strain;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getWeightGrams() {
		return weightGrams;
	}

	public void setWeightGrams(Integer weightGrams) {
		this.weightGrams = weightGrams;
	}

	@Override
	public String toString() {
		return "SetDomainObject [date=" + date + ", distance=" + distance + ", exerciseId=" + exerciseId + ", id=" + id + ", note=" + note + ", repeats="
				+ repeats + ", strain=" + strain + ", time=" + time + ", weightGrams=" + weightGrams + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (date == null ? 0 : date.hashCode());
		result = prime * result + (distance == null ? 0 : distance.hashCode());
		result = prime * result + (exerciseId == null ? 0 : exerciseId.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (note == null ? 0 : note.hashCode());
		result = prime * result + (repeats == null ? 0 : repeats.hashCode());
		result = prime * result + (strain == null ? 0 : strain.hashCode());
		result = prime * result + (time == null ? 0 : time.hashCode());
		result = prime * result + (weightGrams == null ? 0 : weightGrams.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SetDomainObject other = (SetDomainObject) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (distance == null) {
			if (other.distance != null) {
				return false;
			}
		} else if (!distance.equals(other.distance)) {
			return false;
		}
		if (exerciseId == null) {
			if (other.exerciseId != null) {
				return false;
			}
		} else if (!exerciseId.equals(other.exerciseId)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (note == null) {
			if (other.note != null) {
				return false;
			}
		} else if (!note.equals(other.note)) {
			return false;
		}
		if (repeats == null) {
			if (other.repeats != null) {
				return false;
			}
		} else if (!repeats.equals(other.repeats)) {
			return false;
		}
		if (strain == null) {
			if (other.strain != null) {
				return false;
			}
		} else if (!strain.equals(other.strain)) {
			return false;
		}
		if (time == null) {
			if (other.time != null) {
				return false;
			}
		} else if (!time.equals(other.time)) {
			return false;
		}
		if (weightGrams == null) {
			if (other.weightGrams != null) {
				return false;
			}
		} else if (!weightGrams.equals(other.weightGrams)) {
			return false;
		}
		return true;
	}

}
