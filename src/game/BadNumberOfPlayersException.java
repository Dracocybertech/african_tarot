package game;

public class BadNumberOfPlayersException extends Exception {
    public BadNumberOfPlayersException(){
    }

    public BadNumberOfPlayersException(String msg){
       super(msg); 
    }
}
