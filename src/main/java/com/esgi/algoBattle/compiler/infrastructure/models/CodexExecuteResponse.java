package com.esgi.algoBattle.compiler.infrastructure.models;

public class CodexExecuteResponse {
    public final boolean success; // ": true,
    public final String timestamp; // ": "2022-05-26T19:59:08.014Z",
    public final String output; // ": "Hello\nWorld\n",
    public final String error; // ": "  File \"/app/codes/6d775729-045f-4569-8117-ef129a1e612b.py\", line 1\n    print(123)z\n              ^\nSyntaxError: invalid syntax\n",
    public final String language; // ": "java",
    public final String version; // ": "11.0.15"

    public CodexExecuteResponse() {
        this.success = false;
        this.timestamp = null;
        this.output = null;
        this.error = null;
        this.language = null;
        this.version = null;
    }

    @Override
    public String toString() {
        return "CodexExecuteResponse{" +
                "success=" + success +
                ", timestamp='" + timestamp + '\'' +
                ", output='" + output + '\'' +
                ", error='" + error + '\'' +
                ", language='" + language + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
