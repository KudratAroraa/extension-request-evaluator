package ontrack;

import java.time.Duration;

// Evaluates whether an extension request is valid
public class ExtensionRequestEvaluator {

    // Checks the request against all validation and policy rules
    public static ExtensionRequestResult evaluate(ExtensionRequest request) {

        // Rejects the request if student ID is missing
        if (request.getStudentId() == null || request.getStudentId().trim().isEmpty()) {
            return new ExtensionRequestResult(false, "Student ID is required");
        }

        // Rejects the request if task code is missing
        if (request.getTaskCode() == null || request.getTaskCode().trim().isEmpty()) {
            return new ExtensionRequestResult(false, "Task code is required");
        }

        // Rejects the request if target grade is missing
        if (request.getTargetGrade() == null) {
            return new ExtensionRequestResult(false, "Target grade is required");
        }

        // Rejects the request if reason is missing
        if (request.getReason() == null || request.getReason().trim().isEmpty()) {
            return new ExtensionRequestResult(false, "Reason is required");
        }

        // Rejects the request if the task is already completed
        if (request.isTaskAlreadyCompleted()) {
            return new ExtensionRequestResult(false, "Task is already completed");
        }

        // Rejects the request if another request is already pending
        if (request.hasExistingPendingRequest()) {
            return new ExtensionRequestResult(false, "An extension request is already pending for this task");
        }

        // Rejects the request if it was submitted after the deadline
        if (request.getRequestDateTime().isAfter(request.getTaskDeadline())) {
            return new ExtensionRequestResult(false, "Extension request must be submitted on or before the task deadline");
        }

        // Rejects the request if the requested extension date is not after the deadline
        if (!request.getRequestedExtensionUntil().isAfter(request.getTaskDeadline())) {
            return new ExtensionRequestResult(false, "Requested extension date must be after the original task deadline");
        }

        // Calculates how many days of extension are being requested
        long requestedDays = Duration.between(
                request.getTaskDeadline(),
                request.getRequestedExtensionUntil()).toDays();

        // Rejects the request if the extension exceeds 7 days
        if (requestedDays > 7) {
            return new ExtensionRequestResult(false, "Requested extension exceeds maximum allowed duration");
        }

        // Rejects the request if evidence is missing for requests longer than 3 days
        if (requestedDays > 3 && !request.hasEvidence()) {
            return new ExtensionRequestResult(false, "Supporting evidence is required for extensions longer than 3 days");
        }

        // Rejects the request if the selected target grade is not allowed for the task code
        if (!isTaskCodeEligibleForTargetGrade(request.getTaskCode(), request.getTargetGrade())) {
            return new ExtensionRequestResult(false, "Selected target grade is not eligible for this task code");
        }

        // Rejects the request if maximum extension attempts have already been reached
        int maxAttempts = getMaxAttemptsForTargetGrade(request.getTargetGrade());
        if (request.getPreviousExtensionAttempts() >= maxAttempts) {
            return new ExtensionRequestResult(false, "Maximum extension attempts reached for the selected target grade");
        }

        // Accepts the request if all checks pass
        return new ExtensionRequestResult(true, "Extension request accepted");
    }

    // Returns the maximum number of allowed extension attempts for each target grade
    private static int getMaxAttemptsForTargetGrade(TargetGrade targetGrade) {
        switch (targetGrade) {
            case PASS:
                return 2;
            case CREDIT:
                return 2;
            case DISTINCTION:
                return 1;
            case HIGH_DISTINCTION:
                return 1;
            default:
                return 1;
        }
    }

    // Checks whether a task code is valid for the selected target grade
    private static boolean isTaskCodeEligibleForTargetGrade(String taskCode, TargetGrade targetGrade) {
        if (taskCode == null) {
            return false;
        }

        String upperTaskCode = taskCode.toUpperCase();

        switch (targetGrade) {
            case PASS:
                return upperTaskCode.endsWith("P");

            case CREDIT:
                return upperTaskCode.endsWith("P") || upperTaskCode.endsWith("C");

            case DISTINCTION:
                return upperTaskCode.endsWith("P") || upperTaskCode.endsWith("C") || upperTaskCode.endsWith("D");

            case HIGH_DISTINCTION:
                return upperTaskCode.endsWith("P")
                        || upperTaskCode.endsWith("C")
                        || upperTaskCode.endsWith("D")
                        || upperTaskCode.endsWith("HD");

            default:
                return false;
        }
    }
}