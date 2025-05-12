public class Main extends Thread {
    private long numero;

    public Main(long numero) {
        this.numero = numero;
    }

    @Override
    public void run() {
        boolean primo = verificarPrimo(numero);
        System.out.println("Número " + numero + (primo ? " é primo." : " não é primo."));
    }

    private boolean verificarPrimo(long n) {
        if (n < 2) return false;
        for (long i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        long num1 = 1_000_000_039L;
        long num2 = 1_000_000_061L;
        long num3 = 1_000_000_069L;

        // Execução sequencial
        System.out.println("🕒 Execução SEQUENCIAL:");
        long inicioSeq = System.currentTimeMillis();

        new Main(num1).run();
        new Main(num2).run();
        new Main(num3).run();

        long fimSeq = System.currentTimeMillis();
        System.out.println("Tempo sequencial: " + (fimSeq - inicioSeq) + " ms\n");

        // Execução paralela
        System.out.println("⚡ Execução PARALELA:");
        Main t1 = new Main(num1);
        Main t2 = new Main(num2);
        Main t3 = new Main(num3);

        long inicioPar = System.currentTimeMillis();

        t1.start();
        t2.start();
        t3.start();

        t1.join(); // Espera cada thread terminar
        t2.join();
        t3.join();

        long fimPar = System.currentTimeMillis();
        System.out.println("Tempo paralelo: " + (fimPar - inicioPar) + " ms");
    }
}
