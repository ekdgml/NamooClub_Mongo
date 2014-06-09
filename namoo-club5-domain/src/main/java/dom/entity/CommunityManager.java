package dom.entity;

public class CommunityManager {
	
	private int communityNo;
	private SocialPerson rolePerson;

	//--------------------------------------------------------------------------
	// constructor
	
	public CommunityManager() {
		//
	}
	
	public CommunityManager(int communityNo, SocialPerson rolePerson){
		//
		this.communityNo = communityNo;
		this.rolePerson = rolePerson;
	}
	
	//--------------------------------------------------------------------------
	// getter/setter
	
	public int getCommunityNo() {
		//
		return communityNo;
	}

	public void setCommunityNo(int communityNo) {
		this.communityNo = communityNo;
	}

	public SocialPerson getRolePerson() {
		return rolePerson;
	}

	public void setRolePerson(SocialPerson rolePerson) {
		this.rolePerson = rolePerson;
	}
	
	public String getEmail() {
		return rolePerson.getEmail();
	}
	
	public String getName() {
		return rolePerson.getName();
	}
}