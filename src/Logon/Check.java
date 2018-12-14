package Logon;

import MySql.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Check
{
    private Connection conn=null;
    private PreparedStatement ps=null;
    private ResultSet rs=null;
    private int count=0;
    private boolean flag;
    public Check()
    {
        this.conn=JDBCUtil.getConnection();
    }
    //数据库存在该用户返回true，不存在返回false
    public boolean checkCount(String username,String password) throws SQLException {

        String sql="SELECT COUNT(*) FROM users WHERE username=? and password=?;";
        ps=conn.prepareStatement(sql);
        ps.setObject(1,username);
        ps.setObject(2,password);
        rs=ps.executeQuery();
        if(rs.next())
        {
            count=rs.getInt(1);
            if(count==1)
            {
                flag=true;
            }else
                flag=false;
        }
        return flag;
    }
    public boolean checkCount(String username) throws SQLException {

        String sql="SELECT COUNT(*) FROM users WHERE username=? ;";
        ps=conn.prepareStatement(sql);
        ps.setObject(1,username);
        rs=ps.executeQuery();
        if(rs.next())
        {
            count=rs.getInt(1);
            if(count==1)
            {
                flag=true;
            }else
                flag=false;
        }
        return flag;
    }
}
