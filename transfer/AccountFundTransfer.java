package com.pay.chikun.transfer;
import com.pay.chikun.bean.User;
import com.pay.chikun.service.UserService;
import com.pay.chikun.utils.Utils;
import com.pay.chikun.validation.ValidateUser;

import java.util.Scanner;
public class AccountFundTransfer implements UserService {
    User user1, user2;
    int user, activeAcc;
    String accountType;
    Scanner sc= new Scanner(System.in); //now scanner object will be call in every block
    {   //init block
         user1= new User();
         user2= new User();
    }
    public static void main(String[] args) {
        AccountFundTransfer fundTransfer= new AccountFundTransfer();
        fundTransfer.mainMenu();
    }

   private void mainMenu() {
       if (activeAcc != 0) {
           System.out.println("1.  Logout");
           System.out.println("2.  Account Details");
           System.out.println("3.  Account Activity");
           System.out.println("4.  Fund Transfer");
           System.out.println("5.  Withdraw");
           System.out.println("6.  Change PIN");
       } else {
           System.out.println("1.  Login");
           System.out.println("2.  Create an Account");
       }

       int menuChoice = sc.nextInt();

       if (menuChoice == 1) {
           if (activeAcc == 1) {
               logout(user1);
           } else if (activeAcc == 2) {
               logout(user2);
           } else {
               this.login();
           }
       } else if (menuChoice == 2) {
           if (activeAcc != 0) {
               if (activeAcc == 1) {
                   accountInfo(user1);
               } else if (activeAcc == 2) {
                   accountInfo(user2);
               }
           } else {
               createAccount();
           }
       } else if (menuChoice == 3 && activeAcc != 0) {
           if (activeAcc == 1) {
               System.out.println(user1.getHistory());
           } else {
               System.out.println(user2.getHistory());
           }
           mainMenu();
       } else if (menuChoice == 4 && activeAcc !=0) {
           if(activeAcc== 1)
               this.checkAccount(user1, user2);
           else
               this.checkAccount(user2, user1);
       }

       else if (menuChoice == 5 && activeAcc != 0) {
           if (activeAcc == 1) {
               this.withdraw(user1);
           } else {
               this.withdraw(user2);
           }
       } else if (menuChoice == 6 && activeAcc != 0) {
           if (activeAcc == 1) {
               this.changePin(user1);
           } else {
               this.changePin(user2);
           }
       } else {
           System.out.println("Invalid choice. Please try again.");
           mainMenu();
       }
   }


    @Override
    public void login() {
        System.out.println("    [    Welcome to Bank    ]");
        System.out.print("  # Enter Your Bank Account Number ->     ");
        String accNum= sc.next();
        if(accNum.equalsIgnoreCase(user1.getAccountNumber())) {
            System.out.print("  # Enter your 6 Digit Security PIN ->    ");
            int pin= sc.nextInt();
            if(ValidateUser.verifyPin(pin, user1)){
                activeAcc= 1;
                System.out.println("    ~ ~ LOGIN SUCCESSFUL ~ ~");
                createLog(user1, "Account Login");
                mainMenu();
            }
            else {
                System.out.println("!!  WRONG PIN! PLEASE TRY AGAIN  !!");
                login();
            }
        }
        else if(accNum.equalsIgnoreCase(user2.getAccountNumber())) {
            System.out.print("  # Enter your 6 Digit Security PIN ->    ");
            int pin= sc.nextInt();
            if(ValidateUser.verifyPin(pin, user2)){
                activeAcc= 2;
                System.out.println("    ~ ~ LOGIN SUCCESSFUL ~ ~");
                createLog(user2, "Account Login");
                mainMenu();
            }
            else {
                System.out.println("!!  WRONG PIN! PLEASE TRY AGAIN  !!");
                login();
            }
        }
        else {
            System.out.println("||  YOUR ACCOUNT DOES NOT EXIST");
            mainMenu();
        }
    }

    @Override
    public void checkAccount(User fromUser, User toUser) {
        System.out.println("!! ~ Enter Receiver Account Number You Want to Send Money");
        String accountNum= sc.next();
        if(accountNum.equalsIgnoreCase(fromUser.getAccountNumber())) {
            System.out.println("|| This is Your Account Number ||");
            mainMenu();
        }
        else if(accountNum.equalsIgnoreCase(toUser.getAccountNumber())) {
            System.out.println(" ~ You are Sending Money To "+toUser.getUserName());
            System.out.println("|| Enter Amount ||");
            int amount= sc.nextInt();
            System.out.println("|| Enter 6 Digit PIN ||");
            int pin= sc.nextInt();
            if(!ValidateUser.verifyPin(pin,fromUser)) {
                System.out.println("!!  Incorrect PIN   !!");
                mainMenu();
            }
            if(activeAcc== 1) {
                fundTransfer(amount, pin, fromUser, toUser);
            }
            else
                fundTransfer(amount, pin, toUser, fromUser);
        }
        else {
                System.out.println("|| This Account Number Does not Exist...!! ||");
                mainMenu();
             }

    }

