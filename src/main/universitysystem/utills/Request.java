package universitysystem.utills;

import universitysystem.entity.User;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public abstract class Request extends Mail implements Serializable
{
	private RequestStatus status;

	{
		status = RequestStatus.SHIPPED;
	}
	public Request() {
		super();
	}

	public Request(User sender) {
		super(sender);
	}

	public RequestStatus getStatus() {
		return status;	
	}
	
	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Request request = (Request) o;
		return status == request.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), status);
	}

	public String toString() {
        return super.toString() + "\nStatus: " + status;
	}
	
}


