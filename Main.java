import java.util.*;

class BankAccount {

    private String accNo;
    private int pin;
    private double balance;

    private ArrayList<String> history;

    public BankAccount(String accNo, int pin, double balance) {
        this.accNo = accNo;
        this.pin = pin;
        this.balance = balance;
        history = new ArrayList<>();
    }

    public String getAccNo() {
        return accNo;
    }

    public boolean login(String accountNo, int enteredPin) {
        return accNo.equals(accountNo) && pin == enteredPin;
    }

    public void checkBalance() {
        System.out.println("Current Balance: ₹" + balance);
        history.add("Checked Balance");
    }

    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid Amount");
            return;
        }

        balance += amount;

        history.add("Deposited ₹" + amount);

        System.out.println("Deposit Successful");
    }

    public void withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid Amount");
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient Balance");
            history.add("Failed Withdrawal ₹" + amount);
            return;
        }

        balance -= amount;

        history.add("Withdrawn ₹" + amount);

        System.out.println("Withdrawal Successful");
    }

    public void transfer(BankAccount receiver, double amount) {

        if (receiver == null) {
            System.out.println("Receiver Account Not Found");
            return;
        }

        if (receiver.accNo.equals(this.accNo)) {
            System.out.println("Cannot Transfer To Same Account");
            return;
        }

        if (amount <= 0) {
            System.out.println("Invalid Amount");
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient Balance");
            history.add("Failed Transfer ₹" + amount);
            return;
        }

        balance -= amount;
        receiver.balance += amount;

        history.add(
                "Transferred ₹" + amount +
                " to Account " + receiver.accNo
        );

        receiver.history.add(
                "Received ₹" + amount +
                " from Account " + this.accNo
        );

        System.out.println("Transfer Successful");
    }

    public void changePin(int oldPin, int newPin) {

        if (oldPin != pin) {
            System.out.println("Wrong Old PIN");
            return;
        }

        if (newPin < 1000 || newPin > 9999) {
            System.out.println("PIN Must Be 4 Digits");
            return;
        }

        pin = newPin;

        System.out.println("PIN Updated Successfully");
    }

    public void showHistory() {

        System.out.println("\n===== TRANSACTION HISTORY =====");

        if (history.isEmpty()) {
            System.out.println("No Transactions Found");
            return;
        }

        for (String record : history) {
            System.out.println(record);
        }
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        HashMap<String, BankAccount> users =
                new HashMap<>();

        users.put(
                "1001",
                new BankAccount(
                        "1001",
                        1234,
                        5000
                )
        );

        users.put(
                "1002",
                new BankAccount(
                        "1002",
                        2222,
                        8000
                )
        );

        boolean running = true;

        while (running) {

            System.out.println("\n===== ATM SYSTEM =====");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter Choice: ");

            int mainChoice = sc.nextInt();

            switch (mainChoice) {

                case 1:

                    System.out.print("Account Number: ");
                    String accNo = sc.next();

                    System.out.print("PIN: ");
                    int enteredPin = sc.nextInt();

                    BankAccount current =
                            users.get(accNo);

                    if (current == null ||
                            !current.login(
                                    accNo,
                                    enteredPin
                            )) {

                        System.out.println(
                                "Login Failed"
                        );

                        break;
                    }

                    System.out.println(
                            "\nLogin Successful"
                    );

                    int choice;

                    do {

                        System.out.println(
                                "\n===== ATM MENU ====="
                        );

                        System.out.println(
                                "1. Check Balance"
                        );

                        System.out.println(
                                "2. Deposit"
                        );

                        System.out.println(
                                "3. Withdraw"
                        );

                        System.out.println(
                                "4. Transfer"
                        );

                        System.out.println(
                                "5. Transaction History"
                        );

                        System.out.println(
                                "6. Change PIN"
                        );

                        System.out.println(
                                "7. Logout"
                        );

                        System.out.print(
                                "Enter Choice: "
                        );

                        choice = sc.nextInt();

                        switch (choice) {

                            case 1:

                                current.checkBalance();
                                break;

                            case 2:

                                System.out.print(
                                        "Enter Amount: "
                                );

                                current.deposit(
                                        sc.nextDouble()
                                );

                                break;

                            case 3:

                                System.out.print(
                                        "Enter Amount: "
                                );

                                current.withdraw(
                                        sc.nextDouble()
                                );

                                break;

                            case 4:

                                System.out.print(
                                        "Receiver Account Number: "
                                );

                                String receiverAcc =
                                        sc.next();

                                BankAccount receiver =
                                        users.get(
                                                receiverAcc
                                        );

                                System.out.print(
                                        "Enter Amount: "
                                );

                                double amount =
                                        sc.nextDouble();

                                current.transfer(
                                        receiver,
                                        amount
                                );

                                break;

                            case 5:

                                current.showHistory();
                                break;

                            case 6:

                                System.out.print(
                                        "Old PIN: "
                                );

                                int oldPin =
                                        sc.nextInt();

                                System.out.print(
                                        "New PIN: "
                                );

                                int newPin =
                                        sc.nextInt();

                                current.changePin(
                                        oldPin,
                                        newPin
                                );

                                break;

                            case 7:

                                System.out.println(
                                        "Logged Out Successfully"
                                );

                                break;

                            default:

                                System.out.println(
                                        "Invalid Choice"
                                );
                        }

                    } while (choice != 7);

                    break;

                case 2:

                    running = false;

                    System.out.println(
                            "Thank You For Using ATM"
                    );

                    break;

                default:

                    System.out.println(
                            "Invalid Choice"
                    );
            }
        }

        sc.close();
    }
}