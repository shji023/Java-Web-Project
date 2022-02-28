package Pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      response.setContentType("text/html;charset=UTF-8");
      String id = request.getParameter("id");
      String pwd = request.getParameter("pwd");
      String pwdck = request.getParameter("pwdck");
      String email = request.getParameter("email");
      String phone = request.getParameter("phone");

      if(pwd.equals(pwdck)) {
         int n=0;
         PreparedStatement pstmt = null;
         Connection con = null;
         PrintWriter pw = response.getWriter();
         try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://13.125.251.30:3307/db01?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
            con = DriverManager.getConnection(url, "lion", "1234");
            String name_list = "select count(*) from user where id = ?";
            pstmt = con.prepareStatement(name_list);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            if(rs.getInt(1) >= 1) {
               response.setContentType("text/html;charset=UTF-8");
               pw.println("<html>");
               pw.println("<head></head>");
               pw.println("<body>");
               System.out.println("중복있음");
               pw.println ("<html><body><script>");
               pw.println ("alert('" + id + "는 이미 존재하는 아이디입니다.'); history.go(-1)</script></body></html>");
               pw.println("</body>");
               pw.println("</html>");
               return;
            }
            String sql = "insert into user values(null,?,?,?,?,sysdate())";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            n=pstmt.executeUpdate();
         }catch(ClassNotFoundException ce){
            System.out.println(ce.getMessage());
         }catch(SQLException se){
            System.out.println(se.getMessage());
         }finally{
            try{
               if(pstmt!=null) pstmt.close();
               if(con!=null) con.close();
            }catch(SQLException se){
               System.out.println(se.getMessage());
            }
         }
         pw.println("<html>");
         pw.println("<head></head>");
         pw.println("<body>");
         if(n>0){
            pw.println ("<html><body><script>");
            pw.println ("alert('" + id + "님! 성공적으로 가입되었습니다.'); history.go(-2)</script></body></html>");
         }else{
            pw.println ("<html><body><script>");
            pw.println ("alert('오류로 인해 가입에 실패했습니다.'); history.go(-2)</script></body></html>");
         }
         pw.println("</body>");
         pw.println("</html>");
      }else {
         response.setContentType("text/html;charset=UTF-8");
         PrintWriter pw = response.getWriter();
         pw.println ("<html><head><script>alert('비밀번호가 일치하지 않습니다.'); history.go(-1);</script></head></html>");
      }      
   }
}