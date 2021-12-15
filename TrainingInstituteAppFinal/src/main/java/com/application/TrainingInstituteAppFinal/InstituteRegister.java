package com.application.TrainingInstituteAppFinal;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class InstituteRegister {

  public void register() throws ClassNotFoundException, SQLException, IOException {
    Scanner sc = new Scanner(System.in);
    System.out.println("----------INSTITUTE REGISTRATION--------------");
    System.out.println();
    Institute institute = new Institute();
    getRegistrationDetails(institute);
  }
  
  private void getRegistrationDetails(Institute institute) throws ClassNotFoundException, SQLException, IOException {
    while (true) {
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter Institute Name ::");
      institute.setInstitute_name(sc.nextLine());
      System.out.println("Enter password ::");
      institute.setPassword(sc.next());
      sc.nextLine();
      System.out.println("Enter  Affiliation date details(yyyy-mm-dd)");
      institute.setAffiliation_Date(sc.next());
      System.out.println("enter Address ::");
      sc.nextLine();
      institute.setAddress(sc.nextLine());
      System.out.println("Enter number of seats ::");
      try {
        institute.setNumber_of_seats(sc.nextInt());
      } catch (Exception e) {
        System.out.println("Invalid input! Try Again");
        System.out.println("please try again");
        break;
      }
      System.out.println("Enter number of courses ::");
      try {
        institute.setNumber_of_course(sc.nextInt());
      } catch (Exception e) {
        System.out.println("Invalid Input!");
        System.out.println("Please try again");
        break;
      }
      InstituteDao instituteDao = new InstituteDao();
      instituteDao.registerInstitute(institute);
      try {
        institute.instituteMenu(institute, institute.getInstitute_name());
      } catch (Exception e) {
        System.out.println("*Error*");
      }
    }
  }

}
