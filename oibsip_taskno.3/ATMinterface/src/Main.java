 /*This program is using free online database to fetch results of users database name sql12.freesqldatabase.com
 If in case that database stopped working in future
Then just follow the steps:
Step 1: Replace this line {Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12619803","sql12619803","miiR6r7XkE");}
 with your host database or any other database Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/databaseName", "yourRoot", "yourPassword");
Step 2: And while making your choice  just enter -1 it will create table accordingly this program by itself. After that again run program.
Step 3: And enter create account in code choices and create your account and use it as you like.
*/
import java.sql.*;
import java.util.Scanner;

 public class Main {
     public static void createSchema(){
         try {
             Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12619803", "sql12619803", "miiR6r7XkE");
             Statement stm = con.createStatement();
             stm.executeUpdate("create table userdetails(userID int NOT NULL,pin int NOT NULL,balance int NOT NULL,PRIMARY KEY(userID));");
             stm.executeUpdate("create table transaction(userID int,debited int,credited int,balance int);");
         }
         catch(SQLException e){}

     }
//     public static void clearScreen(){
//         System.out.print("\033[H\033[2J");
//         System.out.flush();
//     }
     public static boolean login(){
         try {
             Scanner sc=new Scanner(System.in);
             Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12619803","sql12619803","miiR6r7XkE");
             Statement stm = con.createStatement();
             System.out.println("Enter your Account Number/UserID");
             int userId=sc.nextInt();
             System.out.println("Enter your PIN");
             int pin=sc.nextInt();
             ResultSet rs=stm.executeQuery("select * from userdetails ");
             while(rs.next()){
                 if(rs.getInt("userId")==userId){
                     if(rs.getInt("Pin")==pin){
//                         clearScreen();
                         return true;
                     }
                     else{
                         System.out.println("Password is Incorrect\n\n");
                         Thread.sleep(2000);
//                         clearScreen();
                         return false;
                     }
                 }
             }
             System.out.println("User Not Found\n\n");
             Thread.sleep(2000);
//             clearScreen();
             return false;
         }
         catch(SQLException sq){return false;}
         catch (InterruptedException ie){
         return false;}
     }
     public static void createAccount(){
         try {
             Scanner sc=new Scanner(System.in);
             Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12619803","sql12619803","miiR6r7XkE");
             PreparedStatement preStm= con.prepareStatement("insert into userdetails values(?,?,?);");
             int userID,pin,balance;
             System.out.print("Enter Account Number: ");
             userID=sc.nextInt();
             System.out.print("Enter Your PIN:");
             pin=sc.nextInt();
             System.out.print("Enter Your Balance:");
             balance=sc.nextInt();

             preStm.setInt(1,userID);
             preStm.setInt(2,pin);
             preStm.setInt(3,balance);
             preStm.executeUpdate();

             PreparedStatement preStm2 = con.prepareStatement("insert into transactions values(?,NULL,?,?);");
             preStm2.setInt(1,userID);
             preStm2.setInt(2,balance);
             preStm2.setInt(3,balance);

             preStm2.executeUpdate();

             System.out.println("executed");
             con.close();
         }
         catch(SQLException e){}

     }
     public static void main(String[] args) {
         Scanner sc=new Scanner(System.in);
         System.out.println("----------Welcome in Oasis Infobyte ATM interface----------\n");
         boolean flag=true;
         while(flag) {
             System.out.println("Enter your Operation\n\n1)Create account(type 1)\n2)Transaction History(type 2) \n3)Withdraw(type 3) \n4)Deposit(type 4) \n5)Transfer(type 5) \n6)Exit(type 6)\n");
             int choice=sc.nextInt();
             switch (choice) {
                 case -1:
                     createSchema();
                     break;
                 case 1:
                     createAccount();
                     break;
                 case 2:
                     if (login()) {
                         TransactionsHistory tsh = new TransactionsHistory();
                         tsh.transactionHistory();
                         flag=false;
                     }
                     break;
                 case 3:
                     if (login()) {
                         Withdraw w = new Withdraw();
                         w.withdraw();
                         flag=false;
                     }
                     break;
                 case 4:
                     if (login()) {
                         Deposit d = new Deposit();
                         d.deposit();
                         flag=false;
                     }
                     break;
                 case 5:
                     if (login()) {
                         Transfer t = new Transfer();
                         t.transfer();
                         flag=false;
                     }
                     break;
                 case 6:
                     Exit e = new Exit();
                     e.exit();
                     break;
                 default:
                     System.out.println("Enter the valid Choice");
                     break;
             }
         }
     }
 }
class TransactionsHistory{
       public static void transactionHistory(){
           try {
               Scanner sc=new Scanner(System.in);
               Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12619803","sql12619803","miiR6r7XkE");
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

class Withdraw{
    public static void withdraw(){
        try {
            Scanner sc=new Scanner(System.in);
            Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12619803","sql12619803","miiR6r7XkE");
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
            if(balance>=amount) {
                balance = balance - amount;
            }
            else {
                System.out.println("Insufficient Balance");
            }
            PreparedStatement trans=con.prepareStatement("insert into transactions values(?,?,NULL,?);");
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
class Deposit{
    public static void deposit(){
        try {
            Scanner sc=new Scanner(System.in);
            Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12619803","sql12619803","miiR6r7XkE");
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
class Transfer{
    public static void transfer(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Your Account Number/UserID Again:");
        int yourUserID=sc.nextInt();
        System.out.println("Enter Beneficiary Account Number/UserID:");
        int beneUserID=sc.nextInt();
        System.out.print("Enter the amount: ");
        int amount=sc.nextInt();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12619803","sql12619803","miiR6r7XkE");

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
class Exit{
    public static void exit(){
        System.exit(0);
    }
}

