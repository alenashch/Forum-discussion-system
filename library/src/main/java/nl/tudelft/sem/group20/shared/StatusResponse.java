package nl.tudelft.sem.group20.shared;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

@Embeddable
@MappedSuperclass
public class StatusResponse {
    private Status status;
    private String message;

    public StatusResponse() {

    }

    /**
     * Creates a StatusResponse object. Only for embedding.
     *
     * @param status Status of action.
     * @param message Message of status.
     */
    public StatusResponse(Status status, String message) {
        this.status = status;
        this.message = message;
    }



    public enum Status {
        success, fail
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusResponse)) {
            return false;
        }
        StatusResponse that = (StatusResponse) o;
        return getStatus() == that.getStatus()
                && getMessage().equals(that.getMessage());
    }

    @Override
    public String toString() {
        return "StatusResponse{"
                + "status=" + status
                + ", message='" + message + '\''
                + '}';
    }
}
