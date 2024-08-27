//Name: Usman Khan & Kevin Mehdinia
//Course: COP3330-0V03
//Assignment: FINAL PROJECT
//Date: 12/1/2023

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//  class for person that will be used to either create a faculty, student, or staff
abstract class Person implements Comparable<Person> {
  String fullName; 
  String id;

  public Person(String fullName, String id) {
    this.fullName = fullName;
    this.id = id;
  }

  //  method that will return different items based on the type of person 
  public abstract void print();

  @Override
  public int compareTo(Person person) {
    return this.fullName.compareTo(person.fullName);
  }
}

//  student class that comes from type person 
class Student extends Person {
  double gpa;
  int creditHours;

  //  fills in GPA and credithours variable for the student
  public Student(String fullName, String id, double gpa, int creditHours) {
    super(fullName, id);
    this.gpa = gpa;
    this.creditHours = creditHours;
  }
  //  override normal print to print it out in a specific method which was given in the pdf
  @Override
  public void print() {
    System.out.println("\tHere is the tuition invoice for "+ fullName);
    System.out.println("\t---------------------------------------------");
    System.out.println("\t"+fullName + "\t\t" + id);
    System.out.println("\tCredit Hours: " + creditHours + " ($236.45/credit hour)");
    System.out.println("\tFees: $52");
    double totalPayment = calculateTuition();
    double discount = (gpa >= 3.85) ? 0.25 * totalPayment : 0;
    System.out.println(String.format("\tTotal payment (after discount): $%.2f ($%.2f discount applied)",(totalPayment - discount), discount));
    System.out.println("\t---------------------------------------------");
  }

  //  returns the tuition based on the credithours
  public double calculateTuition() {
    return creditHours * 236.45 + 52;
  }

  //  returns the gpa for a student
  public Double getGPA(){
    return gpa;
  }
  //  returns the credhit hours for a student
  public int getCredit(){
    return creditHours;
  }
}

//  faculty class that comes from type person 
class Faculty extends Person {
  String department;
  String rank;

  //  fills in the department and rank for the faculty
  public Faculty(String fullName, String id, String department, String rank) {
    super(fullName, id);
    this.department = department;
    this.rank = rank;
  }

  //  overridden print function which returns the requested information for a faculty
  @Override
  public void print() {
    System.out.println("\t---------------------------------------------");
    System.out.println("\t"+fullName + "\t\t" + id);
    System.out.println("\t"+department + " Department, " + rank);
    System.out.println("\t---------------------------------------------");
  }

  //  returns the rank for the faculty
  public String getRank(){
    return rank;
  }

  //  returns the department for the faculty
  public String getDepartment(){
    return department;
  }
}

//  staff class that comes form type person 
class Staff extends Person {
  String department;
  String status;

  //  fills in the derpatment and status for a staff
  public Staff(String fullName, String id, String department, String status) {
    super(fullName, id);
    this.department = department;
    this.status = status;
  }

  //  overrident print function which prints the correct information requested for a staff
  @Override
  public void print() {
    System.out.println("\t---------------------------------------------");
    System.out.println("\t"+fullName + "\t\t" + id);
    if (status.equalsIgnoreCase("f")){
    System.out.println("\t"+department + " Department, " + "Full Time");
    }
    else {
      System.out.println("\t"+department+" Department, "+"Part Time");
    }
    System.out.println("\t---------------------------------------------");
  }

  //  returns the status for a given staff
  public String getStatus(){
    return status;
  }

  //  returns the department for a given staff
  public String getDepartment(){
    return department;
  }
}

