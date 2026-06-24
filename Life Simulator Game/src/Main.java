import java.util.Scanner;

// --- PERSON CLASS (The Stats) ---
class Person {
    String name;
    int age = 18; 
    int health = 100;
    int happiness = 80;
    int intelligence = 50;
    int money = 1000; 

    public Person(String name) {
        this.name = name;
    }

    public boolean isAlive() {
        return health > 0 && age < 100; 
    }
}

// --- MAIN GAME ENGINE ---
public class Main {
    // ANSI Colors for a professional UI
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String PURPLE = "\u001B[35m";

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    // --- UI HELPER METHOD ---
    public static String getProgressBar(int value, String color) {
        int solidBars = value / 10;
        StringBuilder bar = new StringBuilder(color + "[");
        for (int i = 0; i < 10; i++) {
            if (i < solidBars) {
                bar.append("█"); 
            } else {
                bar.append("░"); 
            }
        }
        bar.append("] ").append(value).append("%").append(RESET);
        return bar.toString();
    }

    // --- ANIMATION: ROLLING DICE ---
    public static void playDiceAnimation() {
        String[] frames = {"[ ⚀ ]", "[ ⚁ ]", "[ ⚂ ]", "[ ⚃ ]", "[ ⚄ ]", "[ ⚅ ]"};
        System.out.print(CYAN + "Rolling the dice for your fate... " + RESET);
        
        for (int i = 0; i < 15; i++) { 
            System.out.print("\r" + CYAN + "Rolling the dice for your fate... " + YELLOW + frames[i % 6] + RESET);
            try { Thread.sleep(100); } catch (Exception e) {} 
        }
        System.out.println(); 
    }

    // --- ANIMATION: GAME OVER SKULL ---
    public static void playDeathAnimation() {
        String[] colors = {RED, YELLOW, RESET}; 
        for (int i = 0; i < 6; i++) {
            clearScreen();
            String color = colors[i % 2]; 
            System.out.println(color + "      .@@@@@@@@.      ");
            System.out.println("    .@@@@@@@@@@@@.    ");
            System.out.println("   @@@@@@@@@@@@@@@@   ");
            System.out.println("  @@@@@@@@@@@@@@@@@@  ");
            System.out.println("  @@@@@  @@@@  @@@@@  ");
            System.out.println("  @@@@@  @@@@  @@@@@  ");
            System.out.println("  @@@@@@@@@@@@@@@@@@  ");
            System.out.println("   @@@@@@@@@@@@@@@@   ");
            System.out.println("    .@@@@@@@@@@@@.    ");
            System.out.println("       @@@@@@@@       " + RESET);
            System.out.println(RED + "\n      YOU DIED." + RESET);
            try { Thread.sleep(400); } catch (Exception e) {}
        }
    }

