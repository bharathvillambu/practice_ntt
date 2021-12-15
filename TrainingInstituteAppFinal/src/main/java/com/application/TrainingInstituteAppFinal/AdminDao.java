package com.application.TrainingInstituteAppFinal;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class AdminDao {
  Connection conn;
  PreparedStatement pstmt;
  AdminDao admindao;
  
  public AdminDao() {
    conn = ConnectDataBase.getConnection();
  }
  
  public void viewInstitutes() {
    try {
      pstmt = conn.prepareStatement("select * from instituTes");
      ResultSet resultSet = pstmt.executeQuery();
      System.out.println("----------DISPLAYING ALL THE INSTITUES----------");
      System.out.println();
      while (resultSet.next()) {
        System.out.println(resultSet.getString(1) + "--->" + resultSet.getString(2) + "-->" + resultSet.getString(3) + "-->" + resultSet.getString(4) + "-->" + resultSet.getString(6) + "-->" + resultSet.getString(7));
        System.out.println();
      }
      System.out.println();
    } catch (SQLException e) {
      System.out.println("----------Error occured while viewing institutes----------");
      Admin.showMenu();
    }
  }
  
  public  void deleteInstitute()  {
    viewInstitutes();
    String inst_name = null;
    Scanner  sc = new Scanner(System.in);
    System.out.println();
    System.out.println("Enter the ID of institue you want to delete ::");
    int choice = 0;
    try {
      choice = sc.nextInt();
    } catch (InputMismatchException e) {
      System.out.println("Invalid Entry :( ");
      deleteInstitute();
    }
    try {
      pstmt = conn.prepareStatement("select inst_name from institutes where inst_id =" + choice);
      ResultSet resulSet = pstmt.executeQuery();
      if (resulSet.next()) {
        inst_name = resulSet.getString(1);
      }
      pstmt = conn.prepareStatement("update students set inst_name=NULL where inst_name='" + inst_name + "'");
      pstmt.executeUpdate();
      pstmt = conn.prepareStatement("delete from institutes where inst_id=" + choice);
      pstmt.executeUpdate();
      pstmt = conn.prepareStatement("drop table " + inst_name.substring(0, 3) + "_faculty");  
      pstmt.executeUpdate();
      System.out.println("Deletion Of the institue with ID " + choice + " is complete");
      System.out.println();
    } catch (SQLException e) {
      System.out.println("***Sql exception occured***");
      Admin.showMenu();
    }
  }
  /*
  * @author Jon Doe
  */
    
  public void viewStudent() {
    try {
      pstmt = conn.prepareStatement("select * from students");
      ResultSet resultSet = pstmt.executeQuery();
      System.out.println("----------DISPLAYING ALL STUDENTS----------");
      System.out.println();
      while (resultSet.next()) {
        System.out.println(resultSet.getString(1) + "--->" + resultSet.getString(2) + "--->" + resultSet.getString(3) + "-->" + resultSet.getString(4) + "--->" + resultSet.getString(5) + "--->" + resultSet.getString(6) + "--->" + resultSet.getString(7));
        System.out.println();
      }
      System.out.println();
    } catch (SQLException e) {
      System.out.println("***Sql error occured***");
      Admin.showMenu();
    }
  }
  
  public void viewFeedback() {
    try {
      pstmt = conn.prepareStatement("select * from feedback");
      ResultSet resultSet = pstmt.executeQuery();
      System.out.println("---------DISPLAYING ALL STUDENTS FEEDBACK------------");
      System.out.println();
      while (resultSet.next()) {
        System.out.println(resultSet.getString(1) + "--->" + resultSet.getString(2) + "--->" + resultSet.getString(3));
        System.out.println();
      }
      System.out.println();
    } catch (SQLException e) {
      System.out.println("***Sql error occured***");
      Admin.showMenu();
    }
  }
}
