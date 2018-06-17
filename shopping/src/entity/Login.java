package entity;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * �û���¼��
 * @author felixcui42
 *
 */
public class Login implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username = "";
    private String backNews = "δ��¼";
    private LinkedList<String> car = null;   //���ﳵcar
    
    public Login() {
		car = new LinkedList<String>();
	}
	
	public LinkedList<String> getCar() {
		return car;
	}
	public void setCar(LinkedList<String> car) {
		this.car = car;
	}
	public String getUsername() {
	    if (username.trim()=="") {
            return "userNull";
        }
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBackNews() {
		return backNews;
	}
	public void setBackNews(String backNews) {
		this.backNews = backNews;
	}

}
