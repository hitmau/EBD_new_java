package App;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class Categoria {

	private static Connection con = Conexao.getConexao();
	private static int conf;
	private static String tabelaBd = "categoria";
	private static int codcategoria;
	private static String descricao;
	private static boolean inativo;
	
	public static int getCodcategoria() {
		return codcategoria;
	}

	public static void setCodcategoria(int codcategoria) {
		Categoria.codcategoria = codcategoria;
	}

	public static String getDescricao() {
		return descricao;
	}

	public static void setDescricao(String descricao) {
		Categoria.descricao = descricao;
	}

	public static boolean isInativo() {
		return inativo;
	}

	public static void setInativo(boolean inativo) {
		Categoria.inativo = inativo;
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
System.out.println("teste categorias");
getListaTudo();
	}

	public static Categoria getLista(int id) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + tabelaBd + " order by 1");
		return null;
		
	}
	
	public static void getListaTudo() throws SQLException {
		PreparedStatement query = con.prepareStatement("SELECT * FROM " + tabelaBd + " order by 1;");
		ResultSet rs = query.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("codcategoria") + " - " + rs.getString("descricao") + " - " + rs.getBoolean("inativo"));
		}
	}
	
}
