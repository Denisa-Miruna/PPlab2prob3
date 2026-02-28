import org.graalvm.polyglot.*;
import java.util.*;

public class Polyglot {

    public static void main(String[] args) {
        // 1. Construim contextul cu acces la Python
        try (Context context = Context.newBuilder().allowAllAccess(true).build()) {

            // --- FUNCȚIA 1 (PYTHON): Generare listă ---

            Value pythonList = context.eval("python", """
                import random
                def generate_numbers():
                    lista = []
                    for i in range(20):
                        numar = random.randint(1, 100)
                        lista.append(numar)
                    return lista
                
                # Apelăm funcția ca să obținem rezultatul
                generate_numbers()
                """);

            // Convertim rezultatul din Python într-o listă Java (List<Integer>)
            List<Integer> numbers = new ArrayList<>();
            for (long i = 0; i < pythonList.getArraySize(); i++) {
                numbers.add(pythonList.getArrayElement(i).asInt());
            }

            // --- FUNCȚIA 2 (JAVA): Afișare listă ---
            displayList(numbers);

            // --- FUNCȚIA 3 (JAVA): Procesare și Medie ---
            double media = processAndCalculateAverage(numbers);

            System.out.println("Media aritmetică după filtrare : " + media);
        }
    }

    // Funcția Java pentru afișare
    private static void displayList(List<Integer> list) {
        System.out.println(list);
    }

    // Funcția Java pentru sortare, filtrare și medie
    private static double processAndCalculateAverage(List<Integer> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++)
        {
            for (int j = i+1; j < n ; j++)
            {
                // Dacă elementul curent e mai mare decât următorul, le schimbăm cu locul
                if (list.get(i) > list.get(j))
                {
                   int temp = list.get(i);
                   list.set(i, list.get(j));
                   list.set(j, temp);
                }
            }
        }

        displayList(list);

        int toRemove = (int) (n * 0.20); // 4 elemente

        List<Integer> filteredList = list.subList(toRemove, n - toRemove);

        // Logica simplă pentru medie
        double suma = 0;
        for (int numar : filteredList) {
            suma += numar;
        }

        return filteredList.isEmpty() ? 0 : suma / filteredList.size();
    }
}