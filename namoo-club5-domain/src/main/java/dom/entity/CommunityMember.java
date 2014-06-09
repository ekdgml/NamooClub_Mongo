package dom.entity;


public class CommunityMember {

	private int communityNo;
	private SocialPerson rolePerson;

	//--------------------------------------------------------------------------
	// constructor
	
	public CommunityMember() {
		//
	}
	
	/**
	 * 
	 * @param rolePerson
	 */
	public CommunityMember(int communityNo, SocialPerson rolePerson){
		//
		this.communityNo = communityNo;
		this.rolePerson = rolePerson;
	}
	
	//--------------------------------------------------------------------------
	// getter/setter
	
	public String getName() {
		return rolePerson.getName();
	}
	
	public String getEmail() {
		return rolePerson.getEmail();
	}

	public int getCommunityNo() {
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
}
