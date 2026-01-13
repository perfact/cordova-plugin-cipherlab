package de.perfact.cordova.cipherlab;

public final class CipherlabScannerLogicTest {

    public static void main(String[] args) {
        testServiceNotConnected();
        testReaderManagerMissing();
        testSetActiveSuccess();
        testSetActiveFailure();
        System.out.println("OK");
    }

    private static void testServiceNotConnected() {
        CipherlabScannerLogic logic = new CipherlabScannerLogic();
        CipherlabScannerLogic.ActionResult result =
                logic.setScannerEnabled(true, false, new FakeReaderManager(true, "S_OK"));
        assert !result.success : "expected failure when service is disconnected";
        assert !result.returnValue : "expected execute to return false when service is disconnected";
        assert "Service not connected".equals(result.errorMessage) : "unexpected error message";
    }

    private static void testReaderManagerMissing() {
        CipherlabScannerLogic logic = new CipherlabScannerLogic();
        CipherlabScannerLogic.ActionResult result =
                logic.setScannerEnabled(true, true, null);
        assert !result.success : "expected failure when reader manager is null";
        assert result.returnValue : "expected execute to return true when action was handled";
        assert "ReaderManager not initialized".equals(result.errorMessage) : "unexpected error message";
    }

    private static void testSetActiveSuccess() {
        CipherlabScannerLogic logic = new CipherlabScannerLogic();
        CipherlabScannerLogic.ActionResult result =
                logic.setScannerEnabled(true, true, new FakeReaderManager(true, "S_OK"));
        assert result.success : "expected success when SetActive returns ok";
        assert result.returnValue : "expected execute to return true for handled action";
        assert result.errorMessage == null : "expected no error message";
    }

    private static void testSetActiveFailure() {
        CipherlabScannerLogic logic = new CipherlabScannerLogic();
        CipherlabScannerLogic.ActionResult result =
                logic.setScannerEnabled(true, true, new FakeReaderManager(false, "E_FAIL"));
        assert !result.success : "expected failure when SetActive returns error";
        assert result.returnValue : "expected execute to return true for handled action";
        assert "SetActive failed: E_FAIL".equals(result.errorMessage) : "unexpected error message";
    }

    private static final class FakeReaderManager implements CipherlabScannerLogic.ReaderManagerFacade {
        private final boolean ok;
        private final String message;

        private FakeReaderManager(boolean ok, String message) {
            this.ok = ok;
            this.message = message;
        }

        @Override
        public CipherlabScannerLogic.SetActiveResult setActive(boolean enable) {
            if (ok) {
                return CipherlabScannerLogic.SetActiveResult.ok();
            }
            return CipherlabScannerLogic.SetActiveResult.error(message);
        }
    }
}
