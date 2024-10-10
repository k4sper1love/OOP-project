package universitysystem.utills;

import universitysystem.entity.User;

import java.io.Serializable;
import java.util.*;

public class EmployeeRequest extends Request implements Serializable
{
	private String request;
	private boolean signed;

	{
		signed = false;
	}
	public EmployeeRequest() {
		super();
	}

	public EmployeeRequest(User sender, String request) {
		super(sender);
		this.request = request;
	}

	public String getRequest() {
		return request;	
	}

	public boolean getSigned() {
		return signed;	
	}
	
	public void setSigned(boolean signed) {
		this.signed = signed;
	}
	
	public void setRequest(String request) {
		this.request = request;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		EmployeeRequest that = (EmployeeRequest) o;
		return signed == that.signed && Objects.equals(request, that.request);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), request, signed);
	}

	@Override
	public String toString() {
		return super.toString() + ", signed: " + signed + "\nRequest: " + request;
	}
}



