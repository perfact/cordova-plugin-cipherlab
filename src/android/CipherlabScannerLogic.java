package de.perfact.cordova.cipherlab;

public final class CipherlabScannerLogic {

    public static final class ActionResult {
        public final boolean returnValue;
        public final boolean success;
        public final String errorMessage;

        private ActionResult(boolean returnValue, boolean success, String errorMessage) {
            this.returnValue = returnValue;
            this.success = success;
            this.errorMessage = errorMessage;
        }

        public static ActionResult success(boolean returnValue) {
            return new ActionResult(returnValue, true, null);
        }

        public static ActionResult error(boolean returnValue, String errorMessage) {
            return new ActionResult(returnValue, false, errorMessage);
        }
    }

    public interface ReaderManagerFacade {
        SetActiveResult setActive(boolean enable);
    }

    public static final class SetActiveResult {
        public final boolean ok;
        public final String message;

        private SetActiveResult(boolean ok, String message) {
            this.ok = ok;
            this.message = message;
        }

        public static SetActiveResult ok() {
            return new SetActiveResult(true, null);
        }

        public static SetActiveResult error(String message) {
            return new SetActiveResult(false, message);
        }
    }

    public ActionResult setScannerEnabled(
            boolean enable,
            boolean isReaderServiceConnected,
            ReaderManagerFacade readerManager) {
        if (!isReaderServiceConnected) {
            return ActionResult.error(false, "Service not connected");
        }
        if (readerManager == null) {
            return ActionResult.error(true, "ReaderManager not initialized");
        }
        SetActiveResult result = readerManager.setActive(enable);
        if (result != null && result.ok) {
            return ActionResult.success(true);
        }
        String detail = result == null ? "null" : result.message;
        return ActionResult.error(true, "SetActive failed: " + detail);
    }
}
