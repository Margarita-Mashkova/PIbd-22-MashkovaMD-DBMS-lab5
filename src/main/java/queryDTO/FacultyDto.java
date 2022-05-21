package queryDTO;

public class FacultyDto {
    private String facultyName;
    private String abbreviation;

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }


    @Override
    public String toString() {
        return " Наименование факультета: " + facultyName + "\n Аббревиатура: " + abbreviation + "\n";
    }
}
