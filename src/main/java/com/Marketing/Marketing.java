package com.Marketing;

import com.Database.Database;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Marketing {
    public static void main(String name) {
        Scanner in = new Scanner(System.in);
        final String budgetID = "606624505afe5468c793940f";

        String s="";
        int marketing;
        boolean status = true;

        System.out.println("Greetings, " + name + "!");
        while (status) {
            System.out.println("Type the number of option. Type 5 when you're done:");
            System.out.println("1. Show a list of categories for marketing;");
            System.out.println("2. Show the allocated budget for a specific category of marketing sites;");
            System.out.println("3. Show marketing budget;");
            System.out.println("4. Spend your budget on promotion;");
            System.out.println("5. Exit.");
            System.out.println("Choose option: >>");
            String divider = "=====================";
            int choice = in.nextByte();

            List tags = new ArrayList();

            for (Document document : Database.foundedBudgets) {
                if (!(tags.contains(document.getString("place"))) && document.getString("place") != null)
                    tags.add(document.getString("place"));
            }

            long many=0;
            for (Document document : Database.foundedBudgets) {
                if (budgetID.equals(document.getObjectId("_id").toString())) {
                    many = document.getLong("marketing");


                }
            }

            switch (choice) {
                case 1:
                    for(int i=0; i<tags.size(); i++){
                        System.out.println(tags.get(i));
                    }
                    System.out.println(divider);
                    break;

                case 2:

                    while (true) {
                        System.out.println("Choose marketing zone:");
                        for (int i = 0; i < tags.size(); i++) {
                            System.out.println((i + 1) + ". " + tags.get(i));
                        }
                        System.out.println("Choose zone: >>");
                        marketing = in.nextInt();
                        if (marketing < tags.size() && marketing >= 0) {
                            s = (String) (tags.get(marketing));
                            break;
                        }
                    }
                    System.out.println("Actual projects");
                    for(Document document : Database.foundedBudgets) {
                        if (s.equals(document.getString("place")) && document.getInteger("cost")!=null && document.getString("name of event")!=null) {
                            System.out.printf("%s: %d%n", document.getString("name of event"), document.getInteger("cost"));
                        }
                    }
                    System.out.println(divider);
                    break;

                case 3:
                    System.out.println("budget of marketing: " + many + "\n");
                    int sumM = 0;
                    for (Document document : Database.foundedBudgets) {
                        if ("marketing".equals(document.getString("type"))) {
                            sumM += document.getInteger("cost");
                        }
                    }
                    System.out.printf("used budget: %d%n%n", sumM);
                    System.out.println(divider);
                    break;

                case 4:
                    System.out.println("Type name of promotion:");
                    for (int i = 0; i < tags.size(); i++) {
                        System.out.println((i + 1) + ". " + tags.get(i));
                    }
                    System.out.println("Choose zone: >>");
                    marketing = in.nextInt();
                    if (marketing < tags.size() && marketing >= 0) {
                        s = (String) (tags.get(marketing));
                    }

                    sumM = 0;
                    for (Document document : Database.foundedBudgets) {
                        if ("marketing".equals(document.getString("type"))) {
                            sumM += document.getInteger("cost");
                        }
                    }

                    System.out.print("Type sum you want to spend: >>");
                    int cost = in.nextInt();

                    if (cost+sumM <= many){
                        System.out.println("Type name of project>>>");
                        String projectName = in.next();

                        Document project = new Document("place", s);
                        project.put("type", "marketing");
                        project.put("cost", cost);
                        project.put("name of event", projectName);

                        Database.budget.insertOne(project);
                        System.out.println("Project created successful!");
                    }else{
                        System.out.println("Insufficient funds have been allocated, please discuss this with the Director");
                    }
                    System.out.println(divider);
                    break;
                case 5:
                    status = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    break;
            }
        }
    }
}