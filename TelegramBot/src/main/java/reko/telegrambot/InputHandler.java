package reko.telegrambot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InputHandler {

    public void handleInput(String input, User user, PizzaCounterBot bot) {
        String[] splitInput = input.split(" ", 2);
        String command = splitInput[0];
        String data = "noData";
        if (splitInput.length == 2) {
            data = splitInput[1];
        }

        switch (command) {
            case "list":
                String message = user.getPizzaEntries().toString();
                System.out.println(user.toString());
                bot.sendMessage(message, user.getChatId());
                break;
            case "add":
                PizzaEntry pizza = parsePizzaEntry(data);
                if (pizza == null) {
                    bot.sendMessage("To add a pizza use the format 'add pizzaname, restaurant, date(dd.mm.yyyy)'", user.getChatId());
                } else {
                    user.addPizzaEntry(pizza);
                    bot.sendMessage("added pizza: "+pizza.toString(), user.getChatId());
                }
                break;
            case "help":
                bot.sendMessage(this.help(), user.getChatId());
                break;
            default:
                bot.sendMessage(data, user.getChatId());

        }
    }

    public String help() {
        return "List all your pizzas by typing 'list'\n"
                + "Add a pizza by typing 'add pizzaname, restaurant, date(dd.mm.yyyy)', no date means current date";
    }
    
    public PizzaEntry parsePizzaEntry(String data) {
        String[] pizzaData = data.split(", ");
        try {
            String pizzaName = pizzaData[0];
            String restaurantName = pizzaData[1];
            Date dateEaten;
            if (pizzaData.length == 2) {
                dateEaten = new Date();
            } else {
                dateEaten = new SimpleDateFormat("dd.mm.yyyy").parse(pizzaData[2]);
            }
            return new PizzaEntry(pizzaName, restaurantName, dateEaten);
            
        } catch (Exception e) {
            System.out.println("could not parse data");
            return null;
        }
    }
}
