package people;

public class PlayerDoesntExistException extends Exception{
    public PlayerDoesntExistException(){
    }

    public PlayerDoesntExistException(String msg){
       super(msg); 
    }
}
