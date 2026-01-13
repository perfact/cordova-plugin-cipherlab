#!/usr/bin/env sh
set -e

ROOT_DIR="$(cd "$(dirname "$0")/../.." && pwd)"
OUT_DIR="$ROOT_DIR/tests/jvm/build"

mkdir -p "$OUT_DIR"

javac -d "$OUT_DIR" \
  "$ROOT_DIR/src/android/CipherlabScannerLogic.java" \
  "$ROOT_DIR/tests/jvm/CipherlabScannerLogicTest.java"

java -ea -cp "$OUT_DIR" de.perfact.cordova.cipherlab.CipherlabScannerLogicTest
