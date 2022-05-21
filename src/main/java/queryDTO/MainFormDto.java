package queryDTO;

public class MainFormDto {
    private String facultyName;
    private String cathedraName;
    private String groupName;
    private String studentFIO;
    private Double averageBall;
    private int grants;

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getCathedraName() {
        return cathedraName;
    }

    public void setCathedraName(String cathedraName) {
        this.cathedraName = cathedraName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getStudentFIO() {
        return studentFIO;
    }

    public void setStudentFIO(String studentFIO) {
        this.studentFIO = studentFIO;
    }

    public Double getAverageBall() {
        return averageBall;
    }

    public void setAverageBall(Double averageBall) {
        this.averageBall = averageBall;
    }

    public int getGrants() {
        return grants;
    }

    public void setGrants(int grants) {
        this.grants = grants;
    }

    @Override
    public String toString() {
        return " Факультет: " + facultyName + "\n Кафедра: " + cathedraName + "\n Группа: " + groupName + "\n ФИО: " + studentFIO
                + "\n Средний балл за сессию: " + averageBall + "\n Размер стипендии: " + grants + "\n";
    }
}
