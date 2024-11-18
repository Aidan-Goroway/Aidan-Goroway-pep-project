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

    /**
     * 
     * @param message
     * @return
     */
    public Message getMessagebyId(int message_id){
        if (messageDAO.getMessagebyId(message_id) != null){
            return messageDAO.getMessagebyId(message_id);
        }
        else{
            return null;
        }
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
