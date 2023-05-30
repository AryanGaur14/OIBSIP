import java.sql.*;
import java.util.Scanner;

public class TransactionsHistory {

        public void transactionHistory(){
            try {
                Scanner sc=new Scanner(System.in);
                Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12622461", "sql12622461", "8dzE8c3Gci");
                PreparedStatement ps=con.prepareStatement("select * from transactions where userID=?");
                System.out.println("Enter Your Account Number/UserID Again: ");
                int userID=sc.nextInt();
                ps.setInt(1,userID);
                ResultSet rs=ps.executeQuery();
                System.out.println("UserID   Debit   Credit   Balance");
                while(rs.next()){
                    System.out.printf("%6d ",rs.getInt("userID"));
                    System.out.printf("%8d",rs.getInt("debited"));
                    System.out.printf("%8d ",rs.getInt("credited"));
                    System.out.printf("%8d",rs.getInt("balance"));
                    System.out.println();
                }
            }
            catch(SQLException e){}
        }
}


