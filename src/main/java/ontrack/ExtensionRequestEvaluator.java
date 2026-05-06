package ontrack;

// Evaluates whether an extension request is valid
public class ExtensionRequestEvaluator {

    // Checks the request against the current validation rules
    public static ExtensionRequestResult evaluate(ExtensionRequest request) {

        // Rejects the request if student ID is missing
        if (request.getStudentId() == null || request.getStudentId().trim().isEmpty()) {
            return new ExtensionRequestResult(false, "Student ID is required");
        }

        // Rejects the request if task code is missing
        if (request.getTaskCode() == null || request.getTaskCode().trim().isEmpty()) {
            return new ExtensionRequestResult(false, "Task code is required");
        }

        // Rejects the request if reason is missing
        if (request.getReason() == null || request.getReason().trim().isEmpty()) {
            return new ExtensionRequestResult(false, "Reason is required");
        }

        // Accepts the request if all current checks pass
        return new ExtensionRequestResult(true, "Extension request accepted");
    }
}