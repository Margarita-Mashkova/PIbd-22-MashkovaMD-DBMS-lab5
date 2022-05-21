package BusinessLogic;

import Models.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SessionReportLogic {
    private Session session;

    public SessionReportLogic(Session session) {
        this.session = session;
    }

    public Session_report getRate(SessionReportKey key) {
        return session.get(Session_report.class, key);
    }

    public List<Session_report> readRates(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Session_report> query = builder.createQuery(Session_report.class);
        Root<Session_report> root = query.from(Session_report.class);

        query.select(root);

        List<Session_report> list = session.createQuery(query).getResultList();
        return list;
    }

    public void createRate(SessionReportKey sessionReportKey, int rate){
        Transaction transaction = session.beginTransaction();

        Session_report rateStr = new Session_report();

        StudentLogic studentLogic = new StudentLogic(session);
        Student student = studentLogic.getStudent(sessionReportKey.getStudentId());
        rateStr.setStudent(student);

        SubjectLogic subjectLogic = new SubjectLogic(session);
        Subject subject = subjectLogic.getSubject(sessionReportKey.getSubjectId());
        rateStr.setSubject(subject);

        rateStr.setRate(rate);
        session.save(rate);

        transaction.commit();
    }

    public void deleteRate(SessionReportKey sessionReportKey){
        Transaction transaction = session.beginTransaction();
        session.delete(getRate(sessionReportKey));
        transaction.commit();
    }

    public void updateRate(SessionReportKey sessionReportKey, int rate){
        Transaction transaction = session.beginTransaction();

        Session_report rateStr = getRate(sessionReportKey);
        rateStr.setId(sessionReportKey);
        rateStr.setRate(rate);
        session.update(rateStr);

        transaction.commit();
    }
}