    // --- INVESTMENT LOGIC ---
    public static void handleInvestments(Person player, Scanner scanner) {
        boolean investing = true;
        clearScreen();

        while (investing) {
            System.out.println(GREEN + "=== ADVANCED INVESTMENT MARKET ===" + RESET);
            System.out.println("Your Market Knowledge (Intelligence): " + CYAN + player.intelligence + "/100" + RESET);
            System.out.println("Available Balance: " + GREEN + String.format("Rs.%,d", player.money) + RESET);
            System.out.println("\n1. Stock Market (Medium Risk, Depends on Intelligence)");
            System.out.println("2. Crypto Day Trading (High Risk, Heavily Depends on Intelligence)");
            System.out.println("3. Fixed Deposit / Bonds (Zero Risk, Low Reward)");
            System.out.println("4. Exit Investment Market (Go back to normal life)");
            System.out.print("\nChoose your investment strategy: ");

            String choice = scanner.nextLine();

            if (choice.equals("4")) {
                System.out.println("Leaving the market...");
                investing = false;
                continue;
            }

            if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                System.out.println(RED + "Invalid choice!" + RESET);
                continue;
            }

            System.out.print("Enter amount to invest: Rs.");
            int investAmount = 0;
            
            try {
                investAmount = Integer.parseInt(scanner.nextLine().replace(",", ""));
            } catch (NumberFormatException e) {
                System.out.println(RED + "Error: Please enter numbers only!" + RESET);
                continue;
            }

            if (investAmount <= 0 || investAmount > player.money) {
                System.out.println(RED + "Insufficient funds or invalid amount!" + RESET);
                continue;
            }

            player.money -= investAmount;
            playDiceAnimation();
            int roll = (int)(Math.random() * 100) + 1;

            if (choice.equals("1")) {
                int winChance = 30 + (player.intelligence / 2); 
                if (roll <= winChance) {
                    int profit = (int)(investAmount * 1.6); 
                    System.out.println(GREEN + "SUCCESS! The stock price surged. You received " + String.format("Rs.%,d", profit) + "!" + RESET);
                    player.money += profit;
                } else {
                    System.out.println(RED + "CRASH! The company went bankrupt. You lost your investment." + RESET);
                }
            } 
            else if (choice.equals("2")) {
                int winChance = 10 + (player.intelligence / 2);
                if (roll <= winChance) {
                    int profit = (int)(investAmount * 3.0); 
                    System.out.println(GREEN + "TO THE MOON! Your crypto blew up! You received " + String.format("Rs.%,d", profit) + "!" + RESET);
                    player.money += profit;
                } else {
                    System.out.println(RED + "RUG PULL! The crypto was a scam. You lost everything you put in." + RESET);
                }
            }
            else if (choice.equals("3")) {
                int profit = (int)(investAmount * 1.1); 
                System.out.println(GREEN + "SAFE INVESTMENT! Your bonds matured safely. You received " + String.format("Rs.%,d", profit) + "." + RESET);
                player.money += profit;
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            clearScreen();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(CYAN + "=== WELCOME TO THE JAVA LIFE SIMULATOR ===" + RESET);
        System.out.print("Enter your character's name to begin: ");
        String playerName = scanner.nextLine();

        Person player = new Person(playerName);
        boolean gameRunning = true;
        boolean ultimateWin = false; // New Win Flag!

        clearScreen();

        // THE CORE AGEING LOOP
        while (gameRunning && player.isAlive()) {
            
            // --- THE BANKRUPTCY & JAIL MECHANIC ---
            if (player.money <= 0) {
                System.out.println(RED + "╔══════════════════════════════════════════════════╗" + RESET);
                System.out.println(RED + "║ [CRITICAL ALERT] YOU ARE COMPLETELY BROKE!       ║" + RESET);
                System.out.println(RED + "╚══════════════════════════════════════════════════╝" + RESET);
                System.out.println("You have Rs.0. You are starving and desperate. You must make a choice:");
                System.out.println(GREEN + "1. GOOD SIDE: Do extreme hard labor (Earn Rs.5,000 to survive, huge Happiness drop)" + RESET);
                System.out.println(PURPLE + "2. BAD SIDE: Attempt an Armed Robbery (50% chance to win Rs.500,000 OR 5 Years in Jail!)" + RESET);
                System.out.print("Make your survival choice: ");
                
                String brokeChoice = scanner.nextLine();
                clearScreen();
                
                if (brokeChoice.equals("2")) {
                    System.out.println(PURPLE + "You put on a mask and enter the bank..." + RESET);
                    playDiceAnimation();
                    int robChance = (int)(Math.random() * 100);
                    
                    if (robChance > 50) { 
                        System.out.println(GREEN + "SUCCESS! You pulled off the ultimate heist! You escaped with Rs.500,000!" + RESET);
                        player.money += 500000;
                        player.age++; 
                    } else {
                        System.out.println(RED + "BUSTED! The police surrounded the bank!" + RESET);
                        System.out.println(RED + "You have been sentenced to 5 YEARS IN MAXIMUM SECURITY PRISON." + RESET);
                        player.age += 5; 
                        player.happiness = 0;
                        player.health -= 20;
                        System.out.println("\nPress Enter to serve your sentence...");
                        scanner.nextLine();
                        clearScreen();
                        continue; 
                    }
                } else {
                    System.out.println(YELLOW + "You swallowed your pride and worked hard labor on the streets." + RESET);
                    player.money += 5000;
                    player.happiness -= 40;
                    player.age++; 
                }
                
                System.out.println("\nPress Enter to continue your life...");
                scanner.nextLine();
                clearScreen();
                continue; 
            }

            // --- THE WEALTH POPUP TRIGGER ---
            if (player.money >= 100000 && player.money < 10000000) { // Only ask if they haven't won yet
                System.out.println(YELLOW + "\n******************************************************" + RESET);
                System.out.println(GREEN + " [WEALTH ALERT] You have accumulated over Rs.100,000!" + RESET);
                System.out.print(YELLOW + " Would you like to invest this money for the future? (Y/N): " + RESET);
                String investDecision = scanner.nextLine();
                
                if (investDecision.equalsIgnoreCase("Y")) {
                    handleInvestments(player, scanner);
                }
                System.out.println(YELLOW + "******************************************************\n" + RESET);
            }

            // Beautiful Unicode Box UI
            System.out.println(BLUE + "╔══════════════════════════════════════════════════╗" + RESET);
            System.out.println(BLUE + "║ " + YELLOW + "--- LIFE DASHBOARD ---                           " + BLUE + "║" + RESET);
            System.out.println(BLUE + "╠══════════════════════════════════════════════════╣" + RESET);
            System.out.println(BLUE + "║ " + RESET + "Name:         " + player.name);
            System.out.println(BLUE + "║ " + RESET + "Age:          " + player.age + " Years Old");
            System.out.println(BLUE + "║ " + RESET + "Health:       " + getProgressBar(player.health, RED));
            System.out.println(BLUE + "║ " + RESET + "Happiness:    " + getProgressBar(player.happiness, YELLOW));
            System.out.println(BLUE + "║ " + RESET + "Intelligence: " + getProgressBar(player.intelligence, CYAN));
            System.out.println(BLUE + "║ " + RESET + "Money:        " + GREEN + String.format("Rs.%,d", player.money) + RESET);
            System.out.println(BLUE + "╚══════════════════════════════════════════════════╝" + RESET);
            
            System.out.println("\nWhat will you do this year?");
            System.out.println("1. Study hard (Increases Intelligence, Lowers Happiness)");
            System.out.println("2. Work a full-time job (Increases Money, Lowers Health & Happiness)");
            System.out.println("3. Go to the Gym (Increases Health, Costs Money)");
            System.out.println("4. Go on a Vacation (Increases Happiness, Costs lots of Money)");
            System.out.println(PURPLE + "5. Party all weekend! (Huge Happiness boost, lowers Health/Intelligence, drains Money)" + RESET);
            System.out.println(PURPLE + "6. Eat junk food & play games (Quick Happiness boost, terrible for Health)" + RESET);
            System.out.println("7. Retire & Exit Game");
            System.out.print("Make your life choice: ");

            String choice = scanner.nextLine();
            clearScreen();

            if (choice.equals("1")) {
                System.out.println("You studied hard all year.");
                player.intelligence += 15;
                player.happiness -= 10;
            } else if (choice.equals("2")) {
                System.out.println("You worked 60 hours a week.");
                player.money += 40000; 
                player.health -= 15;
                player.happiness -= 15;
            } else if (choice.equals("3")) {
                if (player.money >= 5000) {
                    System.out.println("You hit the gym and got shredded!");
                    player.health += 20;
                    player.money -= 5000;
                } else {
                    System.out.println(RED + "You don't have enough money for a gym membership! You wasted a year." + RESET);
                }
            } else if (choice.equals("4")) {
                if (player.money >= 30000) {
                    System.out.println("You took a relaxing trip to Nuwara Eliya!");
                    player.happiness += 30;
                    player.money -= 30000;
                } else {
                    System.out.println(RED + "You are too broke for a vacation! You wasted a year." + RESET);
                }
            } else if (choice.equals("5")) {
                System.out.println(PURPLE + "You partied every single weekend! It was wild, but you feel terrible now." + RESET);
                player.happiness += 25;
                player.health -= 25;
                player.intelligence -= 10; 
                player.money -= 15000;
            } else if (choice.equals("6")) {
                System.out.println(PURPLE + "You ate Kottu and played mobile games every night. Fun, but you gained weight." + RESET);
                player.happiness += 15;
                player.health -= 15;
                player.money -= 3000;
            } else if (choice.equals("7")) {
                System.out.println("You decided to retire early. Goodbye!");
                gameRunning = false;
                continue;
            } else {
                System.out.println(RED + "Invalid choice! You wasted a year doing nothing." + RESET);
            }

            // --- THE RANDOM CHAOS ENGINE ---
            System.out.println();
            playDiceAnimation(); 
            
            int randomEvent = (int)(Math.random() * 100);
            
            try {
                if (randomEvent < 10) {
                    System.out.println(GREEN + "LUCKY EVENT: You found Rs.10,000 on the street!" + RESET);
                    player.money += 10000;
                } else if (randomEvent >= 10 && randomEvent < 20) {
                    System.out.println(RED + "UNLUCKY EVENT: You got Dengue fever! (-20 Health)" + RESET);
                    player.health -= 20;
                } else if (randomEvent >= 20 && randomEvent < 25 && player.health < 50) {
                    System.out.println(RED + "BAD HABIT CONSEQUENCE: You ended up in the hospital! Paid Rs.15,000 in medical bills." + RESET);
                    player.money -= 15000;
                } else {
                    System.out.println(YELLOW + "Another normal year passes by..." + RESET);
                }
                
                Thread.sleep(2500); 
                clearScreen();
                
            } catch (InterruptedException e) {
                System.out.println("Time glitch!");
            }

            // Age up!
            player.age++;

            // Prevent stats from going over 100
            if (player.health > 100) player.health = 100;
            if (player.happiness > 100) player.happiness = 100;
            if (player.intelligence > 100) player.intelligence = 100;
            
            // Prevent stats from dropping below 0 
            if (player.happiness < 0) player.happiness = 0;
            if (player.intelligence < 0) player.intelligence = 0;

            // --- THE ULTIMATE WIN CONDITION CHECK ---
            if (player.health == 100 && player.happiness == 100 && player.intelligence == 100 && player.money >= 5000000) {
                ultimateWin = true;
                break; // Break the while loop immediately to trigger the win!
            }
        }

        // --- FINAL GAME OVER / WIN LOGIC ---
        if (ultimateWin) {
            clearScreen();
            System.out.println(YELLOW + "╔══════════════════════════════════════════════════╗" + RESET);
            System.out.println(YELLOW + "║ " + GREEN + "🏆 ULTIMATE VICTORY ACHIEVED! 🏆                 " + YELLOW + "║" + RESET);
            System.out.println(YELLOW + "╚══════════════════════════════════════════════════╝" + RESET);
            System.out.println(CYAN + "Congratulations, " + player.name + "!" + RESET);
            System.out.println("You achieved perfect Health, Happiness, and Intelligence!");
            System.out.println("You also amassed a massive fortune of " + GREEN + String.format("Rs.%,d", player.money) + RESET + "!");
            System.out.println(PURPLE + "You are truly a SUCCESSFUL PERSON. You beat the game of life at age " + player.age + "!" + RESET);
        } else if (player.health <= 0) {
            playDeathAnimation(); 
            System.out.println(RED + "\nGAME OVER! " + player.name + " died at the age of " + player.age + " due to poor health choices." + RESET);
            System.out.println("Final Bank Balance: " + String.format("Rs.%,d", player.money));
        } else if (player.age >= 100) {
            System.out.println(GREEN + "\nCONGRATULATIONS! " + player.name + " lived a long, full life to 100 years old!" + RESET);
            System.out.println("Final Bank Balance: " + String.format("Rs.%,d", player.money));
        } else {
            // If they just retired early
            System.out.println("Final Bank Balance: " + String.format("Rs.%,d", player.money));
        }
        
        scanner.close();
    }
}