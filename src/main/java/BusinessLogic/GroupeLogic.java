package BusinessLogic;

import Models.Cathedra;
import Models.Groupe;
import Models.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class GroupeLogic {
    private Session session;

    public GroupeLogic(Session session) {
        this.session = session;
    }

    public Groupe getGroupe(int id) {
        return session.get(Groupe.class, id);
    }

    public List<Groupe> readGroupes(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Groupe> query = builder.createQuery(Groupe.class);
        Root<Groupe> root = query.from(Groupe.class);

        query.select(root);

        List<Groupe> list = session.createQuery(query).getResultList();
        return list;
    }

    public void createGroupe(String name, int cathedraId){
        Transaction transaction = session.beginTransaction();

        Groupe groupe = new Groupe();
        groupe.setName(name);
        CathedraLogic cathedraLogic = new CathedraLogic(session);
        Cathedra cathedraAdd = cathedraLogic.getCathedra(cathedraId);
        groupe.setCathedra(cathedraAdd);
        session.save(groupe);

        transaction.commit();
    }

    public void deleteGroupe(int id){
        Transaction transaction = session.beginTransaction();
        StudentLogic studentLogic = new StudentLogic(session);
        List<Student> students = studentLogic.readStudents();
        students.stream().filter(e->e.getGroupe().equals(getGroupe(id))).forEach(Student::nullificationGroupe);
        session.delete(getGroupe(id));
        transaction.commit();
    }

    public void updateGroupe(int id, String name, int cathedraId){
        Transaction transaction = session.beginTransaction();

        Groupe groupe = getGroupe(id);
        groupe.setName(name);
        CathedraLogic cathedraLogic = new CathedraLogic(session);
        Cathedra cathedraAdd = cathedraLogic.getCathedra(cathedraId);
        groupe.setCathedra(cathedraAdd);
        session.update(groupe);

        transaction.commit();
    }
}
