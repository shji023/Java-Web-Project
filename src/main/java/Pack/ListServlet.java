package Pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest resquest, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<style> "
				+ "header {width: 100%; height: 100px;display: flex;justify-content: center;}"
				+ "h1 {color: #6667AB;}"
				+ "div {display: flex; justify-content: center;border-radius: 20px;}"
				+ "table {border-radius: 20px 20px 0 0;  text-align: center; border-collapse: collapse; margin: 25px 0;font-size: 0.9em;font-family: sans-serif;min-width: 400px;box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);}"
				+ "#test {border-radius:20px;}"
				+ "thead tr {border-radius: 20px;background-color: #6667AB;color: #ffffff;text-align: center;font-weight: bold;}"
				+ "tr,td {padding: 12px 15px;}"
				+ "tbody tr {border:none;border-bottom: 1px solid #dddddd;}"
				+ "tbody tr:nth-of-type(even) {background-color: #f3f3f3;}"
				+ "tbody tr:last-of-type {border-radius: 20px; border-bottom: 2px solid #6667AB;}"
				+ "a{text-decoration:none;}"
				+ "#delete a {color: red;"
				+ "</style></head><body><header><h1>user List<h1></header>"
				);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://13.125.251.30:3307/db01?useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
			con = DriverManager.getConnection(url, "lion", "1234");
			String sql = "select * from user";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			pw.println("<div>");
			pw.println("<table border='1' width='1200'>");
			pw.println("<thead>");
			pw.println("<tr id=\"test\">");
			pw.println("<td>아이디</td>");
			pw.println("<td>비밀번호</td>");
			pw.println("<td>이메일</td>");
			pw.println("<td>전화번호</td>");
			pw.println("<td>등록일</td>");
			pw.println("<td>삭제</td>");
			pw.println("<td>수정</td>");
			pw.println("</tr>");
			pw.println("</thead>");
			pw.println("<tbody>");
			while(rs.next()){
				String idx = rs.getString("idx");
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				Timestamp regdate = rs.getTimestamp("sysdate");
				
				pw.println("<tr>");
				pw.println("<td>" + id + "</td>");
				pw.println("<td>" + pwd + "</td>");
				pw.println("<td>" + email + "</td>");
				pw.println("<td>" + phone + "</td>");
				pw.println("<td>" + regdate + "</td>");
				pw.println("<td id=\"delete\"><a href='delete.do?idx=" + idx + "'>삭제</a></td>");
				pw.println("<td><a href='update.do?idx=" + idx + "'>수정</a></td>");
				pw.println("</tr>");
			}
			pw.println("</tbody>");
			pw.println("</table>");
			pw.println("</div>");
			pw.println("<a href='index.html'>메인페이지로 이동</a>");

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
	}
}