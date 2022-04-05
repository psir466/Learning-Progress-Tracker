package tracker;

import java.util.Objects;

public class Course implements Comparable<Course> {

    private String name;
    private String codeName;
    int order;
    double point;

    public Course(String name, String codeName, int order, int point) {
        this.name = name;
        this.codeName = codeName;
        this.order = order;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public double getPoint() {
        return point;
    }

    public String getCodeName() {
        return codeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Course course) {

        return this.getOrder() - course.getOrder();
    }
}
