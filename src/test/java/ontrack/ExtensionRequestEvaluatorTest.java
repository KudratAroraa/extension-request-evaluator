package ontrack;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

// Unit tests for ExtensionRequestEvaluator using TDD
public class ExtensionRequestEvaluatorTest {

    // Creates one valid PASS extension request for reuse in tests
    private ExtensionRequest createValidPassRequest() {
        return new ExtensionRequest(
                "225145633",
                "5.2P",
                TargetGrade.PASS,
                LocalDateTime.of(2026, 5, 20, 23, 59),
                LocalDateTime.of(2026, 5, 19, 10, 0),
                LocalDateTime.of(2026, 5, 22, 23, 59),
                "Medical issue affected my preparation",
                false,
                false,
                false,
                0
        );
    }

    // Tests that a valid PASS extension request should be accepted
    @Test
    public void testValidPassExtensionRequestAccepted() {
        ExtensionRequest request = createValidPassRequest();

        ExtensionRequestResult result = ExtensionRequestEvaluator.evaluate(request);

        Assert.assertTrue(result.isApprovedForSubmission());
        Assert.assertEquals("Extension request accepted", result.getMessage());
    }

    // Tests that a null student ID should be rejected
    @Test
    public void testNullStudentIdRejected() {
        ExtensionRequest request = new ExtensionRequest(
                null,
                "5.2P",
                TargetGrade.PASS,
                LocalDateTime.of(2026, 5, 20, 23, 59),
                LocalDateTime.of(2026, 5, 19, 10, 0),
                LocalDateTime.of(2026, 5, 22, 23, 59),
                "Medical issue affected my preparation",
                false,
                false,
                false,
                0
        );

        ExtensionRequestResult result = ExtensionRequestEvaluator.evaluate(request);

        Assert.assertFalse(result.isApprovedForSubmission());
        Assert.assertEquals("Student ID is required", result.getMessage());
    }

    // Tests that an empty student ID should be rejected
    @Test
    public void testEmptyStudentIdRejected() {
        ExtensionRequest request = new ExtensionRequest(
                "",
                "5.2P",
                TargetGrade.PASS,
                LocalDateTime.of(2026, 5, 20, 23, 59),
                LocalDateTime.of(2026, 5, 19, 10, 0),
                LocalDateTime.of(2026, 5, 22, 23, 59),
                "Medical issue affected my preparation",
                false,
                false,
                false,
                0
        );

        ExtensionRequestResult result = ExtensionRequestEvaluator.evaluate(request);

        Assert.assertFalse(result.isApprovedForSubmission());
        Assert.assertEquals("Student ID is required", result.getMessage());
    }

    // Tests that a null task code should be rejected
    @Test
    public void testNullTaskCodeRejected() {
        ExtensionRequest request = new ExtensionRequest(
                "225145633",
                null,
                TargetGrade.PASS,
                LocalDateTime.of(2026, 5, 20, 23, 59),
                LocalDateTime.of(2026, 5, 19, 10, 0),
                LocalDateTime.of(2026, 5, 22, 23, 59),
                "Medical issue affected my preparation",
                false,
                false,
                false,
                0
        );

        ExtensionRequestResult result = ExtensionRequestEvaluator.evaluate(request);

        Assert.assertFalse(result.isApprovedForSubmission());
        Assert.assertEquals("Task code is required", result.getMessage());
    }

    // Tests that an empty task code should be rejected
    @Test
    public void testEmptyTaskCodeRejected() {
        ExtensionRequest request = new ExtensionRequest(
                "225145633",
                "",
                TargetGrade.PASS,
                LocalDateTime.of(2026, 5, 20, 23, 59),
                LocalDateTime.of(2026, 5, 19, 10, 0),
                LocalDateTime.of(2026, 5, 22, 23, 59),
                "Medical issue affected my preparation",
                false,
                false,
                false,
                0
        );

        ExtensionRequestResult result = ExtensionRequestEvaluator.evaluate(request);

        Assert.assertFalse(result.isApprovedForSubmission());
        Assert.assertEquals("Task code is required", result.getMessage());
    }

    // Tests that a null reason should be rejected
    @Test
    public void testNullReasonRejected() {
        ExtensionRequest request = new ExtensionRequest(
                "225145633",
                "5.2P",
                TargetGrade.PASS,
                LocalDateTime.of(2026, 5, 20, 23, 59),
                LocalDateTime.of(2026, 5, 19, 10, 0),
                LocalDateTime.of(2026, 5, 22, 23, 59),
                null,
                false,
                false,
                false,
                0
        );

        ExtensionRequestResult result = ExtensionRequestEvaluator.evaluate(request);

        Assert.assertFalse(result.isApprovedForSubmission());
        Assert.assertEquals("Reason is required", result.getMessage());
    }

