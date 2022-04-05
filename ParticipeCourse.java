package tracker;

public class ParticipeCourse{
    Student student;
    Course cours;
    double nunberOfPoint;

    public ParticipeCourse(Student student, Course cours, int nunberOfPoint) {
        this.student = student;
        this.cours = cours;
        this.nunberOfPoint = nunberOfPoint;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCours() {
        return cours;
    }

    public double getNunberOfPoint() {
        return nunberOfPoint;
    }

    public int getCourseOrder(){

        return this.cours.getOrder();
    }
}
