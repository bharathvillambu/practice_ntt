package com.application.TrainingInstituteAppFinal;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InstituteDao {
	Connection conn;
	PreparedStatement pstmt;
	OpenPage openpage = new OpenPage();
	
	public InstituteDao()  throws ClassNotFoundException, SQLException, IOException {
		conn = ConnectDataBase.getConnection();	
	}

	public void registerInstitute(Institute institute) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into institutes(inst_name,inst_password,aff_date,address,no_of_seats,no_of_courses) values(?,?,?,?,?,?)");
			pstmt.setString(1, institute.getInstitute_name());
			pstmt.setString(2, institute.getPassword());
			pstmt.setString(3, institute.getAffiliation_Date());
			pstmt.setString(4, institute.getAddress());
			pstmt.setInt(5, institute.getNumber_of_seats());
			pstmt.setInt(6, institute.getNumber_of_course());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("***Sql excption occured***");
			try {
				openpage.mainMenu();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		String name = institute.getInstitute_name().substring(0, 3);
		createTables(name);
	}
	
	private void createTables(String name) {
		try {
			pstmt = conn.prepareStatement("create table " + name + "_faculty(faculty_id int,faculty_name varchar(20))");
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println("***sql exception occured while create table***");
			try {
				openpage.mainMenu();
			} catch (Exception e1) {
				System.out.println("Unable to create faculty table");
				try {
					openpage.mainMenu();
				} catch (Exception e2) {
					System.out.println("Error occured while redirecting to MainPage");
				}
			}
		}
	}

	public int configureLogin(String instituteName, String password) {
		int flag = 0;
		try {
			pstmt = conn.prepareStatement("select * from institutes where inst_name='" + instituteName + "' and inst_password='" + password + "'");
			ResultSet resultset = pstmt.executeQuery();
			flag = 0;
			while (resultset.next()) {
				flag = 1;
			}
		} catch (SQLException e) {
			System.out.println("***Sql exception occured***");
			try {
				openpage.mainMenu();
			} catch (Exception e1) {
				
			}
		}
		return flag;
	}

	public void viewStudents(Institute institute) {
		try {
			pstmt = conn.prepareStatement("select * from students where inst_name='" + institute.getInstitute_name() + "'");
			ResultSet resultSet = pstmt.executeQuery();
			System.out.println("--------------STUDENTS UNDER " + institute.getInstitute_name() + " are-------------------");
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + "---" + resultSet.getString(2) + "---" + resultSet.getString(3) + "---" + resultSet.getString(4) + "---" + resultSet.getString(5) + "---" + resultSet.getString(6) + "---" + resultSet.getString(7));
			}
		} catch (SQLException e) {
			System.out.println("***Sql exception occured****");
			institute.instituteMenu(institute, institute.getInstitute_name());
		}
	}

	public Institute getDetails(Institute institute, String instituteName)  {
		try {
			pstmt = conn.prepareStatement("select * from institutes where inst_name='" + instituteName + "'");
			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				institute.setInst_id(resultSet.getInt(1));
				institute.setInstitute_name(resultSet.getString(2));
				institute.setPassword(resultSet.getString(3));
				institute.setAffiliation_Date(resultSet.getString(4));
				institute.setAddress(resultSet.getString(5));
				institute.setNumber_of_seats(resultSet.getInt(6));
				institute.setNumber_of_course(resultSet.getInt(7));
			}
		} catch (SQLException e) {
			System.out.println("***Sql exception occured****");
			institute.instituteMenu(institute, institute.getInstitute_name());
		}
		return institute;
	}

	public void insertFaculty(Institute institute)  {
		Scanner sc = new Scanner(System.in);
		System.out.println("---------INSERTING A NEW FACULTY-------------");
		try {
			pstmt = conn.prepareStatement("insert into " + institute.getInstitute_name().substring(0, 3) + "_faculty values(?,?)");
			System.out.println("please enter the Faculty ID:: ");
			try {
				pstmt.setInt(1, sc.nextInt());
			} catch (Exception e) {
				System.out.println("Invalid values!");
				try {
					institute.instituteMenu(institute, institute.getInstitute_name());
				} catch (Exception e1) {
					System.out.println("***Sql exception occured***");
					institute.instituteMenu(institute, institute.getInstitute_name());
				}
			}
			System.out.println("Please enter the Faculty Name:: ");
			pstmt.setString(2, sc.next());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("***Sql exception occured****");
			institute.instituteMenu(institute, institute.getInstitute_name());
		}
		System.out.println("New Faculty has been added");
	}

	public void viewFaculty(Institute institute, String instituteName) {
		System.out.println("-----------ALREADY EXISTING FACULTY-------------");
		try {
			pstmt = conn.prepareStatement("SELECT * from " + instituteName.substring(0, 3) + "_faculty");
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + "-------------" + resultSet.getString(2));
			}
		} catch (SQLException e) {
			System.out.println("***Sql exception occured***");
			institute.instituteMenu(institute, institute.getInstitute_name());
		}
	}

	public Institute updateDetails(Institute institute) {
		System.out.println("1. Institute password");
		System.out.println("2. Institute Address");
		System.out.println("3. Number of Seats");
		System.out.println("4. Number of Courses");
		System.out.println("5. Quit Updating");
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.println("Which details you want to update");
		int choice = 0;
		try {
			choice = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Invalid Choice! Try Again");
			updateDetails(institute);
		}
		if (choice == 5) {
			try {
				institute.instituteMenu(institute, institute.getInstitute_name());
			} catch (Exception e) {
				System.out.println("***Sql exception occured***");
				institute.instituteMenu(institute, institute.getInstitute_name());
			}
		}

		if (choice == 1) {
			institute = updatePassword(institute);
			updateDetails(institute);
		}
		else if (choice == 2) {
			institute = updateAddress(institute);
			updateDetails(institute);
		}
		else if (choice == 3) {
			institute = updateNoOfSeats(institute);
			updateDetails(institute);
		}
		else if (choice == 4) {
			institute = updateNoOfCourses(institute);
			updateDetails(institute);
		}
		return institute;
	}

	private Institute updateNoOfCourses(Institute institute)  {
		System.out.println("----------------UPDATING NUMBER OF COURSES----------------------");
		System.out.println();
		System.out.println("ENTER THE No Of COURSES  ::");
		Scanner sc = new Scanner(System.in);
		int uCourse = 0;
		try {
			uCourse = sc.nextInt();
		} catch (Exception e1) {
			System.out.println("Invalid Value! Try Again");
			updateNoOfCourses(institute);
		}
		try {
			pstmt = conn.prepareStatement("update institutes set no_of_courses='" + uCourse + "' where inst_id=" + institute.getInst_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("***Sql Exception Occured***");
			institute.instituteMenu(institute, institute.getInstitute_name());
		}
		institute.setNumber_of_course(uCourse);
		System.out.println("UPDATED INSTITUTE ADDRESS SUCCESFULLY");
		return institute;
	}

	private Institute updateNoOfSeats(Institute institute) {
		System.out.println("----------------UPDATING NUMBER OF SEATS----------------------");
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.println("ENTER THE NO OF SEATS::");
		int uSeats = 0;
		try {
			uSeats = sc.nextInt();
		} catch (Exception e1) {
			System.out.println("Invalid value! Try Again");
			updateNoOfSeats(institute);
		}
		try {
			pstmt = conn.prepareStatement("update institutes set no_of_seats='" + uSeats + "' where inst_id=" + institute.getInst_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("***Sql exception occured***");
			institute.instituteMenu(institute, institute.getInstitute_name());
		}
		institute.setNumber_of_seats(uSeats);
		System.out.println("UPDATED INSTITUTE ADDRESS SUCCESFULLY");
		return institute;
	}

	private Institute updateAddress(Institute institute) {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.println("----------UPDATING ADDRESS----------");
		System.out.println();
		System.out.println("ENTER THE ADDRESS ::");
		sc.nextLine();
		String uaddress = sc.nextLine();
		try {
			pstmt = conn.prepareStatement("update institutes set address='" + uaddress + "' where inst_id=" + institute.getInst_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("***Sql exception occured***");
			institute.instituteMenu(institute, institute.getInstitute_name());
		}
		institute.setAddress(uaddress);
		System.out.println("UPDATED INSTITUTE ADDRESS SUCCESFULLY");
		return institute;
	}

	private Institute updatePassword(Institute institute) {
		System.out.println();
		System.out.println("----------------UPDATING PASSWORD----------------------");
		System.out.println();
		Scanner sc =  new Scanner(System.in);
		System.out.println("ENTER THE PASSWORD  ::");
		String upass = sc.next();
		try {
			pstmt = conn.prepareStatement("update institutes set inst_password='" + upass + "' where inst_id=" + institute.getInst_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("***Sql exception occured***");
			institute.instituteMenu(institute, institute.getInstitute_name());
		}
		institute.setPassword(upass);
		System.out.println("UPDATED INSTITUTE PASSWORD SUCCESFULLY");
		return institute;
	}

	public void getFeedBack(Institute institute) {
		System.out.println();
		System.out.println("-----------FEEDBACK OF " + institute.getInstitute_name() + "------------");
		try {
			pstmt = conn.prepareStatement("SELECT * from feedback where inst_name='" + institute.getInstitute_name() + "'");
			ResultSet resultSet = pstmt.executeQuery(); 
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + "-->" + resultSet.getString(2) + "--->" + resultSet.getString(3));
      }
		} catch (SQLException e) {
			System.out.println("***Sql exception occured***");
			institute.instituteMenu(institute, institute.getInstitute_name());
		}

	}

	public void getRequest(Institute institute)  {
		System.out.println();
		System.out.println("---------------VIEWING REQUESTS FROM STUDENTS---------------");
		System.out.println();
		try {
			pstmt = conn.prepareStatement("SELECT * from requests where inst_name='" + institute.getInstitute_name() + "'");
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + "-->" + resultSet.getString(2) + "--->" + resultSet.getString(3));
      }
		} catch (SQLException e) {
			System.out.println("***Sql exception occured***");
			institute.instituteMenu(institute, institute.getInstitute_name());
		}
	}

	public void approveRequests(Institute institute) {
		System.out.println();
		//	System.out.println("-----------------VIEWING RESPONSE FOR "+institute.getInstitute_name()+" INSTITUTE---------------------------");
		System.out.println();
		getRequest(institute);
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.println("CHOOSE YOUR CHOICE::");
		System.out.println("1.APPROVE");
		System.out.println("2.DECLINE");
		System.out.println("3.BACK");
		int choice = 0;
		try {
			choice = sc.nextInt();
			if (choice == 3) {
				try {
					System.out.println("Redirecting to MainPage");
					institute.instituteMenu(institute, institute.getInstitute_name());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println("Invalid Choice! Try Again");
			try {
				approveRequests(institute);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (choice == 1) {
			aprroveStudents(institute);
		}
		else if (choice == 2) {
			declineStudents(institute);
		}
		else {
			System.out.println("Invalid Choice! Try Again");
		}

	}

	private void declineStudents(Institute institute)  {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the students ID whome you want to decline :: ");
		int sDecline = 0;
		try {
			sDecline = sc.nextInt();
		} catch (Exception e1) {
			System.out.println("Invalid Input! Try Again");
			approveRequests(institute);
		}
		try {
			pstmt = conn.prepareStatement("delete from requests where student_id=" + sDecline);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement("update request_status set status='" + "declined" + "' where student_id="+ sDecline);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("***Sql exception occured***");
			institute.instituteMenu(institute, institute.getInstitute_name());
		}
		System.out.println("DECLINED THE STUDENT WITH STUDENT_ID= " + sDecline);
	}

	private void aprroveStudents(Institute institute)  {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the students ID whome you want to Approve :: ");
		int sApprove = 0;
		try {
			sApprove = sc.nextInt();
		} catch (Exception e1) {
			System.out.println("Invalid Input! Try Again");
			approveRequests(institute);
		}
		try {
			pstmt = conn.prepareStatement("delete from requests where student_id=" + sApprove);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement("update request_status set status='" + "approved" + "' where student_id="+ sApprove);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement("update students set inst_name='" + institute.getInstitute_name() + "' where student_id=" + sApprove);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement("update institutes set no_of_seats=no_of_seats-1 where inst_name='" + institute.getInstitute_name() + "'");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("***Sql exception occured***");
			institute.instituteMenu(institute, institute.getInstitute_name());
		}
		System.out.println("APPROVED THE STUDENT WITH STUDENT_ID= " + sApprove);
	}

}
