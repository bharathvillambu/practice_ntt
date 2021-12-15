package com.application.TrainingInstituteAppFinal;



import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StudentDao {

  Connection conn;
  PreparedStatement pstmt;

  public StudentDao()  {
    conn = ConnectDataBase.getConnection();
  }

  public Student updateProfile(Student student) {
    System.out.println();
    System.out.println("1. Student address");
    System.out.println("2. Student phone number");
    System.out.println("3. Student email");
    System.out.println("4. Password");
    System.out.println("5. Quit Updating");
    System.out.println();
    Scanner sc = new Scanner(System.in);
    System.out.println("Which details you want to update?");
    int choice = 0;
    try {
      choice = sc.nextInt();
    } catch (Exception e1) {
      System.out.println("Invalid Choice! Try Again");
    }
    if (choice == 5) {
      try {
        student.studentMenu(student, student.getS_Name());
      } catch (Exception e) {
      }
    }
    if (choice == 1) {
      try {
        student = UpdateAddress(student);
      } catch (SQLException e) {
        System.out.println("***Sql exception occured***");
        try {
          student.studentMenu(student, student.s_Userid);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
      updateProfile(student);
    }
    if (choice == 2) {
      try {
        student = UpdatePhNumber(student);
      } catch (SQLException e) {
        System.out.println("***Sql exception occured***");
        try {
          student.studentMenu(student, student.s_Userid);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
      updateProfile(student);
    }
    if (choice == 3) {
      try {
        student = UpdateEmail(student);
      } catch (SQLException e) {
        System.out.println("***Sql exception occured***");
        try {
          student.studentMenu(student, student.s_Userid);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
      updateProfile(student);
    }
    if (choice == 4) {
      try {
        student = UpdatePassword(student);
      } catch (SQLException e) {
        System.out.println("***Sql exception occured***");
        try {
          student.studentMenu(student, student.s_Userid);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
      updateProfile(student);
    }
    return student;
  }
  
  private Student UpdatePassword(Student student) throws SQLException {
    Scanner sc = new Scanner(System.in);
    System.out.println("----------------UPDATING PASSWORD----------------------");
    System.out.println();
    System.out.println("ENTER THE PASSWORD  ::");
    sc.nextLine();
    String upassword = sc.nextLine();
    pstmt = conn.prepareStatement("update students set password='" + upassword + "' where student_id=" + student.getS_id());
    pstmt.executeUpdate();
    student.setS_email(upassword);
    System.out.println("UPDATED STUDENT PASSWORD SUCCESFULLY");
    return student;
  }

  private Student UpdateEmail(Student student) throws SQLException {
    Scanner sc = new Scanner(System.in);
    System.out.println("----------------UPDATING EMAIL----------------------");
    System.out.println();
    System.out.println("ENTER THE EMAIL  ::");
    sc.nextLine();
    String uemail = sc.nextLine();
    pstmt = conn.prepareStatement("update students set student_email='" + uemail + "' where student_id=" + student.getS_id());
    pstmt.executeUpdate();
    student.setS_email(uemail);
    System.out.println("UPDATED STUDENT PHONE NUMBER SUCCESFULLY");
    return student;
  }

  private Student UpdatePhNumber(Student student) throws SQLException {
    Scanner sc = new Scanner(System.in);
    System.out.println("----------------UPDATING PHONE NUMBER----------------------");
    System.out.println();
    System.out.println("ENTER THE PHONE NUMBER  ::");
    sc.nextLine();
    String uNumber = sc.nextLine();
    pstmt = conn.prepareStatement("update students set student_phno='" + uNumber + "' where student_id=" + student.getS_id());
    pstmt.executeUpdate();
    student.setS_number(uNumber);
    System.out.println("UPDATED STUDENT PHONE NUMBER SUCCESFULLY");
    return student;
  }

  private Student UpdateAddress(Student student) throws SQLException {
    Scanner sc = new Scanner(System.in);
    System.out.println("----------------UPDATING ADDRESS----------------------");
    System.out.println();
    System.out.println("ENTER THE ADDRESS  ::");
    sc.nextLine();
    String uaddress = sc.nextLine();
    pstmt = conn.prepareStatement("update students set student_address='" + uaddress + "' where student_id=" + student.getS_id());
    pstmt.executeUpdate();
    student.setS_address(uaddress);
    System.out.println("UPDATED STUDENT ADDRESS SUCCESFULLY");
    return student;
  }

  public void sendFeedback() throws Exception {
    PreparedStatement insert = conn.prepareStatement("insert into feedback values(?,?,?)");
    System.out.println("Please Enter As Specified : " + "\n");
    Scanner scan = new Scanner(System.in);
    System.out.println("What's Your Name ?? ");
    String name = scan.next();
    System.out.println("Please give a Rating");
    String rate = scan.next();
    System.out.println("Describe Your Experience In One Statement : ");
    String experience = scan.next();
    insert.setString(1, name);
    insert.setString(2, rate);
    insert.setString(3, experience);
    insert.executeUpdate();
    System.out.println("FeedBack Sent!!" + "\n" + "Your FeedBack : ");
    Statement statement = conn.createStatement();
    ResultSet result = statement.executeQuery("Select * from feedback where name = '" + name + "'");
    while (result.next()) {
      System.out.println(result.getString(1) + "---------" + result.getString(2) + "---------" + result.getString(3));
    }
    System.out.println("Redirecting to HomePage");
    OpenPage.student();
  }

  public void viewProfile() throws Exception {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter Your Name : ");
    String name = sc.next();
    Statement statement = conn.createStatement();
    ResultSet result = statement.executeQuery("Select * from stud where name = '" + name + "'");
    int flag = 0;
    while (result.next()) {
      System.out.println(result.getString(1) + "---------" + result.getString(2) + "---------" + result.getString(3) + "----------" + result.getString(4) + "----------" + result.getInt(5));
      flag = 1;
      if (flag == 0) {
        System.out.println("No Data Found");
      }
    }
    System.out.println("Redirecting Too HomePage");
    OpenPage.student();
  }

  public void registerStudent(Student student) throws SQLException {
    pstmt = conn.prepareStatement("insert into students(student_name,student_address,student_phno,student_email,Userid,password) values(?,?,?,?,?,?)");
    pstmt.setString(1, student.getS_Name());
    pstmt.setString(2, student.getS_address());
    pstmt.setString(3, student.getS_number());
    pstmt.setString(4, student.getS_email());
    pstmt.setString(5, student.getS_Userid());
    pstmt.setString(6, student.getS_password());
    pstmt.executeUpdate();
  }

  public int configureLogin(String userId, String password) throws SQLException {
    pstmt = conn.prepareStatement("select * from students where Userid='" + userId + "' and password='" + password + "'");
    ResultSet resultset = pstmt.executeQuery();
    int flag = 0;
    while (resultset.next()) {
      flag = 1;
    }
    return flag;
  }

  public Student getDetails(Student student, String userId) throws SQLException {
    pstmt = conn.prepareStatement("select * from students where Userid='" + userId + "'");
    ResultSet resultSet = pstmt.executeQuery();
    if (resultSet.next()) {
      student.setS_id(resultSet.getInt(1));
      student.setS_Name(resultSet.getString(2));
      student.setInst_name(resultSet.getString(3));
      student.setS_address(resultSet.getString(4));
      student.setS_number(resultSet.getString(5));
      student.setS_email(resultSet.getString(6));
      student.setS_Userid(userId);
      student.setS_password(resultSet.getString(8));
    }
    return student;
  }

  public void viewInstitutes() {
    System.out.println("----------------VIEWING ALL THE INSTITUTES--------------------");
    try {
      pstmt = conn.prepareStatement("select inst_id,inst_name,address,no_of_courses from institutes");
      ResultSet resultSet = pstmt.executeQuery();
      while (resultSet.next()) {
        System.out.println(resultSet.getString(1) + "---------" + resultSet.getString(2) + "---------" + resultSet.getString(3) + "----------" + resultSet.getString(4));
      }
    } catch (SQLException e) {
      System.out.println("***sql exception occured***");
    }
  }

  public void sendRequest(Student student)  {
    Scanner sc = new Scanner(System.in);
    String inst_name = null;
    viewInstitutes();
    System.out.println();
    System.out.println("ENTER THE INSTITUTE ID WHICH YOU WANT TO SEND REQUEST ::");
    int choice = 0;
    try {
      choice = sc.nextInt();
    } catch (Exception e) {
      sendRequest(student);
    }
    try {
      pstmt = conn.prepareStatement("select inst_name from institutes where inst_id=" +  choice);
      ResultSet resultSet = pstmt.executeQuery();
      if (resultSet.next()) {
        inst_name = resultSet.getString(1);
      }
      pstmt = conn.prepareStatement("insert into requests values(" + student.getS_id() + ",'" + student.getS_Name() + "','" + student.getS_email() + "','" + inst_name + "')");
      pstmt.executeUpdate(); 
      pstmt = conn.prepareStatement("insert into request_status values(" + student.getS_id() + ",'" + student.getS_Name() + "','" + "pending" + "','" + inst_name + "')");
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("REQUEST SENT SUCCESFULLY");
  }

  public void viewResponse(Student student) {
    try {
      pstmt = conn.prepareStatement("select * from request_status where student_id=" + student.getS_id());
      ResultSet resultSet = pstmt.executeQuery();
      while (resultSet.next()) {
        System.out.println(resultSet.getString(1) + "-->" + resultSet.getString(2) + "-->" + resultSet.getString(3) + "-->" + resultSet.getString(4));
      }
      //pstmt = conn.prepareStatement("delete from request_status where student_id=" + student.getS_id());
      //pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void sendFeedBack(Student student) {
    Scanner sc = new Scanner(System.in);
    System.out.println("ENTER THE FEEDBACK FOR " + student.getInst_name());
    System.out.println("values should be Good/Average/Bad");
    String feedback = sc.next();
    try {
      pstmt = conn.prepareStatement("insert into feedback values(" + student.getS_id() + ",'" + student.getInst_name() + "','" + feedback + "')");
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("Feedback succesfully sent");
  }
  
  public void viewDetails(Student student) {
    System.out.println("----------------VIEWING DETAILS-------------------");
    try {
      pstmt = conn.prepareStatement("select student_id,student_name,inst_name,student_address,student_phno,student_email,Userid from students where student_id=" + student.getS_id());
      ResultSet resultSet = pstmt.executeQuery();
      while (resultSet.next()) {
        System.out.println(resultSet.getString(1) + "--" + resultSet.getString(2) + "--" + resultSet.getString(3) + "--" + resultSet.getString(4) + "--" + resultSet.getString(5) + "--" + resultSet.getString(6) + "--" + resultSet.getString(7));
      }
    } catch (SQLException e) {
      System.out.println("***Sql exception occured***");
      try {
        student.studentMenu(student, student.s_Userid);
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
  }
}