    @Override
    public void createAccount() {
        if(user1.getUserName()== null) {
            user= 1;
        }
        else if(user2.getUserName()==null) {
            user= 2;
        }
        else {
            System.out.println(". . . .Oops!!  Only Two User Can be Created. . . .");
            mainMenu();
        }
        System.out.println("-----------Fill Detail to Continue-----------");
        System.out.println(".*.*.*.*.*.< <    Enter Bank Name    > >.*.*.*.*.*.");
        String bankName= sc.next();
        user1.setBankName(bankName);
        if(!ValidateUser.checkLength(3, bankName, false)) {
            System.out.println("---!!!!  Bank Name is Not Valid OR Empty  !!!!---");
            createAccount();
        }
        System.out.println(".*.*.*.*.*.< <    Enter Full Name    > >.*.*.*.*.*.");
        String name= sc.next();
        if(!ValidateUser.checkLength(2, name, false)) {
            System.out.println("---!!!!  Name is Not Valid OR Empty  !!!!---");
            createAccount();
        }
        System.out.println(".*.*.*.*.*.< <    Email    > >.*.*.*.*.*.");
        String email= sc.next();
        if(!ValidateUser.checkLength(10, email, false)  &&  !ValidateUser.validateEmail(email)) {
            System.out.println("---!!!!  Email is Not Valid OR Empty  !!!!---");
            createAccount();
        }
        System.out.println(".*.*.*.*.*.< <    Contact Number    > >.*.*.*.*.*.");
        String mobile= sc.next();
        if(ValidateUser.validateMaxMobile(mobile) || ValidateUser.validateMinMobile(mobile)) {
            System.out.println("---!!!!  Mobile Number Must be 10 digit  !!!!---");
            createAccount();
        }
        System.out.println(".*.*.*.*.*.< <    Create IFSC Code    > >.*.*.*.*.*.");
        String ifsc= sc.next();
        if(!ValidateUser.checkLength(11, ifsc, true)) {
            System.out.println("---!!!!  IFSC Code is Not Valid OR Empty  !!!!---");
            createAccount();
        }
        System.out.println(".*.*.*.*.*.< <    Select Account Type    > >.*.*.*.*.*.");
        System.out.println("1.  Saving");
        System.out.println("2.  Current");
        int accType= sc.nextInt();
        if(accType!= 0  &&  accType<=2) {
            if(accType== 1)
                accountType= "Saving";
            else
                accountType= "Current";
        }
        else
            System.out.println("Account Type is Not Valid");
        System.out.println(".*.*.*.*.*.< <    Enter the Amount You Want To Save    > >.*.*.*.*.*.");
        int amt= sc.nextInt();
        if(amt<= 0) {
            System.out.println("Sorry You Cannot Open Your Account With 0 Balance");
        }
        System.out.println(".*.*.*.*.*.< <    Create 6 Digit Pin    > >.*.*.*.*.*.");
        int pin= sc.nextInt();
        if(!ValidateUser.checkLength(6, String.valueOf(pin), true)) {  //make pin as string to check
            System.out.println("---!!!!  PIN Should be 6 digit  !!!!---");
            createAccount();
        }
        System.out.println("___*---*___*<< <   Generating 11 Digit Account Number   > >>___*---*___*");
        String accNum= Utils.generateAccNum();
        if(user== 1) {
            user1.setUserName(name);
            user1.setAccountNumber(accNum);
            user1.setAccountBalance(amt);
            user1.setBankName(bankName);
            user1.setMobile(mobile);
            user1.setAccountPin(pin);
            user1.setType(accountType);
            user1.setEmail(email);
            user1.setIfscCode(ifsc);
            user1.setHistory(Utils.getTimestamp());
            this.createLog(user1, "Account Created");

            this.accountInfo(user1);
        }
        else {
            user2.setUserName(name);
            user2.setAccountNumber(accNum);
            user2.setAccountBalance(amt);
            user2.setBankName(bankName);
            user2.setMobile(mobile);
            user2.setAccountPin(pin);
            user2.setType(accountType);
            user2.setEmail(email);
            user2.setIfscCode(ifsc);
            user2.setHistory(Utils.getTimestamp());
            this.createLog(user2, "Account Created");

            this.accountInfo(user2);
        }
    }

