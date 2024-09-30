package aplicacionprocesos;

public class ProcessModel {
    private String name;
    private int pid;

    public ProcessModel(String name, int pid) {
        this.name = name;
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public int getPid() {
        return pid;
    }
}
