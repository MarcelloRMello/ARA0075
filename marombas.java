import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// -------------------- 1. CLASSES E OBJETOS --------------------

// Classe base para todos os produtos de estoque
class Produto {
    private int id;
    private String nome;
    private double preco;
    private int quantidadeEstoque;

    public Produto(int id, String nome, double preco, int quantidadeEstoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // Getters (Encapsulamento)
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    // Setters (Encapsulamento)
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // -------------------- 5. PRINCIPAIS MÉTODOS DE OBJETOS EM JAVA --------------------

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Preço: R$" + String.format("%.2f", preco) + ", Estoque: " + quantidadeEstoque;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto produto = (Produto) obj;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    // Método comum a todos os produtos (Polimorfismo - será sobrescrito nas subclasses)
    public String obterInformacoesAdicionais() {
        return "Nenhuma informação adicional.";
    }
}

// -------------------- 2. IMPLEMENTAÇÃO DE HERANÇA E HIERARQUIA DE HERANÇA EM JAVA --------------------

// Classe para Suplementos (Herança de Produto)
class Suplemento extends Produto {
    private String sabor;
    private String tipo; // Ex: Proteína, Creatina, Termogênico

    public Suplemento(int id, String nome, double preco, int quantidadeEstoque, String sabor, String tipo) {
        super(id, nome, preco, quantidadeEstoque);
        this.sabor = sabor;
        this.tipo = tipo;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String obterInformacoesAdicionais() {
        return "Sabor: " + sabor + ", Tipo: " + tipo;
    }
}

// Classe para Acessórios (Herança de Produto)
class Acessorio extends Produto {
    private String material;
    private String utilidade; // Ex: Coqueteleira, Cinta de Treino

    public Acessorio(int id, String nome, double preco, int quantidadeEstoque, String material, String utilidade) {
        super(id, nome, preco, quantidadeEstoque);
        this.material = material;
        this.utilidade = utilidade;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getUtilidade() {
        return utilidade;
    }

    public void setUtilidade(String utilidade) {
        this.utilidade = utilidade;
    }

    @Override
    public String obterInformacoesAdicionais() {
        return "Material: " + material + ", Utilidade: " + utilidade;
    }
}

// -------------------- 7. CRIAÇÃO E O USO DE INTERFACES EM JAVA --------------------

interface OperacoesEstoque {
    void adicionarProduto(Produto produto);
    void removerProduto(int id) throws ProdutoNaoEncontradoException;
    Produto buscarProduto(int id) throws ProdutoNaoEncontradoException;
    void atualizarProduto(Produto produto) throws ProdutoNaoEncontradoException;
    void listarProdutos();
}

// -------------------- 3. IMPLEMENTAÇÃO DE AGRUPANDO OBJETOS --------------------

class Estoque implements OperacoesEstoque {
    private List<Produto> produtos = new ArrayList<>();
    private int proximoId = 1;

    @Override
    public void adicionarProduto(Produto produto) {
        produto.setId(proximoId++);
        produtos.add(produto);
        System.out.println("Produto adicionado ao estoque.");
    }

    @Override
    public void removerProduto(int id) throws ProdutoNaoEncontradoException {
        Produto produtoRemover = buscarProduto(id);
        if (produtoRemover != null) {
            produtos.remove(produtoRemover);
            System.out.println("Produto com ID " + id + " removido do estoque.");
        } else {
            throw new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado no estoque.");
        }
    }

    @Override
    public Produto buscarProduto(int id) throws ProdutoNaoEncontradoException {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        throw new ProdutoNaoEncontradoException("Produto com ID " + id + " não encontrado no estoque.");
    }

    @Override
    public void atualizarProduto(Produto produtoAtualizado) throws ProdutoNaoEncontradoException {
        Produto produtoExistente = buscarProduto(produtoAtualizado.getId());
        if (produtoExistente != null) {
            produtoExistente.setNome(produtoAtualizado.getNome());
            produtoExistente.setPreco(produtoAtualizado.getPreco());
            produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
            if (produtoAtualizado instanceof Suplemento && produtoExistente instanceof Suplemento) {
                ((Suplemento) produtoExistente).setSabor(((Suplemento) produtoAtualizado).getSabor());
                ((Suplemento) produtoExistente).setTipo(((Suplemento) produtoAtualizado).getTipo());
            } else if (produtoAtualizado instanceof Acessorio && produtoExistente instanceof Acessorio) {
                ((Acessorio) produtoExistente).setMaterial(((Acessorio) produtoAtualizado).getMaterial());
                ((Acessorio) produtoExistente).setUtilidade(((Acessorio) produtoAtualizado).getUtilidade());
            }
            System.out.println("Produto com ID " + produtoAtualizado.getId() + " atualizado.");
        } else {
            throw new ProdutoNaoEncontradoException("Produto com ID " + produtoAtualizado.getId() + " não encontrado no estoque.");
        }
    }

    @Override
    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("O estoque está vazio.");
        } else {
            System.out.println("--- Produtos no Estoque ---");
            for (Produto produto : produtos) {
                System.out.println(produto + " - " + produto.obterInformacoesAdicionais());
            }
        }
    }
}

// -------------------- 8. IMPLEMENTAÇÃO DE TRATAMENTO DE EXCEÇÕES EM JAVA --------------------

// Classe de Exceção Personalizada (Herança de Exception)
class ProdutoNaoEncontradoException extends Exception {
    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

public class Marombas {
    private static Estoque estoque = new Estoque();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            mostrarMenu();
            int opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1:
                        adicionarNovoProduto();
                        break;
                    case 2:
                        removerProduto();
                        break;
                    case 3:
                        buscarProduto();
                        break;
                    case 4:
                        atualizarProduto();
                        break;
                    case 5:
                        listarProdutos();
                        break;
                    case 6:
                        System.out.println("Saindo do sistema de estoque.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Erro: Entrada inválida. Por favor, digite um número inteiro.");
                scanner.next(); // Limpar o buffer do scanner
            } catch (ProdutoNaoEncontradoException e) {
                System.err.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Sistema de Estoque de Suplementos ---");
        System.out.println("1. Adicionar Novo Produto");
        System.out.println("2. Remover Produto");
        System.out.println("3. Buscar Produto");
        System.out.println("4. Atualizar Produto");
        System.out.println("5. Listar Produtos");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        return scanner.nextInt();
    }

