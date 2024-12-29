package game;

public class NotEnoughCardsInDeckException extends Exception {
    public NotEnoughCardsInDeckException() {
    }

    public NotEnoughCardsInDeckException(String msg) {
        super(msg);
    }
}
