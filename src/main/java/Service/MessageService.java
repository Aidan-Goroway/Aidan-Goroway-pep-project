package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

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
     * TODO
     * @param message
     * @return
     */
    public Message addMessage(Message message){
        return messageDAO.insertMessage(message);
    }

    /**
     * Get all Messages in the database.
     * @return List of all messages.
     */
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

}
