package skr.aadpreparation;

public class Message {
    private CharSequence message;
    private long timestamp;
    private CharSequence sender;

    public Message(CharSequence message, CharSequence sender) {
        this.message = message;
        this.sender = sender;
        timestamp = System.currentTimeMillis();
    }

    public CharSequence getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public CharSequence getSender() {
        return sender;
    }
}
