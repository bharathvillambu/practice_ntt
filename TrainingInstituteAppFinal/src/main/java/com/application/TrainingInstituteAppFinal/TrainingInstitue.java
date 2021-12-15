package com.application.TrainingInstituteAppFinal;



import java.sql.SQLException;
/*
* @author Jon Doe
*/

public class TrainingInstitue {
  /*
  * @author Jon Doe
  */
  public static void main(String[] args)   {
    OpenPage o = new OpenPage();
    try {
      o.mainMenu();
    } catch (Exception e) {
      System.out.println("***Error Occured***");
      System.exit(0);
    }
  }
}
