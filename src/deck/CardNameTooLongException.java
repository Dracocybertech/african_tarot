package deck;

public class CardNameTooLongException extends Exception {
    public CardNameTooLongException() {
    }

    public CardNameTooLongException(String msg) {
        super(msg);
    }
}
