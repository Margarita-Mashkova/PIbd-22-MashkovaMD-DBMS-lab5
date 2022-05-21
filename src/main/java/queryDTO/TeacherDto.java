package queryDTO;

public class TeacherDto {
    private String teacherFIO;
    private int cathedraId;

    public String getTeacherFIO() {
        return teacherFIO;
    }

    public void setTeacherFIO(String teacherFIO) {
        this.teacherFIO = teacherFIO;
    }

    public int getCathedraId() {
        return cathedraId;
    }

    public void setCathedraId(int cathedraId) {
        this.cathedraId = cathedraId;
    }

    @Override
    public String toString() {
        return " ФИО преподавателя: " + teacherFIO + "\n Id кафедры: " + cathedraId + "\n";
    }
}
