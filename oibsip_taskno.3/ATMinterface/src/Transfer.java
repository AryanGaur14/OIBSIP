import java.sql.*;
import java.util.Scanner;

public class Transfer {
    public static void transfer(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Your Account Number/UserID Again:");
        int yourUserID=sc.nextInt();
        System.out.println("Enter Beneficiary Account Number/UserID:");
        int beneUserID=sc.nextInt();
        System.out.print("Enter the amount: ");
        int amount=sc.nextInt();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12622461", "sql12622461", "8dzE8c3Gci");

            PreparedStatement ps=con.prepareStatement("select * from userdetails where userID = ?;");

            int balance=0;
            ps.setInt(1,yourUserID);
            ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                balance=rs.getInt("balance");
            }
            if(balance>=amount) {
                balance = balance - amount;
            }
            else {
                System.out.println("Insufficient Balance");
            }
            PreparedStatement trans=con.prepareStatement("insert into transactions values(?,?,NULL,?);");
            trans.setInt(1,yourUserID);
            trans.setInt(2,amount);
            trans.setInt(3,balance);
            trans.executeUpdate();

            PreparedStatement userD= con.prepareStatement("Update userdetails set balance = ? where userID= ?");
            userD.setInt(1,balance);
            userD.setInt(2,yourUserID);
            userD.executeUpdate();

            con.close();

        }
        catch(SQLException e){}
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12619803","sql12619803","miiR6r7XkE");


            PreparedStatement ps=con.prepareStatement("select * from userdetails where userID = ?;");

            int balance=0;
            ps.setInt(1,beneUserID);
            ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                balance=rs.getInt("balance");
            }
            balance = balance + amount;

            PreparedStatement trans=con.prepareStatement("insert into transactions values(?,NULL,?,?);");
            trans.setInt(1,beneUserID);
            trans.setInt(2,amount);
            trans.setInt(3,balance);
            trans.executeUpdate();

            PreparedStatement userD= con.prepareStatement("Update userdetails set balance = ? where userID= ?");
            userD.setInt(1,balance);
            userD.setInt(2,beneUserID);
            userD.executeUpdate();

            con.close();

        }
        catch(SQLException e){}
    }
}
