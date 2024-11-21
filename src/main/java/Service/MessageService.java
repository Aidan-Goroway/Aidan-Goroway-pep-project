package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

import javax.print.DocFlavor.STRING;

public class MessageService {
    MessageDAO messageDAO;

    /**
     * No args constructor.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }
    
    /**
     * Constructor when messageDAO is provided.
     * For testing purposes.
     */
    public MessageService(MessageDAO messageDAO){
        messageDAO = new MessageDAO();
    }

    /**
     * Insert a message into the database.
     * @param message The message we are attempting to insert.
     * @return The message we have inserted. May return null on SQL failure.
     */
    public Message addMessage(Message message){
        return messageDAO.insertMessage(message);
    }

    /**
     * Get all Messages in the database.
     * @return List of all messages. May return null if no messages exist.
     */
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    /**
     * Quereys for a Message in the database by its message_id.
     * @param message_id The message_id we are quereying by.
     * @return The Message we have attempted to query. May return null if no message by the message_id exists.
     */
    public Message getMessagebyId(String message_id){
        return messageDAO.getMessagebyId(Integer.parseInt(message_id));
    }

    /**
     * 
     * @param message
     * @return
     */
    public Message deleteMessageById(int message_id){
        if (messageDAO.getMessagebyId(message_id) != null){
            return messageDAO.getMessagebyId(message_id);
        }
        else{
            return null;
        }
    }

    public Message updateMessageById(int message_id, Message message){
        if (messageDAO.getMessagebyId(message_id) != null){
            messageDAO.updateMessageById(message_id, message);
            return message;
        }
        else{
            return null;
        }
    }

    public List<Message> getAllMessagesByUserId(String account_id){
        return messageDAO.getAllMessagesByUserId(Integer.parseInt(account_id));
    }


}
