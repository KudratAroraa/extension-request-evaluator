package ontrack;

import java.time.Duration;

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

        // Rejects the request if it was submitted after the deadline
        if (request.getRequestDateTime().isAfter(request.getTaskDeadline())) {
            return new ExtensionRequestResult(false, "Extension request must be submitted on or before the task deadline");
        }

        // Rejects the request if the requested extension date is not after the deadline
        if (!request.getRequestedExtensionUntil().isAfter(request.getTaskDeadline())) {
            return new ExtensionRequestResult(false, "Requested extension date must be after the original task deadline");
        }

        // Calculates how many days of extension are being requested
        long requestedDays = Duration.between(request.getTaskDeadline(), request.getRequestedExtensionUntil()).toDays();

        // Rejects the request if the extension exceeds 7 days
        if (requestedDays > 7) {
            return new ExtensionRequestResult(false, "Requested extension exceeds maximum allowed duration");
        }

        // Rejects the request if evidence is missing for requests longer than 3 days
        if (requestedDays > 3 && !request.hasEvidence()) {
            return new ExtensionRequestResult(false, "Supporting evidence is required for extensions longer than 3 days");
        }

        // Accepts the request if all current checks pass
        return new ExtensionRequestResult(true, "Extension request accepted");
    }
}