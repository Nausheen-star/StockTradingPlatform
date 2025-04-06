import java.util.*;

class Stock {
    String symbol;
    double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public void updatePrice() {
        // Simulate price change
        double change = (Math.random() - 0.5) * 10;
        price = Math.max(1, price + change);
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    double balance;

    public Portfolio(double initialBalance) {
        this.balance = initialBalance;
    }

    public void buyStock(String symbol, int quantity, double price) {
        double cost = quantity * price;
        if (cost <= balance) {
            balance -= cost;
            holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
            System.out.println("Bought " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void sellStock(String symbol, int quantity, double price) {
        int owned = holdings.getOrDefault(symbol, 0);
        if (quantity <= owned) {
            holdings.put(symbol, owned - quantity);
            balance += quantity * price;
            System.out.println("Sold " + quantity + " shares of " + symbol);
        } else {
            System.out.println("You don't own enough shares.");
        }
    }

    public void viewPortfolio(Map<String, Stock> market) {
        System.out.println("\n--- Portfolio ---");
        double totalValue = balance;
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            int qty = entry.getValue();
            double currentPrice = market.get(symbol).price;
            double value = qty * currentPrice;
            totalValue += value;
            System.out.printf("%s - %d shares @ %.2f each (%.2f total)\n",
                    symbol, qty, currentPrice, value);
        }
        System.out.printf("Cash Balance: $%.2f\n", balance);
        System.out.printf("Total Portfolio Value: $%.2f\n", totalValue);
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", 150));
        market.put("GOOG", new Stock("GOOG", 2800));
        market.put("AMZN", new Stock("AMZN", 3300));
        market.put("TSLA", new Stock("TSLA", 700));

        Portfolio portfolio = new Portfolio(10000); // Starting with $10,000

        while (true) {
            // Update stock prices
            for (Stock stock : market.values()) {
                stock.updatePrice();
            }

            System.out.println("\n--- Stock Trading Platform ---");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Market Data ---");
                    for (Stock stock : market.values()) {
                        System.out.printf("%s : $%.2f\n", stock.symbol, stock.price);
                    }
                    break;

                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.next().toUpperCase();
                    if (!market.containsKey(buySymbol)) {
                        System.out.println("Stock not found.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int buyQty = scanner.nextInt();
                    portfolio.buyStock(buySymbol, buyQty, market.get(buySymbol).price);
                    break;

                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.next().toUpperCase();
                    if (!market.containsKey(sellSymbol)) {
                        System.out.println("Stock not found.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int sellQty = scanner.nextInt();
                    portfolio.sellStock(sellSymbol, sellQty, market.get(sellSymbol).price);
                    break;

                case 4:
                    portfolio.viewPortfolio(market);
                    break;

                case 5:
                    System.out.println("Exiting... Happy trading!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
