package application;

import model.entities.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        List<Product> list = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new FileReader("/home/ana-meira/Documentos/sourceFile.txt"))){

            while (true){
                String line = bf.readLine();

                if (line == null){
                    break;
                }

                String[] campos = line.split(",");

                list.add(new Product(campos[0], Double.parseDouble(campos[1])));
            }


        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        Double averagePrice = list.stream()
                .map(p -> p.getPrice())
                .reduce(0.0, (x, y) -> x+y) / list.size();

        System.out.println("Average price: " + averagePrice);

        List<String> names = list.stream()
                .filter(x -> x.getPrice() < averagePrice)
                .map(x -> x.getName())
                .sorted((x1, x2) -> -x1.compareTo(x2))
                .collect(Collectors.toList());

        names.forEach(System.out::println);
    }
}
