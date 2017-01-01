package cc.ethon.logmaker.reader.redy.db;

public class ExerciseDomainObject {

	// CREATE TABLE `exercise` (
	// `deleted` SMALLINT NOT NULL ,
	// `id` INTEGER PRIMARY KEY AUTOINCREMENT ,
	// `name` VARCHAR ,
	// `note` VARCHAR ,
	// `pos` INTEGER ,
	// `statParameter` INTEGER ,
	// `statPeriod` INTEGER ,
	// `timerEnabled` SMALLINT NOT NULL ,
	// `timerSecs` INTEGER ,
	// `type` INTEGER )

	private Boolean deleted;
	private Integer id;
	private String name;
	private String note;
	private Integer pos;
	private Integer statParameter;
	private Integer statPeriod;
	private Boolean timerEnabled;
	private Integer timerSecs;
	private Integer type;

	public ExerciseDomainObject() {
	}

	public ExerciseDomainObject(Boolean deleted, Integer id, String name, String note, Integer pos, Integer statParameter, Integer statPeriod,
			Boolean timerEnabled, Integer timerSecs, Integer type) {
		super();
		this.deleted = deleted;
		this.id = id;
		this.name = name;
		this.note = note;
		this.pos = pos;
		this.statParameter = statParameter;
		this.statPeriod = statPeriod;
		this.timerEnabled = timerEnabled;
		this.timerSecs = timerSecs;
		this.type = type;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	public Integer getStatParameter() {
		return statParameter;
	}

	public void setStatParameter(Integer statParameter) {
		this.statParameter = statParameter;
	}

	public Integer getStatPeriod() {
		return statPeriod;
	}

	public void setStatPeriod(Integer statPeriod) {
		this.statPeriod = statPeriod;
	}

	public Boolean getTimerEnabled() {
		return timerEnabled;
	}

	public void setTimerEnabled(Boolean timerEnabled) {
		this.timerEnabled = timerEnabled;
	}

	public Integer getTimerSecs() {
		return timerSecs;
	}

	public void setTimerSecs(Integer timerSecs) {
		this.timerSecs = timerSecs;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ExerciseDomainObject [deleted=" + deleted + ", id=" + id + ", name=" + name + ", note=" + note + ", pos=" + pos + ", statParameter="
				+ statParameter + ", statPeriod=" + statPeriod + ", timerEnabled=" + timerEnabled + ", timerSecs=" + timerSecs + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (deleted == null ? 0 : deleted.hashCode());
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (note == null ? 0 : note.hashCode());
		result = prime * result + (pos == null ? 0 : pos.hashCode());
		result = prime * result + (statParameter == null ? 0 : statParameter.hashCode());
		result = prime * result + (statPeriod == null ? 0 : statPeriod.hashCode());
		result = prime * result + (timerEnabled == null ? 0 : timerEnabled.hashCode());
		result = prime * result + (timerSecs == null ? 0 : timerSecs.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
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
		final ExerciseDomainObject other = (ExerciseDomainObject) obj;
		if (deleted == null) {
			if (other.deleted != null) {
				return false;
			}
		} else if (!deleted.equals(other.deleted)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (note == null) {
			if (other.note != null) {
				return false;
			}
		} else if (!note.equals(other.note)) {
			return false;
		}
		if (pos == null) {
			if (other.pos != null) {
				return false;
			}
		} else if (!pos.equals(other.pos)) {
			return false;
		}
		if (statParameter == null) {
			if (other.statParameter != null) {
				return false;
			}
		} else if (!statParameter.equals(other.statParameter)) {
			return false;
		}
		if (statPeriod == null) {
			if (other.statPeriod != null) {
				return false;
			}
		} else if (!statPeriod.equals(other.statPeriod)) {
			return false;
		}
		if (timerEnabled == null) {
			if (other.timerEnabled != null) {
				return false;
			}
		} else if (!timerEnabled.equals(other.timerEnabled)) {
			return false;
		}
		if (timerSecs == null) {
			if (other.timerSecs != null) {
				return false;
			}
		} else if (!timerSecs.equals(other.timerSecs)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

}
