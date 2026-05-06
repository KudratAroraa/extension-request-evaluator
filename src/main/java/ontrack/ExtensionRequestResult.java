package ontrack;

// Stores the result returned by the extension evaluator
public class ExtensionRequestResult {

    private boolean approvedForSubmission;
    private String message;

    // Creates a result object with status and message
    public ExtensionRequestResult(boolean approvedForSubmission, String message) {
        this.approvedForSubmission = approvedForSubmission;
        this.message = message;
    }

    // Returns whether the request is accepted for submission
    public boolean isApprovedForSubmission() {
        return approvedForSubmission;
    }

    // Returns the evaluation message
    public String getMessage() {
        return message;
    }
}