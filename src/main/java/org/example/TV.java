package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;


public class TV {

    public static void main(String[] args) {
        ArrayList<TVSet> tvSets;

        try {
            tvSets = readTvsFromFile("C:\\Users\\vikah\\IdeaProjects\\LR6\\src\\main\\java\\org\\example\\var.3");
            ArrayList<TVSet> tvsByName = sortingTVsByName(tvSets);
            ArrayList<TVSet> tvsByPrice = sortingTVsByPriceInOrderOfDecreasingCost(tvSets);
            TVSet mostExpensive = findMostExpensive(tvSets);
            ArrayList<TVSet> highlyRatedTelevisions = findHighlyRatedTelevisions(tvSets);
            double averageCost = calculationOfTheAverageCostOfTVs(tvSets);
            TVSet theCheapest32InchTV = findTheCheapest32InchTV(tvSets);
            boolean allTVsHaveACustomerRatingOfMoreThan4 = checkingThatAllTVsHaveACustomerRatingOfMoreThan4(tvSets);
            boolean aTVWorthMoreThan30000Rubles = checkingIfThereIsATVWorthMoreThan30000Rubles(tvSets);
            TVSet mostExpensive2 = findMostExpensive2(tvSets);
            Map<Boolean, List<TVSet>> tvsInto2Collections = dividingTVsInto2Collections(tvSets);
            Map<String, List<TVSet>> tvByBrand = groupingOfTVsByBrand(tvSets);
            Map<String, Long> statsByBrand = theNumberOfTVsForEachBrand(tvSets);
            Map<String, Double> averagePriceByBrand = theAveragePriceForEachBrand(tvSets);
            String tvModels = tvModelsWithJoining(tvSets);
            writeResultsToFile(tvSets, tvsByName, tvsByPrice, mostExpensive, highlyRatedTelevisions,
                    averageCost, theCheapest32InchTV, allTVsHaveACustomerRatingOfMoreThan4, aTVWorthMoreThan30000Rubles,
                    mostExpensive2, tvsInto2Collections, tvByBrand, averagePriceByBrand, statsByBrand, tvModels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeResultsToFile(ArrayList<TVSet> tvSets, ArrayList<TVSet> sortingTVsByName,
                                           ArrayList<TVSet> sortingTVsByPriceInOrderOfDecreasingCost, TVSet mostExpensive,
                                           ArrayList<TVSet> highlyRatedTelevisions, double averageCost, TVSet theCheapest32InchTV,
                                           boolean allTVsHaveACustomerRatingOfMoreThan4, boolean aTVWorthMoreThan30000Rubles,
                                           TVSet mostExpensive2, Map<Boolean, List<TVSet>> tvsInto2Collections, Map<String,
                                           List<TVSet>> tvByBrand, Map<String, Double> averagePriceByBrand,
                                           Map<String, Long> statsByBrand, String tvModelsWithJoining) {
        try (PrintWriter writer = new PrintWriter("results.txt")) {
            writer.println("Список песен: \n" + tvSets + "\n");
            writer.println("Сортировка телевизоров по наименованию: \n" + sortingTVsByName + "\n");
            writer.println("Сортировка телевизоров по цене в порядке уменьшения стоимости: \n" + sortingTVsByPriceInOrderOfDecreasingCost + "\n");
            writer.println("Самый дорогой телевизор: \n" + mostExpensive + "\n");
            writer.println("Телевизоры с оценкой выше 4,5: \n" + highlyRatedTelevisions + "\n");
            writer.println("Средняя стоимость телевизоров: \n" + averageCost + "\n");
            writer.println("Самый дешевый телевизор с диагональю 32 дюйма: \n" + theCheapest32InchTV + "\n");
            writer.println("У всех телевизоров оценка покупателей больше 4: " + allTVsHaveACustomerRatingOfMoreThan4 + "\n");
            writer.println("Есть ли телевизор стоимостью более 30 тыс. руб.: " + aTVWorthMoreThan30000Rubles + "\n");
            writer.println("Самый дорогой телевизор с помощью maxBy(): \n" + mostExpensive2 + "\n");
            writer.println("Разделение телевизоров на 2 коллекции: с диагональю меньше и больше 30 дюймов: \n" + tvsInto2Collections.get(false) + "\n" + tvsInto2Collections.get(true) + "\n");
            writer.println("Группировка телевизоров по бренду: \n" + tvByBrand + "\n");
            writer.println("Количество телевизоров и средняя цена по брендам: \n" + statsByBrand + "\n" + averagePriceByBrand + "\n");
            writer.println("Модели телевизоров стоимостью от 10 до 15 тыс. руб.: " + tvModelsWithJoining + ".\n");
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения файла");
        }
    }

    private static ArrayList<TVSet> readTvsFromFile(String filePath) throws IOException {
        ArrayList<TVSet> tvSets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\t");
                TVSet tvSet = new TVSet(Integer.parseInt(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Double.parseDouble(parts[5]));
                tvSets.add(tvSet);
            }
        }
        return tvSets;
    }

    private static ArrayList<TVSet> sortingTVsByName(ArrayList<TVSet> tvSets) {
        ArrayList<TVSet> tvsByName = (ArrayList<TVSet>) tvSets.stream()
                .sorted((tv1, tv2) -> tv1.getName().compareTo(tv2.getName()))
                .collect(Collectors.toList());
        return tvsByName;
    }

    private static ArrayList<TVSet> sortingTVsByPriceInOrderOfDecreasingCost(ArrayList<TVSet> tvSets) {
        ArrayList<TVSet> tvsByPrice = (ArrayList<TVSet>) tvSets.stream()
                .sorted((tv1, tv2) -> Integer.compare(tv2.getPricePerItem(), tv1.getPricePerItem()))
                .collect(Collectors.toList());
        return tvsByPrice;
    }
    private static TVSet findMostExpensive(ArrayList<TVSet> tvSets){
        TVSet mostExpensive = tvSets.stream()
                .max((tv1, tv2) -> Integer.compare(tv1.getPricePerItem(), tv2.getPricePerItem()))
                .orElse(new TVSet(0,"anon","anon",0,0,0));
        return mostExpensive;
    }
    private static ArrayList<TVSet> findHighlyRatedTelevisions(ArrayList<TVSet> tvSets){
        ArrayList<TVSet> highlyRatedTelevisions = (ArrayList<TVSet>) tvSets.stream()
                .filter(tv -> tv.getRating() > 4.5)
                .collect(Collectors.toList());
        return highlyRatedTelevisions;
    }

    private static double calculationOfTheAverageCostOfTVs(ArrayList<TVSet> tvSets){
        double averageCost =tvSets.stream()
                .mapToDouble(TVSet :: getPricePerItem)
                .average()
                .orElse(0);
        return averageCost;
    }

    private static TVSet findTheCheapest32InchTV(ArrayList<TVSet> tvSets){
        TVSet theCheapest32InchTV = tvSets.stream()
                .filter(tv -> tv.getDiagonal() == 32)
                .min((tv1, tv2) -> Double.compare(tv1.getPricePerItem(), tv2.getPricePerItem()))
                .orElse(new TVSet(0,"anon","anon",0,0,0));
        return theCheapest32InchTV;
    }

    private static boolean checkingThatAllTVsHaveACustomerRatingOfMoreThan4(ArrayList<TVSet> tvSets){
        boolean allTVsHaveACustomerRatingOfMoreThan4 = tvSets.stream()
                .allMatch(tv -> tv.getRating() > 4);
        return allTVsHaveACustomerRatingOfMoreThan4;
    }

    private static boolean checkingIfThereIsATVWorthMoreThan30000Rubles(ArrayList<TVSet> tvSets){
        boolean aTVWorthMoreThan30000Rubles = tvSets.stream()
                .anyMatch(tv -> tv.getPricePerItem() > 30000);
        return aTVWorthMoreThan30000Rubles;
    }

    private static TVSet findMostExpensive2(ArrayList<TVSet> tvSets){
        TVSet mostExpensive2 = tvSets.stream()
                .collect(Collectors.maxBy(Comparator.comparing(TVSet :: getPricePerItem)))
                .orElse(new TVSet(0,"anon","anon",0,0,0));
        return mostExpensive2;
    }

    private static Map<Boolean, List<TVSet>> dividingTVsInto2Collections(ArrayList<TVSet> tvSets){
        Map<Boolean, List<TVSet>> tvsInto2Collections = tvSets.stream()
                .collect(Collectors.groupingBy(tv -> tv.getDiagonal()>30));
        return (Map<Boolean, List<TVSet>>) tvsInto2Collections;
    }

    private static Map<String, List<TVSet>> groupingOfTVsByBrand(ArrayList<TVSet> tvSets){
        Map<String, List<TVSet>> tvByBrand = tvSets.stream()
                .collect(Collectors.groupingBy(TVSet::getName));
        return (Map<String, List<TVSet>>) tvByBrand;
    }

    private static Map<String, Long> theNumberOfTVsForEachBrand(ArrayList<TVSet> tvSets){
        Map<String, Long> statsByBrand = tvSets.stream()
                .collect(Collectors.groupingBy(TVSet::getBrand, counting()));
        return statsByBrand;
    }

    private static Map<String, Double> theAveragePriceForEachBrand(ArrayList<TVSet> tvSets){
        Map<String, Double> averagePriceByBrand = tvSets.stream()
                .collect(Collectors.groupingBy(TVSet::getBrand, Collectors.averagingDouble(TVSet::getPricePerItem)));
        return averagePriceByBrand;
    }

    private static String tvModelsWithJoining(ArrayList<TVSet> tvSets){
        String tvModels = tvSets.stream()
                .filter(tv -> tv.getPricePerItem() >= 10000 && tv.getPricePerItem() <= 15000)
                .map(TVSet::getName)
                .collect(Collectors.joining(", "));
        return tvModels;
        }
    }