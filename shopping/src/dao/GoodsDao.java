package dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;


import db.DbClose;
import db.DbConn;
import entity.Goods;
import entity.Login;
import entity.OrderForm;

public class GoodsDao extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public GoodsDao() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;chartset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String value = "";
		value = request.getParameter("key");
		int key = Integer.parseInt(value);
		System.out.println("����Ƿ���key:"+key);
		String keyWord = "";
		keyWord = request.getParameter("keyWord");
		System.out.println("keyWord:"+keyWord);
		try {
			queryGoods(request, response, key,keyWord);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void init() throws ServletException
	{
		// Put your code here
	}
	
	/**
	 * ��Ʒ��ѯ
	 * @param request
	 * @param response
	 * @param key ��ѯ������/int:4(�򵥲�ѯ)
	 * @return ��Ʒ��Ϣ����
	 * @throws Exception 
	 */
	@SuppressWarnings("null")
	public void queryGoods(HttpServletRequest request, HttpServletResponse response,int key,String keyWord)
			throws Exception
	{
	    response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet rowSet = factory.createCachedRowSet();
        //�м�����
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Goods goods = null;
		Login username = null;
		OrderForm orderForm = null;
		
		HttpSession session = request.getSession(true);
		username = (Login)session.getAttribute("loginBean");
		goods = (Goods)session.getAttribute("goods");
		orderForm = (OrderForm)session.getAttribute("orderForm");
		ArrayList<Goods> goodsList = new ArrayList<Goods>();
		if (goods==null)
		{
			goods = new Goods();
			session.setAttribute("goods", goods);
		}
		if (username==null)
		{
		    username = new Login();
		    session.setAttribute("username", username);
		}
		if (orderForm==null)
		{
		    orderForm = new OrderForm();
		    session.setAttribute("orderForm", orderForm);
		}
		  //�ж��û��Ƿ��¼
		  String user = "";
          user = username.getUsername();//��¼�ߵ��û���
          System.out.println("�û���"+user);
          if (user.equals("userNull"))
          {
              out.print("<br>");
              out.print("<center><font color=#008B8B> ���ȵ�¼ </font>");
              out.print("<a href=/shopping/jsp/join/login.jsp><font color=red size=6>��¼</font></a></center>");
              return;
          }
		
		conn = DbConn.getConn();	

		switch (key)
		{
			case 1:
					//key=1 ��Ʒ ���� �����ѯ
					String sqlGnum = "SELECT * FROM GOODS ORDER BY GNUM ASC";
					try
					{
						pstmt = conn.prepareStatement(sqlGnum);
						rs = pstmt.executeQuery();
						while (rs.next())
						{
							
						}
					} catch (SQLException e)
					{
						e.printStackTrace();
					}finally
							{
								DbClose.allClose(pstmt, rs, conn);
							}
				break;
			case 2:
        			   //key=2 ���չؼ��ֲ�ѯ ��Ʒ��Ϣ
                      
                        String sqlShowGoodsByKey =  
                        "select * from commodity WHERE commodity_name LIKE ?";
                        try
                        {
                            pstmt = conn.prepareStatement(sqlShowGoodsByKey);
                            pstmt.setString(1, "%"+keyWord+"%"); //%��Ҫд��sqlShowGoodsByKey
                            rs = pstmt.executeQuery();
                            System.out.println("--2�鿴����ִ�����ݿ����--");
                            if(rs.next())
                            {
                                rs = pstmt.executeQuery();//���²�ѯ��ԭ����rs.nextʱ���ƫ�ƺ󣬶�����¼��
                                
                                rowSet.populate(rs);
                                goods.setRowSet(rowSet);
                                System.out.println("2�Ѿ������ݿ��л�ȡ��ֵ���������м�");
                                request.getRequestDispatcher("/jsp/browse/showGoods.jsp").forward(request, response);
                            }else 
                                {
                                    out.print("<br><br><br><center>");
                                    out.print("<font color=green> ��ѯ������ </font>");
                                    out.print("<a href=/shopping/jsp/browse/searchByKeyWord.jsp><font color=red size=6>��ѯ</font></a>");
                                    out.print("</center>");     
                                }
                        } catch (SQLException e)
                        {
                            System.out.println("key=2�鿴�����쳣��"+e);
                            
                        }finally
                                {
                                    System.out.println("�鿴����ִ�йر���");
                                    DbClose.allClose(pstmt, rs, conn);
                                }
        				break;
			case 3:
                    //key=3 ���յ�¼�˲�ѯ���� ��Ʒ����+����+����
			      
                    String sqlOrder= 
                    "select commodity_name,commodity_price,sum from orderform where username=?";
                    try
                    {
                        pstmt = conn.prepareStatement(sqlOrder);
                        pstmt.setString(1, user);
                        rs = pstmt.executeQuery();
                        System.out.println("--�鿴����ִ�����ݿ����--");
                        if(rs.next())
                        {
                            rs = pstmt.executeQuery();//���²�ѯ��ԭ����rs.nextʱ���ƫ�ƺ󣬶�����¼��
                            rowSet.populate(rs);
                            goods.setRowSet(rowSet);
                            System.out.println("3�Ѿ������ݿ��л�ȡ��ֵ���������м�");
                            request.getRequestDispatcher("/jsp/order/lookOrderForm.jsp").forward(request, response);
                        }else 
                            {
                                out.print("<br><br><br><center>");
                                out.print("<font color=blue> ����Ϊ�� </font>");
                                out.print("<a href=/shopping/dao/GoodsDao?key=4><font color=red size=6>ȥ����</font></a>");
                                out.print("</center>");		
                            }
                    } catch (SQLException e)
                    {
                        System.out.println("key=3�鿴�����쳣��"+e);
                        
                    }finally
                            {
                                System.out.println("�鿴����ִ�йر���");
                                DbClose.allClose(pstmt, rs, conn);
                            }
                    break;
			case 4:
					//key=4 �����Ʒ
					String sqlList= "select * from commodity";
					try
					{
						pstmt = conn.prepareStatement(sqlList);
						rs = pstmt.executeQuery();
						
						System.out.println("--4�����Ʒִ�����ݿ����--");
						if(rs.next())
						{
							rs = pstmt.executeQuery();//���²�ѯ��ԭ����rs.nextʱ���ƫ�ƺ󣬶�����¼��
							rowSet.populate(rs);
                            goods.setRowSet(rowSet);
							System.out.println("4�����Ʒ�Ѿ������ݿ��л�ȡ��ֵ");
							request.getRequestDispatcher("/jsp/browse/showGoods.jsp").forward(request, response);
						}else 
                        {
                                out.print("<br><br><br><center>");
                                out.print("<font color=green> ������Ʒ </font>");
                                out.print("<a href=/shopping/dao/GoodsDao?key=4><font color=red size=6>������ҳ</font></a>");
                                out.print("</center>");     
                            }
					} catch (SQLException e)
					{
						e.printStackTrace();
						response.sendRedirect("shopping/jsp/browse/showGoods.jsp");
					}finally
							{
								DbClose.allClose(pstmt, rs, conn);
							}
					break;
			default:
				break;
		}
	}
}
