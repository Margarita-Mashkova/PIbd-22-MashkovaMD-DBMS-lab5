package BusinessLogic;

import Models.Cathedra;
import Models.Faculty;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FacultyLogic {
    private Session session;

    public FacultyLogic(Session session){
        this.session=session;
    }

    public Faculty getFaculty(int id) {
        return session.get(Faculty.class, id);
    }

    public List<Faculty> readFaculty(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Faculty> query = builder.createQuery(Faculty.class);
        Root<Faculty> root = query.from(Faculty.class);

        query.select(root);

        List<Faculty> list = session.createQuery(query).getResultList();
        return list;
    }

    public void createFaculty(String name, String abbreviation){
        Transaction transaction = session.beginTransaction();

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setAbbreviation(abbreviation);
        session.save(faculty);

        transaction.commit();
    }

    public void deleteFaculty(int id){
        Transaction transaction = session.beginTransaction();
        CathedraLogic cathedraLogic = new CathedraLogic(session);
        List<Cathedra> cathedras = cathedraLogic.readCathedras();
        cathedras.stream().filter(e->e.getFaculty().equals(getFaculty(id))).forEach(Cathedra::nullificationFaculty);
        session.delete(getFaculty(id));
        transaction.commit();
    }

    public void updatePosition(int id, String name, String abbreviation){
        Transaction transaction = session.beginTransaction();

        Faculty faculty = getFaculty(id);
        faculty.setName(name);
        faculty.setAbbreviation(abbreviation);
        session.update(faculty);

        transaction.commit();
    }
}