    // Tests that an empty reason should be rejected
    @Test
    public void testEmptyReasonRejected() {
        ExtensionRequest request = new ExtensionRequest(
                "225145633",
                "5.2P",
                TargetGrade.PASS,
                LocalDateTime.of(2026, 5, 20, 23, 59),
                LocalDateTime.of(2026, 5, 19, 10, 0),
                LocalDateTime.of(2026, 5, 22, 23, 59),
                "",
                false,
                false,
                false,
                0
        );

        ExtensionRequestResult result = ExtensionRequestEvaluator.evaluate(request);

        Assert.assertFalse(result.isApprovedForSubmission());
        Assert.assertEquals("Reason is required", result.getMessage());
    }

    // Tests that a request submitted after the task deadline should be rejected
    @Test
    public void testLateRequestRejected() {
        ExtensionRequest request = new ExtensionRequest(
                "225145633",
                "5.2P",
                TargetGrade.PASS,
                LocalDateTime.of(2026, 5, 20, 23, 59),
                LocalDateTime.of(2026, 5, 21, 10, 0),
                LocalDateTime.of(2026, 5, 22, 23, 59),
                "Medical issue affected my preparation",
                false,
                false,
                false,
                0
        );

        ExtensionRequestResult result = ExtensionRequestEvaluator.evaluate(request);

        Assert.assertFalse(result.isApprovedForSubmission());
        Assert.assertEquals("Extension request must be submitted on or before the task deadline", result.getMessage());
    }

    // Tests that a requested extension date on the same deadline should be rejected
    @Test
    public void testRequestedDateEqualToDeadlineRejected() {
        ExtensionRequest request = new ExtensionRequest(
                "225145633",
                "5.2P",
                TargetGrade.PASS,
                LocalDateTime.of(2026, 5, 20, 23, 59),
                LocalDateTime.of(2026, 5, 19, 10, 0),
                LocalDateTime.of(2026, 5, 20, 23, 59),
                "Medical issue affected my preparation",
                false,
                false,
                false,
                0
        );

        ExtensionRequestResult result = ExtensionRequestEvaluator.evaluate(request);

        Assert.assertFalse(result.isApprovedForSubmission());
        Assert.assertEquals("Requested extension date must be after the original task deadline", result.getMessage());
    }

    // Tests that a request for more than 7 days extension should be rejected
    @Test
    public void testRequestedExtensionMoreThanSevenDaysRejected() {
        ExtensionRequest request = new ExtensionRequest(
                "225145633",
                "5.2P",
                TargetGrade.PASS,
                LocalDateTime.of(2026, 5, 20, 23, 59),
                LocalDateTime.of(2026, 5, 19, 10, 0),
                LocalDateTime.of(2026, 5, 29, 23, 59),
                "Medical issue affected my preparation",
                true,
                false,
                false,
                0
        );

        ExtensionRequestResult result = ExtensionRequestEvaluator.evaluate(request);

        Assert.assertFalse(result.isApprovedForSubmission());
        Assert.assertEquals("Requested extension exceeds maximum allowed duration", result.getMessage());
    }

    // Tests that evidence is required when extension requested is more than 3 days
    @Test
    public void testEvidenceRequiredForMoreThanThreeDaysRejected() {
        ExtensionRequest request = new ExtensionRequest(
                "225145633",
                "5.2P",
                TargetGrade.PASS,
                LocalDateTime.of(2026, 5, 20, 23, 59),
                LocalDateTime.of(2026, 5, 19, 10, 0),
                LocalDateTime.of(2026, 5, 25, 23, 59),
                "Medical issue affected my preparation",
                false,
                false,
                false,
                0
        );

        ExtensionRequestResult result = ExtensionRequestEvaluator.evaluate(request);

        Assert.assertFalse(result.isApprovedForSubmission());
        Assert.assertEquals("Supporting evidence is required for extensions longer than 3 days", result.getMessage());
    }

    // Tests that exactly 3 extra days without evidence should still be accepted
    @Test
    public void testExactlyThreeDaysWithoutEvidenceAccepted() {
        ExtensionRequest request = new ExtensionRequest(
                "225145633",
                "5.2P",
                TargetGrade.PASS,
                LocalDateTime.of(2026, 5, 20, 23, 59),
                LocalDateTime.of(2026, 5, 19, 10, 0),
                LocalDateTime.of(2026, 5, 23, 23, 59),
                "Medical issue affected my preparation",
                false,
                false,
                false,
                0
        );

        ExtensionRequestResult result = ExtensionRequestEvaluator.evaluate(request);

        Assert.assertTrue(result.isApprovedForSubmission());
        Assert.assertEquals("Extension request accepted", result.getMessage());
    }
}