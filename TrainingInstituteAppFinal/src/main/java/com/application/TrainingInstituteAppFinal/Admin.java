package com.application.TrainingInstituteAppFinal;



import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
/*
* @author Jon Doe
*/
public class Admin {
  String adminId = "admin";
  String password = "admin";
  static Admin admin;
  static OpenPage open = new OpenPage();
  
  public String getAdminId() {
    return adminId;
  }
  
  public String getPassword() {
    return password;
  }
  
  public  static Admin getInstance() {
    if (admin == null) {
      admin = new Admin();
    }
    return admin;
  }
  
  public static void showMenu() {
    System.out.println("1.View institute");
    System.out.println("2.Delete Institute'");
    System.out.println("3.View Student");
    System.out.println("4.View FeedBack");
    System.out.println("5.LogOut");
    System.out.println();
    Scanner scan = new Scanner(System.in);
    System.out.println("What do you want to do??? Enter Your Choice ::");
    int option = 0;
    try {
      option = scan.nextInt();
    } catch (Exception e) {
      System.out.println("Invalid input!!! Enter a valid option from 1-5");
      showMenu();
    }
    if(option == 5) {
      System.out.println();
      try {
        open.mainMenu();
      } catch (Exception e) {
        System.out.println("----------Error occured while opening mainmenu---------");
      }
    }
    AdminDao adminDao = new AdminDao();
    if(option == 1) {
      adminDao.viewInstitutes();
      Admin.showMenu();
    }
    else if(option == 2) {
      adminDao.deleteInstitute();
      Admin.showMenu(); 
    }
    else if(option == 3) {
      adminDao.viewStudent();
      Admin.showMenu();
    }
    else if(option == 4) {
      adminDao.viewFeedback();
      Admin.showMenu();
    }
  }
}
