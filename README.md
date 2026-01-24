This Java program is a console-based banking system with three main user roles and basic banking operations. Below is a simple, structured explanation.

🧑‍💼 Roles in the System
1. Owner

Can:

Create admins

Delete admins

View all admins

2. Admin

Can:

View logs

Create customer accounts

Delete accounts

Block accounts

Deposit money into accounts

View all customer data

3. Customer

Can:

Log in using account ID

View account details

Withdraw money

Transfer money to another account

Check balance

Recover account if blocked (using a security question)

💾 Data Storage

The program saves and loads data using text files:

File	Stores
AdminData.txt	Admin credentials
CustomerData.txt	Customer accounts
logs.txt	Activity logs

So data persists even after the program closes.

💰 Banking Features

Account Creation: Admins create accounts with name, PIN, and security question.

Deposit: Admins can deposit money.

Withdraw: Customers can withdraw money.

Transfer: Customers can send money using a unique transfer ID.

Block/Recover: Admins can block accounts; users can recover them by answering a security question.

🔐 Security

PINs must be 4 or 8 digits.

Accounts can be blocked.

Account recovery requires answering a security question.

🧾 Logs

Every major action (create account, delete, deposit, withdraw, etc.) is recorded and viewable by admins.

🧠 In One Sentence

This program simulates a mini banking system where an owner manages admins, admins manage customer accounts, and customers perform banking operations, with all data saved to files and protected using PINs and security questions.
