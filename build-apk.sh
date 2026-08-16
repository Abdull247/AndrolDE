#!/bin/bash
# ============================================================
# AndroIDE - build-apk.sh
# Builds the app and places the APK in ./build/ (replaces old).
# Usage:  chmod +x build-apk.sh && ./build-apk.sh
# ============================================================
set -e

# --- Environment defaults (override by exporting before running) ---
export JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-17-openjdk-amd64}"
export ANDROID_HOME="${ANDROID_HOME:-/opt/android-sdk}"

GRADLE_CMD="${GRADLE_CMD:-}"
if [ -z "$GRADLE_CMD" ]; then
    if command -v gradle >/dev/null 2>&1; then
        GRADLE_CMD="gradle"
    elif [ -x "/opt/gradle/gradle-8.13/bin/gradle" ]; then
        GRADLE_CMD="/opt/gradle/gradle-8.13/bin/gradle"
    else
        echo "ERROR: gradle not found. Set GRADLE_CMD or install Gradle." >&2
        exit 1
    fi
fi

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

OUT_DIR="$SCRIPT_DIR/build"
APK_SRC="$SCRIPT_DIR/app/build/outputs/apk/debug/app-debug.apk"

echo "=============================================="
echo "  AndroIDE APK Builder"
echo "  JAVA_HOME  : $JAVA_HOME"
echo "  ANDROID_HOME: $ANDROID_HOME"
echo "  GRADLE     : $GRADLE_CMD"
echo "=============================================="

# --- Run the build (logs stream to terminal) ---
"$GRADLE_CMD" :app:assembleDebug --no-daemon --console=plain

if [ ! -f "$APK_SRC" ]; then
    echo "ERROR: APK not found at $APK_SRC" >&2
    exit 1
fi

# --- Place APK into build/ replacing the old one ---
mkdir -p "$OUT_DIR"
cp -f "$APK_SRC" "$OUT_DIR/app-debug.apk"

echo ""
echo "=============================================="
echo "  Build complete!"
echo "  APK: $OUT_DIR/app-debug.apk"
echo "=============================================="
