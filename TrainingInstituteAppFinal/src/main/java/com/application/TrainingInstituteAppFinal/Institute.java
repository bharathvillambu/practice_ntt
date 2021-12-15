package com.application.TrainingInstituteAppFinal;



import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Institute {
	int inst_id;
	static OpenPage open = new OpenPage();

	public int getInst_id() {
		return inst_id;
	}

	public void setInst_id(int inst_id) {
		this.inst_id = inst_id;
	}

	String Institute_name;
	String password;
	String affiliation_Date;
	String Address;
	int Number_of_seats;
	int number_of_course;
	static InstituteDao instituteDao;

	public String getInstitute_name() {
		return Institute_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAffiliation_Date() {
		return affiliation_Date;
	}

	public void setAffiliation_Date(String affiliation_Date) {
		this.affiliation_Date = affiliation_Date;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public int getNumber_of_seats() {
		return Number_of_seats;
	}

	public void setNumber_of_seats(int number_of_seats) {
		Number_of_seats = number_of_seats;
	}

	public int getNumber_of_course() {
		return number_of_course;
	}

	public void setNumber_of_course(int number_of_course) {
		this.number_of_course = number_of_course;
	}

	public void setInstitute_name(String institute_name) {
		this.Institute_name = institute_name;
	}

	public void login(Institute institute) {
		System.out.println("----------LOGGING AS INSTITUTE--------------");
		String instituteName = null;
		String password = null;
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Enter the Institute name ::");
			instituteName = sc.nextLine();
			System.out.println("Enter the Password ::");
			password = sc.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			instituteDao = new InstituteDao();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			System.out.println("//While creating establishing connection with Database//");
		}
		int logInResult = 0;
		logInResult = instituteDao.configureLogin(instituteName, password);
		if (logInResult == 1) {
			System.out.println("Login successfull valid user :) ");
			try {
				instituteMenu(institute, instituteName);
			} catch (Exception e) {
				System.out.println("***Sql excepion occured***"); } } 
		else if (logInResult == 0) {
			System.out.println("Invalid user please try again :( ");
			System.out.println("You sure you want to continue::(yes|no)");
			String choice = sc.next();
			if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")) {
				if (choice.equalsIgnoreCase("no")) {
					try {
						open.mainMenu();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					login(institute);
				} catch (Exception e) {
					System.out.println("//While opening main error occured//");
				}
			}
		}
	}

	public void instituteMenu(Institute institute, String instituteName) {
		while (true) {
			System.out.println("---------------INSTITUTE MENU-------------------");
			System.out.println();
			System.out.println("1.VIEW STUDENTS");
			System.out.println("2.INSERT FACULTY DATA");
			System.out.println("3.UPDATE INSTUTUTE PROFILE");
			System.out.println("4.VIEW FEEDBACK");
			System.out.println("5.VIEW REQUEST");
			System.out.println("6.SEND RESPONSE REQUEST");
			System.out.println("7.LOG OUT");
			Scanner sc = new Scanner(System.in);
			int choice = 0;
			try {
				choice = sc.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid input!!! Enter a valid choice from 1-7");
				continue;
			}
			if (choice == 7) {
				try {
					open.mainMenu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				instituteDao = new InstituteDao();
			} catch (ClassNotFoundException | SQLException | IOException e) {
				e.printStackTrace();
			}
			institute = instituteDao.getDetails(institute, instituteName);
			if (choice == 1) {
				instituteDao.viewStudents(institute);
				instituteMenu(institute, instituteName); }
			else if (choice == 2) {
				instituteDao.viewFaculty(institute, instituteName);
				instituteDao.insertFaculty(institute);
				instituteMenu(institute, instituteName); }
			else if (choice ==  3) {
				institute = instituteDao.updateDetails(institute);
				System.out.println("----------UPDATION PROCESS COMPLETE----------");
				instituteMenu(institute, instituteName); }
			else if (choice == 4) {
				instituteDao.getFeedBack(institute);
				instituteMenu(institute, instituteName); }
			else if (choice == 5) {
				instituteDao.getRequest(institute);
				instituteMenu(institute, instituteName); }
			else if (choice == 6) {
				instituteDao.approveRequests(institute);
				instituteMenu(institute, instituteName); }
		}
	}
}
