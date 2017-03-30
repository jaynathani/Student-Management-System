import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;



public class JDBCStudentManagement {

	public static void main(String[] args) throws SQLException, Exception, IOException {
		
		
		
		Class.forName("com.mysql.jdbc.Driver"); 	//To run the class com.mysql.jdbc.Driver without any instance of the class 
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student_Management_System", "root", "root");
		connection.setAutoCommit(false);
		
		int studentId = 0;
		String studentFirstName = null;
		String studentLastName = null;
		String studentDOB = null;
		String studentGender = null;
		int courseId = 0;
		String courseCourseName = null;
		String courseCourseDescription;
		String courseSemester;
		String courseLocation;
		int addId;
		String homeAdd = null;
		String homeCountry = null;
		String homeZip = null;
		String currAdd = null;
		String currCountry = null;
		String currZip = null;
		int regId;
		int studentIdforDisplay = 0;
		int courseIdFromMapping = 0;
		
		Statement displayAllCourses = connection.createStatement();
		Statement enterStudentData = connection.createStatement();
		Statement enterAddressData = connection.createStatement();
		Statement getStudentId = connection.createStatement();
		Statement getCourseId = connection.createStatement();
		Statement enterSCMapping = connection.createStatement();
		Statement displayStudent = connection.createStatement();
		Statement displayAddress = connection.createStatement();
		Statement getCourseIdFromMapping = connection.createStatement();
		Statement getCoursesFromMapping = connection.createStatement();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
		System.out.println("Enter choice from the following:");
		System.out.println("1. Enter Data");
		System.out.println("2. View Data");
		System.out.println("3. Exit");
		
		String ch = "Y";
		int choice = Integer.parseInt(br.readLine());
		String course;
		
		
		
		switch(choice){
		case 1: System.out.println("Enter data of new student:");
				System.out.println("Enter First name:");
				studentFirstName = br.readLine();
				System.out.println("Enter Last name:");
				studentLastName = br.readLine();
				System.out.println("Enter Date of birth(mm/dd/yyyy):");
				studentDOB = br.readLine();
				System.out.println("Enter Gender:");
				studentGender = br.readLine();
				enterStudentData.executeUpdate("insert into Student(FirstName, LastName, DOB, Gender) values('"+studentFirstName+"', '"+studentLastName+"', '"+studentDOB+"', '"+studentGender+"')");
				ResultSet resultSet1 = getStudentId.executeQuery("select (StudentId) from Student where FirstName = '"+studentFirstName+"' and LastName = '"+studentLastName+"' ");
				while(resultSet1.next()){
					studentId = resultSet1.getInt("StudentId");
				}
				System.out.println("Enter Address:");
				currAdd = br.readLine();
				System.out.println("Enter Country:");
				currCountry = br.readLine();
				System.out.println("Enter Zip:");
				currZip = br.readLine();
				enterAddressData.executeUpdate("insert into Address(StudentId, Address, Country, Zip) values('"+studentId+"', '"+currAdd+"', '"+currCountry+"', '"+currZip+"')");
				System.out.println("Select from the following courses:");
				ResultSet resultSet = displayAllCourses.executeQuery("select * from Course");
				while(resultSet.next()){
					System.out.println(resultSet.getInt("CourseId") + "   " + resultSet.getString("CourseName"));
				} 
				while(ch.equals("Y")){
					System.out.println("Enter Course Name:");
					courseCourseName = br.readLine();
					ResultSet resultSet2 = getCourseId.executeQuery("select (CourseId) from Course where CourseName = '"+courseCourseName+"' ");
					while(resultSet2.next()){
						courseId = resultSet2.getInt("CourseId");
					}
					enterSCMapping.executeUpdate("insert into Student_Course_Mapping(StudentId, CourseId) values('"+studentId+"', '"+courseId+"' )");
					System.out.println("Do you want to enroll for more courses?(Y/N): ");
					ch = br.readLine();
				}
				System.out.println("Your Student Id is: " + studentId);
				break;
				
		case 2: System.out.println("Enter StudentId: ");
				studentIdforDisplay = Integer.parseInt(br.readLine());
				ResultSet resultSet3 = displayStudent.executeQuery("select * from Student where StudentId = '"+studentIdforDisplay+"' ");
				while(resultSet3.next()){
					System.out.println("Student Id: " + studentIdforDisplay);
					System.out.println("Name: " + resultSet3.getString("FirstName") + " " + resultSet3.getString("LastName"));
					System.out.println("Date of Birth: " + resultSet3.getString("DOB"));
					System.out.println("Gender: " + resultSet3.getString("Gender"));
					ResultSet resultSet4 = displayAddress.executeQuery("select * from Address where StudentId = '"+studentIdforDisplay+"' ");
					while(resultSet4.next()){
						System.out.println("Address Id: " + resultSet4.getInt("AddId"));
						System.out.println("Address: " + resultSet4.getString("Address"));
						System.out.println("Country: " + resultSet4.getString("Country"));
						System.out.println("Zipcode: " + resultSet4.getString("Zip"));
					}
					ResultSet resultSet5 = getCourseIdFromMapping.executeQuery("select * from Student_Course_Mapping where StudentId = '"+studentIdforDisplay+"' ");
					while(resultSet5.next()){
						courseIdFromMapping = resultSet5.getInt("CourseId");
						System.out.println("Registration Id: " + resultSet5.getString("RegistrationId"));
						ResultSet resultSet6 = getCoursesFromMapping.executeQuery("select * from Course where CourseId = '"+courseIdFromMapping+"' ");
						while(resultSet6.next()){
							System.out.println("Course Id: " + resultSet6.getInt("CourseId"));
							System.out.println("Course Name: " + resultSet6.getString("CourseName"));
							System.out.println("Semester: " + resultSet6.getString("Semester"));
							System.out.println("Location: " + resultSet6.getString("Location"));
						}
					}
					
				}
				break;
		case 3: break;
		default: System.out.println("Wrong choice!");
		}
		
	
		connection.commit();
		connection.close();
		
		System.out.println("Exit");
		
	}

}
