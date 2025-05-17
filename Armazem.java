import java.util.LinkedList;
import java.util.Queue;

public class Armazem {
    private final int CAPACIDADE = 5;
    private Queue<Integer> fila = new LinkedList<>();

    // Método para o produtor adicionar itens
    public synchronized void produzir(int item) throws InterruptedException {
        while (fila.size() == CAPACIDADE) {
            System.out.println("Armazém cheio! Produtor aguardando...");
            wait(); // espera até que haja espaço
        }

        fila.add(item);
        System.out.println("Produzido: " + item);
        notify(); // avisa o consumidor
    }

    // Método para o consumidor remover itens
    public synchronized int consumir() throws InterruptedException {
        while (fila.isEmpty()) {
            System.out.println("Armazém vazio! Consumidor aguardando...");
            wait(); // espera até que haja item
        }

        int item = fila.remove();
        System.out.println("Consumido: " + item);
        notify(); // avisa o produtor
        return item;
    }

    // MAIN
    public static void main(String[] args) {
        Armazem armazem = new Armazem();

        // Produtor
        Thread produtor = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    armazem.produzir(i);
                    Thread.sleep(300); // simula tempo de produção
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Consumidor
        Thread consumidor = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    armazem.consumir();
                    Thread.sleep(500); // simula tempo de consumo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        produtor.start();
        consumidor.start();
    }
}
