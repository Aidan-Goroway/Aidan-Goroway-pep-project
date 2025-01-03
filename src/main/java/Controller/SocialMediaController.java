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

        app.post("/register", this::postNewUserHandler); //#1                               DONE!
        app.post("/login", this::postNewLoginHandler); //#2                                 DONE!
        app.post("/messages", this::postNewMessageHandler); //#3                            DONE!
        app.get("/messages", this::getAllMessagesHandler); //#4                             DONE!
        app.get("/messages/{message_id}", this::getMessageByIdHandler); //#5                DONE!
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler); //#6          DONE!
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler); //#7           DONE!
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler); //#8 DONE!

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }

    
    /** HANDLER #1
    * As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. 
    * The body will contain a representation of a JSON Account, but will not contain an account_id.

    * The registration will be successful if and only if
            - the username is not blank, 
            - the password is at least 4 characters long, and
            - an Account with that username does not already exist. 
    * If all these conditions are met, the response body should contain a JSON of the Account, including its account_id.

    * The response status should be 200 OK, which is the default. The new account should be persisted to the database.
    * If the registration is not successful, the response status should be 400. (Client error)

    * @param ctx Responce and request handler
    * @throws JsonProcessingException
    */
    private void postNewUserHandler(Context ctx) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        
        if (account.getUsername() != ""             &&      // username not blank 
            account.getPassword().length() >= 4     &&      // password 4+ chars long
            accountService.getValidAccount(account.getUsername()) == null){ //username not preexistent
                Account postedAccount = accountService.addAccount(account);
                ctx.json(mapper.writeValueAsString(postedAccount));
        }
        else{
            ctx.status(400);
        }
    }

    /** HANDLER #2
    * As a user, I should be able to verify my login on the endpoint POST localhost:8080/login. 
    * The request body will contain a JSON representation of an Account, not containing an account_id.
    * In the future, this action may generate a Session token to allow the user to securely use the site.
    * We will not worry about this for now.

    * The login will be successful if and only if: 
        - the username and password provided in the request body JSON match a real account existing on the database.
    * If successful, the response body should contain a JSON of the account in the response body, including its account_id.
    
    * The response status should be 200 OK, which is the default.
    * If the login is not successful, the response status should be 401. (Unauthorized)
    */
    private void postNewLoginHandler(Context ctx) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account postedLogin = accountService.getValidAccount(account.getUsername());

        if (postedLogin != null &&  // username matches an existing account
            postedLogin.getPassword().equals(account.getPassword())){   // pass also matches  
                ctx.json(mapper.writeValueAsString(postedLogin));
        }
        else{
            ctx.status(401);
        }
    }

    /**
    * HANDLER #3: Create new message
    * 
    * As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages.
    * The request body will contain a JSON representation of a message, which should be persisted to the database, 
    * but will not contain a message_id.
    * The creation of the message will be successful if and only if: 
        - the message_text is not blank,
        - is not over 255 characters,
        - and posted_by refers to a real, existing user. 
    * If successful, the response body should contain a JSON of the message, including its message_id. 
    
    * The response status should be 200, which is the default. The new message should be persisted to the database.
    * If the creation of the message is not successful, the response status should be 400. (Client error)
    */
    private void postNewMessageHandler(Context ctx) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        if (!message.getMessage_text().equals("") &&   // messege not blank
            message.getMessage_text().length() < 256       &&   // message less than 256 long
            !(accountService.getValidAccount(message.getPosted_by()) == null)){  // message from a real user
                Message addedMessage = messageService.addMessage(message);
                ctx.json(mapper.writeValueAsString(addedMessage));
        }
        else{
            ctx.status(400);
        }
    }

    /**
    * HANDLER #4: Retreieve all messages
    * 
    * The response body should contain a JSON representation of a list containing all messages retrieved from the database. 
    * It is expected for the list to simply be empty if there are no messages. The response status should always be 200, 
    * which is the default.
    */
    private void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
    * HANDLER #5: Get message by id
    * 
    * The response body should contain a JSON representation of the message identified by the message_id. 
    * It is expected for the response body to simply be empty if there is no such message. 
    * The response status should always be 200, which is the default.
    */
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException{
        if (messageService.getMessagebyId(ctx.pathParam("message_id")) != null){
            ctx.json(messageService.getMessagebyId(ctx.pathParam("message_id")));
        } // Does not assign a json if null
    }

    /**
    * HANDLER #6: Delete message by id
    * The deletion of an existing message should remove an existing message from the database. 
    * If the message existed, the response body should contain the now-deleted message. 
    * The response status should be 200, which is the default.

    * If the message did not exist, the response status should be 200, but the response body should be empty. 
    * This is because the DELETE verb is intended to be idempotent, ie, 
    * multiple calls to the DELETE endpoint should respond with the same type of response.
    */
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(message_id);
        
        if (deletedMessage != null){
            ctx.json(mapper.writeValueAsString(deletedMessage));
        }
        else{ // Despite being status 200 being the default, it is not actually assigned automatically
            ctx.status(200);
        }
    }

    /**
    * HANDLER #7
    * The update of a message should be successful if and only if: 
    *   - the message id already exists 
    *   - and the new message_text is not blank
    *   - and is not over 255 characters. 
    * If the update is successful, the response body should contain the full
    * updated message (including message_id, posted_by, message_text, and time_posted_epoch), 
    * and the response status should be 200, which is the default. 
    * The message existing on the database should have the updated message_text.

    * If the update of the message is not successful for any reason, the response status should be 400. (Client error)

    * @param ctx
    * @throws JsonProcessingException
    */
    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessageById(message_id, message);

        if (updatedMessage != null){ //Specifications are made in the service class for this handler 
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }
        else{
            ctx.status(400);
        }

    }

    /**
     * Handler #8
     * 
     * The response body should contain a JSON representation of a list containing all messages posted by a particular user,
     * which is retrieved from the database. It is expected for the list to simply be empty if there are no messages. 
     * The response status should always be 200, which is the default.
     */
    private void getAllMessagesByUserHandler(Context ctx) throws JsonProcessingException{
        ctx.json(messageService.getAllMessagesByUserId(ctx.pathParam("account_id")));
    }

}