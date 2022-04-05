package tracker;

import java.util.*;
import java.util.stream.Collectors;

public class StudentRepository {

    private static Set<String> setEmail = new HashSet<>();

    private static Map<Integer, Student> students = new HashMap<>();

    private static int compteur = 0;

    public static void addStudent(Student student){
        compteur++;
        student.setId(compteur);
        students.put(compteur, student);
        setEmail.add(student.getEmail());
    }

    public static boolean eMailAlreadyExist(String email){
        return setEmail.contains(email);
    }

    public static Map<Integer, Student> getStudents() {
        return students;
    }

    public static Student findStudentByID(int id){

        Student student = null;

        if(students.containsKey(id)){
            student = students.get(id);
        }

        return student;

    }

    public static List<Student> studentsList() {

        return new ArrayList<>(students.values());
    }

    public static Map<Course, Integer> studentCoursesNote(Student student, List<Course> courses){


        Map<Course, Integer> hm = new HashMap<>();

        for (Course course : courses) {
            hm.put(course, 0);
        }

        for(Map.Entry<Integer, List<Integer>> m : student.getCoursesStudent().entrySet()){

            for (Course course : courses) {

                int newNote = m.getValue().get(course.getOrder());

                int cumul = hm.get(course);

                hm.put(course, cumul+newNote);

            }

        }

        return hm;
    }

    public static Map<Course, Integer> studentCoursesNb(Student student, List<Course> courses){

        Map<Course, Integer> hm = new HashMap<>();

        for (Course course : courses) {
            hm.put(course, 0);
        }

        for(Map.Entry<Integer, List<Integer>> m : student.getCoursesStudent().entrySet()){

            for (Course course : courses) {

                if(m.getValue().get(course.getOrder()) != 0){

                    int cumul = hm.get(course);

                    hm.put(course, cumul + 1);

                }

            }

        }

        return hm;
    }
}
