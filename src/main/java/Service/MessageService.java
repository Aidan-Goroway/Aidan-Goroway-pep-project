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
     * Deletes a message by its message_id.
     * @param message_id The id of the message we are trying to delete.
     * @return Returns the message we have deleted, or null if no message by the id exists.
     */
    public Message deleteMessageById(int message_id){
        Message message = messageDAO.getMessagebyId(message_id);
        if (message != null){
            return messageDAO.deleteMessageById(message_id);
        }
        else{
            return null;
        }
    }

    /**
     * Updates a message by its id.
     * Unlike most of the other handlers, the filtering process for what
     * messages can be updated occurs here, rather than in the Controller.
     * @param message_id 
     * @param message Initial message we are patching
     * @return
     */
    public Message updateMessageById(int message_id, Message message){
        Message messageCopy = messageDAO.getMessagebyId(message_id);
        if (messageCopy != null //a "copy" of the message, taken from its id
            && !message.getMessage_text().isBlank()
            && message.getMessage_text().length() < 256
        ){
            messageDAO.updateMessageById(message_id, message); //update the message, but dont return it
            return messageDAO.getMessagebyId(message_id);
        }
        else{
            return null;
        }
    }

    public List<Message> getAllMessagesByUserId(String account_id){
        return messageDAO.getAllMessagesByUserId(Integer.parseInt(account_id));
    }


}
