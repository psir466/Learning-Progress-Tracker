package tracker;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Track {

    private static Scanner sc = new Scanner(System.in);


    public static List<Course> courses = new ArrayList<>();


    private static String title = "Learning Progress Tracker";

    private static DecimalFormat df = new DecimalFormat("0.0%");
    private static DecimalFormat dfInt = new DecimalFormat("#");


    public static void track() {

        Track.courses.add(new Course("Java", "JAVA", 0, 600));
        Track.courses.add(new Course("DSA", "DSA", 1, 400));
        Track.courses.add(new Course("Databases", "DATABASES", 2, 480));
        Track.courses.add(new Course("Spring", "SPRING", 3, 550));

        System.out.println(title);

        boolean end = false;

        while (!end) {

            String input = sc.nextLine();

            input = input.toUpperCase();

            if (input.isEmpty() || input.isBlank()) {

                System.out.println("No input.");

            } else {

                switch (input) {

                    case "ADD STUDENTS":
                        addStudent();
                        break;

                    case "EXIT":
                        end = true;
                        System.out.println("Bye!");
                        break;

                    case "BACK":
                        System.out.println("Enter 'exit' to exit the program");
                        break;

                    case "LIST":
                        ListStudent();
                        break;

                    case "ADD POINTS":
                        addPoints();
                        break;

                    case "STATISTICS":
                        statistics();
                        break;

                    case "FIND":
                        find();
                        break;

                    case "NOTIFY":
                        notif();
                        break;

                    default:
                        System.out.println("Error: unknown command!");
                        break;
                }
            }

        }

        sc.close();
    }

    private static void addStudent() {

        System.out.println("Enter student credentials or 'back' to return:");

        boolean end = false;

        int nbStudentAdded = 0;

        boolean tryToAdd = false;

        while (!end) {

            String input = sc.nextLine();


            switch (input) {

                case "BACK":

                case "back":
                    if (nbStudentAdded != 0 || tryToAdd) {
                        System.out.println("Total " + nbStudentAdded + " students have been added.");
                        end = true;
                    } else {
                        System.out.println("Enter 'exit' to exit the program");
                        end = true;
                    }
                    break;

                default:
                    tryToAdd = true;
                    if (addingStudent(input)) {
                        System.out.println("The student has been added.");
                        nbStudentAdded++;
                    }

            }

        }
    }

    private static Boolean checkNameEmail(String nameEmail) {
        boolean nameEmailIsOK = true;

        String[] tab = nameEmail.trim().toLowerCase().split(" ");

        if (tab.length < 3) {
            System.out.println("Incorrect credentials.");
            return false;
        }

        if (tab[0].length() < 2) {
            System.out.println("Incorrect first name.");
            return false;
        }

        if (tab[1].length() < 2) {
            System.out.println("Incorrect last name.");
            return false;
        }

        for (int i = 0; i < tab.length; i++) {

            if (i == 0) {
                // firstname
                nameEmailIsOK = chekName(tab[i]);

                if (!nameEmailIsOK) {
                    System.out.println("Incorrect first name.");
                    return false;
                }
            }

            if (nameEmailIsOK) {
                if (i > 0 && i < tab.length - 1) {

                    nameEmailIsOK = chekName(tab[i]);

                    if (!nameEmailIsOK) {
                        System.out.println("Incorrect last name.");
                        return false;
                    }
                }

            }

            if (nameEmailIsOK) {
                if (i == tab.length - 1) {

                    nameEmailIsOK = checkEmail(tab[i]);

                    if (!nameEmailIsOK) {
                        System.out.println("Incorrect email.");
                        return false;
                    }
                    // véri unicité Email;
                    if (StudentRepository.eMailAlreadyExist(tab[i])) {
                        System.out.println("This email is already taken.");
                        return false;
                    }


                }

            }

        }
        return nameEmailIsOK;
    }

    private static Boolean addingStudent(String nameEmail) {

        String[] tab = nameEmail.trim().split(" ");

        nameEmail = nameEmail.toLowerCase(Locale.ROOT);

        if (checkNameEmail(nameEmail)) {


            Student student = new Student();

            student.setEmail(tab[tab.length - 1]);
            student.setFirstname(tab[0]);

            StringBuilder sb = new StringBuilder();

            for (int i = 1; i <= tab.length - 2; i++) {
                sb.append(tab[i] + " ");

            }

            student.setLastname(sb.toString().trim());

            StudentRepository.addStudent(student);

        } else {
            return false;
        }

        return true;

    }

    private static Boolean checkEmail(String eMail) {

        boolean check;

        String regex = "[a-z0-9.]+@[a-z0-9]+\\.[a-z0-9]{1,10}";

        check = eMail.toLowerCase().matches(regex);


        return check;


    }

    public static Boolean chekName(String name) {

        String regex = "[a-zA-z-']*";

        String regex1 = "-" + regex;
        String regex2 = regex + "-";

        String regex6 = "'" + regex;
        String regex7 = regex + "'";


        String regex3 = "[-'][-']";

        Pattern javaPattern = Pattern.compile(regex3, Pattern.CASE_INSENSITIVE);

        Matcher matcher = javaPattern.matcher(name);


        if (!name.matches(regex)) {
            return false;
        }

        if (name.matches(regex1) || name.matches(regex2)) {
            return false;
        }

        if (name.matches(regex6) || name.matches(regex7)) {
            return false;
        }


        if (matcher.find()) {
            return false;
        }

        return true;
    }

    public static void ListStudent() {


        if (StudentRepository.getStudents().size() > 0) {

            System.out.println("Students:");

            StudentRepository.getStudents().forEach((integer, student) -> System.out.println(integer));
        } else {

            System.out.println("No students found");
        }

    }

    public static void addPoints() {

        System.out.println("Enter an id and points or 'back' to return:");

        boolean end = false;

        while (!end) {

            String input = sc.nextLine();

            switch (input) {

                case "BACK":

                case "back":

                    end = true;

                    break;

                default:

                    addingPoints(input);

            }

        }

    }

    public static void addingPoints(String input) {

        String[] tab = input.trim().split(" ");

        if (tab.length != courses.size() + 1) {
            System.out.println("Incorrect points format.");
            return;
        }

        int id = 0;

        try {
            id = Integer.parseInt(tab[0]);

            Student student = StudentRepository.findStudentByID(id);

            if (student == null) {

                System.out.println("No student is found for id=" + id + ".");

            } else {

                int index = 1;

                boolean pointUpdated = true;

                for (Course course : courses) {

                    try {
                        int points = Integer.parseInt(tab[index]);

                        if (points >= 0) {

                        } else {
                            System.out.println("Incorrect points format.");
                            pointUpdated = false;
                            break;
                        }


                    } catch (NumberFormatException e) {
                        System.out.println("Incorrect points format.");
                        pointUpdated = false;
                        break;
                    }

                    index++;
                }

                index = 1;

                if (pointUpdated) {

                    List<Course> trieCourse = courses.stream().sorted().collect(Collectors.toList());

                    int n = student.getCoursesStudent().size();

                    List<Integer> notes = new ArrayList<>();

                    for (Course course : trieCourse) {

                        notes.add(Integer.parseInt(tab[index]));
                        index++;

                    }

                    student.getCoursesStudent().put(n + 1, notes);

                    System.out.println("Points updated.");
                }
            }


        } catch (NumberFormatException e) {


            System.out.println("No student is found for id=" + tab[0]);
        }


    }

    public static void find() {

        System.out.println("Enter an id or 'back' to return:");

        boolean end = false;

        while (!end) {

            String input = sc.nextLine();


            switch (input) {

                case "BACK":

                case "back":

                    end = true;

                    break;

                default:

                    findingPoints(input);

            }

        }


    }

    public static void findingPoints(String input) {

        try {
            int id = Integer.parseInt(input);

            Student student = StudentRepository.findStudentByID(id);

            if (student == null) {

                System.out.println("No student is found for id=" + id + ".");

            } else {

                StringBuilder sb = new StringBuilder();

                sb.append(id + " points:");

                int index = 0;

                Map<Course, Integer> hm = StudentRepository.studentCoursesNote(student, courses);

                List<Course> courseTrie = courses.stream().sorted().collect(Collectors.toList());

                for (Course course : courseTrie) {

                    sb.append(" " + course.getName() + "=" + hm.get(course));

                    if (index < hm.size()) {
                        sb.append(";");
                    }
                    index++;

                }

                System.out.println(sb.toString());

            }


        } catch (NumberFormatException e) {
            System.out.println("Id is not valid.");
        }

    }

    public static void statistics() {

        System.out.println("Type the name of a course to see details or 'back' to quit:");

        List<ParticipeCourse> listParticipeCourses = new ArrayList<>();

        List<Student> ls = StudentRepository.studentsList();

        Map<Course, List<Double>> stats = new HashMap<>();

        double[] cumulNote = new double[courses.size()];
        double[] nbTotalNote = new double[courses.size()];

        for (Course course : courses) {

            Double d = 0.0;
            List<Double> lsd = new ArrayList<>();
            lsd.add(d);
            lsd.add(d);
            lsd.add(d);
            lsd.add(d);

            stats.put(course, lsd);
        }

        for (Student student : ls) {

            Map<Course, Integer> hm = StudentRepository.studentCoursesNote(student, courses);
            Map<Course, Integer> hmNb = StudentRepository.studentCoursesNb(student, courses);

            for (Course course : courses) {

                // nb étudiant
                if (hm.get(course) != 0) {
                    double n = stats.get(course).get(0);

                    stats.get(course).set(0, ++n);

                    cumulNote[course.getOrder()] = cumulNote[course.getOrder()] + hm.get(course);
                    nbTotalNote[course.getOrder()] = nbTotalNote[course.getOrder()] + 1;

                    listParticipeCourses.add(new ParticipeCourse(student, course, hm.get(course)));
                }

                // Popularité
                int nb = hmNb.get(course);
                if (nb != 0) {
                    double n = stats.get(course).get(1);

                    stats.get(course).set(1, n + nb);

                }

            }


        }

        for (Course course : courses) {

            stats.get(course).set(2, cumulNote[course.getOrder()] / nbTotalNote[course.getOrder()]);
        }


        List<String> courseMostPopular = new ArrayList<>();
        List<String> courseLeastPopular = new ArrayList<>();
        List<String> courseHighestActivity = new ArrayList<>();
        List<String> courseLowestActivity = new ArrayList<>();
        List<String> courseEasiest = new ArrayList<>();
        List<String> courseHardest = new ArrayList<>();

        double nbStudentMostPopular = 0;
        double nbStudentLeastPopular = 99999999999999999.99;
        double pointMostPopular = 0;
        double pointLeastPopular = 9999999999999999999.99;
        double averageEasiest = 0;
        double averageHardest = 99999999999999999999.99;

        for (Map.Entry<Course, List<Double>> entry : stats.entrySet()) {

            if (entry.getValue().get(0) >= nbStudentMostPopular) {

                if (entry.getValue().get(0) > nbStudentMostPopular) {
                    courseMostPopular.clear();
                }

                courseMostPopular.add(entry.getKey().getName());
                nbStudentMostPopular = entry.getValue().get(0);
            }

            if (entry.getValue().get(0) <= nbStudentLeastPopular) {

                if (entry.getValue().get(0) < nbStudentLeastPopular) {
                    courseLeastPopular.clear();
                }

                courseLeastPopular.add(entry.getKey().getName());
                nbStudentLeastPopular = entry.getValue().get(0);
            }

            if (entry.getValue().get(1) >= pointMostPopular) {

                if (entry.getValue().get(1) > pointMostPopular) {
                    courseHighestActivity.clear();
                }
                courseHighestActivity.add(entry.getKey().getName());
                pointMostPopular = entry.getValue().get(1);
            }

            if (entry.getValue().get(1) <= pointLeastPopular) {

                if (entry.getValue().get(1) < pointLeastPopular) {
                    courseLowestActivity.clear();
                }

                courseLowestActivity.add(entry.getKey().getName());
                pointLeastPopular = entry.getValue().get(1);
            }

            if (entry.getValue().get(2) >= averageEasiest) {

                if (entry.getValue().get(2) > averageEasiest) {
                    courseEasiest.clear();
                }
                courseEasiest.add(entry.getKey().getName());
                averageEasiest = entry.getValue().get(2);
            }

            if (entry.getValue().get(2) <= averageHardest) {

                if (entry.getValue().get(2) < averageHardest) {
                    courseHardest.clear();
                }

                courseHardest.add(entry.getKey().getName());
                averageHardest = entry.getValue().get(2);
            }

        }


        courseLeastPopular.removeAll(courseMostPopular);
        courseEasiest.removeAll(courseHardest);
        courseLowestActivity.removeAll(courseHighestActivity);

        if (ls.size() != 0) {

            if (courseMostPopular.size() != 0) {
                System.out.println("Most popular:" + toListString(courseMostPopular));
            } else {
                System.out.println("Most popular: n/a");
            }
            if (courseLeastPopular.size() != 0) {
                System.out.println("Least popular:" + toListString(courseLeastPopular));
            } else {
                System.out.println("Least popular: n/a");
            }
            if (courseHighestActivity.size() != 0) {
                System.out.println("Highest activity:" + toListString(courseHighestActivity));
            } else {
                System.out.println("Highest activity: n/a");
            }

            if (courseLowestActivity.size() != 0) {
                System.out.println("Lowest activity:" + toListString(courseLowestActivity));
            } else {
                System.out.println("Lowest activity: n/a");
            }

            if (courseEasiest.size() != 0) {
                System.out.println("Easiest course:" + toListString(courseEasiest));
            } else {
                System.out.println("Easiest course: : n/a");
            }
            if (courseHardest.size() != 0) {
                System.out.println("Hardest course:" + toListString(courseHardest));
            } else {
                System.out.println("Hardest course: n/a");
            }
        } else {
            System.out.println("Most popular: n/a");

            System.out.println("Least popular: n/a");

            System.out.println("Highest activity: n/a");

            System.out.println("Lowest activity: n/a");

            System.out.println("Easiest course: : n/a");

            System.out.println("Hardest course: n/a");

        }

        boolean end = false;

        while (!end) {

            String input = sc.nextLine();


            input = input.toUpperCase(Locale.ROOT);

            if (input.equals("BACK")) {
                end = true;
            } else {


                String finalInput = input;
                Optional<Course> optc = courses.stream().filter(c -> c.getCodeName().equals(finalInput)).findFirst();

                if (optc.isEmpty()) {
                    System.out.println("Unknown course.");
                } else {

                    System.out.println(optc.get().getName());

                    System.out.println("id    points    completed");

                    List<ParticipeCourse> lp = listDetailCourse(optc.get(), listParticipeCourses);

                    for (ParticipeCourse p : lp) {

                        double percentageCompleted = p.getNunberOfPoint() / p.getCours().getPoint();

                        int nbpoint = (int) p.getNunberOfPoint();

                        df.setRoundingMode(RoundingMode.HALF_UP);

                        System.out.println(String.format("%-5d %-6d    %-6s",
                                p.getStudent().getId(),
                                nbpoint,
                                df.format(percentageCompleted)));

                    }

                }
            }


        }

    }

    private static String toListString(List<String> l) {

        StringBuilder sb = new StringBuilder();

        int index = 0;
        for (String str : l) {

            sb.append(" " + str);

            index++;

            if (index < l.size()) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    private static List<ParticipeCourse> listDetailCourse(Course course, List<ParticipeCourse> listParticipeCourses) {

        return listParticipeCourses.stream()
                .sorted(new Comparator<ParticipeCourse>() {
                    @Override
                    public int compare(ParticipeCourse p1, ParticipeCourse p2) {

                        if (p1.getCours().getOrder() != p2.getCours().getOrder()) {
                            return p1.getCours().getOrder() - p2.getCours().getOrder();
                        }

                        // trie inverse sur nombre de point

                        if (p2.getNunberOfPoint() == p1.getNunberOfPoint()) {
                            return 0;
                        }


                        return p2.getNunberOfPoint() > p1.getNunberOfPoint() ? 1 : -1;
                    }
                })
                .filter(p -> p.getCours().equals(course))
                .collect(Collectors.toList());

    }

    private static void notif() {

        int nbNotifyStudent = 0;

        List<Student> ls = StudentRepository.studentsList();

        List<ParticipeCourse> notifyList = new ArrayList<>();

        for (Student student : ls) {

            boolean studentNotify = false;

            Map<Course, Integer> hm = StudentRepository.studentCoursesNote(student, courses);

            for (Map.Entry<Course, Integer> entry : hm.entrySet()) {

                if (entry.getValue() >= entry.getKey().getPoint() &&
                        !student.getListCoursTerminated().contains(entry.getKey())) {

                    student.getListCoursTerminated().add(entry.getKey());

                    notifyList.add(new ParticipeCourse(student, entry.getKey(), 0));


                    studentNotify = true;

                }

            }

            if (studentNotify) {

                notifyList.stream().sorted(new Comparator<ParticipeCourse>() {
                    @Override
                    public int compare(ParticipeCourse p1, ParticipeCourse p2) {

                        return p1.getCours().getOrder() - p2.getCours().getOrder();

                    }
                }).forEach( p -> {

                            String name = p.getStudent().getFirstname() + " " + p.getStudent().getLastname();

                            System.out.println(String.format("To: %s", p.getStudent().getEmail()));
                            System.out.println("Re: Your Learning Progress");
                            System.out.println(String.format("Hello %s! You have accomplished our " +
                                            "%s course!", name,
                                    p.getCours().getName()));
                        });

                nbNotifyStudent++;
            }

        }

        System.out.println(String.format("Total %d students have been notified", nbNotifyStudent));

    }

}
