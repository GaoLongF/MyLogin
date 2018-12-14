package Logon;

import MySql.JDBCUtil;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogonServlet extends HttpServlet
{
    private Connection conn =null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String username;
    private String password;
    private String method;
    private PrintWriter pw=null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {

        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        pw=resp.getWriter();
        conn=JDBCUtil.getConnection();
        method=req.getParameter("method");
        if(method.equals("logon"))
        {
            Logon(req,resp);
        }
        else if (method.equals("register"))
        {
            Register(req,resp);
        }

    }

    private void Logon(HttpServletRequest req, HttpServletResponse resp) {
        pw.print("<style>body{ text-align:center} " +
                ".div{ margin:0 auto; width:400px; height:100px; border:1px solid #F00}"+
                "</style>"+ "----欢迎登录----"+"<br>");
        username=req.getParameter("username");
        password=req.getParameter("password");
        try {
            Check ck=new Check();
            boolean flag=ck.checkCount(username,password);
            if(flag)
            {
                pw.print("欢迎回来"+username+"<br>");

            }else
            {
                pw.print("登录失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void Register(HttpServletRequest req, HttpServletResponse resp){
        pw.print("<style>body{ text-align:center} " +
                ".div{ margin:0 auto; width:400px; height:100px; border:1px solid #F00}"+
                "</style>"+"----欢迎注册----"+"<br>");
        username=req.getParameter("username");
        password=req.getParameter("password");
        Check ck=new Check();
        String sql = "INSERT INTO users(username,password,time) VALUES (?,?,now());";
        try {
            boolean flog=ck.checkCount(username);
            if(!flog)
            {
                ps = conn.prepareStatement(sql);
                ps.setObject(1, username);
                ps.setObject(2, password);
                ps.executeUpdate();
                pw.print("注册成功！");
            }else
                {
                    pw.print("注册失败！");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
