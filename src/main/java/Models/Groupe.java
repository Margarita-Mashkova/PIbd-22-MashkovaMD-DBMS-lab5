package Models;

import javax.persistence.*;

@Entity
@Table(name = "groupe",indexes = { @Index(name = "IDX_MYIDX_groupe", columnList = "id, name") })
public class Groupe {
    @Id
    @SequenceGenerator(name = "identifier", sequenceName = "seq_group", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "identifier")
    private int id;

    @Column(name = "name")
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

    public void nullificationCathedra() {
        this.cathedra = null;
    }

    @Override
    public String toString() {
        if (cathedra != null) {
            return "Id: " + id + "\nНаименование группы: " + name + "\nКафедра: " + cathedra.getName() + "\n";
        } else {
            return "Id: " + id + "\nНаименование группы: " + name + "\nКафедра: " + cathedra + "\n";
        }
    }
}
