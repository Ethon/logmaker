package cc.ethon.logmaker.reader.redy.db;

public class WorkoutRoutineDomainObject {

	// CREATE TABLE `workoutroutine` (
	// `id` INTEGER PRIMARY KEY AUTOINCREMENT ,
	// `lastTrained` VARCHAR ,
	// `name` VARCHAR ,
	// `pos` INTEGER )

	private Integer id;
	private String lastTrained;
	private String name;
	private Integer pos;

	public WorkoutRoutineDomainObject() {
	}

	public WorkoutRoutineDomainObject(Integer id, String lastTrained, String name, Integer pos) {
		super();
		this.id = id;
		this.lastTrained = lastTrained;
		this.name = name;
		this.pos = pos;
	}

	public Integer getId() {
		return id;
	}

	public String getLastTrained() {
		return lastTrained;
	}

	public String getName() {
		return name;
	}

	public Integer getPos() {
		return pos;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLastTrained(String lastTrained) {
		this.lastTrained = lastTrained;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	@Override
	public String toString() {
		return "WorkoutRoutineDomainObject [id=" + id + ", lastTrained=" + lastTrained + ", name=" + name + ", pos=" + pos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (lastTrained == null ? 0 : lastTrained.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (pos == null ? 0 : pos.hashCode());
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
		final WorkoutRoutineDomainObject other = (WorkoutRoutineDomainObject) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lastTrained == null) {
			if (other.lastTrained != null) {
				return false;
			}
		} else if (!lastTrained.equals(other.lastTrained)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (pos == null) {
			if (other.pos != null) {
				return false;
			}
		} else if (!pos.equals(other.pos)) {
			return false;
		}
		return true;
	}

}