public class FinalProject {
  public static void main(String[] args) {
    //  variable initializtions
    Person[] people = new Person[100];
    int[] facultyList = new int[100];
    int[] studentList = new int[100];
    int[] staffList = new int[100];
    Student[] studentArray = new Student[100];
    Scanner scanner = new Scanner(System.in);

    int countStudent = 0;
    int count = 0;
    int countFaculty = 0;
    int countStaff = 0;

    //  menu in a who while loop with catches for incorrect input of the menu
    System.out.println("\t\tWelcome to my Personal Management Program");
    int choice;
    do {
      System.out.println("Choose one of the options:");
      System.out.println("1- Enter the information of a faculty");
      System.out.println("2- Enter the information of a student");
      System.out.println("3- Print tuition invoice for a student");
      System.out.println("4- Print faculty information");
      System.out.println("5- Enter the information of a staff member");
      System.out.println("6- Print the information of a staff member");
      System.out.println("7- Delete a person");
      System.out.println("8- Exit Program");

      System.out.print("\tEnter your selection: ");
      try{
        //  exception handling for invalid inputs for the menu
        choice = scanner.nextInt();
      } catch (InputMismatchException e){
        System.out.println("Invalid input- please try again");
        choice = -1;
      }
      scanner.nextLine();
      switch (choice) {
        case 1:
          // Enter faculty information logic
          System.out.println("Enter the faculty info:");
          people[count] = createFaculty(people, count);
          facultyList[countFaculty] = count;
          count++;          
          countFaculty++;
          break;
        case 2:
          // Enter student information logic
          System.out.println("Enter the student info:");
          people[count] = createStudent(people, count);
          studentArray[countStudent] = ((Student)people[count]);
          studentList[countStudent] = count;
          count++;
          countStudent++;
          break;
        case 3:
          // Print tuition invoice logic
          System.out.print("Enter the student's id: ");
          String studentId = scanner.nextLine();
          printStudentInvoice(people, count, studentId);
          break;
        case 4:
          // Print faculty information logic
          System.out.print("Enter the Faculty's id: ");
          String facultyId = scanner.nextLine();
          printFacultyInformation(people, count, facultyId);
          break;
        case 5:
          // Enter staff information logic
          System.out.println("Enter the staff member info:");
          people[count] = createStaff(people, count);
          staffList[countStaff] = count;
          count++;
          countStaff++;
          break;
        case 6:
          // Print staff information logic
          System.out.print("Enter the Staff's id: ");
          String staffId = scanner.nextLine();
          printStaffInformation(people, count, staffId);
          break;
        case 7:
          // Delete person logic
          System.out.print("Enter the id of the person to delete: ");
          String deleteId = scanner.nextLine();
          count = deletePerson(people, count, deleteId);
          break;
        case 8:
          // Exit program logic
          break;
        default:
          break;
      }
    } while (choice != 8);

    // Report creation logic
    try {
    System.out.print("Would you like to create the report? (Y/N): ");
    char createReport = scanner.next().charAt(0);
    if (createReport == 'Y' || createReport == 'y') {
      System.out.print("Would you like to sort your students by descending GPA or name (1 for GPA, 2 for name): ");
      int sortChoice = scanner.nextInt();
      if (sortChoice == 1) {
        Arrays.sort(people, 0, count);
        System.out.println("Report created and saved on your hard drive!");
        createReportFile(people, count, 1, countStudent, countFaculty, countStaff, facultyList, studentList, staffList, studentArray);
      } else if (sortChoice == 2) {
        Arrays.sort(people, 0, count, (p1, p2) -> p2.fullName.compareTo(p1.fullName));
        System.out.println("Report created and saved on your hard drive!");
        createReportFile(people, count, 2, countStudent, countFaculty, countStaff, facultyList, studentList, staffList, studentArray);
      } else {
        System.out.println("Invalid choice. Report not created.");
      }
    }
  } catch (IOException e){
    e.printStackTrace();
  }

    System.out.println("Goodbye!");
  }

