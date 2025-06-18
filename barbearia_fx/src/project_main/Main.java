package project_main;

import project_dao.UsuarioDAO;
import project_dao.AgendamentoDAO;
import project_dao.BarbeiroDAO;
import project_dao.ClienteDAO;
import project_model.Agendamento;
import project_model.Barbeiro;
import project_model.Cliente;
import project_model.Usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ClienteDAO clienteDAO = new ClienteDAO();
        BarbeiroDAO barbeiroDAO = new BarbeiroDAO();
        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioLogado = null;

        do {
            System.out.print("Login: ");
            String login = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            if (usuarioDAO.autenticar(login, senha) != null) {
                usuarioLogado = usuarioDAO.buscarUsuarioPorLogin(login);
                System.out.println("Login efetuado com sucesso! Bem-vindo à " + usuarioLogado.getNomeBarbearia());
            } else {
                System.out.println("Login ou senha inválidos. Tente novamente.");
            }

        } while (usuarioLogado == null);


        int opcaoPrincipal;
        // Menu Principal 
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1 - Agendamento");
            System.out.println("2 - Barbeiro");
            System.out.println("3 - Cliente");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcaoPrincipal = scanner.nextInt();
            scanner.nextLine();

            switch (opcaoPrincipal) {

            case 1: // Submenu Agendamento
                int opcaoAgendamento;
                do {
                    System.out.println("\n--- MENU AGENDAMENTO ---");
                    System.out.println("1 - Incluir Agendamento");
                    System.out.println("2 - Excluir Agendamento");
                    System.out.println("3 - Listar Agendamentos");
                    System.out.println("4 - Pesquisar Agendamento por Cliente");
                    System.out.println("5 - Pesquisar Agendamento por Barbeiro");
                    System.out.println("0 - Voltar");
                    System.out.print("Escolha: ");
                    opcaoAgendamento = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcaoAgendamento) {
                        case 1:
                            incluirAgendamento(scanner, clienteDAO, barbeiroDAO, agendamentoDAO);
                            break;

                        case 2:
                            excluirAgendamentoComConfirmacao(scanner, agendamentoDAO);
                            break;

                        case 3:
                            List<Agendamento> agendamentos = agendamentoDAO.listar();
                            
                            if (agendamentos == null || agendamentos.isEmpty()) {
                                System.out.println("Nenhum agendamento encontrado.");
                                break;
                            }

                            for (Agendamento a : agendamentos) {
                                System.out.println("ID: " + a.getId() +
                                        " | Cliente: " + a.getClienteNome() +
                                        " | Barbeiro: " + a.getBarbeiroNome() +
                                        " | Serviço: " + a.getServico() +
                                        " | Data: " + a.getDataHora());
                            }
                            break;

                        case 4:
                            boolean repetirCliente;
                            do {
                                repetirCliente = false;

                                System.out.print("Digite o nome do cliente: ");
                                String nomeCliente = scanner.nextLine();
                                List<Agendamento> agPorCliente = agendamentoDAO.pesquisarPorNomeCliente(nomeCliente);

                                if (agPorCliente == null || agPorCliente.isEmpty()) {
                                    System.out.println("\u274c Nenhum agendamento encontrado para o cliente informado.");
                                    System.out.print("Deseja tentar novamente? (S/N): ");
                                    String resposta = scanner.nextLine();
                                    if (resposta.equalsIgnoreCase("S")) {
                                        repetirCliente = true;
                                    }
                                } else {
                                    System.out.println("\n--- AGENDAMENTOS ENCONTRADOS ---");
                                    for (Agendamento ag : agPorCliente) {
                                        System.out.println("ID: " + ag.getId() +
                                                           " | Cliente: " + ag.getClienteNome() +
                                                           " | Barbeiro: " + ag.getBarbeiroNome() +
                                                           " | Serviço: " + ag.getServico() +
                                                           " | Data: " + ag.getDataHora());
                                    }
                                }
                            } while (repetirCliente);
                            break;

                        case 5:
                            boolean repetirBarbeiro;
                            do {
                                repetirBarbeiro = false;

                                System.out.print("Digite o nome do barbeiro: ");
                                String nomeBarbeiro = scanner.nextLine();
                                List<Agendamento> agPorBarbeiro = agendamentoDAO.pesquisarPorNomeBarbeiro(nomeBarbeiro);

                                if (agPorBarbeiro == null || agPorBarbeiro.isEmpty()) {
                                    System.out.println("\u274c Nenhum agendamento encontrado para o barbeiro informado.");
                                    System.out.print("Deseja tentar novamente? (S/N): ");
                                    String resposta = scanner.nextLine();
                                    if (resposta.equalsIgnoreCase("S")) {
                                        repetirBarbeiro = true;
                                    }
                                } else {
                                    System.out.println("\n--- AGENDAMENTOS ENCONTRADOS ---");
                                    for (Agendamento ag : agPorBarbeiro) {
                                        System.out.println("ID: " + ag.getId() +
                                                           " | Cliente: " + ag.getClienteNome() +
                                                           " | Barbeiro: " + ag.getBarbeiroNome() +
                                                           " | Serviço: " + ag.getServico() +
                                                           " | Data: " + ag.getDataHora());
                                    }
                                }
                            } while (repetirBarbeiro);
                            break;
                    }
                } while (opcaoAgendamento != 0);
                break;


                case 2: // Submenu Barbeiro
                    int opcaoBarbeiro;
                    do {
                        System.out.println("\n--- MENU BARBEIRO ---");
                        System.out.println("1 - Incluir Barbeiro");
                        System.out.println("2 - Excluir Barbeiro");
                        System.out.println("3 - Listar Barbeiros");
                        System.out.println("4 - Lista de Agendamento de Barbeiros");
                        System.out.println("0 - Voltar");
                        System.out.print("Escolha: ");
                        opcaoBarbeiro = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoBarbeiro) {
                            case 1:
                                System.out.print("Nome: ");
                                String nome = scanner.nextLine();
                                System.out.print("Especialidade: ");
                                String especialidade = scanner.nextLine();
                                System.out.print("Telefone: ");
                                String telefone = scanner.nextLine();

                                Barbeiro novoBarbeiro = new Barbeiro(nome, especialidade, telefone);
                                barbeiroDAO.inserir(novoBarbeiro);
                                break;

                            case 2: // Excluir Barbeiro
                                excluirBarbeiroComConfirmacao(scanner, barbeiroDAO);
                                break;

                            case 3:
                                List<Barbeiro> barbeiros = barbeiroDAO.listar();
                                for (Barbeiro b : barbeiros) {
                                    System.out.println("ID: " + b.getId() +
                                            " | Nome: " + b.getNome() +
                                            " | Especialidade: " + b.getEspecialidade() +
                                            " | Telefone: " + b.getTelefone());
                                }
                                break;
                                
                            case 4:
                                pesquisarBarbeiroAgendamentos(scanner, barbeiroDAO, agendamentoDAO);
                                break;

                        }
                    } while (opcaoBarbeiro != 0);
                    break;

                case 3: // Submenu Cliente
                    int opcaoCliente;
                    do {
                        System.out.println("\n--- MENU CLIENTE ---");
                        System.out.println("1 - Incluir Cliente");
                        System.out.println("2 - Excluir Cliente");
                        System.out.println("3 - Listar Clientes");
                        System.out.println("4 - Pesquisa de Agendamento de Clientes ");
                        System.out.println("0 - Voltar");
                        System.out.print("Escolha: ");
                        opcaoCliente = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoCliente) {
                            case 1:
                                System.out.print("Nome: ");
                                String nomeCliente = scanner.nextLine();
                                System.out.print("Telefone: ");
                                String telCliente = scanner.nextLine();
                                System.out.print("Email: ");
                                String email = scanner.nextLine();

                                Cliente novoCliente = new Cliente(nomeCliente, telCliente, email);
                                clienteDAO.inserir(novoCliente);
                                break;
                                
                            case 2: // Excluir Cliente 
                                excluirClienteComConfirmacao(scanner, clienteDAO);
                                break;

                            case 3:
                                List<Cliente> clientes = clienteDAO.listar();
                                for (Cliente c : clientes) {
                                    System.out.println("ID: " + c.getId() +
                                            " | Nome: " + c.getNome() +
                                            " | Telefone: " + c.getTelefone() +
                                            " | Email: " + c.getEmail());
                                }
                                break;
                                
                            case 4:
                                pesquisarClienteAgendamentos(scanner, clienteDAO, agendamentoDAO);
                                break;
                        }
                    }
                    while (opcaoCliente != 0);
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcaoPrincipal != 0);
        
        scanner.close();
    }
    
    // Listar cliente e barbeiros na hora da exlusão no Agendamentos, Celientes e Barbeiros
    public static void listarClientesBarbeiros(ClienteDAO clienteDAO, BarbeiroDAO barbeiroDAO) {
        System.out.println("\n--- CLIENTES CADASTRADOS ---");
        List<Cliente> clientes = clienteDAO.listar();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente c : clientes) {
                System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Telefone: " + c.getTelefone());
            }
        }

        System.out.println("\n--- BARBEIROS CADASTRADOS ---");
        List<Barbeiro> barbeiros = barbeiroDAO.listar();
        if (barbeiros.isEmpty()) {
            System.out.println("Nenhum barbeiro cadastrado.");
        } else {
            for (Barbeiro b : barbeiros) {
                System.out.println("ID: " + b.getId() + " | Nome: " + b.getNome() + " | Especialidade: " + b.getEspecialidade());
            }
        }
    }
    
    //Incluir data e hora no agendamento dos cleintes 
    public static void incluirAgendamento(Scanner scanner, ClienteDAO clienteDAO, BarbeiroDAO barbeiroDAO, AgendamentoDAO agendamentoDAO) {
        listarClientesBarbeiros(clienteDAO, barbeiroDAO);

        System.out.print("\nInforme o ID do Cliente: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine();

        if (!clienteDAO.existeClientePorId(clienteId)) {
            System.out.println("Cliente com ID " + clienteId + " não existe. Retornando ao menu.");
            return;
        }

        System.out.print("Informe o ID do Barbeiro: ");
        int barbeiroId = scanner.nextInt();
        scanner.nextLine();

        if (!barbeiroDAO.existeBarbeiroPorId(barbeiroId)) {
            System.out.println("Barbeiro com ID " + barbeiroId + " não existe. Retornando ao menu.");
            return;
        }

        System.out.print("Informe a data do agendamento (dd/MM/yyyy): ");
        String dataInput = scanner.nextLine();

        System.out.print("Informe o horário do agendamento (HH:mm): ");
        String horaInput = scanner.nextLine();

        LocalDateTime dataHoraAgendamento;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            dataHoraAgendamento = LocalDateTime.parse(dataInput + " " + horaInput, formatter);
        } catch (Exception e) {
            System.out.println("Data ou hora inválida. Utilize os formatos dd/MM/yyyy e HH:mm.");
            return;
        }

        System.out.print("Serviço: ");
        String servico = scanner.nextLine();

        System.out.print("Observações: ");
        String obs = scanner.nextLine();

        Agendamento agendamento = new Agendamento();
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setId(barbeiroId);

        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setServico(servico);
        agendamento.setObservacoes(obs);
        agendamento.setDataHora(dataHoraAgendamento);

        agendamentoDAO.inserir(agendamento);
    }

    //Excluir Agendamento com confirmação 
    public static void excluirAgendamentoComConfirmacao(Scanner scanner, AgendamentoDAO agendamentoDAO) {
        System.out.println("\n--- AGENDAMENTOS CADASTRADOS ---");
        List<Agendamento> agendamentos = agendamentoDAO.listar();

        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento cadastrado. Não é possível excluir.");
            return;
        }

        for (Agendamento a : agendamentos) {
            System.out.println("ID: " + a.getId() +
                    " | Cliente: " + a.getCliente().getNome() +
                    " | Barbeiro: " + a.getBarbeiro().getNome() +
                    " | Serviço: " + a.getServico() +
                    " | Data: " + a.getDataHora());
        }

        System.out.print("Informe o ID do Agendamento para excluir: ");
        int idAgendamento = scanner.nextInt();
        scanner.nextLine();

        if (!agendamentoDAO.existeAgendamentoPorId(idAgendamento)) {
            System.out.println("Agendamento com ID " + idAgendamento + " não existe. Retornando ao menu.");
            return;
        }

        System.out.print("Deseja realmente excluir o Agendamento com ID " + idAgendamento + "? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (!confirmacao.equalsIgnoreCase("S")) {
            System.out.println("Exclusão cancelada.");
            return;
        }

        agendamentoDAO.excluir(idAgendamento);
    }

    //Excluir Barbeiro com confirmação 
    public static void excluirBarbeiroComConfirmacao(Scanner scanner, BarbeiroDAO barbeiroDAO) {
        System.out.println("\n--- BARBEIROS CADASTRADOS ---");
        List<Barbeiro> barbeiros = barbeiroDAO.listar();

        if (barbeiros.isEmpty()) {
            System.out.println("Nenhum barbeiro cadastrado. Não é possível excluir.");
            return;
        }

        for (Barbeiro b : barbeiros) {
            System.out.println("ID: " + b.getId() +
                    " | Nome: " + b.getNome() +
                    " | Especialidade: " + b.getEspecialidade() +
                    " | Telefone: " + b.getTelefone());
        }

        System.out.print("Informe o ID do Barbeiro para excluir: ");
        int idBarbeiro = scanner.nextInt();
        scanner.nextLine();

        if (!barbeiroDAO.existeBarbeiroPorId(idBarbeiro)) {
            System.out.println("Barbeiro com ID " + idBarbeiro + " não existe. Retornando ao menu.");
            return;
        }

        System.out.print("Deseja realmente excluir o Barbeiro com ID " + idBarbeiro + "? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (!confirmacao.equalsIgnoreCase("S")) {
            System.out.println("Exclusão cancelada.");
            return;
        }

        barbeiroDAO.excluir(idBarbeiro);
    }

    //Excluir Cliente com confirmação 
    public static void excluirClienteComConfirmacao(Scanner scanner, ClienteDAO clienteDAO) {
        System.out.println("\n--- CLIENTES CADASTRADOS ---");
        List<Cliente> clientes = clienteDAO.listar();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Não é possível excluir.");
            return;
        }

        for (Cliente c : clientes) {
            System.out.println("ID: " + c.getId() +
                    " | Nome: " + c.getNome() +
                    " | Telefone: " + c.getTelefone() +
                    " | Email: " + c.getEmail());
        }

        System.out.print("Informe o ID do Cliente para excluir: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();

        if (!clienteDAO.existeClientePorId(idCliente)) {
            System.out.println("Cliente com ID " + idCliente + " não existe. Retornando ao menu.");
            return;
        }

        System.out.print("Deseja realmente excluir o Cliente com ID " + idCliente + "? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (!confirmacao.equalsIgnoreCase("S")) {
            System.out.println("Exclusão cancelada.");
            return;
        }

        clienteDAO.excluir(idCliente);
    }
    
    //Pesquisar Cliente pelo nome e ver se tem agendamento
    public static void pesquisarClienteAgendamentos(Scanner scanner, ClienteDAO clienteDAO, AgendamentoDAO agendamentoDAO) {
        System.out.print("Digite o nome ou parte do nome do cliente: ");
        String nomeBusca = scanner.nextLine();

        List<Cliente> clientes = clienteDAO.listar();
        List<Cliente> encontrados = clientes.stream()
                .filter(c -> c.getNome().toLowerCase().contains(nomeBusca.toLowerCase()))
                .toList();

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum cliente encontrado com o nome informado.");
            return;
        }

        System.out.println("\n--- CLIENTES ENCONTRADOS ---");
        for (Cliente c : encontrados) {
            System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Telefone: " + c.getTelefone());
        }

        System.out.print("Informe o ID do Cliente para ver agendamentos: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();

        List<Agendamento> agendamentos = agendamentoDAO.listar();
        List<Agendamento> agendamentosCliente = agendamentos.stream()
                .filter(a -> a.getCliente().getId() == idCliente)
                .toList();

        if (agendamentosCliente.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado para este cliente.");
            return;
        }

        System.out.println("\n--- AGENDAMENTOS DO CLIENTE ---");
        for (Agendamento a : agendamentosCliente) {
            System.out.println("ID: " + a.getId() +
                    " | Barbeiro: " + a.getBarbeiro().getNome() +
                    " | Serviço: " + a.getServico() +
                    " | Data: " + a.getDataHora());
        }
    }
    
  //Pesquisar Barbeiro pelo nome e ver se tem agendamento
    public static void pesquisarBarbeiroAgendamentos(Scanner scanner, BarbeiroDAO barbeiroDAO, AgendamentoDAO agendamentoDAO) {
        System.out.print("Digite o nome ou parte do nome do barbeiro: ");
        String nomeBusca = scanner.nextLine();

        List<Barbeiro> barbeiros = barbeiroDAO.listar();
        List<Barbeiro> encontrados = barbeiros.stream()
                .filter(b -> b.getNome().toLowerCase().contains(nomeBusca.toLowerCase()))
                .toList();

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum barbeiro encontrado com o nome informado.");
            return;
        }

        System.out.println("\n--- BARBEIROS ENCONTRADOS ---");
        for (Barbeiro b : encontrados) {
            System.out.println("ID: " + b.getId() + " | Nome: " + b.getNome() + " | Especialidade: " + b.getEspecialidade());
        }

        System.out.print("Informe o ID do Barbeiro para ver agendamentos: ");
        int idBarbeiro = scanner.nextInt();
        scanner.nextLine();

        List<Agendamento> agendamentosBarbeiro = agendamentoDAO.listarPorIdBarbeiro(idBarbeiro);

        if (agendamentosBarbeiro.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado para este barbeiro.");
            return;
        }

        System.out.println("\n--- AGENDAMENTOS DO BARBEIRO ---");
        for (Agendamento a : agendamentosBarbeiro) {
            System.out.println("ID: " + a.getId() +
                    " | Cliente: " + a.getClienteNome() +
                    " | Serviço: " + a.getServico() +
                    " | Data: " + a.getDataHora());
        }
    }

	

}
