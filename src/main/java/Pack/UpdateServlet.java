package Pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UpdateServlet extends HttpServlet {
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
         throws ServletException, IOException {
      // 1. parameter로 전송된 id얻기.
      //String id=req.getParameter("id");
      String idx=req.getParameter("idx");
      // 2. id에 해당하는 정보를 db에서 조회해서 출력.
      resp.setContentType("text/html;charset=UTF-8");
      PrintWriter pw = resp.getWriter();
      pw.println("<html>");
      pw.println("<head></head>");
      pw.println("<body>");

      PreparedStatement pstmt = null;
      Connection con = null;
      ResultSet rs=null;
      pw.println("<html>");
      pw.println("<head>");
      pw.println("<style> "
            + "* {\r\n"
            + "   margin: 0;\r\n"
            + "   padding: 0;\r\n"
            + "   box-sizing: border-box;\r\n"
            + "   /* font-family: \"Noto Sans KR\", sans-serif; */\r\n"
            + "}"
            + "h1 {color: #6667AB;"
            + "margin-bottom:10px;"
            + "}"
            + "input {width: 50%;"
            + "   height: 50px;"
            + "   border-radius: 30px;"
            + "   padding: 0px 20px;"
            + "   border: 1px solid lightgray;"
            + "   outline: none;"
            + "margin-bottom:10px;"
            + "}"
            + ".wrap {"
            + "width: 100%;\r\n"
            + "   height: 100%;\r\n"
            + "   display: flex;\r\n"
            + "   align-items: center;\r\n"
            + "   justify-content: center;\r\n"
            + "   background: rgba(0, 0, 0, 0);"
            + "}"
            + ".divtitle{"
            + "margin:8px;"
            + "}"
            + ".login {\r\n"
            + "   width: 500px;\r\n"
            + "   height: 90%;\r\n"
            + "   background: white;\r\n"
            + "   border-radius: 20px;\r\n"
            + "   display: flex;\r\n"
            + "   justify-content: center;\r\n"
            + "   align-items: center;\r\n"
            + "   flex-direction: column;\r\n"
            + "   margin-top: 30px;\r\n"
            + "}"
            + ".saveBtn{"
            + "  background-color: #4CAF50;\r\n"
            + "  border: none;\r\n"
            + "  color: white;\r\n"
            + "  padding: 10px 10px;\r\n"
            + "  text-align: center;\r\n"
            + "  text-decoration: none;\r\n"
            + "  display: inline-block;\r\n"
            + "  font-size: 16px;\r\n"
            + "  margin-top:20px;\r\n"
            + "  transition-duration: 0.4s;\r\n"
            + "  cursor: pointer;\r\n"
            + "    background-color: white; \r\n"
            + "  color: black; \r\n"
            + "  border: 2px solid #008CBA;"
            + "  }"
            + ".saveBtn:hover{"
            + "background-color: #008CBA;\r\n"
            + "  color: white;"
            + "}"
            + ".title{"
            + ""
            + "}"
            + "</style>"
            + "</head>"
            + "<body>"
            );

      try{
         // 2. 전송된 값을 db에 저장.
         Class.forName("com.mysql.jdbc.Driver");
         String url = "jdbc:mysql://13.125.251.30:3307/db01?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
         con = DriverManager.getConnection(url, "lion", "1234");

         String sql = "select * from user where idx=?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, idx);

         //sql구문 실행하기
         rs = pstmt.executeQuery();
         rs.next();
         String id = rs.getString("id");
         String idx2 = rs.getString("idx");
         String pwd = rs.getString("pwd");
         String email=rs.getString("email");
         String phone=rs.getString("phone");
         System.out.println(id);
         pw.println("<div class=\"wrap\">");
         pw.println("<form method='post' action='updateok.do'>");
         pw.println("<div class=\"login\">");
         pw.println("<h1 class=\"title\">회원정보 수정</h1>");
         pw.println("<input type='hidden' name='idx' value='" + idx2 + "'/>");
         pw.println("<input type='hidden' name='id' value='" + id + "'/>");
         pw.println("<div class=\"divtitle\">아이디</div>"
               + "<input type='text' name='id' value='" + id + "' disabled='disabled'/>");
         pw.println("<div class=\"divtitle\">비밀번호</div>"
               + "<input type='password' name='pwd' value='" + pwd + "'/>");
         pw.println("<div class=\"divtitle\">Email</div>"
               + "<input type='text' name='email' value='" + email + "'/>");
         pw.println("<div class=\"divtitle\">Phone</div>"
               + "<input type='text' name='phone' value='" + phone + "'/><br/>");
         pw.println("<input type='submit' value='저장' class=\"saveBtn\"/><br/>");
         pw.println("</div>");
         pw.println("</form>");
         pw.println("</div>");

      }catch(ClassNotFoundException ce){
         System.out.println(ce.getMessage());
      }catch(SQLException se){
         System.out.println(se.getMessage());
      }finally{
         try{
            if(rs!=null) rs.close();
            if(pstmt!=null) pstmt.close();
            if(con!=null) con.close();
         }catch(SQLException se){
            System.out.println(se.getMessage());
         }
      }

      pw.println("</body>");
      pw.println("</html>");
      pw.close();
   }
}