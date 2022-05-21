package BusinessLogic;

import Models.Cathedra;
import Models.Faculty;
import Models.Groupe;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CathedraLogic {
    private Session session;

    public CathedraLogic(Session session) {
        this.session = session;
    }

    public Cathedra getCathedra(int id) {
        return session.get(Cathedra.class, id);
    }

    public List<Cathedra> readCathedras(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Cathedra> query = builder.createQuery(Cathedra.class);
        Root<Cathedra> root = query.from(Cathedra.class);

        query.select(root);

        List<Cathedra> list = session.createQuery(query).getResultList();
        return list;
    }

    public void createCathedra(String name, String abbreviation, String location, int facultyId){
        Transaction transaction = session.beginTransaction();

        Cathedra cathedra = new Cathedra();
        cathedra.setName(name);
        cathedra.setAbbreviation(abbreviation);
        cathedra.setLocation(location);
        FacultyLogic facultyLogic = new FacultyLogic(session);
        Faculty facultyAdd = facultyLogic.getFaculty(facultyId);
        cathedra.setFaculty(facultyAdd);
        session.save(cathedra);

        transaction.commit();
    }

    public void deleteCathedra(int id){
        Transaction transaction = session.beginTransaction();
        GroupeLogic groupeLogic = new GroupeLogic(session);
        List<Groupe> groupes = groupeLogic.readGroupes();
        groupes.stream().filter(e->e.getCathedra().equals(getCathedra(id))).forEach(Groupe::nullificationCathedra);
        session.delete(getCathedra(id));
        transaction.commit();
    }

    public void updateCathedra(int id, String name, String abbreviation, String location, int facultyId){
        Transaction transaction = session.beginTransaction();

        Cathedra cathedra = getCathedra(id);
        cathedra.setName(name);
        cathedra.setAbbreviation(abbreviation);
        cathedra.setLocation(location);
        FacultyLogic facultyLogic = new FacultyLogic(session);
        Faculty facultyAdd = facultyLogic.getFaculty(facultyId);
        cathedra.setFaculty(facultyAdd);
        session.update(cathedra);

        transaction.commit();
    }

    /*public void addFaculty(int cathedraId, int facultyId){
        Transaction transaction = session.beginTransaction();

        Cathedra cathedra = getCathedra(cathedraId);
        Faculty faculty = session.get(Faculty.class, facultyId);
        cathedra.setFaculty(faculty);
        session.update(cathedra);
        session.update(faculty);

        transaction.commit();
    }*/
}
