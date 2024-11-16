package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    /**
     * Get all messages, return in list.
     * @return
     */
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"), 
                    rs.getInt("posted_by"),
                    rs.getString("password"),
                    rs.getLong("message_text"));
                messages.add(message);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Insert message
     */
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();

        try{
        String sql = "INSERT INTO message (posted_by,message_text,time_posted_epoch) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        // preparedStatement.setInt(1, message.getMessage_id());
        preparedStatement.setInt(1, message.getPosted_by());
        preparedStatement.setString(2, message.getMessage_text());
        preparedStatement.setLong(3, message.getTime_posted_epoch());
        preparedStatement.executeUpdate();

        ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * SELECT message by ID from message table.
     * @param id
     * @return
     */
    public Message getMessagebyId(int id){
        Connection connection = ConnectionUtil.getConnection();
        // List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"), 
                    rs.getInt("posted_by"),
                    rs.getString("password"),
                    rs.getLong("message_text"));
                return message;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * DELETE message by ID
     * @param id
     */
    public Message deleteMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "DELETE * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"), 
                    rs.getInt("posted_by"),
                    rs.getString("password"),
                    rs.getLong("message_text"));
                return message;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
