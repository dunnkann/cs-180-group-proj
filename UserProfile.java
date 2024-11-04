public class UserProfile {
    String description;
    String username;

    public UserProfile (User user) {
        description = user.getDescription();
        username = user.getUsername();
    }

    public String toString() {
        String ans = username + "\n\n" + "Description: " + description;
        return ans;
    }

    public static void main (String[] args) {
        User user = new User("Me,password,1,I like chocolate and video games :)");
        UserProfile profile = new UserProfile(user);
        System.out.print(profile);
    }
}