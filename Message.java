public class Message {
    private int messageId;
    private User sender;
    private String contents;

    public Message(User sender, String contents) {
        //this.messageId = Database.getMessageId() + 1;
        this.sender = sender;
        this.contents = contents;
    }
}