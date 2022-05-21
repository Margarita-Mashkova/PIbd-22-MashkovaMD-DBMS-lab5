import BusinessLogic.*;
import Models.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import queryDTO.*;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    Session session;

    public ConsoleUI(Session session) {
        this.session = session;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            String[] params = input.split(" ", 2);
            switch (params[0]) {
                case ("главная"):
                    mainForm();
                    break;
                //case ("руководство") -> printManual();
                case ("справочник"):
                    printHandBooks(params[1]);
                    break;
                case ("faculty"):
                    doFacultyAction(params[1]);
                    break;
                case ("cathedra"):
                    doCathedraAction(params[1]);
                    break;
                case ("groupe"):
                    doGroupAction(params[1]);
                    break;
                case ("student"):
                    doStudentAction(params[1]);
                    break;
                case ("subject"):
                    doSubjectAction(params[1]);
                    break;
                case ("teacher"):
                    doTeacherAction(params[1]);
                    break;
                case ("calc"):
                    Calculation(params[1]);
                    break;
                case ("выход"):
                    session.close();
                    //case ("rate") -> doRateAction(params[1]);
                    //case ("запрос") -> executeFreeSqlQuery();
                default:
                    throw new IllegalArgumentException();
            }
        } catch (PersistenceException e) {
            System.out.println("В полях сущности указаны неверные значения");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка ввода данных");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Вы ввели недостаточное количество аргументов");
        } catch (NullPointerException e) {
            System.out.println("Запрашиваемых данных не существует");
        } catch (Exception e) {
            System.out.println("Неизвестная ошибка");
        }
    }

    private void Calculation(String param) {
        if ("avg".equals(param)) {
            String sql = "CALL CALCULATE_AVGBALL();";
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            doStudentAction("read");
            System.out.println("Средний балл посчитан");
        }
        else if ("grants".equals(param)) {
            String sql = "CALL CALCULATE_GRANTS();";
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            doStudentAction("read");
            System.out.println("Стипендии посчитаны");
        }else {
            throw new IllegalArgumentException();
        }
    }

    public void mainForm() {
        String sql = "SELECT faculty.abbreviation AS \"Факультет\",\n" +
                "\tcathedra.abbreviation AS \"Кафедра\",\n" +
                "\tgroupe.name AS \"Группа\",\n" +
                "\tsurname_name_patronymic AS \"ФИО студента\",\n" +
                "\taverage_ball AS \"Средний балл за сессию\",\n" +
                "\tgrants AS \"Размер стипендии\"\n" +
                "FROM student JOIN groupe\n" +
                "\tON student.groupe_id = groupe.id\n" +
                "JOIN cathedra\n" +
                "\tON cathedra.id = groupe.cathedra_id\n" +
                "JOIN faculty\n" +
                "\tON faculty.id = cathedra.faculty_id\n";

        NativeQuery query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--- Главная форма ---");
        for (Object[] obj : queryResultRows) {
            MainFormDto mainFormDTO = new MainFormDto();
            mainFormDTO.setFacultyName((String) obj[0]);
            mainFormDTO.setCathedraName((String) obj[1]);
            mainFormDTO.setGroupName((String) obj[2]);
            mainFormDTO.setStudentFIO((String) obj[3]);
            mainFormDTO.setAverageBall((Double) obj[4]);
            mainFormDTO.setGrants((int) obj[5]);
            System.out.println(mainFormDTO.toString());
        }
        System.out.println("--- Главная форма ---");
    }

    private void doTeacherAction(String action) {
        TeacherLogic logic = new TeacherLogic(session);
        if ("create".equals(action)) {
            System.out.println("Введите 'Фамилия И.О.;idКафедры'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(";", 2);
            logic.createTeacher(attributes[0], Integer.parseInt(attributes[1]));
            System.out.println("Преподаватель добавлен");
        }
        else if ("read".equals(action)) {
            System.out.println("--- Преподаватели ---");
            List<Teacher> list = logic.readTeachers();
            list.forEach(System.out::println);
            System.out.println("--- Преподаватели ---");
        }
        else if ("update".equals(action)) {
            System.out.println("Введите 'id;Фамилия И.О.;idКафедры'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(";", 3);
            logic.updateTeacher(Integer.parseInt(attributes[0]), attributes[1], Integer.parseInt(attributes[2]));
            System.out.println("Преподаватель обновлен");
        }
        else if ("delete".equals(action)) {
            System.out.println("Введите 'id'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            logic.deleteTeacher(Integer.parseInt(input));
            System.out.println("Преподаватель удален");
        }else {
            throw new IllegalArgumentException();
        }
        if(!action.equals("read")) {
            doTeacherAction("read");
        }
    }

    private void doSubjectAction(String action) {
        SubjectLogic logic = new SubjectLogic(session);
        if ("create".equals(action)) {
            System.out.println("Введите 'название;idПреподавателя'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(";", 2);
            logic.createSubject(attributes[0], Integer.parseInt(attributes[1]));
            System.out.println("Предмет добавлен");
        }
        else if ("read".equals(action)) {
            System.out.println("--- Предметы ---");
            List<Subject> list = logic.readSubjects();
            list.forEach(System.out::println);
            System.out.println("--- Предметы ---");
        }
        else if ("update".equals(action)) {
            System.out.println("Введите 'id;название;idПреподавателя'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(";", 3);
            logic.updateSubject(Integer.parseInt(attributes[0]), attributes[1], Integer.parseInt(attributes[2]));
            System.out.println("Предмет обновлен");
        }
        else if ("delete".equals(action)) {
            System.out.println("Введите 'id'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            logic.deleteSubject(Integer.parseInt(input));
            System.out.println("Предмет удален");
        }else {
            throw new IllegalArgumentException();
        }
        if(!action.equals("read")) {
            doSubjectAction("read");
        }
    }

    private void doStudentAction(String action) {
        StudentLogic logic = new StudentLogic(session);
        if ("create".equals(action)) {
            System.out.println("Введите 'ФИО;idГруппы;Семейное положение (да или нет);Проживание в обжещитии(да или нет)'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(";", 4);

            if (attributes[2].equals("да")) {
                attributes[2] = "true";
            } else {
                attributes[2] = "false";
            }

            if (attributes[3].equals("да")) {
                attributes[3] = "true";
            } else {
                attributes[3] = "false";
            }
            logic.createStudent(attributes[0], Integer.parseInt(attributes[1]), Boolean.parseBoolean(attributes[2]),
                                                                    Boolean.parseBoolean(attributes[3]));
            System.out.println("Студент добавлен");
        } else if ("read".equals(action)) {
            System.out.println("--- Cтуденты ---");
            List<Student> list = logic.readStudents();
            list.forEach(System.out::println);
            System.out.println("--- Cтуденты ---");
        } else if ("update".equals(action)) {
            System.out.println("Введите 'id;ФИО;Семейное положение (да или нет);Проживание в обжещитии(да или нет);idГруппы'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(";", 5);

            if (attributes[2].equals("да")) {
                attributes[2] = "true";
            } else attributes[2] = "false";

            if (attributes[3].equals("да")) {
                attributes[3] = "true";
            } else attributes[3] = "false";
            logic.updateStudent(Integer.parseInt(attributes[0]), attributes[1],
                    Boolean.parseBoolean(attributes[2]), Boolean.parseBoolean(attributes[3]), Integer.parseInt(attributes[4]));
            System.out.println("Студент обновлен");
        } else if ("delete".equals(action)) {
            System.out.println("Введите 'id'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            logic.deleteStudent(Integer.parseInt(input));
            System.out.println("Студент удален");
        } else {
            throw new IllegalArgumentException();
        }
        if (!action.equals("read")) {
            doStudentAction("read");
        }
    }

    private void doGroupAction(String action) {
        GroupeLogic logic = new GroupeLogic(session);
        if ("create".equals(action)) {
            System.out.println("Введите 'название;idКафедры'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(";", 2);
            logic.createGroupe(attributes[0], Integer.parseInt(attributes[1]));
            System.out.println("Группа добавлена");
        }
        else if ("read".equals(action)) {
            System.out.println("--- Группы ---");
            List<Groupe> list = logic.readGroupes();
            list.forEach(System.out::println);
            System.out.println("--- Группы ---");
        }
        else if ("update".equals(action)) {
            System.out.println("Введите 'id;название;idКафедры'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(";", 3);
            logic.updateGroupe(Integer.parseInt(attributes[0]), attributes[1], Integer.parseInt(attributes[2]));
            System.out.println("Группа обновлена");
        }
        else if ("delete".equals(action)) {
            System.out.println("Введите 'id'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            logic.deleteGroupe(Integer.parseInt(input));
            System.out.println("Группа удалена");
        }else {
            throw new IllegalArgumentException();
        }
        if(!action.equals("read")) {
            doGroupAction("read");
        }
    }

    private void doCathedraAction(String action) {
        CathedraLogic logic = new CathedraLogic(session);
        if ("create".equals(action)) {
            System.out.println("Введите 'название;аббревиатура;локация;idФакультета'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(";", 4);
            logic.createCathedra(attributes[0], attributes[1], attributes[2], Integer.parseInt(attributes[3]));
            System.out.println("Кафедра добавлена");
        }
        else if ("read".equals(action)) {
            System.out.println("--- Кафедры ---");
            List<Cathedra> list = logic.readCathedras();
            list.forEach(System.out::println);
            System.out.println("--- Кафедры ---");
        }
        else if ("update".equals(action)) {
            System.out.println("Введите 'id;название;аббревиатура;локация;idФакультета'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(";", 5);
            logic.updateCathedra(Integer.parseInt(attributes[0]), attributes[1], attributes[2], attributes[3],
                                                                                        Integer.parseInt(attributes[4]));
            System.out.println("Кафедра обновлена");
        }
        else if ("delete".equals(action)) {
            System.out.println("Введите 'id'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            logic.deleteCathedra(Integer.parseInt(input));
            System.out.println("Кафедра удалена");
        }else {
            throw new IllegalArgumentException();
        }
        if(!action.equals("read")) {
            doCathedraAction("read");
        }
    }

    private void doFacultyAction(String action) {
        FacultyLogic logic = new FacultyLogic(session);
        if ("create".equals(action)) {
            System.out.println("Введите 'название;аббревиатура'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(";", 2);
            logic.createFaculty(attributes[0], attributes[1]);
            System.out.println("Факультет добавлен");
        }
        else if ("read".equals(action)) {
            System.out.println("--- Факультеты ---");
            List<Faculty> list = logic.readFaculty();
            list.forEach(System.out::println);
            System.out.println("--- Факультеты ---");
        }
        else if ("update".equals(action)) {
            System.out.println("Введите 'id;название;аббревиатура'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] attributes = input.split(";", 3);
            logic.updatePosition(Integer.parseInt(attributes[0]), attributes[1], attributes[2]);
            System.out.println("Факультет обновлен");
        }
        else if ("delete".equals(action)) {
            System.out.println("Введите 'id'");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            logic.deleteFaculty(Integer.parseInt(input));
            System.out.println("Факультет удален");
        }else {
            throw new IllegalArgumentException();
        }
        if(!action.equals("read")) {
            doFacultyAction("read");
        }
    }

    public void printHandBooks(String param){
        try {
            switch (param) {
                case ("факультет") : printFaculties();
                break;
                case ("кафедра") : printCathedras();
                break;
                case ("группа") : printGroups();
                break;
                case ("студент") : printStudents();
                break;
                case ("предмет") : printSubjects();
                break;
                case ("преподаватель") : printTeachers();
                break;
                //case ("rate") -> printRates();
                default : throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Ошибка ввода данных");
        }
        catch (NullPointerException e){
            System.out.println("Запрашиваемых данных не существует");
        }
        catch (Exception e){
            System.out.println("Неизвестная ошибка");
        }
    }

    private void printTeachers() {
        String sql = "SELECT surname_initials AS \"ФИО преподавателя\", \n" +
                "cathedra_id AS \"Id кафедры\"\n" +
                "FROM teacher\n";

        NativeQuery query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--Справочник \"Преподаватель\"--");
        for (Object[] obj : queryResultRows) {
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.setTeacherFIO((String) obj[0]);
            teacherDto.setCathedraId((int) obj[1]);
            System.out.println(teacherDto.toString());
        }
        System.out.println();
    }

    private void printSubjects() {
        String sql = "SELECT name AS \"Наименование предмета\", \n" +
                "teacher_id AS \"Id преподавателя\"\n" +
                "FROM subject\n";

        NativeQuery query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--Справочник \"Предмет\"--");
        for (Object[] obj : queryResultRows) {
            SubjectDto subjectDto = new SubjectDto();
            subjectDto.setSubjectName((String) obj[0]);
            subjectDto.setTeacherId((int) obj[1]);
            System.out.println(subjectDto.toString());
        }
        System.out.println();
    }

    private void printStudents() {
        String sql = "SELECT surname_name_patronymic as \"ФИО\", \n" +
                "matrial_status AS \"Семейное положение\", \n" +
                "hostel_status AS \"Проживание в обжещитии\",\n" +
                "average_ball AS \"Средний балл за сессию\", \n" +
                "grants AS \"Размер стипендии\",\n" +
                "group_id AS \"Id группы\"\n" +
                "FROM student;\n";

        NativeQuery query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--Справочник \"Студент\"--");
        for (Object[] obj : queryResultRows) {
            StudentDto studentDto = new StudentDto();
            studentDto.setFIO((String) obj[0]);
            studentDto.setMatrialStatus((Boolean) obj[1]);
            studentDto.setHostelStatus((Boolean) obj[2]);
            studentDto.setAverageBall((Double) obj[3]);
            studentDto.setGrants((int) obj[4]);
            studentDto.setGroupeId((int) obj[5]);
            System.out.println(studentDto.toString());
        }
        System.out.println();
    }

    private void printGroups() {
        String sql = "SELECT name AS \"Наименование группы\", \n" +
                "cathedra_id AS \"Id кафедры\"\n" +
                "FROM groupe\n";

        NativeQuery query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--Справочник \"Группа\"--");
        for (Object[] obj : queryResultRows) {
            GroupDto groupDto = new GroupDto();
            groupDto.setGroupName((String) obj[0]);
            groupDto.setCathedraId((int) obj[1]);
            System.out.println(groupDto.toString());
        }
        System.out.println();
    }

    private void printCathedras() {
        String sql = "SELECT name AS \"Наименование кафедры\", \n" +
                "abbreviation AS \"Абрревиатура\",\n" +
                "location AS \"Местоположение факультета\",\n" +
                "faculty_id AS \"Id факультета\"\n" +
                "FROM cathedra\n";

        NativeQuery query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--Справочник \"Кафедра\"--");
        for (Object[] obj : queryResultRows) {
            CathedraDto cathedraDto = new CathedraDto();
            cathedraDto.setCathedraName((String) obj[0]);
            cathedraDto.setAbbreviation((String) obj[1]);
            cathedraDto.setLocation((String) obj[2]);
            cathedraDto.setFacultyId((int) obj[3]);
            System.out.println(cathedraDto.toString());
        }
        System.out.println();
    }

    private void printFaculties() {
        String sql = "SELECT name AS \"Наименование факультета\", \n" +
                "abbreviation AS \"Абрревиатура\"\n" +
                "FROM faculty\n";

        NativeQuery query = session.createSQLQuery(sql);
        List<Object[]> queryResultRows = query.list();
        System.out.println();
        System.out.println("--Справочник \"Факультет\"--");
        for (Object[] obj : queryResultRows) {
            FacultyDto facultyDto = new FacultyDto();
            facultyDto.setFacultyName((String) obj[0]);
            facultyDto.setAbbreviation((String) obj[1]);
            System.out.println(facultyDto.toString());
        }
        System.out.println();
    }
}