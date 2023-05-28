import java.lang.*;
import java.util.Scanner;

class Main {
    public static void main(String[] args){
        int randomNumber=(int)(Math.random()*100);

        Scanner sc=new Scanner(System.in);
        int userInput;
        boolean flag=true;

        int attempts,score=100;

        System.out.println("Choose the Difficult level \n1)Easy(10 chance)\n2)Medium(6 chance)\n3)Hard(3 chance)");
        attempts=sc.nextInt();

        switch (attempts){
            case 1:
                attempts=10;
                System.out.println("You have choose Easy");
                break;
            case 2:
                attempts=6;
                System.out.println("You have choose Medium");
                break;
            case 3:
                attempts=3;
                System.out.println("You have choose Hard");
                break;
        }
        int temp=attempts;
        while(flag){
            if(attempts<=0){
                System.out.println("You have finish all your attempts");
                break;
            }
            System.out.println("Enter the number:");
            userInput=sc.nextInt();
            attempts--;
            if(userInput==randomNumber){
                flag=false;
                System.out.println("You Guessed it Right!");
            }
            else if(userInput>randomNumber){

                System.out.println("The number is smaller!");
                score=score-(100/temp);
            }
            else if (userInput<randomNumber) {
                System.out.println("The number is Greater!");
                score=score-(100/temp);

            }
        }

        System.out.println("Your Score is :"+score);

    }
}