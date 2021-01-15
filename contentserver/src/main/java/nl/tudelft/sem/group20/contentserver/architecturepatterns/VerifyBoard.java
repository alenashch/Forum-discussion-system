package nl.tudelft.sem.group20.contentserver.architecturepatterns;

import exceptions.BoardIsLockedException;
import exceptions.BoardNotFoundException;
import nl.tudelft.sem.group20.shared.IsLockedResponse;
import nl.tudelft.sem.group20.shared.StatusResponse;

public class VerifyBoard extends BaseHandler {

    /**
     * Verifies if board exists and not locked.
     *
     * @param checkRequest info to check
     * @return if board exists and not locked
     */
    @Override
    public boolean handle(CheckRequest checkRequest) throws Exception {
        IsLockedResponse response = checkRequest.restTemplate.getForObject(
                "http://board-server/board/checklocked/" + checkRequest.boardId,
                IsLockedResponse.class);

        if (response == null || response.getStatus() == StatusResponse.Status.fail) {
            throw new BoardNotFoundException();
        }

        if (response.getStatus() == StatusResponse.Status.success && response.isLocked()) {
            throw new BoardIsLockedException();
        }

        return super.handle(checkRequest);
    }

}
