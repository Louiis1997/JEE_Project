package com.esgi.algoBattle.compiler.infrastructure.models;

public class CodexExecuteResponse {
    public boolean success; // ": true,
    public String timestamp; // ": "2022-05-26T19:59:08.014Z",
    public String output; // ": "Hello\nWorld\n",
    public String error; // ": "  File \"/app/codes/6d775729-045f-4569-8117-ef129a1e612b.py\", line 1\n    print(123)z\n              ^\nSyntaxError: invalid syntax\n",
    public String language; // ": "java",
    public String version; // ": "11.0.15"

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
