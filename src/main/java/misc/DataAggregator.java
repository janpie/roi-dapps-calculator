package misc;

import misc.data_models.MyAppConfig;
import misc.data_models.Plan;
import misc.data_models.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

public class DataAggregator {

    private static final double projectFeeMultiplier = 1 - (MyAppConfig.devFee / 100);


    public static void printPlansBalance(List<Transaction> transactions) {
        for (int i = 0; i < MyAppConfig.plans.size(); i++) {
            int finalI = i;
            double sum = transactions
                    .stream()
                    .filter(t -> t.getPlan() == finalI)
                    .mapToDouble(Transaction::getValue)
                    .sum() * projectFeeMultiplier;
            System.out.println("\n");
            System.out.println("Total staked in plan " + i + " : " + sum + " " + ChainUtils.getChainCurrency());
            System.out.println("\n");
        }
    }

    public static void printContractHealthPrediction(List<Transaction> transactions, List<Plan> plans) {
        LocalDateTime now = LocalDateTime.now();
        double sum = transactions
                .stream()
                .mapToDouble(Transaction::getValue)
                .sum() * projectFeeMultiplier;

        double balance1 = 0;
        for (int i = 0; i < 43200; i++) {
            LocalDateTime future = now.plusMinutes(i);

            List<Transaction> possibleWithdrawalTransactions = transactions
                    .stream()
                    .filter(tx -> {
                        Plan plan = plans.get(tx.getPlan());
                        LocalDateTime planExpiry = tx.getDateTime().plusDays(plan.getDays());
                        return future.isAfter(planExpiry);
                    })
                    .collect(Collectors.toList());

            double possibleWithdrawalsSum = possibleWithdrawalTransactions
                    .stream()
                    .mapToDouble(tx -> {
                        Plan plan = plans.get(tx.getPlan());
                        double planWithdrawalFeeMultiplier = 1 - (plan.getWithdrawalFee() / 100);
                        double totalReturnMultiplier = plan.getTotalReturn() / 100;
                        return tx.getValue() * totalReturnMultiplier * planWithdrawalFeeMultiplier;
                    })
                    .sum();
            double balance = sum - possibleWithdrawalsSum;
            if (balance != balance1) {
                String date = future.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
                System.out.println("Predicted contract balance for " + date + " : " + String.format("%.2f", balance) + " " + ChainUtils.getChainCurrency());
                balance1 = balance;
            }
            if (balance < -50000) {
                break;
            }
        }

    }

    public static void printLegal() {
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        System.out.format("+--------------------+%n");
        System.out.format("|       Warning      |%n");
        System.out.format("+--------------------+%n");
        System.out.println("\n");
        System.out.println("These are only rough estimations of ROI app lifespan to give you probable time when contract depletes. The app does not guarantee correctness in any way\n");
        System.out.println("  * Calculations are based on existing transactions, plans, developer and withdrawal fees");
        System.out.println("  * Calculations do not take any advanced contract sustain methods into considerations nor dynamic ROIs");
        System.out.println("  * Calculations base on assumption that 'Withdraw-any-time' plans are consumed at the end of their lifespan");
        System.out.println("  * Calculation is based on the assumption that no further deposits will happen into the contract");
        System.out.println("  * Negative values at the end mean that it would take this amount of currency to be deposited into the contract in order to last to the timestamp");
        System.out.println("\n");
        System.out.println("If you like the app you may consider donating ETH/MATIC/SHIB/SAITAMA/AVAX/BNB to 0x3b858B53F000e0DC7Bb3F87090911DE8d9b660B0");
    }
}