    private static void adicionarNovoProduto() {
        System.out.println("\n--- Adicionar Novo Produto ---");
        System.out.println("Tipo de produto (1 - Suplemento, 2 - Acessório):");
        int tipoProduto = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        System.out.print("Quantidade em Estoque: ");
        int quantidadeEstoque = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        if (tipoProduto == 1) {
            System.out.print("Sabor: ");
            String sabor = scanner.nextLine();
            System.out.print("Tipo (Proteína, Creatina, Termogênico): ");
            String tipoSuplemento = scanner.nextLine();
            estoque.adicionarProduto(new Suplemento(0, nome, preco, quantidadeEstoque, sabor, tipoSuplemento));
        } else if (tipoProduto == 2) {
            System.out.print("Material: ");
            String material = scanner.nextLine();
            System.out.print("Utilidade (Coqueteleira, Cinta de Treino): ");
            String utilidade = scanner.nextLine();
            estoque.adicionarProduto(new Acessorio(0, nome, preco, quantidadeEstoque, material, utilidade));
        } else {
            System.out.println("Tipo de produto inválido.");
        }
    }

    private static void removerProduto() {
        System.out.println("\n--- Remover Produto ---");
        System.out.print("Digite o ID do produto a ser removido: ");
        try {
            int idRemover = scanner.nextInt();
            estoque.removerProduto(idRemover); // Sinaliza e lança ProdutoNaoEncontradoException
        } catch (InputMismatchException e) {
            System.err.println("Erro: ID inválido. Digite um número inteiro.");
            scanner.next(); // Limpar o buffer
        } catch (ProdutoNaoEncontradoException e) {
            System.err.println("Erro: " + e.getMessage()); // Trata a exceção específica
        }
    }

    private static void buscarProduto() {
        System.out.println("\n--- Buscar Produto ---");
        System.out.print("Digite o ID do produto a ser buscado: ");
        try {
            int idBuscar = scanner.nextInt();
            Produto produtoEncontrado = estoque.buscarProduto(idBuscar); // Sinaliza e lança ProdutoNaoEncontradoException
            System.out.println("Produto encontrado: " + produtoEncontrado + " - " + produtoEncontrado.obterInformacoesAdicionais());
        } catch (InputMismatchException e) {
            System.err.println("Erro: ID inválido. Digite um número inteiro.");
            scanner.next(); // Limpar o buffer
        } catch (ProdutoNaoEncontradoException e) {
            System.err.println("Erro: " + e.getMessage()); // Trata a exceção específica
        }
    }

    private static void atualizarProduto() {
        System.out.println("\n--- Atualizar Produto ---");
        System.out.print("Digite o ID do produto a ser atualizado: ");
        try {
            int idAtualizar = scanner.nextInt();
            Produto produtoExistente = estoque.buscarProduto(idAtualizar); // Sinaliza e lança ProdutoNaoEncontradoException
            scanner.nextLine(); // Consumir a quebra de linha

            System.out.print("Novo nome (" + produtoExistente.getNome() + "): ");
            String novoNome = scanner.nextLine();
            System.out.print("Novo preço (" + produtoExistente.getPreco() + "): ");
            double novoPreco = scanner.nextDouble();
            System.out.print("Nova quantidade em estoque (" + produtoExistente.getQuantidadeEstoque() + "): ");
            int novaQuantidade = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            Produto produtoAtualizado;
            if (produtoExistente instanceof Suplemento) {
                System.out.print("Novo sabor (" + ((Suplemento) produtoExistente).getSabor() + "): ");
                String novoSabor = scanner.nextLine();
                System.out.print("Novo tipo (" + ((Suplemento) produtoExistente).getTipo() + "): ");
                String novoTipo = scanner.nextLine();
                produtoAtualizado = new Suplemento(idAtualizar, novoNome, novoPreco, novaQuantidade, novoSabor, novoTipo);
            } else if (produtoExistente instanceof Acessorio) {
                System.out.print("Novo material (" + ((Acessorio) produtoExistente).getMaterial() + "): ");
                String novoMaterial = scanner.nextLine();
                System.out.print("Nova utilidade (" + ((Acessorio) produtoExistente).getUtilidade() + "): ");
                String novaUtilidade = scanner.nextLine();
                produtoAtualizado = new Acessorio(idAtualizar, novoNome, novoPreco, novaQuantidade, novoMaterial, novaUtilidade);
            } else {
                produtoAtualizado = new Produto(idAtualizar, novoNome, novoPreco, novaQuantidade);
            }
            estoque.atualizarProduto(produtoAtualizado); // Sinaliza e lança ProdutoNaoEncontradoException
        } catch (InputMismatchException e) {
            System.err.println("Erro: Entrada inválida. Digite um número.");
            scanner.next(); // Limpar o buffer
        } catch (ProdutoNaoEncontradoException e) {
            System.err.println("Erro: " + e.getMessage()); // Trata a exceção específica
        }
    }

    private static void listarProdutos() {
        estoque.listarProdutos();
    }
}