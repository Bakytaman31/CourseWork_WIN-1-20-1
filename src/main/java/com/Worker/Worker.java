package com.Worker;

//Local imports
import com.Database.Database;

///MongoDB imports
import com.mongodb.client.model.Filters;

//BSON imports
import org.bson.Document;
import org.bson.types.ObjectId;

//Java util imports
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Worker {
    public static void main(ObjectId id, String name) {
        //Declare Scanner
        Scanner in = new Scanner(System.in);
        
        //Main loop
        boolean status = true;
        System.out.println("Greetings, " + name + "!");
        while (status) {
            //Menu
            System.out.println("Type the number of option. Type 5 when you're done:");
            System.out.println("1. Show the list of my tasks;");
            System.out.println("2. Complete task;");
            System.out.println("3. Show the list of completed tasks;");
            System.out.println("4. Show my salary;");
            System.out.println("5. Exit.");
            System.out.println("Choose option: >>");
            String divider = "=====================";
            int choice = in.nextInt();
            boolean tasksExsist = false;
            switch (choice) {
                case 1:
                    //Show tasks list
                    for (Document document : Database.foundedTasks) {
                        if (id.toString().equals(document.getObjectId("worker").toString()) && !document.getString("status").equals("completed")) {
                            System.out.println(document.getString("task"));
                            tasksExsist = true;
                        }
                    }
                    if (!tasksExsist) System.out.println("No tasks for a moment");
                    System.out.println(divider);
                    break;
                case 2:
                    //Complete task
                    int counter = 1;
                    List ids = new ArrayList();
                    for (Document document : Database.foundedTasks) {
                        if (id.toString().equals(document.getObjectId("worker").toString()) && !document.getString("status").equals("completed")) {
                            ObjectId taskId = document.getObjectId("_id");
                            ids.add(taskId);
                            System.out.println(counter + ") " + document.getString("task"));
                            tasksExsist = true;
                            counter++;
                        }
                    }
                    if (!tasksExsist) {
                        System.out.println("No tasks for a moment");
                        System.out.println(divider);
                        break;
                    }
                    System.out.println("Пожалуйста, напишите дело, которое вы собираетесь выполнить >>>");
                    int chooseTask = in.nextInt();
                    Database.tasks.updateOne(Filters.eq("_id", ids.get(chooseTask - 1)), new Document(
                            "$set",
                            new Document("status", "completed")
                    ));
                    System.out.println("Task completed");
                    System.out.println(divider);
                    break;
                case 3:
                    //Show list of completed tasks
                    for (Document document : Database.foundedTasks) {
                        if (id.toString().equals(document.getObjectId("worker").toString()) && document.getString("status").equals("completed")) {
                            System.out.println(document.getString("task"));
                        }
                    }
                    System.out.println(divider);
                    break;
                case 4:
                    //Show my salary
                    Document founded = Database.users.find(new Document("_id", id)).first();
                    assert founded != null;
                    System.out.println("Your salary is " + founded.getInteger("salary"));
                    System.out.println(divider);
                    break;
                case 5:
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
