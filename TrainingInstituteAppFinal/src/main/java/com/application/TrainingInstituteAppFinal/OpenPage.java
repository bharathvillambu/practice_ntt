package com.application.TrainingInstituteAppFinal;


import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OpenPage {
  static OpenPage open = new OpenPage();

  private  void adminLogIN() throws Exception {
    Scanner sc = new Scanner(System.in);
    System.out.println("-----------Logging in as a admin----------");
    System.out.println("You sure you want to continue::(yes|no)");
    String choice = sc.next();
    if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")) {
      if (choice.equalsIgnoreCase("yes")) {
        System.out.println("Enter user Name ::");
        String username = sc.next();
        System.out.println("Enter Password ::");
        String password = sc.next();
        Admin admin = Admin.getInstance();
        if (username.equalsIgnoreCase(admin.getAdminId()) && password.equalsIgnoreCase(admin.getPassword())) {
          System.out.println("Login Sucess :) ");
          Admin.showMenu();
        }
        else {
          System.out.println("Login Failed :( ");
          open.mainMenu();
        }
      }
      else {
        mainMenu();
      }
    }
    else {
      adminLogIN();
    }
  }

  public void mainMenu() throws Exception {
    System.out.println("----------TRAINING INSTITUTE APP----------");
    System.out.println("1.ADMIN");
    System.out.println("2.INSTITUTE");
    System.out.println("3.STUDENT");
    System.out.println("4.quit");
    Scanner sc = new Scanner(System.in);
    System.out.println("who are you ?? Enter your Option");
    boolean valid = true;
    while (valid) {
      try {
        int option = sc.nextInt();
        if (option == 4) {
          System.out.println("Thank you for using our app");
          System.exit(0);
        }
        if (option >= 1 && option <= 3) {
          if (option == 1) {
            adminLogIN(); }
          if (option == 2) {
            institue(); }
          if (option == 3) {
            student(); }
        }
        else {
          System.out.println("Please enter option from the below list only");
          System.out.println("1.ADMIN");
          System.out.println("2.INSTITUTE");
          System.out.println("3.STUDENT");
          System.out.println("4.quit");
          System.out.println("Who are you ?? Enter your Option");
        }
      }
      catch (InputMismatchException e) {
        System.out.println("Please enter valid options from 1-4");
        System.out.println("1.ADMIN");
        System.out.println("2.INSTITUTE");
        System.out.println("3.STUDENT");
        System.out.println("4.Quit");
        System.out.println("Who are you ?? Enter your Option");
        sc.next();
      }
    }
  }

  public static void student() throws Exception {
    int option = 0;
    while (true) {
      System.out.println("1. Already Exsitsing User Log IN  ");
      System.out.println("2. New Student Please Register  ");
      System.out.println("3. Quit");
      Scanner sc = new Scanner(System.in);
      try {
        option = sc.nextInt();
      } catch (Exception e) {
        System.out.println("Invalid input!!! Try Again");
        continue;
      }
      if (option == 2) {
        studentRegister stuRegister = new studentRegister();
        studentRegister.register();
        System.out.println("Redirecting to MainPage");
        open.mainMenu();
      } else if (option == 1) {
        studentLogin();
      }
      else if (option == 3) {
        open.mainMenu();
      }
    }
  }

  private static void studentLogin() throws Exception {
    Student student = new Student();
    student.login(student); 
  }

  private void institue() throws Exception {
    int option = 0;
    while (true) {
      System.out.println("1. Already Exsitsing User Log IN  ");
      System.out.println("2. New Institute Please Register  ");
      System.out.println("3. Quit  ");
      Scanner sc = new Scanner(System.in);
      try {
        option = sc.nextInt();
      } catch (Exception e) {
        System.out.println("invalid input");
        System.out.println("----------REDIRECTING TO OPTIONS----------");
        continue;
      }
      if (option == 3) {
        open.mainMenu();
      }
      if (option == 2) {
        InstituteRegister instituteRegister = new InstituteRegister();
        instituteRegister.register();
        System.out.println("Redirecting to MainPage");
        open.mainMenu();
      } else if (option == 1) {
        instituteLogin();
      }
    }
  }

  private void instituteLogin() throws Exception {
    Institute institute = new Institute();
    institute.login(institute);
  }
}