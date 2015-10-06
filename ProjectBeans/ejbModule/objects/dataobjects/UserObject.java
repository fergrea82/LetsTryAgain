package objects.dataobjects;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="user")
public class UserObject {
	
	@Id
	public int userID;
	public String userName;
	
	@OneToMany(mappedBy="userObj", fetch=FetchType.EAGER)
	private List<TradeHistoryObject> tradesPerUser;
	
	public UserObject(int userID, String userName){
		this.userID=userID;
		this.userName=userName;
	}
	
	public UserObject() {
		//default constructor
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
