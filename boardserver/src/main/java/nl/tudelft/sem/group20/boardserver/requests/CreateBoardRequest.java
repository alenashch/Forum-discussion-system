package nl.tudelft.sem.group20.boardserver.requests;

/**
 * Class to handle requests for creating a board.
 * Not all attributes of a board should be used for requests.
 */
public class CreateBoardRequest {
    private String name;
    private String description;

    public CreateBoardRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
