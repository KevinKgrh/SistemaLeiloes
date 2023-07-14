/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    PreparedStatement st;
    ResultSet rs;

    public boolean conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uc11sim", "root", "cpd13314");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return false;
        }
    }
    
    public void executarConsulta() {
     if (conn == null) {
         return;
     }

     try {
         PreparedStatement statement = conn.prepareStatement("SELECT * FROM tabela");
         
     } catch (SQLException ex) {
         System.out.println("Erro ao executar consulta: " + ex.getMessage());
     }
    }
    
     public void cadastrarProduto (ProdutosDTO produto){
       int status;
        try {
            st = conn.prepareStatement("INSERT INTO produtos VALUES(?,?)");
            st.setString(2, produto.getNome());
            st.setDouble(3, produto.getValor());
            status = st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
        }
      

        
    }
   
    
    public List<ProdutosDTO> listagem(String listarProdutos){
           String sql = "select * from produtos";

        if (!listarProdutos.isEmpty()) {
            sql = sql + " where nome like ?";
        }
        sql = sql + "  order by nome";
        
        try {
            st = conn.prepareStatement(sql);

            if (!listarProdutos.isEmpty()) {
                st.setString(1,"%" + listarProdutos + "%");
            }

            rs = st.executeQuery();
            List<ProdutosDTO> lista = new ArrayList<>();

            while (rs.next()) {
                ProdutosDTO produtosdto = new ProdutosDTO();
                produtosdto.setNome(rs.getString("nome"));
                produtosdto.setStatus(rs.getString("valor"));
                
                lista.add(produtosdto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
        
    }
    
    public List<ProdutosDTO> listarProdutosVendidos() {

    List<ProdutosDTO> produtosVendidos = new ArrayList<>();

    try {
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ProdutosDTO produtoDTO = new ProdutosDTO();
            produtoDTO.setId(rs.getInt("id"));
            produtoDTO.setNome(rs.getString("nome"));
            produtoDTO.setValor(rs.getInt("valor"));
            produtosVendidos.add(produtoDTO);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return produtosVendidos;
}
    
    
    public void venderProduto(int produtoId) {
    try {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, produtoId);
        stmt.executeUpdate();
        System.out.println("Produto vendido com sucesso.");
    } catch (SQLException e) {
        System.err.println("Erro ao vender o produto: " + e.getMessage());
    }

}

    ArrayList<ProdutosDTO> listarProdutos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

 
    
    
    
        
}

