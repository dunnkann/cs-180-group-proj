# cs-180-group-proj
Phase 1 Submission --> Keifer 

Conversation.java:
Conversate class serves to format messages betwwen users
displayMessage class serves to show the messsage between the two users

UserSearch.java:
main serves to prompt the user to input a username and then checks to see if it exists and prints the result
Check serves to read through the file containing the user's names, passwords, and IDs using only the username to see if it exist if it does it returns that the account exists

User.java:
User class makes sure a user has all their Username, Password, ID, and description. It also has the user's permissions like adding and blocking people. Constructor can take a string made by its toString method to reinstantiate it. Inteface: UserActions.java. TestFile: UserTest.java

UserManager.java:
UserManager is used to store user names in a .txt document called users and stores the Username, Password, ID. It also prompts users to create an account if their current creditials aren't in the system. It also counts the amount of accounts made.

