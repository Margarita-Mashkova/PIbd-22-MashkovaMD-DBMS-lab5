package BusinessLogic;

import Models.Subject;
import Models.Teacher;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SubjectLogic {
    private Session session;

    public SubjectLogic(Session session) {
        this.session = session;
    }

    public Subject getSubject(int id) {
        return session.get(Subject.class, id);
    }

    public List<Subject> readSubjects(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> query = builder.createQuery(Subject.class);
        Root<Subject> root = query.from(Subject.class);

        query.select(root);

        List<Subject> list = session.createQuery(query).getResultList();
        return list;
    }

    public void createSubject(String name, int teacherId){
        Transaction transaction = session.beginTransaction();

        Subject subject = new Subject();
        subject.setName(name);
        TeacherLogic teacherLogic = new TeacherLogic(session);
        Teacher teacherAdd = teacherLogic.getTeacher(teacherId);
        subject.setTeacher(teacherAdd);
        session.save(subject);

        transaction.commit();
    }

    public void deleteSubject(int id){
        Transaction transaction = session.beginTransaction();

        //TODO: delete strings
        /*SessionReportLogic srLogic = new SessionReportLogic(session);
        List<Session_report> sr = srLogic.readRates();
        for (Session_report str: sr) {
            if(str.getStudent().getId() == id)
            srLogic.deleteRate()
        };*/

        session.delete(getSubject(id));
        transaction.commit();
    }

    public void updateSubject(int id, String name, int teacherId){
        Transaction transaction = session.beginTransaction();

        Subject subject = getSubject(id);
        subject.setName(name);
        TeacherLogic teacherLogic = new TeacherLogic(session);
        Teacher teacherAdd = teacherLogic.getTeacher(teacherId);
        subject.setTeacher(teacherAdd);
        session.update(subject);

        transaction.commit();
    }
}
