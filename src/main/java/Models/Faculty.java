package Models;

import javax.persistence.*;

@Entity
@Table(name = "faculty",indexes = { @Index(name = "IDX_MYIDX_faculty", columnList = "id, abbreviation") })
public class Faculty {
    @Id
    @SequenceGenerator(name="identifier", sequenceName="seq_faculty", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

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

    @Override
    public String toString() {
        return id + "\nНаименование факультета: " + name + "\nАббревиатура: " + abbreviation + "\n";
    }
}
