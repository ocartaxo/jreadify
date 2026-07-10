#!/usr/bin/env bash
set -euo pipefail
java -cp "libs/*" jreadify.cli.Main "$@"