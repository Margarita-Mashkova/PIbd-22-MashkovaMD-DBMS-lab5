package BusinessLogic;

import Models.Cathedra;
import Models.Subject;
import Models.Teacher;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TeacherLogic {
    private Session session;

    public TeacherLogic(Session session) {
        this.session = session;
    }

    public Teacher getTeacher(int id) {
        return session.get(Teacher.class, id);
    }

    public List<Teacher> readTeachers(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = builder.createQuery(Teacher.class);
        Root<Teacher> root = query.from(Teacher.class);

        query.select(root);

        List<Teacher> list = session.createQuery(query).getResultList();
        return list;
    }

    public void createTeacher(String name, int cathedraId){
        Transaction transaction = session.beginTransaction();

        Teacher teacher = new Teacher();
        teacher.setName(name);
        CathedraLogic cathedraLogic = new CathedraLogic(session);
        Cathedra cathedraAdd = cathedraLogic.getCathedra(cathedraId);
        teacher.setCathedra(cathedraAdd);
        session.save(teacher);

        transaction.commit();
    }

    public void deleteTeacher(int id){
        Transaction transaction = session.beginTransaction();
        SubjectLogic subjectLogic = new SubjectLogic(session);
        List<Subject> subjects = subjectLogic.readSubjects();
        subjects.stream().filter(e->e.getTeacher().equals(getTeacher(id))).forEach(Subject::nullificationTeacher);
        session.delete(getTeacher(id));
        transaction.commit();
    }

    public void updateTeacher(int id, String name, int cathedraId){
        Transaction transaction = session.beginTransaction();

        Teacher teacher = getTeacher(id);
        teacher.setName(name);
        CathedraLogic cathedraLogic = new CathedraLogic(session);
        Cathedra cathedraAdd = cathedraLogic.getCathedra(cathedraId);
        teacher.setCathedra(cathedraAdd);
        session.update(teacher);

        transaction.commit();
    }
}
