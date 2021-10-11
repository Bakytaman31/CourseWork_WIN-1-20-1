package com.Manager;

//Local imports
import com.Database.Database;

//MongoBD imports
import com.mongodb.client.model.Filters;

//BSON imports
import org.bson.Document;
import org.bson.types.ObjectId;

//Java util imports
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Manager{
    public static void main(String name, ObjectId id) {

        //Declare Scanner and Mongo collections
        Scanner in = new Scanner(System.in);

        //Main loop
        boolean status = true;
        System.out.println("Greetings, " + name + "!");
        while (status) {
            //Menu
            System.out.println("Type the number of option. Type 7 when you're done:");
            System.out.println("1. Show the list of employees;");
            System.out.println("2. Show to-do list;");
            System.out.println("3. Show a list of instructions to employees");
            System.out.println("4. Show a list of all coverage areas;");
            System.out.println("5. Show the amount for real estate;");
            System.out.println("6. Calculate % by property category;");
            System.out.println("7. Exit.");
            System.out.println("Choose option: >>");
            String divider = "=====================";
            int choice = in.nextInt();
            String place_name = "";
            float n=0; float m=0;
            float rent = 0;
            float sell = 0;
            int sellAmount = 0;
            int rentAmount = 0;
            switch (choice) {
                case 1:
                    //Show list of workers
                    for (Document document : Database.foundedUsers) {
                        if (document.getString("position").equals("worker")) {
                            System.out.println(document.getString("name"));
                        }
                    }
                    System.out.println(divider);
                    break;
                case 2:
                    //Show list of tasks
                    System.out.println("Type the number of task: >>>");
                    int counter = 1;
                    List ids = new ArrayList();
                    for (Document document : Database.foundedTasks) {
                        if (id.toString().equals(document.getObjectId("worker").toString()) && !document.getString("status").equals("completed")) {
                            ObjectId taskId = document.getObjectId("_id");
                            ids.add(taskId);
                            System.out.println(counter + ") " + document.getString("task"));
                            counter++;
                        }
                    }

                    System.out.println("Пожалуйста, напишите дело, которое вы собираетесь выполнить >>>");
                    int chooseTask = in.nextInt();
                    Database.tasks.updateOne(Filters.eq("_id", ids.get(chooseTask - 1)), new Document(
                            "$set",
                            new Document("status", "completed")
                    ));
                    System.out.println("Task completed");
                    break;
                case 3:
                    //Show workers task list
                    for (Document document : Database.foundedTasks) {
                        for (Document document2 : Database.foundedUsers) {
                            if (document2.getString("position").equals("worker") &&
                                    document.getObjectId("worker").equals(document2.getObjectId("_id")) &&
                                    !document.getString("status").equals("completed")) {
                                System.out.println(document.getString("task") + ", worker: " + document2.getString("name"));
                            }
                        }
                    }
                    System.out.println(divider);
                    break;
                case 4:
                    //Show the percentage of clients depending by their city
                    System.out.println("Select customer coverage area:");
                    System.out.println("1. Bishkek");
                    System.out.println("2. Chui");
                    System.out.println("3. Talas");
                    System.out.println("4. Jalal-Abad");
                    System.out.println("5. Osh");
                    System.out.println("6. Naryn");
                    System.out.println("7. Issyk-Kul");
                    System.out.println("8. Batken");
                    System.out.println("Choose region: >>");
                    int city = in.nextInt();

                    for (Document document : Database.foundedClients) {
                        if(!document.getString("place").equals("")){
                            n++;
                        }
                    }

                    switch (city){
                        case 1:
                            place_name = "Bishkek";
                            break;
                        case 2:
                            place_name = "Chui";
                            break;
                        case 3:
                            place_name = "Talas";
                            break;
                        case 4:
                            place_name = "Jalal-Abad";
                            break;
                        case 5:
                            place_name = "Osh";
                            break;
                        case 6:
                            place_name = "Naryn";
                            break;
                        case 7:
                            place_name = "Issyk-Kul";
                            break;
                        case 8:
                            place_name = "Batken";
                            break;
                        default:
                            break;
                    }
                    for (Document document : Database.foundedClients) {
                        if(document.getString("place").equals(place_name)){
                            m++;
                        }
                    }
                    System.out.printf("In %s: %.2f%s(%.0f/%.0f)%n%n",place_name,(m/n),"%",m,n);
                    break;
                case 5:
                    //Show the amount for real estate
                    System.out.println("1. Show the total amount for sale;");
                    System.out.println("2. Show the amount for rent;");
                    int salesSum = in.nextInt();
                    switch (salesSum) {
                        case 1:
                            for (Document document : Database.foundedObjects) {
                                if(document.getString("sellPrice") != null){
                                    sellAmount += Integer.parseInt(document.getString("sellPrice"));
                                }
                            }
                            System.out.println("Total amount for sell: " + sellAmount);
                            System.out.println(divider);
                            break;
                        case 2:
                            for (Document document : Database.foundedObjects) {
                                if(document.getString("rentPrice") != null){
                                    rentAmount += Integer.parseInt(document.getString("rentPrice"));
                                }
                            }
                            System.out.println("Total amount for rent: " + rentAmount);
                            System.out.println(divider);
                            break;
                        default:
                            break;
                    }
                    break;
                case 6:
                    //Calculate % by property category
                    float totalObj = Database.objects.countDocuments();
                    for (Document document : Database.foundedObjects) {
                        if(document.getString("rentPrice") != null && document.getString("sellPrice") != null){
                            rent++;
                            sell++;
                        } else if (document.getString("rentPrice") != null) {
                            rent++;
                        } else if (document.getString("sellPrice") != null) {
                            sell++;
                        }
                    }
                    System.out.println("Total objects: " + totalObj);
                    System.out.printf("For sell: %.2f%s \n", ((sell/totalObj) * 100), "%");
                    System.out.printf("For rent: %.2f%s \n", ((rent/totalObj) * 100), "%");
                    System.out.println(divider);
                    break;
                case 7:
                    //Exit
                    System.out.println("Goodbye!");
                    status = false;
                    break;
                default:
                    break;
            }
        }
    }
}