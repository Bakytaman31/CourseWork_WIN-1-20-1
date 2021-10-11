package com.company;

//Local packages imports
import com.Database.Database;
import com.Director.Director;
import com.Manager.Manager;
import com.Marketing.Marketing;
import com.SaleManager.SaleManager;
import com.Worker.Worker;

//BSON imports
import org.bson.Document;
import org.bson.types.ObjectId;

//Java util imports
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {

        //Declare Scanner and Mongo collections
        Scanner in = new Scanner(System.in);

        //Remove MongoDB logs
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        //Main loop
        boolean status = true;
        while(status) {
            //Login and password form
            String userPosition = "";
            System.out.println("Enter login");
            String userLogin = in.next();
            System.out.println("Enter password");
            String userPassword = in.next();
            ObjectId id = new ObjectId();
            String name = "";
            Document founded = Database.users.find(new Document("login", userLogin)).first();
            if (founded != null && userPassword.equals(founded.getString("password"))) {
                    userPosition = founded.getString("position");
                    id = founded.getObjectId("_id");
                    name = founded.getString("name");
            } else {
                System.out.println("You entered wrong login or password");
            }
            //Call a roll function depending on user-position
            switch (userPosition) {
                case "director":
                    Director.main(name);
                    status = false;
                    break;
                case "marketing":
                    Marketing.main(name);
                    status = false;
                    break;
                case "manager":
                    Manager.main(name, id);
                    status = false;
                    break;
                case "saleManager":
                    SaleManager.main(name);
                    status = false;
                    break;
                case "worker":
                    Worker.main(id, name);
                    status = false;
                    break;
                default:
                    break;
            }
        }
    }
}