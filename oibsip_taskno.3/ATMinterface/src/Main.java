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
             Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12622461", "sql12622461", "8dzE8c3Gci");
             Statement stm = con.createStatement();
             stm.executeUpdate("create table userdetails(userID int NOT NULL,pin int NOT NULL,balance int NOT NULL,PRIMARY KEY(userID));");
             stm.executeUpdate("create table transaction(userID int,debited int,credited int,balance int);");
         }
         catch(SQLException e){}

     }
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
                         TransactionsHistory trh=new TransactionsHistory();
                         trh.transactionHistory();
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





