package com.application.TrainingInstituteAppFinal;



import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
/*
* @author Jon Doe
*/

public class studentRegister {
  /*
  * @author Jon Doe
  * */
  public static void register() throws ClassNotFoundException, SQLException, IOException {
    System.out.println("----------STUDENT REGISTRATION--------------");
    System.out.println();
    Student student = new Student();
    getRegistrationDetails(student);
  }
  
  private static void getRegistrationDetails(Student student) throws SQLException, ClassNotFoundException, IOException {
    System.out.println("Enter Student Name ::");
    Scanner sc = new Scanner(System.in);
    student.setS_Name(sc.nextLine());
    System.out.println("Enter Address ::");
    student.setS_address(sc.nextLine());
    System.out.println("Enter Phone No ::");
    student.setS_number(sc.next());
    System.out.println("Enter Email ::");
    student.setS_email(sc.next());
    System.out.println("Enter User ID ::");
    student.setS_Userid(sc.next());
    System.out.println("Enter Password ::");
    student.setS_password(sc.next());
    StudentDao studentDao = new StudentDao();
    studentDao.registerStudent(student);
    System.out.println("REGISTRATION SUCCESFULL!");
  }
}
