package tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private Map<Integer,List<Integer>> coursesStudent = new HashMap<>();
    private List<Course> listCoursTerminated = new ArrayList<>();

    public Student() {
    }



    public Student(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, List<Integer>> getCoursesStudent() {
        return coursesStudent;
    }

    public List<Course> getListCoursTerminated() {
        return listCoursTerminated;
    }

    public void setListCoursTerminated(List<Course> listCoursTerminated) {
        this.listCoursTerminated = listCoursTerminated;
    }
}
