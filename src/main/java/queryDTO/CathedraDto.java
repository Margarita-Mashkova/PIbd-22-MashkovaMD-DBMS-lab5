package queryDTO;

public class CathedraDto {
    private String cathedraName;
    private String abbreviation;
    private String location;
    private int facultyId;

    public String getCathedraName() {
        return cathedraName;
    }

    public void setCathedraName(String cathedraName) {
        this.cathedraName = cathedraName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public String toString() {
        return " Наименование кафедры: " + cathedraName + "\n Аббревиатура: " + abbreviation
                + "\n Местоположение кафедры: " + location + "\n Id факультета: " + facultyId + "\n";
    }
}
