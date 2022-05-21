package queryDTO;

public class SubjectDto {
    private String subjectName;
    private int teacherId;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return " Наименование предмета: " + subjectName + "\n Id преподавателя: " + teacherId + "\n";
    }
}
