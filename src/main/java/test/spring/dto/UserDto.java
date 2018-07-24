package test.spring.dto;

public class UserDto {

	private String name;
	private String emailid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	@Override
	public String toString() {
		return "UserDto [name=" + name + ", emailid=" + emailid + "]";
	}
}
