package ontrack;

import java.time.LocalDateTime;

// Stores all input details for a single extension request
public class ExtensionRequest {

    private String studentId;
    private String taskCode;
    private TargetGrade targetGrade;
    private LocalDateTime taskDeadline;
    private LocalDateTime requestDateTime;
    private LocalDateTime requestedExtensionUntil;
    private String reason;
    private boolean hasEvidence;
    private boolean taskAlreadyCompleted;
    private boolean existingPendingRequest;
    private int previousExtensionAttempts;

    // Creates a new extension request object
    public ExtensionRequest(
            String studentId,
            String taskCode,
            TargetGrade targetGrade,
            LocalDateTime taskDeadline,
            LocalDateTime requestDateTime,
            LocalDateTime requestedExtensionUntil,
            String reason,
            boolean hasEvidence,
            boolean taskAlreadyCompleted,
            boolean existingPendingRequest,
            int previousExtensionAttempts) {
        this.studentId = studentId;
        this.taskCode = taskCode;
        this.targetGrade = targetGrade;
        this.taskDeadline = taskDeadline;
        this.requestDateTime = requestDateTime;
        this.requestedExtensionUntil = requestedExtensionUntil;
        this.reason = reason;
        this.hasEvidence = hasEvidence;
        this.taskAlreadyCompleted = taskAlreadyCompleted;
        this.existingPendingRequest = existingPendingRequest;
        this.previousExtensionAttempts = previousExtensionAttempts;
    }

    // Returns the student ID
    public String getStudentId() {
        return studentId;
    }

    // Returns the task code
    public String getTaskCode() {
        return taskCode;
    }

    // Returns the target grade
    public TargetGrade getTargetGrade() {
        return targetGrade;
    }

    // Returns the original task deadline
    public LocalDateTime getTaskDeadline() {
        return taskDeadline;
    }

    // Returns the date and time when the request was submitted
    public LocalDateTime getRequestDateTime() {
        return requestDateTime;
    }

    // Returns the requested extension-until date and time
    public LocalDateTime getRequestedExtensionUntil() {
        return requestedExtensionUntil;
    }

    // Returns the reason entered by the student
    public String getReason() {
        return reason;
    }

    // Returns whether supporting evidence was provided
    public boolean hasEvidence() {
        return hasEvidence;
    }

    // Returns whether the task is already completed
    public boolean isTaskAlreadyCompleted() {
        return taskAlreadyCompleted;
    }

    // Returns whether another pending request already exists
    public boolean hasExistingPendingRequest() {
        return existingPendingRequest;
    }

    // Returns the number of previous extension attempts
    public int getPreviousExtensionAttempts() {
        return previousExtensionAttempts;
    }
}