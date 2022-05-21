package Models;

import javax.persistence.*;

@Entity
@Table(name = "cathedra",indexes = { @Index(name = "IDX_MYIDX_cathedra", columnList = "id, abbreviation") })
public class Cathedra {
    @Id
    @SequenceGenerator(name = "identifier", sequenceName = "seq_cathedra", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "identifier")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "location")
    private String location;

    @ManyToOne
    private Faculty faculty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void nullificationFaculty() {
        this.faculty = null;
    }

    @Override
    public String toString() {
        if (faculty != null) {
            return "Id: " + id + "\nНаименование кафедры: " + name + "\nАббревиатура: " + abbreviation
                    + "\nМестоположение факультета: " + location + "\nФакультет: " + faculty.getAbbreviation() + "\n";
        } else {
            return "Id: " + id + "\nНаименование кафедры: " + name + "\nАббревиатура: " + abbreviation
                    + "\nМестоположение факультета: " + location + "\nФакультет: " + faculty + "\n";
        }
    }
}
