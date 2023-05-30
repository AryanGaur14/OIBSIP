import java.sql.*;
import java.util.Scanner;

public class Deposit {
    public static void deposit(){
        try {
            Scanner sc=new Scanner(System.in);
            Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12622461", "sql12622461", "8dzE8c3Gci");
            Statement stm=con.createStatement();

            System.out.println("Enter the Account Number/UserID Again:");
            int userID=sc.nextInt();
            System.out.print("Enter the amount: ");
            int amount=sc.nextInt();

            PreparedStatement ps=con.prepareStatement("select * from userdetails where userID = ?;");

            int balance=0;
            ps.setInt(1,userID);
            ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                balance=rs.getInt("balance");
            }

            balance = balance + amount;

            PreparedStatement trans=con.prepareStatement("insert into transactions values(?,NULL,?,?);");
            trans.setInt(1,userID);
            trans.setInt(2,amount);
            trans.setInt(3,balance);
            trans.executeUpdate();

            PreparedStatement userD= con.prepareStatement("Update userdetails set balance = ? where userID= ?");
            userD.setInt(1,balance);
            userD.setInt(2,userID);
            userD.executeUpdate();

            con.close();

        }
        catch(SQLException e){}
    }
}
