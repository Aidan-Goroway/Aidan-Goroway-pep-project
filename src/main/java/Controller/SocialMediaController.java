package Controller;

import java.security.Provider.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // app.get("example-endpoint", this::exampleHandler);

        app.get("/messages", this::getAllMessagesHandler);
        app.post("/messages", this::postNewMessageHandler);

        // app.start(8080);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * 
     * @param ctx
     * @throws JsonProcessingException
     */
    private void postUserRegistrationHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        // finish later after DAO classes?
    }

    /**
     * HANDLER #3: Create new message
     * 
     * As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages.
     * The request body will contain a JSON representation of a message, which should be persisted to the database, 
     *      but will not contain a message_id.
        - The creation of the message will be successful if and only if the message_text is not blank, is not over 255 characters,
            and posted_by refers to a real, existing user. If successful, the response body should contain a JSON of the message,
            including its message_id. The response status should be 200, which is the default. The new message should be persisted
            to the database.
        - If the creation of the message is not successful, the response status should be 400. (Client error)
     */
    private void postNewMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
            // System.out.println("MESSAGE LENGTH: " + addedMessage.getMessage_text().length());
        if (addedMessage.getMessage_text() != "" && 
            addedMessage.getMessage_text().length() < 256
            // && addedMessage.getPosted_by()){ //fix later
            ){    
            ctx.json(mapper.writeValueAsString(addedMessage));
        }
        else{
            ctx.status(400);
        }

    }

    /**
     * HANDLER #4: Retreieve all messages
     */
    private void getAllMessagesHandler(Context ctx){
        // if (){

        // }
        // else{
            
        // }
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

}