  private static void createReportFile(Person[] people, int count, int choice, int countStudent, int countFaculty, int countStaff, int[] facultyList, int[] studentList, int[] staffList, Student[] studentArray) throws IOException {
    //  initialize reader and writer for the input file and output file
    BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt"));

    LocalDate currentDate = LocalDate.now();

    //  format the date in the way as described on the pdf
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
    String formattedDate = currentDate.format(formatter);
    writer.write("\t\tReport created on " + formattedDate + "\n");
    writer.write("\t\t***********************\n");

    //  faculty members write logic for the report
    writer.write("Faculty Members\n");
    writer.write("-------------------------\n");
    Faculty[] facultyArr = new Faculty[countFaculty];
    Staff[] staffArr = new Staff[countStaff];
    int facultyIndex = 0;
    int staffIndex = 0;

    //  input the correct type (faculty or staff) in their respective array of the right type to handle type errors properly
    for (int i = 0; i < count; i++) {
      if (people[i] instanceof Faculty) {
        facultyArr[facultyIndex] = (Faculty) people[i];
        facultyIndex++;
      } else if (people[i] instanceof Staff) {
        staffArr[staffIndex] = (Staff) people[i];
        staffIndex++;
      }
    }

    //  writes the information for every faculty member and the respective information required
    for (int i=0; i<countFaculty; i++){
      if (facultyArr[i] != null) {
        writer.write("\t"+(i+1)+". "+facultyArr[i].fullName+"\n");
        writer.write("\tID: "+facultyArr[i].id+"\n");
        writer.write("\t"+(facultyArr[i]).getRank()+", "+(facultyArr[i]).getDepartment()+"\n");
      }
    }
    writer.write("\n");

    //  writes the information for every staff member and the respective information required
    writer.write("Staff Members\n");
    writer.write("-------------------------\n");
    for (int i=0; i<countStaff; i++){
      if (staffArr[i] != null) {
        writer.write("\t"+(i+1)+". "+staffArr[i].fullName+"\n");
        writer.write("\tID: "+staffArr[i].id+"\n");
        writer.write("\t"+(staffArr[i]).getDepartment()+", ");
        if ((staffArr[i]).getStatus().equalsIgnoreCase("F")){
          writer.write("Full Time\n");
        }
        else {
          writer.write("Part Time\n");
        }
      }
      
    }

    writer.write("\n");

    //  first inputs the students into a array of type student and sorts the students by either gpa or alphabetical order
    //  as requested by the user
    //  then prints out the information as required by the assignment
    writer.write("Students\n");
    writer.write("-------------------------\n");
    if (choice == 1) {
      Arrays.sort(studentArray, 0, countStudent, (s1, s2) -> Double.compare(s2.getGPA(), s1.getGPA()));

      for (int i = 0; i < countStudent; i++) {
        writer.write("\t" + (i + 1) + ". " + studentArray[i].fullName + "\n");
        writer.write("\tID: " + studentArray[i].id + "\n");
        writer.write("\tGPA: " + studentArray[i].getGPA() + "\n");
        writer.write("\tCredit Hours: " + studentArray[i].getCredit() + "\n\n");
      }
    } 
    else if (choice == 2) {
      Arrays.sort(studentArray, 0, countStudent, Comparator.comparing(student -> student.fullName));

      for (int i = 0; i < countStudent; i++) {
        writer.write("\t" + (i + 1) + ". " + studentArray[i].fullName + "\n");
        writer.write("\tID: " + studentArray[i].id + "\n");
        writer.write("\tGPA: " + studentArray[i].getGPA() + "\n");
        writer.write("\tCredit Hours: " + studentArray[i].getCredit() + "\n\n");
      }
    }

    writer.close();
  }

  //  creates a faculty with the requested information 
  private static Faculty createFaculty(Person[] people, int count) {
    Scanner scanner = new Scanner(System.in);
    String fullName, id, department, rank;

    //  faculty name
    System.out.print("\tName of the faculty: ");
    fullName = scanner.nextLine();

    //  loop to ensure the correct id is entered in the correct format and ensures that no two id's are the same
    do {
      System.out.print("\tID: ");
      id = scanner.nextLine();
      if (!isValidId(id)) {
        System.out.println("\tInvalid ID format. Must be LetterLetterDigitDigitDigitDigit");
      } else if(isDuplicateId(people, count, id)){
        System.out.println("\tID Already Exists, Please Try Again.");
      }
    } while (!isValidId(id) || isDuplicateId(people, count, id));

    //  faculty rank and checks if the rank is valid or not 
    System.out.print("\tRank: ");
    rank = scanner.nextLine();
    while (!isValidRank(rank)) {
      System.out.println("\t\"" + rank + "\" is invalid");
      System.out.print("\tRank: ");
      rank = scanner.nextLine();
    }

    //  faculty department and checks if the department is valid or not
    System.out.print("\tDepartment: ");
    department = scanner.nextLine();
    while (!isValidDepartment(department)) {
      System.out.println("\t\"" + department + "\" is invalid");
      System.out.print("\tDepartment: ");
      department = scanner.nextLine();
    }
    System.out.println("Faculty added!");
    return new Faculty(fullName, id, department, rank);
  }

  //  create student function and populates the student type with the correct information 
  private static Student createStudent(Person[] people, int count) {
    Scanner scanner = new Scanner(System.in);
    String fullName, id;
    double gpa;
    int creditHours;

    //  student name
    System.out.print("\tName of Student: ");
    fullName = scanner.nextLine();

    //  loop to ensure the correct id is entered in the correct format as well as no 2 id's are the same
    do {
      System.out.print("\tID: ");
      id = scanner.nextLine();
      if (!isValidId(id)) {
        System.out.println("\tInvalid ID format. Must be LetterLetterDigitDigitDigitDigit");
      } else if(isDuplicateId(people, count, id)){
        System.out.println("\tID Already Exists, Please Try Again.");
      }
    } while (!isValidId(id) || isDuplicateId(people, count, id));

    //  loop to ensure that the gpa entered is correctly on the 4.0 scale
    do {
      System.out.print("\tGPA: ");
      gpa = scanner.nextDouble();
      if (gpa < 0.00 || gpa > 4.00) {
          System.out.println("\tInvalid GPA. Please enter a GPA between 0.00 and 4.00.");
      }
    } while (gpa < 0.00 || gpa > 4.00);

    //  student credit hours
    System.out.print("\tCredit hours: ");
    creditHours = scanner.nextInt();
    System.out.println("Student added!");
    return new Student(fullName, id, gpa, creditHours);
  }

