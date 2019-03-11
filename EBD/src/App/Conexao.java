package App;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import br.com.ConexaoBanco.Conexao;

//import br.com.ConexaoBanco.Conexao;

public class Conexao {
	
	public static String mydatabase;
	public static String serverName;
	public static String port;
	public static String url;
	public static String username;
	public static String password;
	
	public static String getServerName() {
		return serverName;
	}

	public static void setServerName(String serverName) {
		Conexao.serverName = serverName;
	}

	public static String getPort() {
		return port;
	}

	public static void setPort(String port) {
		Conexao.port = port;
	}

	public static String getUrl() {
		url = ("jdbc:mysql://" + getServerName() + ":" + getPort() + "/" + getMydatabase() + "?useSSL=true");
		return url;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		Conexao.username = username;
	}

	public static void setStatus(String status) {
		Conexao.status = status;
	}

	public static String getMydatabase() {
		return mydatabase;
	}

	public static void setMydatabase(String mydatabase2) {
		mydatabase = mydatabase2;
	}
	
	private static void setPassword(String password) {
		Conexao.password = password;
	}

	public static String status = "N�o conectou...";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Connection a = Conexao.getConexao();
		//System.out.println(getStatus());
		getConexao();
		Conexao.RetornaStatus();
	}

	//Abre conex�o com o banco
	public static java.sql.Connection getConexao() {
		Connection connection = null;
		try {
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			setServerName("localhost");
			setMydatabase("ebd");
			setPort("3306");
			setUsername("root");
			setPassword("");

			connection = DriverManager.getConnection(getUrl(), username, password);

			// Testa sua conex�o//
			if (connection != null) {
				status = ("1");
			} else {
				status = ("0");
			}

			return connection;
		} catch (ClassNotFoundException e) { // Driver n�o encontrado
			System.out.println("O driver expecificado nao foi encontrado.");
			return null;
		} catch (SQLException e) {
//N�o conseguindo se conectar ao banco	 
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");
			return null;
		}

	}



//M�todo que retorna o status da sua conex�o//
	public static String statusConection() {
		return status;
	}

	public static void RetornaStatus() {
		if (status.equals("1")) {
			System.out.println("Banco '" + getMydatabase() + "' est� conectado!");
		} else {
			System.out.println("Por algum motigo o banco '"+ getMydatabase() + "' est� conectado!");
		}
	}

	public static String getStatus() {
		return status;
	}

//M�todo que fecha conex�o
	public static boolean closeConexao() {
		try {
			Conexao.getConexao().close();
			status = ("STATUS--->Conex�o fechada");
			return true;
		} catch (SQLException e) {
			status = ("STATUS--->Erro ao exibir status");
			return false;
		}
	}

	// M�todo que reinicia conex�o
	public static java.sql.Connection refreshConexao() {
		closeConexao();
		return Conexao.getConexao();
	}

}

