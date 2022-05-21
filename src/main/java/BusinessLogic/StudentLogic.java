package BusinessLogic;

import Models.Groupe;
import Models.Session_report;
import Models.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class StudentLogic {
    private Session session;

    public StudentLogic(Session session) {
        this.session = session;
    }

    public Student getStudent(int id) {
        return session.get(Student.class, id);
    }

    public List<Student> readStudents(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> query = builder.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);

        query.select(root);

        List<Student> list = session.createQuery(query).getResultList();
        return list;
    }

    public void createStudent(String FIO, int groupeId, Boolean matrial_status, Boolean hostel_status){
        Transaction transaction = session.beginTransaction();

        Student student = new Student();
        student.setFIO(FIO);
        GroupeLogic groupeLogic = new GroupeLogic(session);
        Groupe groupeAdd = groupeLogic.getGroupe(groupeId);
        student.setGroupe(groupeAdd);
        student.setMatrial_status(matrial_status);
        student.setHostel_status(hostel_status);
        session.save(student);

        transaction.commit();
    }

    public void deleteStudent(int id){
        Transaction transaction = session.beginTransaction();

        SessionReportLogic srLogic = new SessionReportLogic(session);
        List<Session_report> sr = srLogic.readRates();
        //TODO: delete strings
        /*
        SessionReportKey sessionReportKey = null;
        for (Session_report str: sr) {
            int studentId = str.getId().getStudentId();
            if(studentId == id)
                sessionReportKey = new SessionReportKey();
                sessionReportKey.setStudentId(id);
            srLogic.deleteRate(sessionReportKey);
        };*/

        session.delete(getStudent(id));
        transaction.commit();
    }

    public void updateStudent(int id, String FIO, Boolean matrial_status, Boolean hostel_status, int groupeId){
        Transaction transaction = session.beginTransaction();

        Student student = getStudent(id);
        student.setFIO(FIO);
        GroupeLogic groupeLogic = new GroupeLogic(session);
        Groupe groupeAdd = groupeLogic.getGroupe(groupeId);
        student.setGroupe(groupeAdd);
        student.setHostel_status(hostel_status);
        student.setMatrial_status(matrial_status);
        session.update(student);

        transaction.commit();
    }
}
