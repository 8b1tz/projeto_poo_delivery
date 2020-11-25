package fachada;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import modelo.Cliente;
import modelo.Pedido;
import modelo.PedidoExpress;
import modelo.Produto;
import repositorio.Repositorio;

public class Fachada {
	private static Repositorio repositorio = new Repositorio();
	private static int idproduto = 0;
	private static int idpedido = 0;

	public static ArrayList<Produto> listarProdutos(String text) {
		return repositorio.getProdutos(text);
	}

	public static ArrayList<Cliente> listarClientes() {
		return repositorio.getClientes();
	}

	public static ArrayList<Pedido> listarPedidos() {
		return repositorio.getPedidos();
	}

	public static Pedido getPedidoById(int idpedido) {
		return repositorio.getPedido(idpedido);
	}

	public static ArrayList<Pedido> listarPedidos(String tel, int tipo) {
		return repositorio.getPedido(tel, tipo);
	}

	public static Produto cadastrarProduto(String nome, double preco) {
		idproduto++;
		ArrayList<Pedido> ped = new ArrayList<>();
		;
		Produto produto;
		produto = new Produto(idproduto, nome, preco, ped);
		repositorio.adicionar(produto);
		return produto;
	}

	public static Cliente cadastrarCliente(String telefone, String nome, String endereco) {
		Cliente cliente;
		ArrayList<Pedido> pedidos = new ArrayList<>();
		cliente = new Cliente(telefone, nome, endereco, pedidos);
		repositorio.adicionar(cliente);
		return cliente;
	}

	public static Pedido criarPedido(String telefone) {
		idpedido++;
		Cliente cli = null;
		LocalDateTime agora = LocalDateTime.now();
		ArrayList<Produto> prod = new ArrayList<>();
		Pedido pedido;
		for (Cliente c : repositorio.getClientes()) {
			if (c.getTelefone() == telefone) {
				cli = c;
			}
		}
		if (cli != null) {
			pedido = new Pedido(idpedido, agora, 0, null, false, cli, prod);
			repositorio.adicionar(pedido);
			return pedido;
		}
		return null;
	}

	public static Pedido criarPedido(String telefone, double taxaentrega) {
        idpedido++;
        Cliente cli = null;
        LocalDateTime agora = LocalDateTime.now();
        
        ArrayList<Produto> prod = new ArrayList<>();
        Pedido pedido;
        for (Cliente c : repositorio.getClientes()) {
            if (c.getTelefone() == telefone) {
                cli = c;
            }
        }
        if (cli != null) {
            pedido = new PedidoExpress(idpedido, agora, 0, null, false, cli, prod, taxaentrega);
            repositorio.adicionar(pedido);
            return pedido;
        }
        return null;
    }
	
	public static void adicionarProdutoPedido(int idpedido, int idproduto) {
		for (Pedido pe : repositorio.getPedidos()) {
			if (pe.getId() == idpedido) {
				for (Produto pr : repositorio.getProdutos("")) {
					if (pr.getId() == idproduto) {
						pe.addProduto(pr);
						pr.addPedido(pe);
						pe.getCliente().addPedido(pe);
					}
				}
			}
		}
	}

	public static void removerProdutoPedido(int idpedido, int idproduto) {
		for (Pedido pe : repositorio.getPedidos()) {
			if (pe.getId() == idpedido) {
				for (Produto pr : repositorio.getProdutos("")) {
					if (pr.getId() == idproduto) {
						pe.remProduto(pr);
						pr.remPedido(pe);
					}
				}
			}
		}
	}

	public static Pedido consultarPedido(int idpedido) {
		Pedido res;
		res = repositorio.getPedido(idpedido);
		if (res != null) {
			return res;
		}
		return null;
	}

	public static void pagarPedido(int idpedido, String nomeentregador) {
		for (Pedido p : repositorio.getPedidos()) {
			if (p.getId() == idpedido) {
				p.setEntregador(nomeentregador);
				p.setPago(true);
			}
		}
	}

	public static void cancelarPedido(int idpedido) {
		for (Pedido p : repositorio.getPedidos()) {
			if (p.getId() == idpedido) {
				repositorio.remover(p);
				break;
			}
		}
	}

	public static double consultarArrecadacao(LocalDateTime dia) {
		double total = 0;
		for (Pedido p : repositorio.getPedidos()) {
			if (p.isPago() == true & p.getDatahora() == dia) {
				total = total + p.getValortotal();
			}
		}
		return total;
	}

	public static ArrayList<Produto> consultarProdutoTop() {
        ArrayList<Produto> res = new ArrayList<>();
        HashMap<Produto, Integer> contagem = new HashMap<>();

        for(Pedido pe : repositorio.getPedidos()) {
            for(Produto prod : pe.getProdutos()) {
                if (contagem.containsKey(prod)) {
                    contagem.put(prod, contagem.get(prod) + 1);
                }
                else {
                    contagem.put(prod, 1);
                }
            }}

        Map<Produto, Integer> sortedNewMap = contagem.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, 
                        (e1, e2) -> e1, LinkedHashMap::new));

        sortedNewMap.forEach((key, val) -> {
            //System.out.println(key + " = " + val.toString());
            res.add(key);
        });

        return res;

    }
}
