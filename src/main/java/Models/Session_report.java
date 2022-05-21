package Models;

import javax.persistence.*;

@Entity
public class Session_report {
    @EmbeddedId
    SessionReportKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id")
    Subject subject;

    int rate;

    public SessionReportKey getId() {
        return id;
    }

    public void setId(SessionReportKey id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString(){
        return "Студент: " + student.getFIO() + " Предмет: " + subject.getName() + " Оценка: " + rate;
    }
}