  //  create staff function with the correct values populated
  private static Staff createStaff(Person[] people, int count) {
    Scanner scanner = new Scanner(System.in);
    String fullName, id, department, status;

    //  staff name 
    System.out.print("\tName of the staff member: ");
    fullName = scanner.nextLine();

    //  loop to ensure the correct id is entered following the format as well as no 2 id's are the same
    do {
      System.out.print("\tID: ");
      id = scanner.nextLine();
      if (!isValidId(id)) {
        System.out.println("\tInvalid ID format. Must be LetterLetterDigitDigitDigitDigit");
      } else if(isDuplicateId(people, count, id)){
        System.out.println("\tID Already Exists, Please Try Again.");
      }
    } while (!isValidId(id) || isDuplicateId(people, count, id));

    //  staff department and ensures a valid department is entered
    System.out.print("\tDepartment: ");
    department = scanner.nextLine();
    while (!isValidDepartment(department)) {
      System.out.println("\t\"" + department + "\" is invalid");
      System.out.print("\tDepartment: ");
      department = scanner.nextLine();
    }

    //  staff status and ensures a valid status is entered
    System.out.print("\tStatus, Enter P for Part Time, or Enter F for Full Time: ");
    status = scanner.nextLine();
    while (!isValidStatus(status)) {
      System.out.println("\t\"" + status + "\" is invalid");
      System.out.print("\tStatus, Enter P for Part Time, or Enter F for Full Time: ");
      status = scanner.nextLine();
    }
    System.out.println("\tStaff member added!");
    return new Staff(fullName, id, department, status);
  }

  //  method to check if the entered id matches the format
  private static boolean isValidId(String id) {
    return id.matches("[a-zA-Z]{2}\\d{4}");
  }

  //  method to check if the entered id is not a duplicate
  private static boolean isDuplicateId(Person[] people, int count, String id) {
    for (int i = 0; i < count; i++) {
        if (people[i].id.equalsIgnoreCase(id)) {
            return true; // ID already exists
        }
    }
    return false; // ID is unique
  }

  //  method to check if the entered rank is valid or not
  private static boolean isValidRank(String rank) {
    return rank.equalsIgnoreCase("Professor") || rank.equalsIgnoreCase("Adjunct");
  }

  //  method to check if the entered department is valid or not
  private static boolean isValidDepartment(String department) {
    return department.equalsIgnoreCase("Mathematics") || department.equalsIgnoreCase("Engineering")
        || department.equalsIgnoreCase("English");
  }

  //  method to check if the entered status is valid or not 
  private static boolean isValidStatus(String status) {
    return status.equalsIgnoreCase("P") || status.equalsIgnoreCase("F");
  }

  //  method to print out a student invoice if the given id matches a valid student id in the array of people
  private static void printStudentInvoice(Person[] people, int count, String studentId) {
    for (int i = 0; i < count; i++) {
      if (people[i] instanceof Student && people[i].id.equalsIgnoreCase(studentId)) {
        ((Student) people[i]).print();
        return;
      }
    }
    System.out.println("\tNo student matched!");
  }

  //  method to print out a faculty invoice if the given id matches a valid faculty id in the array of people 
  private static void printFacultyInformation(Person[] people, int count, String facultyId) {
    for (int i = 0; i < count; i++) {
      if (people[i] instanceof Faculty && people[i].id.equalsIgnoreCase(facultyId)) {
        ((Faculty) people[i]).print();
        return;
      }
    }
    System.out.println("\tNo faculty matched!");
  }

  //  method to print out a staff invoice if the given id matches a valid staff id in the array of people
  private static void printStaffInformation(Person[] people, int count, String staffId) {
    for (int i = 0; i < count; i++) {
      if (people[i] instanceof Staff && people[i].id.equalsIgnoreCase(staffId)) {
        ((Staff) people[i]).print();
        return;
      }
    }
    System.out.println("\tNo staff member matched!");
  }

  //  method to delete a person if the given id matches a valid person in the array of people and updates the count
  private static int deletePerson(Person[] people, int count, String deleteId) {
    for (int i = 0; i < count; i++) {
      if (people[i].id.equalsIgnoreCase(deleteId)) {
        System.arraycopy(people, i + 1, people, i, count - i - 1);
        count--;
        System.out.println("\tPerson deleted!");
        return count;
      }
    }
    System.out.println("\tSorry, no such person exists.");
    return count;
  }
}
