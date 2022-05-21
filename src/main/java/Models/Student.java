package Models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "student",indexes = { @Index(name = "IDX_MYIDX_student", columnList = "id, surname_name_patronymic") })
public class Student {
    @Id
    @SequenceGenerator(name="identifier", sequenceName="seq_student", allocationSize=1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="identifier")
    private int id;

    @Column(name = "surname_name_patronymic")
    private String FIO;

    @Column(name = "matrial_status")
    private Boolean matrial_status;

    @Column(name = "hostel_status")
    private Boolean hostel_status;

    @Column(name = "average_ball", columnDefinition = "double default 0.0")
    private double average_ball;

    @Column(name = "grants", columnDefinition = "integer default 0")
    private int grants;

    @ManyToOne
    private Groupe groupe;

    @OneToMany(mappedBy = "student")
    Set<Session_report> ratings;

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "session_report",
            joinColumns = @JoinColumn(name = "student_fk"),
            inverseJoinColumns = @JoinColumn(name = "subject_fk"))
    private List<Subject> subjects;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public Boolean getMatrial_status() {
        return matrial_status;
    }

    public String getMatrial_statusDTO() {
        if(matrial_status){
            return "Состоит в браке";
        }
        else
            return "Не состоит в браке";
    }

    public void setMatrial_status(Boolean matrial_status) {
        this.matrial_status = matrial_status;
    }

    public Boolean getHostel_status() {
        return hostel_status;
    }

    public String getHostel_statusDTO() {
        if(hostel_status){
            return "Да";
        }
        else
        return "Нет";
    }

    public void setHostel_status(Boolean hostel_status) {
        this.hostel_status = hostel_status;
    }

    public Double getAverage_ball() {
        return average_ball;
    }

    public void setAverage_ball(Double average_ball) {
        this.average_ball = average_ball;
    }

    public int getGrants() {
        return grants;
    }

    public void setGrants(int grants) {
        this.grants = grants;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public void nullificationGroupe(){
        this.groupe = null;
    }

    @Override
    public String toString(){
        if(groupe!=null && matrial_status!=null && hostel_status!=null) {
            return "Id: " + id + "\nФИО: " + FIO + "\nГруппа: " + groupe.getName()
                    + "\nСемейное положение: " + this.getMatrial_statusDTO()
                    + "\nПроживание в общежитии: " + this.getHostel_statusDTO()
                    + "\nСредний балл за сессию: " + average_ball
                    + "\nРазмер стипендии: " + grants + "\n";
        }
        else {return "Id: " + id + "\nФИО: " + FIO + "\nГруппа: " + groupe
                + "\nСемейное положение: " + matrial_status
                + "\nПроживание в общежитии: " + hostel_status
                + "\nСредний балл за сессию: " + average_ball
                + "\nРазмер стипендии: " + grants + "\n";}
    }
}