    private void accountInfo(User user1) {
        System.out.println("~~~~~~~~~~~~******************~~~~~~~~~~~~\n");
        System.out.println("**** [ Account Created Successfully ] ****\n");
        System.out.println("[ || ~~Account Details~~ || ]\n");
        System.out.println("@ BANK NAME =>   "+user1.getBankName());
        System.out.println("@ EMAIL ADDRESS =>   "+user1.getEmail());
        System.out.println("@ CONTACT NUMBER =>   "+user1.getMobile());
        System.out.println("@ ACCOUNT NUMBER =>   "+user1.getAccountNumber());
        System.out.println("@ AVAILABLE BALANCE =>   "+user1.getAccountBalance());
        System.out.println("@ ACCOUNT TYPE =>   "+user1.getType());
        System.out.println("@ IFSC CODE =>   "+user1.getIfscCode());
        System.out.println("@ SECURITY PIN =>   "+user1.getAccountPin()+"\n");
        System.out.println("~~~~~~~~~~~~******************~~~~~~~~~~~~");

        this.mainMenu();
    }

    @Override
    public void logout(User user) {
        activeAcc=0;
        System.out.println("...> * Logout Successful * <...");
        createLog(user,"Account Logout");
        mainMenu();
    }

    @Override
    public void myAccountDetails() {

    }

    @Override
    public void accountActivity() {

    }

    @Override
    public void fundTransfer(int amount, int pin, User fromUser, User toUser) {
        if(ValidateUser.verifyPin(pin, fromUser)) {
            if(amount<= fromUser.getAccountBalance()) {
                toUser.setAccountBalance(toUser.getAccountBalance()+amount);
                fromUser.setAccountBalance(fromUser.getAccountBalance()-amount);
                System.out.println("...*** ...*** ..[   FUND TRANSFERRED SUCCESSFULLY   ].. ***... ***...");
                System.out.println("    @ AVAILABLE BALANCE =>  "+fromUser.getAccountBalance());
                createLog(fromUser, amount+" Transfer To "+ toUser.getUserName());
                createLog(toUser, amount+" Received From "+ fromUser.getUserName());
            }
            else {
                System.out.println("!! - - You Have Not Enough Balance - - !!");
                System.out.println("- - - - - - - - - - - - - - - - - - - - -");
                fundTransfer(amount, pin, fromUser, toUser);
            }
        }
        else {
            System.out.println("    Incorrect PIN !!  Enter the Correct PIN.");
            login();
        }
    }
    @Override
    public void withdraw(User user) {
        System.out.println("Enter Amount");
        int amount= sc.nextInt();

        System.out.println("Enter 6 Digit PIN");
        int pin= sc.nextInt();

        if(ValidateUser.verifyPin(pin, user)) {
            if(amount<= user.getAccountBalance()) {
                user.setAccountBalance(user.getAccountBalance()-amount);
                System.out.println("...*** ...*** ..[   AMOUNT WITHDRAW SUCCESSFULLY   ].. ***... ***...");
                System.out.println("    @ AVAILABLE BALANCE =>  "+ user.getAccountBalance());
                createLog(user, amount+"WITHDRAW");
                mainMenu();
            }
            else {
                System.out.println("!! - - You Have Not Enough Balance - - !!");
                System.out.println("- - - - - - - - - - - - - - - - - - - - -");
                System.out.println("Choose an Option\n");
                System.out.println("1. DashBoard");
                System.out.println("2. Again Try For Withdraw\n");
                int n= sc.nextInt();
                if(n== 1) {
                    mainMenu();
                }
                else if (n==2) {
                    withdraw(user);
                }
                else {
                    System.out.println("Invalid Option");
                    mainMenu();
                }
            }
        }
        else {
            System.out.println("!! PIN Is Not Valid !!");
            System.out.println("Choose an Option\n");
            System.out.println("1. DashBoard");
            System.out.println("2. Again Try For Withdraw\n");
            int n= sc.nextInt();
            if(n== 1) {
                mainMenu();
            }
            else if (n==2) {
                withdraw(user);
            }
            else {
                System.out.println("Invalid Option");
                mainMenu();
            }
        }
    }

    @Override
    public void createLog(User user, String msg) {
        String history;
        if(user.getHistory()== null) {
            history= "";
        }
        else
            history= user.getHistory();

        user.setHistory(msg+" on " + Utils.getTimestamp()+ "\n"+ history);
    }

    @Override
    public void changePin(User user) {
        System.out.println("- - - Enter Your Old PIN - - -");
        int oldPin= sc.nextInt();
        if(oldPin== user.getAccountPin()) {     //as the pin is integer type we cannot use equal operator
            System.out.println("- - - Enter New 6 Digit PIN - - -");
            int newPin= sc.nextInt();
            user.setAccountPin(newPin);
            System.out.println("System.out.println(\"...*** ...*** ..[   YOUR PIN HAS UPDATED SUCCESSFULLY   ].. ***... ***...");
            mainMenu();
        }
        else {
            System.out.println("!!   YOU HAVE ENTERED WRONG PIN   !!");
            mainMenu();
        }
    }
}
