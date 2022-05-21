package Models;

import javax.persistence.*;

@Entity
@Table(name = "teacher",indexes = { @Index(name = "IDX_MYIDX_teacher", columnList = "id, surname_initials") })
public class Teacher {
    @Id
    @SequenceGenerator(name = "identifier", sequenceName = "seq_teacher", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "identifier")
    private int id;

    @Column(name = "surname_initials")
    private String name;

    @ManyToOne
    private Cathedra cathedra;

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

    public Cathedra getCathedra() {
        return cathedra;
    }

    public void setCathedra(Cathedra cathedra) {
        this.cathedra = cathedra;
    }

    @Override
    public String toString() {
        if (cathedra != null) {
            return "Id: " + id + "\nФИО: " + name + "\nКафедра: " + cathedra.getName() + "\n";
        } else {
            return "Id: " + id + "\nФИО: " + name + "\nКафедра: " + cathedra + "\n";
        }
    }
}
