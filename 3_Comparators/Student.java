import java.io.File;
import java.util.Scanner;

/**
 * 905560, Fabiola
 * Student class makes a Student object for every student in a directory. Stores student's
 * characteristics including: first name, last name, address, phone, email, and SU Box number.
 * Includes a method getStudentList() to read a File and get student directory as a SortableArrayList which contains
 * each student as an individual Student object.
 */
public class Student {
    /**
     * Student's first name.
     */
    private String firstName;
    /**
     * Student's last name.
     */
    private String lastName;
    /**
     * Student's on-campus address. If currently studying elsewhere, "Off-Campus Study".
     */
    private String address;
    /**
     * Student's phone number.
     */
    private String phone;
    /**
     * Student's Bowdoin email.
     */
    private String email;
    /**
     * Student's SU Box number.
     */
    private int suBox;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public int getSuBox() {
        return suBox;
    }

    public String toString () {
        return String.format("%s %s, %s, %s, %s, %d", firstName, lastName, address, phone, email, suBox);
    }

    public Student (String[] studentDetails) {

        // Loops through all pieces of information for an individual student found
        // in directory to create Student object.
        for (int i = 0; i < studentDetails.length; i++) {
            studentDetails[i] = studentDetails[i].trim();

            if (i == 0) {
                String[] fullName = studentDetails[i].split(" ");
                this.firstName = fullName[0];
                this.lastName = fullName[1];
            }

            if (i==1) this.address = studentDetails[i];
            if (i==2) this.phone = studentDetails [i];
            if (i==3) this.email = studentDetails [i];
            if (i==4) this.suBox = Integer.parseInt(studentDetails [i]);
        }
    }

    /**
     * Scans a file named "directory.txt" containing student directory and reads
     * each individual line containing an individual student's information, calling on
     * the Student constructor to create a new Student object for each student in directory.
     * Once created, adds each Student object with information attributes to a SortableArrayList
     * called studentList.
     * @return studentList, a SortableArrayList containing all students in a directory.
     */
    public static SortableArrayList getStudentList () {
        SortableArrayList studentsList = new SortableArrayList();

        Scanner scan = null;
        try {
            scan = new Scanner(new File("directory.txt"));
        } catch (Exception e) {
            System.out.println("Error! Couldn't read file: " + e.getMessage());
            return studentsList;
        }

        // Until all file contents are read, split up individual parts of each Student's information
        // and use it to create a new Student object for each line. Once object, is created, add
        // to studentList (SortableArrayList):
        while (scan.hasNext()) {
            String line = scan.nextLine();
            String[] studentDetails = line.split("\\|");
            Student student = new Student(studentDetails);
            studentsList.add(student);
        }

        scan.close();
        return studentsList;
    }
}
