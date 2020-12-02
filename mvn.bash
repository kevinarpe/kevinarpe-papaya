#!/usr/bin/env bash

# Exit immediately if a command exits with a non-zero status.
set -e
# Treat unset variables as an error when substituting.
set -u
# The return value of a pipeline is the status of
# the last command to exit with a non-zero status,
# or zero if no command exited with a non-zero status
set -o pipefail
# Print commands and their arguments as they are executed.
# set -x

main()
{
    local this_script_abs_dir_path="$(dirname "$(readlink -f "$0")")"
    local java_home="$(readlink -f "$this_script_abs_dir_path/../../jdk-11.0.2")"

    echo_and_run_cmd \
        export JAVA_HOME="$JAVA8_HOME"

    echo_and_run_cmd \
        export PATH="$JAVA_HOME/bin:$PATH"

    echo_and_run_cmd \
        which java

    echo_and_run_cmd \
        java -version

    echo_and_run_cmd \
        which mvn

    echo_and_run_cmd \
        mvn -version

    echo_and_run_cmd \
        mvn "$@"
}

echo_cmd()
{
    echo
    echo '$' "$@"
}

echo_and_run_cmd()
{
    echo_cmd "$@"
    "$@"
}

main "$@"

