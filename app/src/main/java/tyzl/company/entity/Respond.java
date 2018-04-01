package tyzl.company.entity;

import java.util.List;


/**
 * "Error: could not open `C:\\Program Files (x86)\\Java\\jre7\\lib\\i386\\jvm.cfg'\r"
  */

public class Respond<T>  {
    private String error;
    private String status;
    private String date;
    private List<T> results;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
