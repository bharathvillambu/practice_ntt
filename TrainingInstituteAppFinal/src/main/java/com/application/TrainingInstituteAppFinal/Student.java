package com.application.TrainingInstituteAppFinal;



import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Student {
  static OpenPage open = new OpenPage();
  int s_id;

  public int getS_id() {
    return s_id;
  }

  public void setS_id(int s_id) {
    this.s_id = s_id;
  }

  public String getInst_name() {
    return inst_name;
  }

  public void setInst_name(String inst_name) {
    this.inst_name = inst_name;
  }

  String s_Name;
  String inst_name;
  String s_address;
  String s_number;
  String s_email;
  String s_Userid;
  String s_password;
  static StudentDao studentDao;

  public String getS_Name() {
    return s_Name;
  }

  public void setS_Name(String s_Name) {
    this.s_Name = s_Name;
  }

  public String getS_password() {
    return s_password;
  }

  public void setS_password(String s_password) {
    this.s_password = s_password;
  }

  public String getS_number() {
    return s_number;
  }

  public void setS_number(String s_number) {
    this.s_number = s_number;
  }

  public String getS_address() {
    return s_address;
  }

  public void setS_address(String s_address) {
    this.s_address = s_address;
  }

  public String getS_email() {
    return s_email;
  }

  public void setS_email(String s_email) {
    this.s_email = s_email;
  }

  public String getS_Userid() {
    return s_Userid;
  }

  public void setS_Userid(String s_Userid) {
    this.s_Userid = s_Userid;
  }

  public void login(Student student) throws Exception {
    Scanner sc = new Scanner(System.in);
    System.out.println("----------LOGGING IN AS STUDENT--------------");
    System.out.println("Enter the USER_ID ::");
    String userId = sc.nextLine();
    System.out.println("Enter the PASSWORD::");
    String password = sc.nextLine();
    student = new Student();
    studentDao = new StudentDao();
    int logInResult = studentDao.configureLogin(userId, password);
    if (logInResult == 1) {
      System.out.println("Login successfull valid user");
      studentMenu(student, userId);
    }
    else if (logInResult == 0) {
      System.out.println("Invalid user! Please try again");
      System.out.println("You sure you want to continue::(yes|no)");
      String choice = sc.next();
      if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")) {
        if (choice.equalsIgnoreCase("no")) {
          try {
            open.mainMenu();
          } catch (Exception e) {
            System.out.println("***Sql error occured***");
          }
        }
        else {
          login(student);
        }
      }
    }
  }

  public void studentMenu(Student student, String userId) throws Exception {
    System.out.println("Welcome Student, What You Wanna Do Today??" + "\n");
    Scanner sc = new Scanner(System.in);
    System.out.println("1.Update Profile");
    System.out.println("2.View Institutes");
    System.out.println("3.SEND REQUEST");
    System.out.println("4.View RESPONSE");
    System.out.println("5.Send Feedback");
    System.out.println("6.View My Profile");
    System.out.println("7.Logout");
    int option = sc.nextInt();
    if (option == 7) {
      open.mainMenu();
    }
    studentDao = new StudentDao();
    student = studentDao.getDetails(student, userId);
    if (option == 1) {
      student = studentDao.updateProfile(student);
      student.studentMenu(student, student.getS_Userid());
    }
    else if (option == 2) {
      studentDao.viewInstitutes();
      student.studentMenu(student, student.getS_Userid());
    }
    else if (option == 3) {
      studentDao.sendRequest(student);
      student.studentMenu(student, student.getS_Userid());
    } 
    else if (option == 4) {
      studentDao.viewResponse(student);
      student.studentMenu(student, student.getS_Userid());
    } 
    else if (option == 5) {
      studentDao.sendFeedBack(student);
      student.studentMenu(student, student.getS_Userid());
    } 
    else if (option == 6) {
      studentDao.viewDetails(student);
      student.studentMenu(student, student.getS_Userid());
    }
  }
}
