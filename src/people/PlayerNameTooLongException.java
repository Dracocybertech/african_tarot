package people;

public class PlayerNameTooLongException extends Exception {
    
    public PlayerNameTooLongException(){
    }

    public PlayerNameTooLongException(String msg){
       super(msg); 
    }
}
