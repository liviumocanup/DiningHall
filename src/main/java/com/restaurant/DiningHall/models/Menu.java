package com.restaurant.DiningHall.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
//    private static final List<Food> foodList = new ArrayList<>();
//
//    public static List<Food> getFoodList(){
//        return foodList;
//    }
//
//    public static Long[] getAvailableIds(){
//        Long[] ids = new Long[foodList.size()];
//        for(int i=0; i<ids.length; i++){
//            ids[i] = foodList.get(i).getId();
//        }
//        return ids;
//    }
//
//    public static void loadDefault() throws FileNotFoundException {
//        String fileName = "src/main/java/com/restaurant/DiningHall/constants/foods.txt";
//        Scanner scanner = new Scanner(new File(fileName));
//
//        while (scanner.hasNextLine()) {
//            String data = scanner.nextLine();
//
//            if(data.isBlank())
//                continue;
//
//            int id = -1, preparationTime = -1, complexity = -1;
//            String name = "", cookingApparatus = "";
//
//            while (!data.equals("}")){
//                if(!data.equals("{")) {
//                    String[] toLoad = data.split(":");
//                    toLoad[0] = toLoad[0].substring(1, toLoad[0].length() - 1);
//                    if (!toLoad[1].trim().equals("null"))
//                        toLoad[1] = toLoad[1].substring(1, toLoad[1].length() - 1);
//
//                    switch (toLoad[0]) {
//                        case "id":
//                            id = Integer.parseInt(toLoad[1]);
//                            break;
//                        case "name":
//                            name = toLoad[1].substring(1, toLoad[1].length()-1);
//                            break;
//                        case "preparation-time":
//                            preparationTime = Integer.parseInt(toLoad[1]);
//                            break;
//                        case "complexity":
//                            complexity = Integer.parseInt(toLoad[1]);
//                            break;
//                        case "cooking-apparatus":
//                            cookingApparatus = toLoad[1].substring(1);
//                            break;
//                        default:
//                            System.out.println("ERROR");
//                    }
//                }
//                data = scanner.nextLine();
//            }
//            if(id == -1 || preparationTime == -1 || complexity == -1 || name.isBlank() || cookingApparatus.isBlank())
//                System.out.println("FOOD PARAMETER SET INCORRECTLY");
//            else foodList.add(new Food((long)id, name, (long)preparationTime, complexity, cookingApparatus));
//            //should check for duplicates
//        }
//        scanner.close();
//    }
}
