package org.hy.common.xml.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hy.common.StringHelp;





/**
 * App的基础的Servlet
 *
 * @author      ZhengWei(HY)
 * @createDate  2013-12-19
 * @version     v1.0
 */
public class AppBaseServlet extends HttpServlet
{
    
    private static final long serialVersionUID = -5189383556838396564L;
    
    
    
    public void doPost(HttpServletRequest i_Request, HttpServletResponse i_Response) throws ServletException, IOException 
    {
        AppMessage<?> v_AppMsg = AppInterfaces.executeMessage(this ,this.getI(i_Request));
        
        if ( v_AppMsg != null )
        {
            this.responseJson(i_Request ,i_Response ,v_AppMsg.toString());
        }
    }
    
    
    
    /**
     * 客户端发来的请求信息
     * 
     * @param i_Request
     * @return
     */
    protected String getI(HttpServletRequest i_Request)
    {
        String v_Info = this.getPostInfo(i_Request);
        
        if ( v_Info != null )
        {
            v_Info = StringHelp.unescape_toUnicode(v_Info);
            String [] v_Infos = v_Info.split("=");
            
            if ( v_Infos.length == 2 && "i".equals(v_Infos[0]) )
            {
                return v_Infos[1];
            }
            else
            {
                return v_Info;
            }
        }
        
        return "";
    }
    
    
    
    /**
     * 获取Post方式的Http请求信息
     * 
     * @return
     */
    protected String getPostInfo(HttpServletRequest i_Request)
    {
        BufferedReader v_Input = null;
        try
        {
            StringBuilder v_Buffer = new StringBuilder();
            String        v_Line   = "";
            v_Input  = new BufferedReader(new InputStreamReader(i_Request.getInputStream()));
            while ( (v_Line = v_Input.readLine())!= null )
            {
                v_Buffer.append(v_Line);
            }
            
            return v_Buffer.toString();
        }
        catch (Exception exce)
        {
            exce.printStackTrace();
        }
        finally
        {
            if ( v_Input != null )
            {
                try
                {
                    v_Input.close();
                }
                catch (Exception exce)
                {
                    exce.printStackTrace();
                }
            }
            v_Input = null;
        }
        
        return null;
    }
    
    
    
    /**
     * 响应Json字符串
     * 
     * @param i_Request
     * @param i_Response
     * @param i_JsonData
     * @throws ServletException
     * @throws IOException
     */
    protected void responseJson(HttpServletRequest i_Request, HttpServletResponse i_Response ,String i_JsonData) throws ServletException, IOException
    {
        i_Response.setCharacterEncoding("UTF-8");
        i_Response.setContentType("application/json; charset=utf-8");
        
        PrintWriter v_Out = null;
        try
        {
            v_Out = i_Response.getWriter();
            v_Out.write(i_JsonData);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if ( v_Out != null )
            {
                v_Out.close();
            }
        }
    }
    
}
