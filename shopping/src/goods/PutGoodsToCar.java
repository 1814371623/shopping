package goods;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Login;

/**
 * �����Ʒ�����ﳵ
 * @author felixcui42
 *
 */
public class PutGoodsToCar extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public PutGoodsToCar() {
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String goods = null;
        goods = request.getParameter("GoodsCar");
        
        if (goods==null) {
            response.sendRedirect("/shopping/index.jsp");
        }else {
                String[] details = null;
                details = goods.split(",");//�����ڴ������Ϣ�����ݿ�һ�¡�
                
                //����Ʒ��Ϣ�Ž�ģ����
                HttpSession session = request.getSession(true);
                Login loginBean = (Login)session.getAttribute("loginBean");
                LinkedList<String> car = null;
                car = loginBean.getCar();
                car.add(goods);
                loginBean.setCar(car);
                
                backNews(request, response, details[1]);
            }
     
    }
    
    
/**
 * �����û���Ϣ
 * �����Ʒ�����ﳵ�ɹ��󣬷�����Ϣ   
 * @param request
 * @param response
 * @param goodsName
 * @throws IOException
 */
    private void backNews(HttpServletRequest request, HttpServletResponse response, String goodsName) 
    		throws IOException {
        PrintWriter out = response.getWriter();
        out.print("<br><br><br>");
        out.print("<center><font size=5 color=red><B>"+goodsName+"</B></font>&nbsp;�ѳɹ���ӹ��ﳵ");
        out.print("<br><br><br>");
        out.print("<a href=/shopping/jsp/browse/showGoods.jsp>���ؼ�������</a>");
        out.print("&nbsp;or&nbsp;");
        out.print("<a href=/shopping/jsp/shoppingCar/lookShoppingCar.jsp>�鿴���ﳵ</a></center>");
    }

    public void init()
        throws ServletException {
    }
}
