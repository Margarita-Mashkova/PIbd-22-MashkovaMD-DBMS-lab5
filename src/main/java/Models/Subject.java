package Models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "subject",indexes = { @Index(name = "IDX_MYIDX_subject", columnList = "id, name") })
public class Subject {
    @Id
    @SequenceGenerator(name="identifier", sequenceName="seq_subject", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="identifier")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Teacher teacher;

    @OneToMany(mappedBy = "subject")
    Set<Session_report> ratings;

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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void nullificationTeacher(){
        this.teacher = null;
    }

    @Override
    public String toString(){
        if(teacher != null) {
            return "Id: " + id + "\nНаименование предемета: " + name + "\nПреподаватель: " + teacher.getName() + "\n";
        }
        else  return "Id: " + id + "\nНаименование предемета: " + name + "\nПреподаватель: " + teacher + "\n";
    }
}
    /*@ManyToMany(mappedBy = "subjects", fetch = FetchType.EAGER)
    private List<Student> students;*/